import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Creates main menu screen
 *
 * @author Julia Xie
 * @version May 27, 2019
 */
public class MainMenu {

    private Button playButton, instructionsButton, recordsButton, exitButton;

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

    public Button getPlayButton() {
        return playButton;
    }

    public Button getInstructionsButton() {
        return instructionsButton;
    }

    public Button getRecordsButton() {
        return recordsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
