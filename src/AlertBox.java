import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Creates an alert popup
 *
 * @author Julia XIe
 * @version May 17, 2019
 */
public class AlertBox {
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);

        Label label = new Label();
        label.setText(message);
        label.setPadding(new Insets(10, 10, 10, 10));
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("OCR A Extended", 16));
        Button closeButton = new Button("Close");
        closeButton.setFont(Font.font("OCR A Extended", 16));
        closeButton.setStyle("-fx-background-color: #3a3a3a");
        closeButton.setTextFill(Color.WHITE);
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: black");
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
