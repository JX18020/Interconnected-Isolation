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

/**
 * Creates choosing name menu screen
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.4 - Julia Xie
 * <br>Date: 2019/05/31
 * <br>Time Spent: 2 hours
 * <br>Added window() method.
 * <br>Added getName() method.
 * <br>Added getConfirm() method.
 * <br>Added getBack() method.
 * </p>
 * @since 1.4
 */
public class ChooseName implements Screen {

    /**
     * The name entered into the text field.
     */
    private String name;

    /**
     * The confirm and back buttons.
     */
    private Button confirm, back;
    /**
     * The textfield where the user enter's their name.
     */
    private TextField choose;
    /**
     * The Text that shows up if there is an error.
     */
    private Text error;

    /**
     * Creates and formats the screen for the choosing name scene.
     * Adds all buttons and text.
     *
     * @return the choosing name scene
     * @since 1.4
     */
    public Scene window() {

        error = new Text("Your name may not contain spaces, special characters or numbers.");
        error.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 24px");
        error.setFill(Color.BLACK);

        Text prompt = new Text("Enter your name:");
        prompt.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 50px");
        prompt.setFill(Color.WHITE);

        choose = new TextField();
        choose.setAlignment(Pos.CENTER);

        confirm = new Button("Confirm");
        confirm.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 24px");
        confirm.setTextFill(Color.WHITE);

        back = new Button("Back to Menu");
        back.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 24px");
        back.setTextFill(Color.WHITE);

        VBox layout1 = new VBox(100);

        FlowPane layout2 = new FlowPane();
        layout2.setHgap(100);
        layout2.getChildren().addAll(back, confirm);
        layout2.setAlignment(Pos.CENTER);

        layout1.setAlignment(Pos.CENTER);
        layout1.setPadding(new Insets(10, 400, 10, 400));
        layout1.getChildren().addAll(prompt, error, choose, layout2);
        layout1.setStyle("-fx-background-color: black");
        layout1.getStylesheets().add("https://fonts.googleapis.com/css?family=Megrim&display=swap");

        return new Scene(layout1, 1280, 720);
    }

    /**
     * @return the name from the text field
     * @since 1.4
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the confirm button
     * @since 1.4
     */
    public Button getConfirm() {
        return confirm;
    }

    /**
     * @return the back button
     * @since 1.4
     */
    public Button getBack() {
        return back;
    }

    /**
     * @return the choose textfield
     * @since 1.11
     */
    public TextField getChoose() {
        return choose;
    }

    /**
     * @return the error text
     * @since 1.11
     */
    public Text getError() {
        return error;
    }
}
