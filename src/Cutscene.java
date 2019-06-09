import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Creates cutscene animation for level 2.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.10 - Julia Xie
 * <br>Date: 2019/06/07
 * <br>Time Spent: 1 hour
 * <br>Added constructor.
 * <br>Added initBackground() method.
 * <br>Added initStage() method.
 * </p>
 * @since 1.10
 */
public class Cutscene extends GameLoop {
    private ImageView background;

    /**
     * Constructor for the Cutscene class.
     * Calls the constructor of the superclass and sets the width and height of the stage.
     *
     * @param primaryStage the stage which the scene appears on
     * @param width        the width of the stage
     * @param height       the height of the stage
     * @param scene        the scene number
     * @param flowSceneNum the number of scene changes
     * @since 1.10
     */
    public Cutscene(Stage primaryStage, int width, int height, int scene, int flowSceneNum) {
        super(primaryStage, false, scene, flowSceneNum);
        setStageWidth(width);
        setStageHeight(height);
    }

    /**
     * Initializes the background with a picture or colour.
     * Sets constraints based on the scene number.
     *
     * @param sceneNum the scene number
     * @throws IOException if an input or output exception occurs when looking for an image
     * @since 1.10
     */
    @Override
    public void initBackground(int sceneNum) throws IOException {
        Player.setHasBag(false);
        background = new ImageView(new Image(new FileInputStream("assets/images/scene1_nighttime.png")));
        setCanInteract(false);
        setCanExit(true);
        background.setPreserveRatio(true);
        background.fitHeightProperty().bind(scene.heightProperty());
        componentsGroup.getChildren().add(background);
    }

    /**
     * Does not initialize player for cutscene.
     *
     * @param flowSceneNum the number of stage changes so far
     * @since 1.10
     */
    @Override
    public void initStage(int flowSceneNum) {
    }
}
