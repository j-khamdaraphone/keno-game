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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
        primaryStage.setTitle("Keno");

        Welcome welcome = new Welcome();
        StackPane rootPane = new StackPane();
        rootPane.setStyle("-fx-background-color: #01203f;");
        BorderPane root = new BorderPane();


        rootPane.getChildren().add(root);
        root.setCenter(welcome.createWelcomeScreen());

        KenoMenu kenoMenu = new KenoMenu();
        root.setTop(kenoMenu.getMenuBar());


        welcome.getStartButton().setOnAction(e -> {
            System.out.println("Switching to GamePlaySpot screen...");

            GamePlaySpot gamePlaySpot = new GamePlaySpot();
            Scene gameScene = new Scene(gamePlaySpot, 700, 700);
            primaryStage.setScene(gameScene);


        kenoMenu.getRulesOfGame().setOnAction(e ->
                kenoMenu.showPopup("Rules of Keno", Rules.getText(), root, rootPane)
        );

        kenoMenu.getOddsOfwin().setOnAction(e ->
                kenoMenu.showPopup("Odds of Winning", Odds.getText(), root, rootPane)
        );

        kenoMenu.getExit().setOnAction(e -> primaryStage.close());

        kenoMenu.getNewLookItem().setOnAction(e -> kenoMenu.applyNewLook());

        Scene scene = new Scene(rootPane, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();


		
	}

}
