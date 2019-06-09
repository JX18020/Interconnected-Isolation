import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Creates splash screen scene which appears on the stage.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.0 - Julia Xie
 * <br>Date: 2019/05/28
 * <br>Time Spent: 1 hour
 * <br>Added window() method.
 * </p>
 * <p>
 * 1.1 - Julia Xie
 * <br>Date: 2019/05/28
 * <br>Time Spent: 1 hour
 * <br>Modified window() method.
 * </p>
 * @since 1.0
 */
public class SplashScreen implements Screen {

    /**
     * Creates and formats the scene for the splash screen.
     * Adds all buttons and text.
     *
     * <p>
     * 1.1 - Julia Xie
     * <br>Added a fade in and fade out in the scene.
     * </p>
     *
     * @return the splash screen scene
     * @since 1.0
     */
    public Scene window() throws IOException {
        Image image = new Image(new FileInputStream("assets/images/splash_screen.png"));

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(720);
        imageView.setPreserveRatio(true);

        Group root = new Group(imageView);

        VBox layout = new VBox();
        layout.getChildren().add(imageView);
        root.getChildren().add(layout);

        FadeTransition ft = new FadeTransition(Duration.millis(2000), layout);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();

        return new Scene(root, 1280, 720, Color.BLACK);
    }
}
