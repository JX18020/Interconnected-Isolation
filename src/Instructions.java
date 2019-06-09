import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Creates scene which displays instructions.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.0 - Julia Xie
 * <br>Date: 2019/05/24
 * <br>Time Spent: 1.5 hours
 * <br>Added window() method.
 * </p>
 * <p>
 * 1.1 - Julia Xie
 * <br>Date: 2019/05/24
 * <br>Time Spent: 30 minutes
 * <br>Modified window() method.
 * <br>Added getBackToMenu() method.
 * </p>
 * <p>
 * 1.9 - Julia Xie
 * <br>Date:2019/06/06
 * <br>Time Spent: 10 minutes
 * <br>Modified window() method.
 * </p>
 * @since 1.0
 */
public class Instructions implements Screen {

    /**
     * The button which allows the user to return to the main menu.
     */
    private Button backToMenu;

    /**
     * Creates and formats the scene for the instructions screen.
     * Adds all buttons and text.
     * <p>
     * 1.1 - Julia Xie
     * <br>Changed the fonts and colours of the buttons and text.
     * </p>
     * <p>
     * 1.9 - Julia Xie
     * <br>Added more instructions to reflect the mechanics of the game.
     * </p>
     *
     * @return the instructions scene
     * @since 1.0
     */
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

    /**
     * @return the backToMenu button
     * @since 1.1
     */
    public Button getBackToMenu() {
        return backToMenu;
    }
}
