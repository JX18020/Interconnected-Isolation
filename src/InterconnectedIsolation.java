import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

/**
 * Creates the main stage for the game where all of the scenes are added.
 * Controls the flow of the game.
 *
 * @author Julia Xie
 * @version 1.11
 * <p>
 * 1.0 - Julia Xie
 * <br>Date: 2019/05/24
 * <br>Time Spent: 2 hours
 * <br>Added start() method.
 * <br>Added closeProgram() method.
 * <br>Added main method.
 * </p>
 * <p>
 * 1.4 - Julia Xie
 * <br>Date: 2019/05/31
 * <br>Time Spent: 1 hour
 * <br>Modified start() method.
 * </p>
 * <p>
 * 1.11 - Julia Xie
 * <br>Date: 2019/06/08
 * <br>Time Spent: 1 hour
 * <br>Modified start() method.
 * <br>Added clearRecord() method.
 * </p>
 * @since 1.0
 */
public class InterconnectedIsolation extends Application {

    /**
     * The main stage where all the scenes appear.
     */
    private static Stage window;
    /**
     * The splash screen scene which appears in the stage.
     */
    private Scene splashScreen;
    /**
     * The instructions scene which appears in the stage.
     */
    private Scene instructions;
    /**
     * The chooing name scene which appears in the stage.
     */
    private Scene chooseName;
    /**
     * The main menu scene which appears in the stage.
     */
    private static Scene mainMenu;
    /**
     * The number of aspects the player chooses improve.
     * Acts like a score.
     */
    private static int improveNum;

    /**
     * Creates all instances of screens and controls their flow.
     * It also controls all of the button functions.
     * <p>
     * 1.4 - Julia Xie
     * <br>Added an errortrap for the chooing name scene so that the user cannot enter special characters or numbers.
     * </p>
     * <p>
     * 1.11 - Julia Xie
     * <br>Finalized the flow of the game.
     * </p>
     *
     * @param primaryStage the main stage that the scenes appear on
     * @throws Exception if any exception occurs
     * @since 1.0
     */
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
        records.window();

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

    /**
     * Creates a ConfirmBox which asks if the user really wants to exit.
     * The window only closes if the user chooses to click the yes button.
     *
     * @since 1.0
     */
    public void closeProgram() {
        boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer)
            window.close();
    }

    /**
     * Creates a ConfirmBox which asks if the user really wants to clear all records.
     * The window only closes if the user chooses to click the yes button.
     *
     * @since 1.11
     */
    public static void clearRecords() {
        boolean answer = ConfirmBox.display("Clear Records", "Are you sure you want to clear all records?");
        if (answer) {
            RecordsList.clearRecords();
            window.setScene(mainMenu);
        }
    }

    /**
     * @return the main stage
     * @since 1.11
     */
    public static Stage getWindow() {
        return window;
    }

    /**
     * @return the main menu scene
     * @since 1.11
     */
    public static Scene getMainMenu() {
        return mainMenu;
    }

    /**
     * @return the number of improvements
     * @since 1.11
     */
    public static int getImproveNum() {
        return improveNum;
    }

    /**
     * @param num the number of improvements
     * @since 1.11
     */
    public static void setImproveNum(int num) {
        improveNum = num;
    }

    /**
     * Launches the program.
     *
     * @param args an array of String which contain command-line arguments
     * @since 1.0
     */
    public static void main(String[] args) {
        launch(args);
    }
}
