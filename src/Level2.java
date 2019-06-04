import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Controls the flow of Level2
 *
 * @author Julia Xie
 * @version June 3
 */
public class Level2 extends GameLoop {

    Image background;
    ImageView backgroundView;

    public Level2(Stage primaryStage, int width, int height, int scene, int flowSceneNum){
        super(primaryStage, true, scene, flowSceneNum);
        setStageWidth(width);
        setStageHeight(height);
    }

    public void initBackground(int sceneNum) throws IOException {
        Player.hasBag = false;
        if (sceneNum == 2) {
            background = new Image(new FileInputStream("assets/images/scene2_door_open.png"));
            setCanInteract(true);
            setCanExit(false);
        }
        else {
            background = new Image(new FileInputStream("assets/images/scene1_daytime.png"));
            setCanInteract(false);
            setCanExit(true);
        }
        backgroundView = new ImageView(background);
        backgroundView.setPreserveRatio(true);
        backgroundView.fitHeightProperty().bind(scene.heightProperty());
        componentsGroup.getChildren().add(backgroundView);
    }

    @Override
    public void initStage(int flowSceneNum) {
        if (flowSceneNum == 3) {
            player = new Player(componentsGroup, backgroundView.getFitWidth(), backgroundView.getFitHeight());
            player.reposition(150, scene.getHeight() - 370);
        }
        else {
            player = new Player(componentsGroup, backgroundView.getFitWidth(), backgroundView.getFitHeight());
            player.reposition(scene.getWidth() - 150, scene.getHeight() - 370);
        }
    }

}
