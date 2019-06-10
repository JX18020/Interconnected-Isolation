import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Creates a popup which asks for confirmation from the user.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.0 - Julia Xie
 * <br>Date: 2019/05/24
 * <br>Time spent: 30 minutes
 * <br>Added display() method.
 * </p>
 * <p>
 * 1.1 - Julia Xie
 * <br>Date: 2019/05/28
 * <br>Time spent: 30 minutes
 * <br>Modified display() method.
 * </p>
 * @since 1.0
 */
public class ConfirmBox {

    /**
     * Stores whether the user clicked yes or no.
     */
    private static boolean answer;

    /**
     * Creates the layout of the popup and displays it.
     * <p>1.1 - Changed the colours and fonts of the text and buttons.</p>
     *
     * @param title   the title of the window
     * @param message the message displayed in the box
     * @return whether the user clicked yes or no
     * @since 1.0
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);

        Label label = new Label();
        label.setText(message);
        label.setPadding(new Insets(10, 10, 10, 10));
        label.setStyle("-fx-font-family: 'Quicksand', sans-serif; -fx-font-size: 18px");
        label.setTextFill(Color.WHITE);

        Button yesButton = new Button("Yes");
        yesButton.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Quicksand', sans-serif; -fx-font-size: 18px");
        yesButton.setTextFill(Color.WHITE);

        Button noButton = new Button("No");
        noButton.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Quicksand', sans-serif; -fx-font-size: 18px");
        noButton.setTextFill(Color.WHITE);

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        FlowPane layout2 = new FlowPane();
        layout2.getChildren().addAll(yesButton, noButton);
        layout2.setAlignment(Pos.BOTTOM_CENTER);
        layout2.setPadding(new Insets(10, 10, 10, 10));
        layout2.setHgap(20);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, layout2);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: black");
        layout.getStylesheets().add("https://fonts.googleapis.com/css?family=Quicksand&display=swap");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
