import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Level2 extends Level {
    private Scene level;

    public Level2() {
        super();
    }

    public Scene window() {
        level.setFill(Color.BLACK);
        return level;
    }
}

