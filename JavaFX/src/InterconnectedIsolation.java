import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;

public class InterconnectedIsolation extends Application {

    Stage window;
    Scene splashScreen, mainMenu, instructions, level1, level2, level3;

    Random rnd = new Random();

    Pane playfieldLayer;

    List<Player> player = new ArrayList<>();

    Image playerImage;

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

        menu.getPlayButton().setOnAction(e -> window.setScene(level1));
        menu.getInstructionsButton().setOnAction(e -> window.setScene(instructions));
        menu.getExitButton().setOnAction(e -> closeProgram());

        howToPlay.getBackToMenu().setOnAction(e -> window.setScene(mainMenu));

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> window.setScene(mainMenu));
            }
        };
        window.setScene(splashScreen);
        window.show();
        timer.schedule(task, 4000l);

        Group root = new Group();
        playfieldLayer = new Pane();

        root.getChildren().add(playfieldLayer);

        level1 = new Scene (root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        window.setScene(level1);
        loadGame();
        createPlayer();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.forEach(sprite -> sprite.processInput());

                player.forEach(sprite -> sprite.move());

                player.forEach(sprite -> sprite.updateUI());

            }
        };

        gameLoop.start();
    }

    private void loadGame() {
        playerImage = new Image(getClass().getResource("player.png").toExternalForm());
    }

    private void createPlayer() {
        Input input = new Input(scene);
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
