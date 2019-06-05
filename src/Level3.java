import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Controls the flow of Level3
 *
 * @author Julia Xie
 * @version June 3
 */
public class Level3 extends GameLoop {

    Image background;
    ImageView backgroundView;

    public Level3(Stage primaryStage, int width, int height, int scene, int flowSceneNum){
        super(primaryStage, true, scene, flowSceneNum);
        setStageWidth(width);
        setStageHeight(height);
    }

    public void initBackground(int sceneNum) throws IOException {
        Player.hasBag = true;
        if (sceneNum == 2) {
            background = new Image(new FileInputStream("assets/images/scene2_door_open.png"));
            setCanInteract(false);
            setCanExit(true);
        }
        else {
            background = new Image(new FileInputStream("assets/images/scene1_daytime.png"));
            setCanInteract(true);
            setCanExit(false);
        }
        backgroundView = new ImageView(background);
        backgroundView.setPreserveRatio(true);
        backgroundView.fitHeightProperty().bind(scene.heightProperty());
        componentsGroup.getChildren().add(backgroundView);
    }

    @Override
    public void initStage(int flowSceneNum) {
        player = new Player(componentsGroup, backgroundView.getFitWidth(), backgroundView.getFitHeight());
        player.reposition(150, scene.getHeight() - 370);
    }

}
