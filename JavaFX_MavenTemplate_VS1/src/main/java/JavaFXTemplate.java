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

	     BorderPane root = new BorderPane();
         root.setStyle("-fx-background-color: #1e1e1e;");
	     KenoMenu kenoMenu = new KenoMenu();
         Welcome welcome = new Welcome();

        root.setTop(kenoMenu.getMenuBar());
        root.setCenter(welcome.createWelcomeScreen());

	     Scene scene = new Scene(root, 700, 700);
			primaryStage.setScene(scene);
			primaryStage.show();
		
				
		
	}

}
