import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.*;

public class InterconnectedIsolation extends Application {

    static Stage window;
    Scene splashScreen, mainMenu, instructions, chooseName, level1, level2, level3;

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

        howToPlay.getBackToMenu().setOnAction(e -> window.setScene(mainMenu));
        menu.getPlayButton().setOnAction(e -> window.setScene(chooseName));
        menu.getInstructionsButton().setOnAction(e -> window.setScene(instructions));
        menu.getExitButton().setOnAction(e -> closeProgram());
        choose.getBack().setOnAction(e -> window.setScene(mainMenu));
        choose.getConfirm().setOnAction(e -> new Level1(window, 2298, 720, 2).display());

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
