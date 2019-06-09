import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controls the flow of Level3
 *
 * @author Julia Xie
 * @version June 3
 */
public class EndScreen {
    private Button backToMenu;

    public Scene window() {
        Text text1 = new Text("Results");
        text1.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 60px");
        text1.setFill(Color.WHITE);

        Text text2 = new Text("You managed to improve on " + InterconnectedIsolation.improveNum + " out of 9 aspects of your behaviour.");
        text2.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 40px");
        text2.setFill(Color.WHITE);

        backToMenu = new Button("Main Menu");
        backToMenu.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        backToMenu.setTextFill(Color.WHITE);

        VBox layout1 = new VBox(70);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(text1, text2, backToMenu);
        layout1.setStyle("-fx-background-color: black");
        layout1.getStylesheets().add("https://fonts.googleapis.com/css?family=Megrim&display=swap");

        backToMenu.setOnAction(e -> InterconnectedIsolation.window.setScene(InterconnectedIsolation.mainMenu));

        try {
            storeResults();
        } catch (IOException e) {
        }

        return new Scene(layout1, 1280, 720);
    }

    public void storeResults() throws IOException {
        ArrayList<Record> arr = RecordsList.readRecords();
        arr.add(new Record(Player.getName(), (int) (Math.round(InterconnectedIsolation.improveNum / 9.0 * 100))));

        if (arr.size() > 1) {
            int n = arr.size();
            for (int i = 1; i < n; i++) {
                Record key = arr.get(i);

                int j = i - 1;
                while (j >= 0 && arr.get(j).getPercent() < key.getPercent()) {
                    arr.set(j + 1, arr.get(j));
                    j = j - 1;
                }
                arr.set(j + 1, key);
            }
        }

        PrintWriter out = new PrintWriter(new FileWriter("assets/files/records.txt"));
        if (arr.size()< 10) {
            for (int i = 0; i < arr.size(); i++) {
                out.println(arr.get(i));
            }
        } else {
            for (int i = 0; i < 10; i++) {
                out.println(arr.get(i));
            }
        }

        out.close();
    }
}
