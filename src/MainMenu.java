import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainMenu {

    private Button playButton, instructionsButton, exitButton;

    public Scene window() {
        playButton = new Button("Play");
        playButton.setFont(Font.font("OCR A Extended", 20));
        playButton.setStyle("-fx-background-color: #3a3a3a");
        playButton.setTextFill(Color.WHITE);

        instructionsButton = new Button("How To Play");
        instructionsButton.setFont(Font.font("OCR A Extended", 20));
        instructionsButton.setStyle("-fx-background-color: #3a3a3a");
        instructionsButton.setTextFill(Color.WHITE);

        exitButton = new Button("Exit");
        exitButton.setFont(Font.font("OCR A Extended", 20));
        exitButton.setStyle("-fx-background-color: #3a3a3a");
        exitButton.setTextFill(Color.WHITE);

        Text text = new Text("Interconnected Isolation\n");
        text.setFont(Font.font("OCR A Extended", 70));
        text.setFill(Color.WHITE);

        VBox layout1 = new VBox(70);
        layout1.setAlignment(Pos.BOTTOM_CENTER);
        layout1.setPadding(new Insets(10, 10, 100, 10));
        layout1.getChildren().addAll(text, playButton, instructionsButton, exitButton);
        layout1.setStyle("-fx-background-color: black");
        return new Scene(layout1, 1280, 720);
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getInstructionsButton() {
        return instructionsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
