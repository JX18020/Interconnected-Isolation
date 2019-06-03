import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Creates dialogue in the dialogue box
 *
 * @author Julia Xie
 * @version May 27, 2019
 */
public class Dialogue {

    private Image dialogueBox;
    private ImageView viewDialogueBox;
    private BorderPane layout;

    public Dialogue () {
        try {
            dialogueBox = new Image(new FileInputStream("assets/images/dialogue_box.png"));
        } catch (IOException e){}
        viewDialogueBox = new ImageView(dialogueBox);
        viewDialogueBox.setFitWidth(700);
        viewDialogueBox.setPreserveRatio(true);
        viewDialogueBox.setX(200);
        viewDialogueBox.setY(0);
        layout = new BorderPane();
    }
    public void showDialogue (String dialogue) {
        layout.getChildren().add(viewDialogueBox);
    }
    public void removeDialogue() {
        layout.getChildren().remove(viewDialogueBox);
    }
}
