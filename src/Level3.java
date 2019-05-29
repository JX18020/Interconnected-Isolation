import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Level3 extends Level {
    private Scene level;

    public Level3() {
        super();
        level = getLevel();
    }

    public Scene window() {
        level.setFill(Color.BLACK);
        return level;
    }
}
