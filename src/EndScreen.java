import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * The screen which is displayed at the end of the game.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.11 - Julia Xie
 * <br>Date: 2019/06/08
 * <br>Time Spent: 3 hours
 * <br>Added window() method.
 * <br>Added storeResults() method.
 * </p>
 * @since 1.11
 */
public class EndScreen {
    /**
     * The button which brings the player back to the main menu.
     */
    private Button backToMenu;

    /**
     * Creates and formats the scene on the end screen.
     * Adds all buttons and text.
     * @return the end screen scene
     * @since 1.11
     */
    public Scene window() {
        Text text1 = new Text("Results");
        text1.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 60px");
        text1.setFill(Color.WHITE);

        Text text2 = new Text("You managed to improve on " + InterconnectedIsolation.getImproveNum() + " out of 9 aspects of your behaviour.");
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

        backToMenu.setOnAction(e -> InterconnectedIsolation.getWindow().setScene(InterconnectedIsolation.getMainMenu()));

        try {
            storeResults();
        } catch (IOException e) {
        }

        return new Scene(layout1, 1280, 720);
    }

    /**
     * Sorts and then stores the results from the game into a file.
     * @throws IOException
     * @since 1.11
     */
    public void storeResults() throws IOException {
        ArrayList<Record> arr = RecordsList.readRecords();
        arr.add(new Record(Player.getName(), (int) (Math.round(InterconnectedIsolation.getImproveNum() / 9.0 * 100))));

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
        if (arr.size() < 10) {
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
