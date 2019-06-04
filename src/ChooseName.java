import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *  Creates choosing name menu screen
 *
 * @author Julia Xie
 * @version May 31, 2019
 */
public class ChooseName {

    private String name;

    Button confirm, back;
    private TextField choose;
    private Text error;

    public Scene window() {

        error = new Text ("Your name may not contain special characters or numbers.");
        error.setFont(Font.font("OCR A Extended", 20));
        error.setFill(Color.BLACK);

        Text prompt = new Text ("Enter your name:");
        prompt.setFont(Font.font("OCR A Extended", 50));
        prompt.setFill(Color.WHITE);

        choose = new TextField();
        choose.setAlignment(Pos.CENTER);
        System.out.println(choose.getCharacters());

        confirm = new Button("Confirm");
        confirm.setFont(Font.font("OCR A Extended", 20));
        confirm.setStyle("-fx-background-color: #3a3a3a");
        confirm.setTextFill(Color.WHITE);

        back = new Button ("Back to Menu");
        back.setFont(Font.font("OCR A Extended", 20));
        back.setStyle("-fx-background-color: #3a3a3a");
        back.setTextFill(Color.WHITE);

        VBox layout1 = new VBox (100);

        FlowPane layout2 = new FlowPane();
        layout2.setHgap(100);
        layout2.getChildren().addAll(back, confirm);
        layout2.setAlignment(Pos.CENTER);

        layout1.setAlignment(Pos.CENTER);
        layout1.setPadding(new Insets(10, 400, 10, 400));
        layout1.getChildren().addAll(prompt, error, choose, layout2);
        layout1.setStyle("-fx-background-color: black");

        return new Scene (layout1, 1280, 720);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button getConfirm() {
        return confirm;
    }

    public Button getBack() {
        return back;
    }

    public TextField getChoose() {
        return choose;
    }

    public Text getError() {
        return error;
    }
}
