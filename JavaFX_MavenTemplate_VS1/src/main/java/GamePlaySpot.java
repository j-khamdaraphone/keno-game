
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
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.Region;

public class GamePlaySpot extends VBox {
    private List<Button> spotOptions;
    private List<Button> drawOptions;
    private Button nextButton;

    private int selectedSpots = 0;
    private int selectedDrawings = 0;

    public GamePlaySpot() {
        setSpacing(25);
        setPadding(new Insets(20));
        setStyle("-fx-background-color: linear-gradient(to right, #e76366, #8355eb);");
        setAlignment(Pos.CENTER);

        Font gliker = Font.loadFont(getClass().getResourceAsStream("/Gliker-Bold.ttf"), 32);

        Text title1 = new Text("SELECT PICK");
        Text title2 = new Text("SELECT DRAWINGS");
        title1.setFont(gliker);
        title1.setFill(Color.web("#ffbd59"));
        title2.setFont(gliker);
        title2.setFill(Color.web("#ffbd59"));



        spotOptions = createSpotButtons();
        drawOptions = createDrawButtons();


        HBox spotBox = new HBox(10);
        spotBox.getChildren().addAll(spotOptions);
        spotBox.setAlignment(Pos.CENTER);
        HBox drawBox = new HBox(10);
        drawBox.getChildren().addAll(drawOptions);
        drawBox.setAlignment(Pos.CENTER);

        nextButton = new Button("NEXT");
        styleOptionButton(nextButton, gliker, false);
        nextButton.setPrefSize(140, 60);

        Region spacer = new Region();
        spacer.setPrefHeight(20);

        getChildren().addAll(

                title1,
                spotBox,
                title2,
                drawBox,
                spacer,
                nextButton
        );

    }

    private List<Button> createSpotButtons(){
        List<Integer> spotValues = List.of(1, 4, 8, 10);
        List<Button> buttons = new ArrayList<>();

        Font gliker = Font.loadFont(getClass().getResourceAsStream("/Gliker-Bold.ttf"), 24);

        for (int val : spotValues) {
            Button b = new Button(String.valueOf(val));
            styleOptionButton(b, gliker, false);

            b.setOnAction(e -> {
                selectedSpots = val;
                for (Button btn : buttons) {
                    styleOptionButton(btn, gliker, false);
                }
                styleOptionButton(b, gliker, true);
            });
            buttons.add(b);

        }

        return buttons;
    }

    private List<Button> createDrawButtons() {
        List<Integer> drawValues = List.of(1, 4, 8, 10);
        List<Button> buttons = new ArrayList<>();
        Font gliker = Font.loadFont(getClass().getResourceAsStream("/Gliker-Bold.ttf"), 24);

        for (int val : drawValues) {
            Button b = new Button(String.valueOf(val));
            styleOptionButton(b, gliker, false);
            buttons.add(b);

            b.setOnAction(e -> {
                selectedDrawings = val;
                for (Button btn : buttons) {
                    styleOptionButton(btn, gliker, false);
                }
                styleOptionButton(b, gliker, true);
            });
        }

        return buttons;
    }

    private void styleOptionButton(Button b, Font font, boolean selected) {
        b.setFont(font);
        if (selected) {
            b.setStyle(
                    "-fx-background-color: #ffbd59;" +
                            "-fx-text-fill: #01203f;" +
                            "-fx-background-radius: 12;"
            );
        } else {
            b.setStyle(
                    "-fx-background-color: #01203f;" +
                            "-fx-text-fill: #ffbd59;" +
                            "-fx-background-radius: 12;"
            );
        }
        b.setPrefSize(100, 60);
    }


    public Button getNextButton() { return nextButton; }
    public int getSelectedSpots() { return selectedSpots; }
    public int getSelectedDrawings() { return selectedDrawings; }

}