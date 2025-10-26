
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.Text;




public class GamePlaySpot extends VBox {
    private List<Button> spotOptions;
    private List<Button> drawOptions;
    private Button nextButton;
    private KenoMenu menuHelper;
    private int selectedSpots = 0;
    private int selectedDrawings = 0;

    public GamePlaySpot() {
        setSpacing(25);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: linear-gradient(to bottom, #003366, #001a33);");

        Text title = new Text("Keno: Select Game Options!");
        title.setStyle("-fx-font-size: 24px; -fx-fill: white; -fx-font-weight: bold;");

        menuHelper= new KenoMenu();

        spotOptions = createSpotButtons();
        drawOptions = createDrawButtons();


        HBox spotBox = new HBox(10);
        spotBox.getChildren().addAll(spotOptions);
        HBox drawBox = new HBox(10);
        drawBox.getChildren().addAll(drawOptions);

        nextButton = new Button("Next");
        nextButton.setStyle("-fx-background-color: gold; -fx-font-size: 16px; -fx-font-weight: bold;");

        getChildren().addAll(
                menuHelper.getMenuBar(),
                title,
                new Text("Select Spots:"), spotBox,
                new Text("Select Drawings:"), drawBox,
                nextButton
        );

    }

    private List<Button> createSpotButtons(){
        List<Integer> spotValues = List.of(1, 4, 8, 10);
        List<Button> buttons = new ArrayList<>();

        for (int val : spotValues) {
            Button b = new Button(String.valueOf(val));
            b.setStyle("-fx-background-color: lightgray; -fx-font-size: 14px;");

            b.setOnAction(e -> {
                selectedSpots = val;
                buttons.forEach(btn -> btn.setStyle("-fx-background-color: lightgray; -fx-font-size: 14px;"));
                b.setStyle("-fx-background-color: gold; -fx-font-size: 14px; -fx-font-weight: bold;");
            });
            buttons.add(b);

        }

        return buttons;
    }

    private List<Button> createDrawButtons() {
        List<Integer> spotValues = List.of(1, 4, 8, 10);
        List<Button> buttons = new ArrayList<>();

        for (int val : spotValues) {
            Button b = new Button(String.valueOf(val));
            b.setStyle("-fx-background-color: lightgray; -fx-font-size: 14px;");

            buttons.add(b);

            b.setOnAction(e -> {
                selectedDrawings = val;
                buttons.forEach(btn -> btn.setStyle("-fx-background-color: lightgray; -fx-font-size: 14px;"));
                b.setStyle("-fx-background-color: gold; -fx-font-size: 14px; -fx-font-weight: bold;");
            });
        }

        return buttons;
    }



    public Button getNextButton() { return nextButton; }
    public int getSelectedSpots() { return selectedSpots; }
    public int getSelectedDrawings() { return selectedDrawings; }


}