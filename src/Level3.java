import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    Rectangle rect;

    public Level3(Stage primaryStage, boolean scrollable, int width, int height, int scene, int flowSceneNum){
        super(primaryStage, scrollable, scene, flowSceneNum);
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
        else if (sceneNum == 1){
            background = new Image(new FileInputStream("assets/images/scene1_daytime.png"));
            setCanInteract(true);
            setCanExit(false);
        } else {
            rect = new Rectangle(1280, 720, Color.BLACK);
            Text text = new Text ("A few months later...");
            text.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 50px");
            text.setTextAlignment(TextAlignment.CENTER);
            text.setFill(Color.WHITE);
            StackPane layout = new StackPane();
            layout.getChildren().addAll(rect, text);
            root.getChildren().addAll(layout);
        }
        if (sceneNum == 1 || sceneNum == 2) {
            backgroundView = new ImageView(background);
            backgroundView.setPreserveRatio(true);
            backgroundView.fitHeightProperty().bind(scene.heightProperty());
            componentsGroup.getChildren().add(backgroundView);
        }
    }

    @Override
    public void initStage(int flowSceneNum) {
        if (flowSceneNum >= 9) {
            player = new Player(componentsGroup, backgroundView.getFitWidth(), backgroundView.getFitHeight());
            player.reposition(150, scene.getHeight() - 370);
        }
    }

}
