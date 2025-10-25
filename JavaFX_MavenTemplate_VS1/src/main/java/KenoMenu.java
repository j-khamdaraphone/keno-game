import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;

public class KenoMenu {
    private MenuBar menuBar;
    private Menu kenoMenu;
    private MenuItem rulesOfGame;
    private MenuItem oddsOfwin;
    private MenuItem exit;
    private MenuItem newLookItem;
    private BorderPane mainContent;

    public KenoMenu() {
        menuBar= new MenuBar();
        kenoMenu=new Menu("☆ Menu");

        rulesOfGame= new MenuItem("Rules");
        oddsOfwin= new MenuItem("Odds");
        exit=new MenuItem("Exit");
        newLookItem = new MenuItem("Apply New Look");

        kenoMenu.getItems().addAll(rulesOfGame,oddsOfwin,exit,newLookItem);

        menuBar.getMenus().add(kenoMenu);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void setNewLookVisible(boolean visible) {
        newLookItem.setVisible(visible);
    }

    public MenuItem getNewLookItem() {
        return newLookItem;
    }

    public MenuItem getRulesOfGame() {
        return rulesOfGame;
    }

    public MenuItem getOddsOfwin() {
        return oddsOfwin;
    }

    public MenuItem getExit() {
        return exit;
    }

    public void applyNewLook() {
        System.out.println("Applying new look...");
        // Add your look-and-feel changes here
    }

    public void showPopup(String title, String message, BorderPane mainContent, StackPane rootPane) {
        mainContent.setEffect(new GaussianBlur(5));

        Rectangle overlay = new Rectangle();
        overlay.setWidth(rootPane.getWidth());
        overlay.setHeight(rootPane.getHeight());
        overlay.setStyle("-fx-fill: rgba(0,0,0,0.4);");

        VBox popup = new VBox(15);
        popup.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10;");
        popup.setAlignment(Pos.CENTER);
        popup.setMaxWidth(400);
        popup.setMaxHeight(300);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label msgLabel = new Label(message);
        msgLabel.setStyle("-fx-font-size: 16px;");
        msgLabel.setWrapText(true);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            rootPane.getChildren().removeAll(overlay, popup);
            mainContent.setEffect(null);
        });

        popup.getChildren().addAll(titleLabel, msgLabel, closeButton);

        rootPane.getChildren().addAll(overlay, popup);
        StackPane.setAlignment(popup, Pos.CENTER);
    }
}