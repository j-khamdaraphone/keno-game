import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;

public class Welcome {
    private ImageView kenoImage;
    private Label welcomeText;
    private Button startButton;
    static final int picHeight = 600;
    static final int picWidth = 600;

    public Welcome() {
        // Background img
        Image pic = new Image("keno2.png");
        kenoImage = new ImageView(pic);
        kenoImage.setFitHeight(picHeight);
        kenoImage.setFitWidth(picWidth);
        kenoImage.setPreserveRatio(true);

        // Start Button
        Image startImage = new Image("play-button.png");
        ImageView startImageView = new ImageView(startImage);
        startImageView.setFitWidth(250);
        startImageView.setFitHeight(200);
        startImageView.setPreserveRatio(true);

        startButton = new Button();
        startButton.setGraphic(startImageView);
        startButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        startButton.setOnAction(e -> startPlay());

    }

    public StackPane createWelcomeScreen() {
        StackPane stack = new StackPane();
        stack.setStyle("-fx-background-color: linear-gradient(to right, #e76366, #8355eb);");
        stack.getChildren().addAll(kenoImage, startButton);
        StackPane.setAlignment(startButton, Pos.BOTTOM_CENTER);
        startButton.setTranslateY(-150);
        return stack;
    }

    public void startPlay() {
        System.out.println("Start button pressed...");
    }

    public Button getStartButton() {
        return startButton;
    }
}