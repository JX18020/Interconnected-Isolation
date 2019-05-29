import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public abstract class Level {
    private Scene level;
    private BorderPane layout;
    private Image bgImage;

    public Level () {
        layout = new BorderPane();
        layout.setStyle("-fx-background-color: black");
        level = new Scene (layout, 1280, 720, Color.BLACK);
    }

    public abstract Scene window();

    public Scene getLevel() {
        return level;
    }

}
