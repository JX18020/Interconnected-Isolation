import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Creates instructions screen
 *
 * @author Julia Xie
 * @version May 23, 2019
 */
public class Instructions {

    private Button backToMenu;

    public Scene window() {
        Text text1 = new Text("Instructions");
        text1.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 60px");
        text1.setFill(Color.WHITE);

        Text text2 = new Text("\nPress A or left arrow to move left\nPress D or right arrow to move right\nPress E to interact\nPress ENTER or SPACE to progress dialogue\nChoose dialogue using the Z, X, and C keys\n");
        text2.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 40px");
        text2.setFill(Color.WHITE);

        backToMenu = new Button("Main Menu");
        backToMenu.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        backToMenu.setTextFill(Color.WHITE);

        VBox layout1 = new VBox(10);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(text1, text2, backToMenu);
        layout1.setStyle("-fx-background-color: black");
        layout1.getStylesheets().add("https://fonts.googleapis.com/css?family=Megrim&display=swap");

        return new Scene(layout1, 1280, 720);
    }

    public Button getBackToMenu() {
        return backToMenu;
    }
}
