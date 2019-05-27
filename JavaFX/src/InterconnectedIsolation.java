import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import popups.ConfirmBox;

import java.util.Timer;
import java.util.TimerTask;

public class InterconnectedIsolation extends Application {

    Stage window;
    Scene splashScreen, mainMenu, instructions, scene1, scene2;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Interconnected Isolation");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        SplashScreen splash = new SplashScreen();
        splashScreen = splash.window();

        MainMenu menu = new MainMenu();
        mainMenu = menu.window();

        Instructions howToPlay = new Instructions();
        instructions = howToPlay.window();

        menu.playButton.setOnAction(e -> window.setScene(scene1));
        menu.instructionsButton.setOnAction(e -> window.setScene(instructions));
        menu.exitButton.setOnAction(e -> closeProgram());

        howToPlay.backToMenu.setOnAction(e -> window.setScene(mainMenu));

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> window.setScene(mainMenu));
            }
        };
        window.setScene(splashScreen);
        window.show();
        timer.schedule(task, 4000l);
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Exit", "Are you sure you want to exit?");
        if (answer)
            window.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
