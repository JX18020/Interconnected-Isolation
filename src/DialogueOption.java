import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Creates dialogue options in 3 different dialogue boxes.
 *
 * @author Chris Trevisan
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.9 - Chris Trevisan
 * <br>Date: 2019/06/06
 * <br>Time Spent: 1 hour
 * <br>Added constructor
 * <br>Added setOption() method.
 * </p>
 * @since 1.9
 */
public class DialogueOption {
    private Image OptionBox;
    private ImageView OptionView;
    Group optionGroup;
    private int y;

    /**
     * Constructor for the DialogueOption class.
     * Creates 3 dialogue option boxes and adds them to a group.
     * @param option the dialogue option
     * @since 1.9
     */
    public DialogueOption(char option) {
        try {
            OptionBox = new Image(new FileInputStream("assets/images/choice_" + option + ".png"));
        } catch (IOException e) {
        }
        OptionView = new ImageView(OptionBox);
        OptionView.setFitWidth(700);
        OptionView.setPreserveRatio(true);
        OptionView.setX(280);
        OptionView.setY(y = (490 + (45 * (option - 'a' + 2))));
        optionGroup = new Group(OptionView);
    }

    /**
     * Sets the option to the specified string.
     * @param s the string to be set as an option
     * @since 1.9
     */
    public void setOption(String s) {
        Text text = new Text(s);
        text.setX(300);
        text.setY(y + 30);
        text.setStyle("-fx-font-family: 'Quicksand', sans-serif; -fx-font-size: 18");
        text.setWrappingWidth(670);
        optionGroup = new Group(OptionView, text);
        optionGroup.getStylesheets().add("https://fonts.googleapis.com/css?family=Quicksand&display=swap");
    }
}
