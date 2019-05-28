import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public abstract class Level {
    private Scene level;
    private BorderPane layout;

    public Level () {
        level = new Scene (layout, 1280, 720, Color.BLACK);
    }

}
