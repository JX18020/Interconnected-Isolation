import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
public class EndScreen {
    private Button backToMenu;

    public Scene window() {
        Text text1 = new Text("Results");
        text1.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 60px");
        text1.setFill(Color.WHITE);

        Text text2 = new Text("You managed to improve on " + InterconnectedIsolation.improveNum + " out of 9 aspects of your behaviour.");
        text2.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 40px");
        text2.setFill(Color.WHITE);

        backToMenu = new Button("Main Menu");
        backToMenu.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        backToMenu.setTextFill(Color.WHITE);

        VBox layout1 = new VBox(70);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(text1, text2, backToMenu);
        layout1.setStyle("-fx-background-color: black");
        layout1.getStylesheets().add("https://fonts.googleapis.com/css?family=Megrim&display=swap");

        backToMenu.setOnAction(e -> InterconnectedIsolation.window.setScene(InterconnectedIsolation.mainMenu));

        return new Scene(layout1, 1280, 720);
    }

    public Button getBackToMenu() {
        return backToMenu;
    }
}
