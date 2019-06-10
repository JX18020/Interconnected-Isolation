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
 * Initializes the proper background and scene for Level3.
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
 * <br>Date: 2019/05/29
 * <br>Time Spent: 30 minutes
 * <br>Added constructor.
 * <br>Added initBackground() method.
 * <br>Added initStage() method.
 * </p>
 * <p>
 * 1.11 - Julia Xie
 * <br>Date: 2019/06/08
 * <br>Time Spent: 2 hours
 * <br>Modified initBackground() method.
 * </p>
 * @since 1.1
 */
public class Level3 extends GameLoop {

    /**
     * The image of the background.
     */
    private ImageView background;

    /**
     * The background rectangle.
     */
    private Rectangle rect;

    /**
     * Whether the scene has homework, trash, plates, or the player is playing games.
     */
    private static boolean hasHomework, hasTrash, hasPlates, playedGames;

    /**
     * Constructor for Level3.
     * Calls the constructor of the superclass and sets the width and height of the stage.
     *
     * @param primaryStage the stage which the scene appears on
     * @param scrollable   whether or not the scene can be scrolled
     * @param width        the width of the stage
     * @param height       the height of the stage
     * @param scene        the scene number
     * @param flowSceneNum the number of scene changes
     * @param hasHomework  whether or not the level has homework
     * @param hasPlates    whether or not the level has plates
     * @param hasTrash     whether or not the level has trash
     * @param playedGames  whether or not the player is playing games
     * @since 1.3
     */
    public Level3(Stage primaryStage, boolean scrollable, int width, int height, int scene, int flowSceneNum, boolean hasHomework, boolean hasPlates, boolean hasTrash, boolean playedGames) {
        super(primaryStage, scrollable, scene, flowSceneNum);
        setStageWidth(width);
        setStageHeight(height);
        this.hasHomework = hasHomework;
        this.hasPlates = hasPlates;
        this.hasTrash = hasTrash;
        this.playedGames = playedGames;
    }

    /**
     * Initializes the background with a picture or colour.
     * Sets constraints based on the scene number.
     * <p>
     * 1.11 - Julia Xie
     * <br>Added a "a few months later" fade in screen.
     * </p>
     *
     * @param sceneNum the scene number
     * @throws IOException if an input or output exception occurs when looking for an image
     * @since 1.2
     */
    @Override
    public void initBackground(int sceneNum) throws IOException {
        Player.setHasBag(true);
        if (sceneNum == 2) {
            background = new ImageView(new Image(new FileInputStream("assets/images/scene2_door_open.png")));
            setCanInteract(false);
            setCanExit(true);
        } else if (sceneNum == 1) {
            background = new ImageView(new Image(new FileInputStream("assets/images/scene1_daytime.png")));
            setCanInteract(true);
            setCanExit(false);
        } else {
            rect = new Rectangle(1280, 720, Color.BLACK);
            Text text = new Text("A few months later...");
            text.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 50px");
            text.setTextAlignment(TextAlignment.CENTER);
            text.setFill(Color.WHITE);
            StackPane layout = new StackPane();
            layout.getChildren().addAll(rect, text);
            root.getChildren().addAll(layout);
        }
        if (sceneNum == 1 || sceneNum == 2) {
            background.setPreserveRatio(true);
            background.fitHeightProperty().bind(scene.heightProperty());
            componentsGroup.getChildren().add(background);
        }
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
        if (flowSceneNum == 9 || flowSceneNum == 10) {
            player = new Player(componentsGroup, background.getFitWidth(), background.getFitHeight());
            player.reposition(150, scene.getHeight() - 370);
        }
    }

    /**
     * @return if the scene has homework
     * @since 1.11
     */
    public static boolean isHasHomework() {
        return hasHomework;
    }

    /**
     * @return if the scene has plates
     * @since 1.11
     */
    public static boolean isHasPlates() {
        return hasPlates;
    }

    /**
     * @return if the scene has trash
     * @since 1.11
     */
    public static boolean isHasTrash() {
        return hasTrash;
    }

    /**
     * @return if the player played game
     * @since 1.11
     */
    public static boolean isPlayedGames() {
        return playedGames;
    }
}
