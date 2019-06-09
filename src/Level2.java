import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Initializes the proper background and scene for Level2.
 * <p>
 * References:
 * <br>Dillon, A. (2015). Picasso (Version 2) [Software]. Retrieved from https://github.com/Hopding/Picasso.
 * </p>
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.1 - Julia Xie
 * <br>Date: 2019/05/28
 * <br>Time Spent: 5 minutes
 * <br>Created class.
 * </p>
 * <p>
 * 1.2 - Julia Xie
 * <br>Date: 2019/50/29
 * <br>Time Spent: 30 minutes
 * <br>Added constructor.
 * <br>Added initBackground() method.
 * <br>Added initStage() method.
 * </p>
 * @since 1.1
 */
public class Level2 extends GameLoop {

    /**
     * The image of the background.
     */
    private ImageView background;


    /**
     * Constructor for the Level2 class.
     * Calls the constructor of the superclass and sets the width and height of the stage.
     *
     * @param primaryStage the stage which the scene appears on
     * @param scrollable   whether or not the scene can be scrolled
     * @param width        the width of the stage
     * @param height       the height of the stage
     * @param scene        the scene number
     * @param flowSceneNum the number of scene changes
     * @since 1.2
     */
    public Level2(Stage primaryStage, boolean scrollable, int width, int height, int scene, int flowSceneNum) {
        super(primaryStage, scrollable, scene, flowSceneNum);
        setStageWidth(width);
        setStageHeight(height);
    }

    /**
     * Initializes the background with a picture or colour.
     * Sets constraints based on the scene number.
     *
     * @param sceneNum the scene number
     * @throws IOException if an input or output exception occurs when looking for an image
     * @since 1.2
     */
    @Override
    public void initBackground(int sceneNum) throws IOException {
        Player.setHasBag(false);
        if (sceneNum == 2) {
            background = new ImageView(new Image(new FileInputStream("assets/images/scene2_door_closed.png")));
            setCanInteract(true);
            setCanExit(false);
        } else {
            background = new ImageView(new Image(new FileInputStream("assets/images/scene1_nighttime.png")));
            setCanInteract(false);
            setCanExit(true);
        }
        background.setPreserveRatio(true);
        background.fitHeightProperty().bind(scene.heightProperty());
        componentsGroup.getChildren().add(background);
    }

    /**
     * Initializes the stage with the player.
     * Sets the player's position.
     *
     * @param flowSceneNum the number of stage changes so far
     * @since 1.2
     */
    @Override
    public void initStage(int flowSceneNum) {
        if (flowSceneNum == 5) {
            player = new Player(componentsGroup, background.getFitWidth(), background.getFitHeight());
            player.reposition(1360, scene.getHeight() - 380);
        } else if (flowSceneNum == 6) {
            player = new Player(componentsGroup, background.getFitWidth(), background.getFitHeight());
            player.reposition(2100, scene.getHeight() - 380);
        }
    }

}
