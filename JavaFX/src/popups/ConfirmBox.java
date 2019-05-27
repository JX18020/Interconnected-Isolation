package popups;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);

        Label label = new Label();
        label.setText(message);
        label.setPadding(new Insets(10, 10, 10, 10));
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
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
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
