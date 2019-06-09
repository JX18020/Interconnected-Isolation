import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

/**
 * Main class which runs the flow of the game
 *
 * @author Julia Xie
 * @version June 3, 2019
 */
public class InterconnectedIsolation extends Application {

    static Stage window;
    Scene splashScreen, instructions, playerRecords, chooseName;
    static Scene mainMenu;
    static int improveNum;

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        window.setTitle("Interconnected Isolation");

        SplashScreen splash = new SplashScreen();
        splashScreen = splash.window();

        Instructions howToPlay = new Instructions();
        instructions = howToPlay.window();

        MainMenu menu = new MainMenu();
        mainMenu = menu.window();

        PlayerRecords records = new PlayerRecords();
        playerRecords = records.window();

        ChooseName choose = new ChooseName();
        chooseName = choose.window();

        howToPlay.getBackToMenu().setOnAction(e -> window.setScene(mainMenu));
        menu.getPlayButton().setOnAction(e -> window.setScene(chooseName));
        menu.getInstructionsButton().setOnAction(e -> window.setScene(instructions));
        menu.getRecordsButton().setOnAction(e -> window.setScene(records.window()));
        menu.getExitButton().setOnAction(e -> closeProgram());
        records.getClear().setOnAction(e -> {
            clearRecords();
            window.setScene(records.window());
        });
        choose.getBack().setOnAction(e -> {
            choose.getError().setFill(Color.BLACK);
            window.setScene(mainMenu);
        });
        choose.getConfirm().setOnAction(e -> {
            boolean error = true;
            choose.setName(choose.getChoose().getText());
            for (int i = 0; i < choose.getName().length(); i++) {
                if ((choose.getName().charAt(i) >= 65 && choose.getName().charAt(i) <= 90 || choose.getName().charAt(i) >= 97 && choose.getName().charAt(i) <= 122) || choose.getName().charAt(i) == 39 && choose.getName().charAt(i) == 45) {
                    error = false;
                }
            }
            if (!error) {
                Player.setName(choose.getName());
                improveNum = 0;
                new Level1(window, 2298, 720, 2, 1).display();
            } else {
                choose.getError().setFill(Color.WHITE);
            }
        });

        chooseName.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.ENTER) choose.getConfirm().fire();
        });

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> window.setScene(mainMenu));
            }
        };
        window.setScene(splashScreen);
        window.show();
        timer.schedule(task, 4000l);
        window.show();
    }

    public void closeProgram() {
        boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer)
            window.close();
    }

    public static void clearRecords() {
        boolean answer = ConfirmBox.display("Clear Records", "Are you sure you want to clear all records?");
        if (answer) {
            RecordsList.clearRecords();
            window.setScene(mainMenu);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
