import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Creates dialogue in the dialogue box.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.8 - Julia Xie
 * <br>Date: 2019/06/05
 * <br>Time Spent: 1 hour
 * <br>Added constructor.
 * <br>Added setDialogue() method.
 * </p>
 * @since 1.8
 */
public class Dialogue {

    /**
     * The image of the dialogue box.
     */
    private Image dialogueBox;
    /**
     * The view of the image of the dialogue box.
     */
    private ImageView viewDialogueBox;
    /**
     * A group which contains the image of the dialogue box and the dialogue.
     */
    Group dialogueGroup;

    /**
     * Constructor for the Dialogue class.
     * Initializes the dialogueBox with an image and positions the image in the correct spot.
     *
     * @since 1.8
     */
    public Dialogue() {
        try {
            dialogueBox = new Image(new FileInputStream("Interconnected-Isolation/assets/images/dialogue_box.png"));
        } catch (IOException e) {
        }
        viewDialogueBox = new ImageView(dialogueBox);
        viewDialogueBox.setFitWidth(700);
        viewDialogueBox.setPreserveRatio(true);
        viewDialogueBox.setX(280);
        viewDialogueBox.setY(580);
        dialogueGroup = new Group(viewDialogueBox);
    }

    /**
     * Groups the dialogue box and the given dialogue so it can be shown.
     *
     * @param dialogue the given dialogue
     * @since 1.8
     */
    public void setDialogue(String dialogue) {
        Text text = new Text(dialogue);
        text.setX(300);
        text.setY(610);
        text.setStyle("-fx-font-family: 'Quicksand', sans-serif; -fx-font-size: 18");
        text.setWrappingWidth(670);
        dialogueGroup = new Group(viewDialogueBox, text);
        dialogueGroup.getStylesheets().add("https://fonts.googleapis.com/css?family=Quicksand&display=swap");
    }
}
