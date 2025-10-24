import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;

public class Welcome {
    private ImageView kenoImage;
    private Label welcomeText;
    private Button startButton;
    static final int picHeight = 350;
    static final int picWidth = 450;

    public Welcome() {
        // Start Button
        startButton = new Button("Start");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        startButton.setOnAction(e -> startPlay());
        // Image
        Image pic = new Image("keno.png");
        kenoImage = new ImageView(pic);
        kenoImage.setFitHeight(picHeight);
        kenoImage.setFitWidth(picWidth);
        kenoImage.setPreserveRatio(true);
        // Welcome Text
        welcomeText = new Label("Welcome!");
        welcomeText.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
    }

    public VBox createWelcomeScreen() {
        VBox paneCenter = new VBox(10);
        paneCenter.setAlignment(Pos.CENTER);
        paneCenter.getChildren().addAll(welcomeText, kenoImage, startButton);

        return paneCenter;
    }

    public void startPlay() {

    }
}