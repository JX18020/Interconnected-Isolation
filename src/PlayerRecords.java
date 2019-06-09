import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PlayerRecords {

    private Button backToMenu, clear;

    public Scene window() {
        BorderPane layout1 = new BorderPane();
        GridPane layout2 = new GridPane();
        StackPane layout3 = new StackPane();
        HBox layout4 = new HBox(50);
        ArrayList<Record> arr = RecordsList.readRecords();
        Text[] names = new Text[arr.size()];
        Text[] percents = new Text[arr.size()];

        for (int i = 0; i < arr.size(); i++) {
            names[i] = new Text();
            percents[i] = new Text();
            names[i].setText(arr.get(i).getName());
            names[i].setStyle("-fx-font-family: 'Quicksand', sans-serif; -fx-font-size: 20px");
            names[i].setFill(Color.WHITE);
            percents[i].setText(arr.get(i).getPercent() + "");
            percents[i].setStyle("-fx-font-family: 'Quicksand', sans-serif; -fx-font-size: 20px");
            percents[i].setFill(Color.WHITE);
        }

        Text text1 = new Text("Player Records");
        text1.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 60px");
        text1.setFill(Color.WHITE);
        layout3.getChildren().add(text1);
        layout3.setPadding(new Insets(30, 10, 30, 10));

        Text text2 = new Text("Name:");
        text2.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 40px");
        text2.setFill(Color.WHITE);
        layout2.add(text2, 0, 0);

        Text text3 = new Text("Percent Improved:");
        text3.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 40px");
        text3.setFill(Color.WHITE);
        layout2.add(text3, 1, 0);

        for (int i = 0; i < arr.size(); i++) {
            layout2.add(names[i], 0, i + 1);
            layout2.add(percents[i], 1, i + 1);
        }
        layout2.setHgap(80);
        layout2.setVgap(10);

        clear = new Button("Clear Records");
        clear.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        clear.setTextFill(Color.WHITE);


        backToMenu = new Button("Main Menu");
        backToMenu.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        backToMenu.setTextFill(Color.WHITE);

        layout4.getChildren().addAll(clear, backToMenu);
        layout4.setPadding(new Insets(30, 10, 30, 10));

        layout3.setAlignment(Pos.CENTER);
        layout2.setAlignment(Pos.TOP_CENTER);
        layout4.setAlignment(Pos.CENTER);
        layout1.setBottom(layout4);
        layout1.setCenter(layout2);
        layout1.setTop(layout3);
        layout1.setStyle("-fx-background-color: black");
        layout1.getStylesheets().add("https://fonts.googleapis.com/css?family=Megrim&display=swap");
        layout1.getStylesheets().add("https://fonts.googleapis.com/css?family=Quicksand&display=swap");

        backToMenu.setOnAction(e -> InterconnectedIsolation.window.setScene(InterconnectedIsolation.mainMenu));
        clear.setOnAction(e -> {
            InterconnectedIsolation.clearRecords();;
        });

        return new Scene(layout1, 1280, 720);
    }

    public Button getBackToMenu() {
        return backToMenu;
    }

    public Button getClear() {
        return clear;
    }
}
