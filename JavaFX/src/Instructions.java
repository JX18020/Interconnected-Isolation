import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Instructions {

    Button backToMenu;
    public Scene window() {
        Text text1 = new Text ("Instructions");
        text1.setFont(Font.font("Trebuchet MS", 60));
        text1.setFill(Color.WHITE);
        Text text2 = new Text ("Press A to move left\n\nPress D to move right\n\nPress E to interact");
        text2.setFont(Font.font("Trebuchet MS", 40));
        text2.setFill(Color.WHITE);
        backToMenu = new Button("Main Menu");
        VBox layout1 = new VBox(70);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(text1, text2, backToMenu);
        layout1.setStyle("-fx-background-color: black");
        return new Scene(layout1, 1280, 720);
    }
}
