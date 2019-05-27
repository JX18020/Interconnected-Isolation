import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;

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
        return new Scene (root, 1280, 720);
    }
}
