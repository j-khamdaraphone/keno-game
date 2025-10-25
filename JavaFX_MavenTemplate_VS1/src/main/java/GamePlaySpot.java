
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




public class GamePlaySpot extends Vbox {
    private List<Button> spotOptions;
    private List<Button> drawOptions;
    private KenoMenuBar menuHelper;
    private int selectedSpots = 0;
    private int selectedDrawings = 0;

    public GamePlaySpot(Stage stage) {
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


        getChildren().addAll(
                menuHelper.getMenuBar(),
                title,
                new Text("Select Spots:"), spotBox,
                new Text("Select Drawings:"), drawBox,
                //backButton
        );

    }

    private List<Button> createSpotButtons(){
        List<Integer> spotValues = List.of(1, 4, 8, 10);
        List<Button> buttons = new ArrayList<>();

        for (int al : spotValues) {
            Button b = new Button(String.valueOf(val));
            b.setStyle("-fx-background-color: lightgray; -fx-font-size: 14px;");

            buttons.add(b);

        }

        return buttons;
    }

    private List<Button> createDrawButtons(){
        List<Button> buttons = new ArrayList<>();
        for (int i=1, i<=4, i++){
            button b= new Button(val)
            b.setStyle("-fx-background-color: lightgray; -fx-font-size: 14px;");
            b.setOnAction(e -> handleSpotSelection(value));
            buttons.add(b);
        }

        return buttons;

    }

    private void handleSpotSelection(int spot){
        selectedSpots = spot;
        checkNextButton();

    }

    private void handleDrawSelection(int drawing){
        selectedDrawings = drawing;
        checkNextButton();
    }

    private void checkNextButton() {
        nextButton.setDisable(selectedSpots == 0 || selectedDrawings == 0);
    }

    private void nextButton(){

        0nNext.accept(null);
    }

    public int getSelectedSpots() { return selectedSpots; }
    public int getSelectedDrawings() { return selectedDrawings; }
    public Button getNextButton() { return nextButton; }
    //public Button getBackButton() { return backButton; }

}