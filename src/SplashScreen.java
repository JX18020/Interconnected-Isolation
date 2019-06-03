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
 * Creates a splash screen
 *
 * @author Julia Xie
 * @version May 31, 2019
 */
public class SplashScreen {

    public Scene window () throws IOException {
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
        return new Scene (root, 1280, 720, Color.BLACK);
    }
}
