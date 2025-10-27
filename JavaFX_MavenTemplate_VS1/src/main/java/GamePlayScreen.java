import javafx.animation.PauseTransition;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private KenoGame game;
    private Text drawingLabel, scoreLabel;
    private boolean locked = false;
    private Button randomPickButton;
    private String backgroundStyle = "-fx-background-color: linear-gradient(to right, #e76366, #8355eb);";


    public GamePlayScreen(int spots, int draws) {
        this.spotsToPlay = spots;
        this.drawsToPlay = draws;
        this.selectedNumbers = new HashSet<>();
        this.numberButtons = new HashMap<>();
        this.game = new KenoGame(spots, draws);

        root = new BorderPane();
        root.setStyle(backgroundStyle);


        Font gliker = Font.loadFont(getClass().getResourceAsStream("/Gliker-Bold.ttf"), 28);
        drawingLabel = new Text("DRAWING #1");
        drawingLabel.setFont(gliker);
        drawingLabel.setFill(Color.web("#ffbd59"));

        scoreLabel = new Text("Score: 0");
        scoreLabel.setFont(gliker);
        scoreLabel.setFill(Color.WHITE);

        HBox topBox = new HBox(20);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        VBox leftBox = new VBox(drawingLabel);
        leftBox.setSpacing(5);
        topBox.getChildren().addAll(leftBox, spacer, scoreLabel);
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.CENTER_LEFT);

        root.setTop(topBox);
        root.setCenter(setupGrid());
        root.setBottom(createControlButtons());
        root.setLeft(createScoreTable());
    }

    public void setBackgroundStyle(boolean isBlue) {
        if (isBlue) {
            backgroundStyle = "-fx-background-color: linear-gradient(to right, #01203f, #0047ab);"; // blue
        } else {
            backgroundStyle = "-fx-background-color: linear-gradient(to right, #e76366, #8355eb);"; // pink/purple
        }
        root.setStyle(backgroundStyle);
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

        startDraw = new Button("START");
        goBack = new Button("GO BACK");
        randomPickButton = new Button("RANDOM");

        styleControlButton(startDraw);
        styleControlButton(goBack);
        styleControlButton(randomPickButton);

        startDraw.setOnAction(e -> startDraws());
        goBack.setOnAction(e -> System.out.println("Go back pressed"));
        randomPickButton.setOnAction(e -> {
            game.performRandomPick();
            selectedNumbers.clear();
            selectedNumbers.addAll(game.getSelectedNumbers());

            for (Button btn : numberButtons.values()) {
                int num = Integer.parseInt(btn.getText());
                if (selectedNumbers.contains(num)) {
                    btn.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-weight: bold;");
                } else {
                    btn.setStyle("-fx-background-color: #2e3a4f; -fx-text-fill: white; -fx-background-radius: 8;");
                }
            }
        });

        controls.getChildren().addAll(startDraw, randomPickButton, goBack);
        return controls;
    }

    private void styleControlButton(Button b) {
        Font gliker = Font.loadFont(getClass().getResourceAsStream("/Gliker-Bold.ttf"), 28);
        b.setFont(gliker);
        b.setPrefSize(250, 60);
        b.setStyle("-fx-background-color: #01203f; -fx-text-fill: #ffbd59; -fx-background-radius: 12;");
        b.setOnMouseEntered(e -> b.setStyle("-fx-background-color: #ffbd59; -fx-text-fill: #01203f; -fx-background-radius: 12;"));
        b.setOnMouseExited(e -> b.setStyle("-fx-background-color: #01203f; -fx-text-fill: #ffbd59; -fx-background-radius: 12;"));
    }

    private VBox createScoreTable() {
        VBox table = new VBox(5);
        table.setPadding(new Insets(10));
        table.setStyle("-fx-background-color: #1a1a2e; -fx-border-color: white; -fx-border-width: 2;");

        HBox header = new HBox(50);
        Label hitsHeader = new Label("HITS");
        hitsHeader.setTextFill(Color.WHITE);
        hitsHeader.setStyle("-fx-font-weight: bold;");

        Label winHeader = new Label("WIN");
        winHeader.setTextFill(Color.WHITE);
        winHeader.setStyle("-fx-font-weight: bold;");

        header.getChildren().addAll(hitsHeader, winHeader);
        table.getChildren().add(header);

        for (int i = 0; i <= spotsToPlay; i++) {
            HBox row = new HBox(50);

            Label hits = new Label(String.valueOf(i));
            hits.setTextFill(Color.WHITE);

            Label win = new Label(String.valueOf(game.calculateWin(i)));
            win.setTextFill(Color.WHITE);

            row.getChildren().addAll(hits, win);
            row.setPadding(new Insets(2, 5, 2, 5));
            row.setStyle("-fx-border-color: gray; -fx-border-width: 1;"); // outline
            table.getChildren().add(row);
        }

        return table;
    }

    private void updateScoreTable(int hits) {
        VBox table = (VBox) root.getLeft();

        for (int i = 1; i < table.getChildren().size(); i++) {
            HBox row = (HBox) table.getChildren().get(i);
            row.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-background-color: transparent;");
        }

        if (hits >= 0 && hits <= spotsToPlay) {
            HBox hitRow = (HBox) table.getChildren().get(hits + 1); // +1 because header
            hitRow.setStyle("-fx-border-color: gold; -fx-border-width: 2; -fx-background-color: rgba(255,215,0,0.2);");
        }

        scoreLabel.setText("Score: " + game.getTotalScore());
    }


    private void startDraws() {
        if (selectedNumbers.size() != spotsToPlay) {
            System.out.println("Select exactly " + spotsToPlay + " numbers!");
            return;
        }

        locked = true;
        startDraw.setDisable(true);
        goBack.setDisable(true);
        randomPickButton.setDisable(true);

        performNextDraw(1);
    }

    private void performNextDraw(int drawNumber) {
        if (drawNumber > drawsToPlay) {
            locked = false;
            startDraw.setDisable(false);
            goBack.setDisable(false);
            randomPickButton.setDisable(false);
            return;
        }

        drawingLabel.setText("DRAWING #" + drawNumber);

        List<Integer> drawResult = new ArrayList<>(game.performDraw());
        Collections.shuffle(drawResult);

        for (Button btn : numberButtons.values()) {
            int num = Integer.parseInt(btn.getText());
            if (selectedNumbers.contains(num)) {
                btn.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-weight: bold;");
            } else {
                btn.setStyle("-fx-background-color: #2e3a4f; -fx-text-fill: white; -fx-background-radius: 8;");
            }
        }

        animateDrawNumbers(drawResult, 0, drawNumber);
    }

    private void animateDrawNumbers(List<Integer> drawResult, int index, int drawNumber) {
        if (index >= drawResult.size()) {
            updateScoreTable(game.countHits());
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> performNextDraw(drawNumber + 1));
            pause.play();
            return;
        }

        int number = drawResult.get(index);
        Button btn = numberButtons.get(number);

        if (selectedNumbers.contains(number)) {
            btn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        } else {
            btn.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(e -> animateDrawNumbers(drawResult, index + 1, drawNumber));
        pause.play();
    }


    public Button getGoBackButton() {
        return goBack;
    }
}
