import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;

public class JavaFXTemplate extends Application {
    private GamePlaySpot gamePlaySpot;
    private GamePlayScreen gamePlayScreen;
    boolean[] isBlue = {false};
	public static void main(String[] args) {

		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Keno");

        Welcome welcome = new Welcome();
        StackPane rootPane = new StackPane();
        rootPane.setStyle("-fx-background-color: #01203f;");
        BorderPane root = new BorderPane();


        rootPane.getChildren().add(root);
        root.setCenter(welcome.createWelcomeScreen());

        KenoMenu kenoMenu = new KenoMenu();
        root.setTop(kenoMenu.getMenuBar());
        kenoMenu.setNewLookVisible(false);


        welcome.getStartButton().setOnAction(e -> {
            System.out.println("Switching to GamePlaySpot screen...");
            gamePlaySpot = new GamePlaySpot();

            kenoMenu.setNewLookVisible(true);

            gamePlaySpot.getNextButton().setOnAction(nextEvent -> {
                int spots = gamePlaySpot.getSelectedSpots();
                int draws = gamePlaySpot.getSelectedDrawings();

                if (spots == 0 || draws == 0) {
                    System.out.println("Please select both spots and drawings!");
                    return;
                }

                gamePlayScreen = new GamePlayScreen(spots, draws);
                gamePlayScreen.setBackgroundStyle(isBlue[0]);


                gamePlayScreen.getGoBackButton().setOnAction(backEvent -> {

                    root.setCenter(gamePlaySpot);
                });

                root.setCenter(gamePlayScreen.getRoot());
            });

            root.setCenter(gamePlaySpot);
        });


        kenoMenu.getRulesOfGame().setOnAction(e ->
                kenoMenu.showPopup("Rules of Keno", Rules.getText(), root, rootPane)
        );

        kenoMenu.getOddsOfwin().setOnAction(e ->
                kenoMenu.showPopup("Odds of Winning", Odds.getText(), root, rootPane)
        );

        kenoMenu.getExit().setOnAction(e -> primaryStage.close());

        kenoMenu.getNewLookItem().setOnAction(e -> {
            isBlue[0] = !isBlue[0];
            if (gamePlaySpot != null) {
                gamePlaySpot.setBackgroundStyle(isBlue[0]);
            }
            if (gamePlayScreen != null) {
                gamePlayScreen.setBackgroundStyle(isBlue[0]);
            }
        });

        Scene scene = new Scene(rootPane, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
