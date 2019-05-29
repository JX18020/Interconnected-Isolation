import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Level1 extends Level {
    private Scene level;

    public Level1() {
        super();
        level = getLevel();
    }

    public Scene window() {
        level.setFill(Color.BLACK);

        return level;
    }
}
