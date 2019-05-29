import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class Level1 extends GameLoop {

    Image background;
    ImageView backgroundView;

    public Level1(Stage primaryStage) {
        super(primaryStage, false);
        super.stageWidth = 1280;
        super.stageHeight = 720;
    }

    public void initBackground() throws IOException {
        background = new Image (new FileInputStream("assets/images/scene1_background.png"));
        backgroundView = new ImageView(background);
        backgroundView.setFitHeight(720);
        backgroundView.setPreserveRatio(true);
        componentsGroup.getChildren().add(backgroundView);
    }

    @Override
    public void initStage() {
        player = new Player(componentsGroup, backgroundView.getFitWidth(), backgroundView.getFitHeight());
        player.reposition(scene.getWidth()/2 - 20);
    }
}
