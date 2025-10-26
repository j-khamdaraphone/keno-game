import javafx.animation.PauseTransition;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

public class GamePlayScreen {
    private BorderPane root;
    private GridPane grid;
    private Map<Integer, Button> numberButtons;
    private Button startDraw, goBack;
    private Set<Integer> selectedNumbers;
    private int spotsToPlay;
    private int drawsToPlay;
    private Text scoreLabel, drawingLabel;
    private boolean locked = false;
    private KenoGame game;

    public GamePlayScreen(int spots, int draws) {
        this.spotsToPlay = spots;
        this.drawsToPlay = draws;
        this.selectedNumbers = new HashSet<>();
        this.numberButtons = new HashMap<>();
        this.game = new KenoGame(spots, draws);

        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #4B0082, #8A2BE2, #FF69B4);");

        drawingLabel = new Text("DRAWING #1");
        drawingLabel.setFont(Font.font("Arial", 20));
        drawingLabel.setFill(Color.WHITE);
        drawingLabel.setStyle("-fx-font-weight: bold;");

        scoreLabel = new Text("Score: 0");
        scoreLabel.setFont(Font.font("Arial", 16));
        scoreLabel.setFill(Color.WHITE);
        scoreLabel.setStyle("-fx-font-weight: bold;");

        HBox topBox = new HBox(20);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        VBox leftBox = new VBox(drawingLabel);
        leftBox.setSpacing(5);
        topBox.getChildren().addAll(leftBox, spacer, scoreLabel);
        topBox.setPadding(new Insets(5));
        topBox.setAlignment(Pos.CENTER_LEFT);

        root.setTop(topBox);
        root.setCenter(setupGrid());
        root.setBottom(createControlButtons());
        root.setLeft(createScoreTable());
    }

    public BorderPane getRoot() {
        return root;
    }

    private GridPane setupGrid() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(6);
        grid.setVgap(6);
        grid.setPadding(new Insets(20));

        for (int i = 1; i <= 80; i++) {
            Button btn = new Button(String.valueOf(i));
            btn.setPrefSize(55, 55);
            btn.setFont(Font.font("Arial", 14));
            btn.setStyle("-fx-background-color: #2e3a4f; -fx-text-fill: white; -fx-background-radius: 8;");
            int num = i;
            btn.setOnAction(e -> handleNumberSelection(num));
            numberButtons.put(num, btn);
            grid.add(btn, (i - 1) % 10, (i - 1) / 10);
        }

        return grid;
    }

    private void handleNumberSelection(int number) {
        if (locked) return;

        Button btn = numberButtons.get(number);
        if (selectedNumbers.contains(number)) {
            selectedNumbers.remove(number);
            btn.setStyle("-fx-background-color: #2e3a4f; -fx-text-fill: white; -fx-background-radius: 8;");
        } else if (selectedNumbers.size() < spotsToPlay) {
            selectedNumbers.add(number);
            btn.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-weight: bold;");
        }

        game.selectNumbers(selectedNumbers);
    }

    private HBox createControlButtons() {
        HBox controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(20));

        startDraw = new Button("Start Draw");
        goBack = new Button("Go Back");

        styleButton(startDraw);
        styleButton(goBack);

        startDraw.setOnAction(e -> startDraws());

        controls.getChildren().addAll(startDraw, goBack);
        return controls;
    }

    private void styleButton(Button btn) {
        btn.setFont(Font.font("Arial", 15));
        btn.setPrefWidth(140);
        btn.setStyle("-fx-background-color: #34568B; -fx-text-fill: white; -fx-background-radius: 8;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #4A6FA5; -fx-text-fill: white; -fx-background-radius: 8;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #34568B; -fx-text-fill: white; -fx-background-radius: 8;"));
    }

    private VBox createScoreTable() {
        VBox table = new VBox(5);
        table.setPadding(new Insets(10));
        table.setStyle("-fx-background-color: #1a1a2e; -fx-border-color: white; -fx-border-width: 1;");

        // Header
        HBox header = new HBox(50);
        Label lblHits = new Label("HITS");
        Label lblWin = new Label("WIN");
        lblHits.setTextFill(Color.WHITE);
        lblWin.setTextFill(Color.WHITE);
        header.getChildren().addAll(lblHits, lblWin);
        table.getChildren().add(header);

        for (int i = 0; i <= spotsToPlay; i++) {
            HBox row = new HBox(50);
            Label lHits = new Label(String.valueOf(i));
            Label lWin = new Label(String.valueOf(game.calculateWin(i)));
            lHits.setTextFill(Color.web("#bbbbbb"));
            lWin.setTextFill(Color.web("#bbbbbb"));
            row.getChildren().addAll(lHits, lWin);
            table.getChildren().add(row);
        }

        return table;
    }

    private void startDraws() {
        if (selectedNumbers.size() != spotsToPlay) {
            System.out.println("Select exactly " + spotsToPlay + " numbers!");
            return;
        }

        locked = true;
        startDraw.setDisable(true);
        goBack.setDisable(true);

        performNextDraw(1);
    }

    private void performNextDraw(int drawNumber) {
        if (drawNumber > drawsToPlay) {
            startDraw.setDisable(false);
            goBack.setDisable(false);
            locked = false;
            return;
        }

        drawingLabel.setText("DRAWING #" + drawNumber);

        Set<Integer> drawResult = game.performDraw();
        int hits = game.countHits();

        for (Button btn : numberButtons.values()) {
            int num = Integer.parseInt(btn.getText());
            if (selectedNumbers.contains(num) && drawResult.contains(num)) {
                btn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
            } else if (drawResult.contains(num)) {
                btn.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
            } else if (selectedNumbers.contains(num)) {
                btn.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
            } else {
                btn.setStyle("-fx-background-color: #2e3a4f; -fx-text-fill: white; -fx-background-radius: 8;");
            }
        }

        updateScoreTable(hits);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> performNextDraw(drawNumber + 1));
        pause.play();
    }

    private void updateScoreTable(int hits) {
        VBox table = (VBox) root.getLeft();

        for (int i = 1; i < table.getChildren().size(); i++) {
            HBox row = (HBox) table.getChildren().get(i);
            row.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
            for (javafx.scene.Node node : row.getChildren()) {
                if (node instanceof Label) {
                    ((Label) node).setTextFill(Color.web("#bbbbbb"));
                }
            }
        }

        if (hits >= 0 && hits <= spotsToPlay) {
            int rowIndex = hits + 1; // skip header
            if (rowIndex < table.getChildren().size()) {
                HBox hitRow = (HBox) table.getChildren().get(rowIndex);
                hitRow.setStyle("-fx-border-color: gold; -fx-border-width: 2; -fx-background-color: transparent;");
                for (javafx.scene.Node node : hitRow.getChildren()) {
                    if (node instanceof Label) {
                        ((Label) node).setTextFill(Color.web("#dddd00"));
                    }
                }
            }
        }

        scoreLabel.setText("Score: " + game.getTotalScore());
    }

    public Button getGoBackButton() {
        return goBack;
    }
}
