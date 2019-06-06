import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    Group dialogueGroup;

    public Dialogue () {
        try {
            dialogueBox = new Image(new FileInputStream("assets/images/dialogue_box.png"));
        } catch (IOException e){}
        viewDialogueBox = new ImageView(dialogueBox);
        viewDialogueBox.setFitWidth(700);
        viewDialogueBox.setPreserveRatio(true);
        viewDialogueBox.setX(280);
        viewDialogueBox.setY(400);
        dialogueGroup = new Group (viewDialogueBox);
    }

    public void setDialogue(String dialogue) {
        Text text = new Text(dialogue);
        text.setX(300);
        text.setY(430);
        text.setFont(Font.font("OCR A Extended", 17));
        text.setWrappingWidth(670);
        dialogueGroup = new Group(viewDialogueBox, text);
    }
}
