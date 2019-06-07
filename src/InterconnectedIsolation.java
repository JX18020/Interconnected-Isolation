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
    Scene splashScreen, mainMenu, instructions, chooseName;

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

        ChooseName choose = new ChooseName();
        chooseName = choose.window();

        Level1 level1 = new Level1(window, 2298, 720, 2, 1);
        Level1 l1 = new Level1(window, 2405, 720, 1, 2);
        Cutscene cut = new Cutscene(window, 2405, 720, 1, 3);


        howToPlay.getBackToMenu().setOnAction(e -> window.setScene(mainMenu));
        menu.getPlayButton().setOnAction(e -> window.setScene(chooseName));
        menu.getInstructionsButton().setOnAction(e -> window.setScene(instructions));
        menu.getExitButton().setOnAction(e -> closeProgram());
        choose.getBack().setOnAction(e -> {
            choose.getError().setFill(Color.BLACK);
            window.setScene(mainMenu);
        });
        choose.getConfirm().setOnAction(e -> {
            boolean error = false;
            choose.setName(choose.getChoose().getText());
            for (int i = 0; i < choose.getName().length(); i++) {
                if ((choose.getName().charAt(i) < 65 || choose.getName().charAt(i) > 90 && choose.getName().charAt(i) < 97 || choose.getName().charAt(i) > 122) && choose.getName().charAt(i) != 32 && choose.getName().charAt(i) != 39 && choose.getName().charAt(i) != 45) {
                    choose.getError().setFill(Color.WHITE);
                    error = true;
                }
            }
            if (!error) {
                level1.display();
                //l1.display();
                //cut.display();
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

    public static void closeProgram() {
        boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer)
            window.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
