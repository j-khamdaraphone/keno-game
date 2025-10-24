import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;


public class KenoMenu {
    private MenuBar menuBar;
    private Menu kenoMenu;
    private MenuItem rulesOfGame;
    private MenuItem oddsOfwin;
    private MenuItem exit;
    private MenuItem newLookItem;

    public KenoMenu() {
        menuBar= new MenuBar();
        kenoMenu=new Menu("Menu");

        rulesOfGame= new MenuItem("Rules");
        oddsOfwin= new MenuItem("Odds");
        exit=new MenuItem("Exit");
        newLookItem = new MenuItem("Apply New Look");

        kenoMenu.getItems().addAll(rulesOfGame,oddsOfwin,exit,newLookItem);

        menuBar.getMenus().add(kenoMenu);

        newLookItem.setOnAction(e->applyNewLook());
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
}