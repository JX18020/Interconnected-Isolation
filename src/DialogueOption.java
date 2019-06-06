import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;

public class DialogueOption {
    private Image OptionBox;
    private ImageView OptionView;
    Group optionGroup;
    private int y;
    public DialogueOption (char option) {
        try {
            OptionBox = new Image(new FileInputStream("assets/images/choice_" + option + ".png"));
        } catch (IOException e) {}
        OptionView = new ImageView(OptionBox);
        OptionView.setFitWidth(700);
        OptionView.setPreserveRatio(true);
        OptionView.setX(280);
        OptionView.setY(y = ((400+((69-9)*(option-'a'+2)))+10));
        optionGroup = new Group(OptionView);
    }
    public void setOption (String s) {
        Text text = new Text(s);
        text.setX(300);
        text.setY(y+30);
        text.setFont(Font.font("OCR A Extended", 17));
        text.setWrappingWidth(670);
        optionGroup = new Group(OptionView, text);
    }
}
