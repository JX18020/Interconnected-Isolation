import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Level2 extends Level {
    private Scene level;

    public Level2() {
        super();
        level = getLevel();
    }

    public Scene window() {
        level.setFill(Color.BLACK);
        return level;
    }
}

