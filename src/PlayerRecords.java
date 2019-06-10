import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Creates a scene which displays all of the player records.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.11 - Julia Xie
 * <br>Date: 2019/06/08
 * <br>Time Spent: 1 hour
 * <br>Added window() scene.
 * <br>Added getClear() method.
 * </p>
 * @since 1.11
 */
public class PlayerRecords implements Screen {

    /**
     * Buttons which the user can click.
     */
    private Button backToMenu, clear, name, percent;
    private boolean increasing;
    private ArrayList<Record> arr;
    private Text[] names,percents;
    private BorderPane layout1;
    private GridPane layout2;
    private StackPane layout3;
    private HBox layout4;
    private Text text1,text2,text3;
    private boolean nameLast, percentLast;
    private static void mergesort(ArrayList<Record> arr,int l, int r, Comparator<Record> comp) {
        if (l >= r) return;
        int mid = (l+r)>>1;
        mergesort(arr,l,mid,comp);
        mergesort(arr,mid+1,r,comp);
        ArrayList<Record> sorted = new ArrayList<>();
        int firstl = l, firstr = mid+1;
        while (firstl <= mid || firstr <= r) {
            if (firstr > r || (firstl <= mid && comp.compare(arr.get(firstl),arr.get(firstr)) < 0)) sorted.add(arr.get(firstl++));
            else sorted.add(arr.get(firstr++));
        }
        for (int x = l; x <= r; x++) arr.set(x,sorted.get(x-l));
    }
    /**
     * Creates and formats the scene for the player records scene.
     * Adds all buttons and text.
     *
     * @return the player records scene
     * @since 1.11
     */
    private void reWrite () {
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
        layout2 = new GridPane();
        layout2.add(text2, 0, 0);
        layout2.add(text3, 1, 0);
        for (int i = 0; i < arr.size(); i++) {
            layout2.add(names[i], 0, i + 1);
            layout2.add(percents[i], 1, i + 1);
        }
        layout2.setHgap(80);
        layout2.setVgap(10);
        layout2.setAlignment(Pos.TOP_CENTER);
        layout1.setCenter(layout2);
        percent.setMinWidth(300);
        name.setMinWidth(300);
        percent.setText(percentLast ? (increasing ? "Sort by Percent v" : "Sort by Percent ^") : "Sort by Percent");
        name.setText(nameLast ? (increasing ? "Sort by Name v" : "Sort by Name ^") : "Sort by Name");
    }
    public Scene window() {
        nameLast = percentLast = false;
        layout1 = new BorderPane();
        layout2 = new GridPane();
        layout3 = new StackPane();
        layout4 = new HBox(50);
        arr = RecordsList.readRecords();
        names = new Text[arr.size()];
        percents = new Text[arr.size()];
        increasing = true;

        text1 = new Text("Player Records");
        text1.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 60px");
        text1.setFill(Color.WHITE);
        layout3.getChildren().add(text1);
        layout3.setPadding(new Insets(30, 10, 30, 10));

        text2 = new Text("Name:");
        text2.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 40px");
        text2.setFill(Color.WHITE);

        text3 = new Text("Percent Improved:");
        text3.setStyle("-fx-font-family: 'Megrim', cursive; -fx-font-size: 40px");
        text3.setFill(Color.WHITE);

        clear = new Button("Clear Records");
        clear.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        clear.setTextFill(Color.WHITE);


        name = new Button("Sort by Name ^");
        name.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        name.setTextFill(Color.WHITE);

        percent = new Button("Sort by Percent ^");
        percent.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        percent.setTextFill(Color.WHITE);

        backToMenu = new Button("Main Menu");
        backToMenu.setStyle("-fx-background-color: #3a3a3a; -fx-font-family: 'Megrim', cursive; -fx-font-size: 30px");
        backToMenu.setTextFill(Color.WHITE);
        reWrite();
        layout4.getChildren().addAll(clear, backToMenu,name,percent);
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

        backToMenu.setOnAction(e -> InterconnectedIsolation.getWindow().setScene(InterconnectedIsolation.getMainMenu()));
        clear.setOnAction(e -> {
            InterconnectedIsolation.clearRecords();
            ;
        });
        name.setOnAction(e -> {
            mergesort(arr, 0, arr.size() - 1, new Comparator<Record>() {
                @Override
                public int compare(Record o1, Record o2) {
                    return increasing ? o1.getName().compareTo(o2.getName()) : o2.getName().compareTo(o1.getName());
                }
            });
            increasing = !increasing;
            nameLast = true; percentLast = false;
            reWrite();
        });
        percent.setOnAction(e -> {
            mergesort(arr, 0, arr.size() - 1, new Comparator<Record>() {
                @Override
                public int compare(Record o1, Record o2) {
                    return increasing ? Integer.compare(o1.getPercent(),o2.getPercent()) : Integer.compare(o2.getPercent(),o1.getPercent());
                }
            });
            increasing = !increasing;
            percentLast = true; nameLast = false;
            reWrite();
        });
        return new Scene(layout1, 1280, 720);
    }

    /**
     * @return the clear button
     * @since 1.11
     */
    public Button getClear() {
        return clear;
    }
}
