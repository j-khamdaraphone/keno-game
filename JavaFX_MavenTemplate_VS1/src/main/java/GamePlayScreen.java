import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    public GamePlayScreen(int spots, int draws) {
        menuHelper = new KenoMenu();
        spotsToPlay = spots;
        drawingToPlay = draws;
        selectedNumbers = new HashSet<>();
        numberButtons = new HashMap<>();
        randomDraw = new HashSet<>();
        locked = false;
        score = 0;

        root = new BorderPane();
        root.setTop(menuHelper.getMenuBar());
        root.setCenter(setupGrid());
        root.setBottom(createControlButtons());
        root.setStyle("-fx-background-color: #01203f;");

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
            // Unselect → revert to original style
            selectedNumbers.remove(number);
            btn.setStyle("-fx-background-color: #2e3a4f; -fx-text-fill: white; -fx-background-radius: 8;");
        } else {
            // Select if spots limit not exceeded
            if (selectedNumbers.size() < spotsToPlay) {
                selectedNumbers.add(number);
                btn.setStyle("-fx-background-color: gold; -fx-text-fill: black; -fx-font-weight: bold;");
            }
        }
    }
    private HBox createControlButtons() {
        controls = new HBox(20);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(20));

        startDraw = new Button("Start Draw");
        goBack = new Button("Go Back");

        styleButton(startDraw);
        styleButton(goBack);

        goBack.setOnAction(e -> {
            // TODO: handle returning to Welcome scene in JavaFXTemplate
            System.out.println("Returning to Welcome screen...");
        });

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

        List<Integer> allNumbers = new ArrayList<>();
        for (int i = 1; i <= 80; i++) allNumbers.add(i);
        Collections.shuffle(allNumbers);

        // Animate drawing numbers
        Iterator<Integer> it = allNumbers.iterator();
        drawNext(it, 0);
    }

    private void drawNext(Iterator<Integer> it, int drawnCount) {
        if (drawnCount >= drawingToPlay || !it.hasNext()) {
            showResults();
            return;
        }

        int number = it.next();
        randomDraw.add(number);

        Button btn = numberButtons.get(number);
        btn.setStyle("-fx-background-color: #ff4c4c; -fx-text-fill: white; -fx-font-weight: bold;");

        PauseTransition pause = new PauseTransition(Duration.millis(200));
        pause.setOnFinished(e -> drawNext(it, drawnCount + 1));
        pause.play();
    }

    private void showResults() {
        int hits = 0;
        for (int n : selectedNumbers) {
            if (randomDraw.contains(n)) hits++;
        }
        showAlert("Game Over", "You hit " + hits + " out of " + spotsToPlay + "!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}