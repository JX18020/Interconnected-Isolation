import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Creates the main menu scene which appears on the stage.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.0 - Julia Xie
 * <br>Date: 2019/05/24
 * <br>Time spent: 1.5 hours
 * <br>Added window() method.
 * </p>
 * <p>
 * 1.1 - Julia Xie
 * <br>Date: 2019/05/28
 * <br>Time spent: 1 hour
 * <br>Modified window() method.
 * <br>Added getPlayButton() method.
 * <br>Added getInstructionsButton() method.
 * <br>Added getExitButton() method.
 * </p>
 * <p>
 * 1.11 - Julia XIe
 * <br>Date: 2019/06/08
 * <br>Time Spent: 1 hour
 * <br>Modified window() method.
 * <br>Added getRecordsButton() method.
 * </p>
 * @since 1.0
 */
public class MainMenu implements Screen {

    /**
     * The button which leads the user to the choosing name scene.
     */
    private Button playButton;
    /**
     * The button which leads the user to the instructions scene.
     */
    private Button instructionsButton;
    /**
     * The button which leads the user to the player records scene.
     */
    private Button recordsButton;
    /**
     * The button allows the user to exit the game.
     */
    private Button exitButton;

    /**
     * Creates and formats the scene for the main menu.
     * Adds all buttons and text.
     *
     * <p>
     * 1.1 - Julia Xie
     * <br>Set the colours and fonts of the text and buttons.
     * </p>
     * <p>
     * 1.11 - Julia Xie
     * <br>Added a button which leads to a player records screen.
     * </p>
     *
     * @return the main menu scene
     * @since 1.0
     */
    public Scene window() {
        playButton = new Button("Play");
        playButton.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        playButton.setTextFill(Color.WHITE);

        instructionsButton = new Button("How To Play");
        instructionsButton.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 24px");
        instructionsButton.setTextFill(Color.WHITE);

        recordsButton = new Button("Player Records");
        recordsButton.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 24px");
        recordsButton.setTextFill(Color.WHITE);

        exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 24px");
        exitButton.setTextFill(Color.WHITE);

        Text text = new Text("Interconnected Isolation\n");
        text.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 70px");
        text.setFill(Color.WHITE);

        VBox layout1 = new VBox(50);
        layout1.setAlignment(Pos.BOTTOM_CENTER);
        layout1.setPadding(new Insets(10, 10, 100, 10));
        layout1.getChildren().addAll(text, playButton, instructionsButton, recordsButton, exitButton);
        layout1.setStyle("-fx-background-color: black");
        layout1.getStylesheets().add("https://fonts.googleapis.com/css?family=Megrim&display=swap");

        return new Scene(layout1, 1280, 720);
    }

    /**
     * @return the playButton
     * @since 1.1
     */
    public Button getPlayButton() {
        return playButton;
    }

    /**
     * @return the instructionsButton
     * @since 1.1
     */
    public Button getInstructionsButton() {
        return instructionsButton;
    }

    /**
     * @return the recordsButton
     * @since 1.11
     */
    public Button getRecordsButton() {
        return recordsButton;
    }

    /**
     * @return the exitButton
     * @since 1.1
     */
    public Button getExitButton() {
        return exitButton;
    }
}
