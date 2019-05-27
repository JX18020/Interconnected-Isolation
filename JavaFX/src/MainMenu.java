import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainMenu {

    Button playButton, instructionsButton, exitButton;

    public Scene window() {
        playButton = new Button("Play");
        instructionsButton = new Button("How To Play");
        exitButton = new Button("Exit");

        Text text = new Text ("Interconnected Isolation");
        text.setFont(Font.font("Trebuchet MS", 70));
        text.setFill(Color.WHITE);

        VBox layout1 = new VBox(70);
        layout1.setAlignment(Pos.BOTTOM_CENTER);
        layout1.setPadding(new Insets(10, 10, 100, 10));
        layout1.getChildren().addAll(text, playButton, instructionsButton, exitButton);
        layout1.setStyle("-fx-background-color: black");
        return new Scene(layout1, 1280, 720);
    }
}
