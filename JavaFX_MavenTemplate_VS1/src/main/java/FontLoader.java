import javafx.scene.text.Font;

public class FontLoader {
    public static void loadFonts() {
        Font.loadFont(
                FontLoader.class.getResourceAsStream("/Gliker-Bold.ttf"),
                20
        );
    }
}