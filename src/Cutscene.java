import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Creates cutscene animation for level 2
 *
 * @author Julia Xie
 * @version May 30, 2019
 */
public class Cutscene extends GameLoop{
    Image background;
    ImageView backgroundView;

    public Cutscene(Stage primaryStage, int width, int height, int scene, int flowSceneNum){
        super(primaryStage, false, scene, flowSceneNum);
        setStageWidth(width);
        setStageHeight(height);
    }

    public void initBackground(int sceneNum) throws IOException {
        Player.hasBag = false;
            background = new Image(new FileInputStream("assets/images/scene1_nighttime.png"));
            setCanInteract(false);
            setCanExit(true);
        backgroundView = new ImageView(background);
        backgroundView.setPreserveRatio(true);
        backgroundView.fitHeightProperty().bind(scene.heightProperty());
        componentsGroup.getChildren().add(backgroundView);
    }

    @Override
    public void initStage(int flowSceneNum) {
    }
}
