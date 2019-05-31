import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ChooseName {

    String name;

    private Button confirm, back;

    public Scene window() {

        Text prompt = new Text ("Enter your name:");
        prompt.setFont(Font.font("OCR A Extended", 50));
        prompt.setFill(Color.WHITE);

        TextField choose = new TextField();

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
        layout1.getChildren().addAll(prompt, choose, layout2);
        layout1.setStyle("-fx-background-color: black");

        return new Scene (layout1, 1280, 720);
    }

    public String getName() {
        return name;
    }

    public Button getConfirm() {
        return confirm;
    }

    public Button getBack() {
        return back;
    }
}
