import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Text;
import java.util.*;

public class GamePlayScreen {
    private BorderPane root;
    private GridPane grid;
    private Map<Integer, Button> numberButtons;
    private Button startDraw;
    private Button goBack;
    private Set<Integer> selectedNumbers;
    private int spotsToPlay;
    private int drawingToPlay;
    private Set<Integer> randomDraw;
    private KenoMenu menuHelper;
    private boolean locked;
    private int score;
    private HBox controls;
    private Text drawingLabel;
    private boolean isDrawing = false;
    private int currentDraw = 0;
    private int totalScore = 0;
    private Text scoreLabel;


    public GamePlayScreen(int spots, int draws) {
        menuHelper = new KenoMenu();
        spotsToPlay = spots;
        drawingToPlay = draws;
        selectedNumbers = new HashSet<>();
        numberButtons = new HashMap<>();
        randomDraw = new HashSet<>();
        locked = false;
        score = 0;
        totalScore = 0;
        currentDraw = 0;
        Font gliker = Font.loadFont(getClass().getResourceAsStream("/Gliker-Bold.ttf"), 32);

        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #e76366, #8355eb);");

        drawingLabel = new Text("DRAWING #1");
        drawingLabel.setFont(gliker);
        drawingLabel.setFill(Color.web("#ffbd59"));

        scoreLabel = new Text("Score: 0");
        scoreLabel.setFont(gliker);
        scoreLabel.setFill(Color.WHITE);

        HBox topBox = new HBox(20);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        VBox leftBox = new VBox(menuHelper.getMenuBar(), drawingLabel);
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
        } else {
            if (selectedNumbers.size() < spotsToPlay) {
                selectedNumbers.add(number);
                btn.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-weight: bold;");
            } else {
                System.out.println("You can only select " + spotsToPlay + " numbers.");
            }
        }
    }
    private HBox createControlButtons() {
        controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(20));

        startDraw = new Button("START DRAW");
        goBack = new Button("GO BACK");

        styleControlButton(startDraw);
        styleControlButton(goBack);

        startDraw.setOnAction(e -> {
            if (selectedNumbers.size() != spotsToPlay) {
                System.out.println("Please select " + spotsToPlay + " numbers before starting draw!");
                return;
            }
            lockSelection();
            startDrawings();
        });

        goBack.setOnAction(e -> {
            System.out.println("Returning to Welcome screen...");
        });

        controls.getChildren().addAll(startDraw, goBack);
        return controls;
    }

    private void styleButton(Button btn) {
        Font gliker = Font.loadFont(getClass().getResourceAsStream("/Gliker-Bold.ttf"), 32);
        btn.setFont(gliker);
        btn.setPrefWidth(140);
        btn.setStyle("-fx-background-color: #34568B; -fx-text-fill: white; -fx-background-radius: 8;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #4A6FA5; -fx-text-fill: white; -fx-background-radius: 8;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #34568B; -fx-text-fill: white; -fx-background-radius: 8;"));
    }

    private void autoPick() {
        if (locked) return;
        selectedNumbers.clear();
        numberButtons.values().forEach(b -> b.setStyle("-fx-background-color: #2e3a4f; -fx-text-fill: white;"));

        Random rand = new Random();
        while (selectedNumbers.size() < spotsToPlay) {
            selectedNumbers.add(rand.nextInt(80) + 1);
        }

        for (int n : selectedNumbers) {
            Button b = numberButtons.get(n);
            b.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-weight: bold;");
        }
    }

    private void lockSelection() {
        locked = true;
    }

    void randomDrawPicks() {
        randomDraw = new HashSet<>();
        Random rand = new Random();

        for (int n : selectedNumbers) {
            Button b = numberButtons.get(n);
            b.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
        }

        List<Integer> allNumbers = new ArrayList<>();
        for (int i = 1; i <= 80; i++) allNumbers.add(i);
        Collections.shuffle(allNumbers);

        Iterator<Integer> it = allNumbers.iterator();
        drawNext(it, 0);
    }

    private void drawNext(Iterator<Integer> it, int drawnCount) {
        if (drawnCount >= drawingToPlay || !it.hasNext()) {
            highlightResults();
            return;
        }

        int number = it.next();
        randomDraw.add(number);

        Button btn = numberButtons.get(number);

        if (selectedNumbers.contains(number)) {
            btn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        } else {
            btn.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        PauseTransition pause = new PauseTransition(Duration.millis(200));
        pause.setOnFinished(e -> drawNext(it, drawnCount + 1));
        pause.play();
    }


    private void highlightResults() {
        int hits = 0;
        for (int n : selectedNumbers) {
            if (randomDraw.contains(n)) hits++;
        }

        VBox table = (VBox) root.getLeft();
        for (int i = 1; i < table.getChildren().size(); i++) {
            HBox row = (HBox) table.getChildren().get(i);
            row.setStyle((i - 1 == hits) ? "-fx-background-color: gold;" : "");
        }
    }

    private VBox createScoreTable() {
        VBox table = new VBox(5);
        table.setPadding(new Insets(10));
        table.setStyle("-fx-background-color: #1a1a2e; -fx-border-color: white; -fx-border-width: 1;");

        HBox header = new HBox(50);
        header.getChildren().addAll(
                createLabel("HITS", true),
                createLabel("WIN", true)
        );
        table.getChildren().add(header);

        for (int i = 0; i <= spotsToPlay; i++) {
            HBox row = new HBox(50);
            row.getChildren().addAll(
                    createLabel(String.valueOf(i), false),
                    createLabel(String.valueOf(calculateWin(i)), false)
            );
            table.getChildren().add(row);
        }

        return table;
    }

    private Label createLabel(String text, boolean bold) {
        Label lbl = new Label(text);
        lbl.setTextFill(Color.WHITE);
        if (bold) lbl.setStyle("-fx-font-weight: bold;");
        return lbl;
    }

    private int calculateWin(int hits) {
        switch (hits) {
            case 0: return 0;
            case 1: return 2;
            case 2: return 5;
            case 3: return 20;
            case 4: return 100;
            case 5: return 200;
            case 6: return 500;
            case 7: return 1000;
            case 8: return 2000;
            case 9: return 5000;
            case 10: return 10000;
            default: return 0;
        }
    }

    private void showResults() {
        int hits = 0;
        for (int n : selectedNumbers) {
            if (randomDraw.contains(n)) hits++;
        }

        VBox table = (VBox) root.getLeft();
        for (int i = 1; i < spotsToPlay; i++) {
            HBox row = (HBox) table.getChildren().get(i);
            if (i-1 == hits) {
                row.setStyle("-fx-background-color: gold;");
            } else {
                row.setStyle("");
            }
        }

        for (int n : selectedNumbers) {
            if (!randomDraw.contains(n)) {
                Button btn = numberButtons.get(n);
                btn.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
            }
        }
    }

    private void startDrawings() {
        startDraw.setDisable(true);
        goBack.setDisable(true);
        isDrawing = true;
        currentDraw = 1;
        totalScore = 0;
        startNextDrawing();
    }

    private void startNextDrawing() {
        if (currentDraw > drawingToPlay) {
            isDrawing = false;
            startDraw.setDisable(false);
            goBack.setDisable(false);
            return;
        }

        drawingLabel.setText("DRAWING #" + currentDraw);

        for (Button btn : numberButtons.values()) {
            if (!selectedNumbers.contains(Integer.parseInt(btn.getText()))) {
                btn.setStyle("-fx-background-color: #2e3a4f; -fx-text-fill: white;");
            } else {
                btn.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
            }
        }

        Set<Integer> winners = new HashSet<>();
        Random rand = new Random();
        while (winners.size() < spotsToPlay) {
            winners.add(rand.nextInt(80) + 1);
        }

        List<Integer> winnerList = new ArrayList<>(winners);
        animateWinners(winnerList, 0, winners);
    }

    private void animateWinners(List<Integer> winnerList, int index, Set<Integer> winners) {
        if (index >= winnerList.size()) {
            int hits = 0;
            for (int n : selectedNumbers) {
                if (winners.contains(n)) hits++;
            }

            totalScore += calculateWin(hits);
            updateScoreTable(hits);

            currentDraw++;
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> startNextDrawing());
            pause.play();
            return;
        }

        int num = winnerList.get(index);
        Button btn = numberButtons.get(num);

        if (selectedNumbers.contains(num)) {
            btn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        } else {
            btn.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        PauseTransition pause = new PauseTransition(Duration.millis(300));
        pause.setOnFinished(e -> animateWinners(winnerList, index + 1, winners));
        pause.play();
    }

    private void updateScoreTable(int hits) {
        VBox table = (VBox) root.getLeft();

        for (int i = 1; i < table.getChildren().size(); i++) {
            HBox row = (HBox) table.getChildren().get(i);
            row.setStyle("");
        }

        if (hits >= 0 && hits <= spotsToPlay) {
            HBox hitRow = (HBox) table.getChildren().get(hits + 1); // +1 because 0th is header
            hitRow.setStyle("-fx-background-color: gold;");
        }

        scoreLabel.setText("Score: " + totalScore);
    }

    private void styleControlButton(Button b) {
        Font gliker = Font.loadFont(getClass().getResourceAsStream("/Gliker-Bold.ttf"), 32);
        b.setFont(gliker);
        b.setPrefSize(300, 60);
        b.setStyle("-fx-background-color: #01203f; -fx-text-fill: #ffbd59; -fx-background-radius: 12;");

        b.setOnMouseEntered(e -> b.setStyle("-fx-background-color: #ffbd59; -fx-text-fill: #01203f; -fx-background-radius: 12;"));
        b.setOnMouseExited(e -> b.setStyle("-fx-background-color: #01203f; -fx-text-fill: #ffbd59; -fx-background-radius: 12;"));
    }


    public Button getGoBackButton() {
        return goBack;
    }

}