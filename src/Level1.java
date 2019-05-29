import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;

public class Level1 extends Level {

    public Level1() {
        super();
    }

    public Scene window() throws IOException {
        Image bgImage = new Image (new FileInputStream("assets/images/scene1_background.png"));
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(700);
        bgImageView.setPreserveRatio(true);
        bgImageView.setX(200);
        bgImageView.setY(0);
        layout.getChildren().add(bgImageView);
        return level;
    }
}
