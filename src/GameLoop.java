import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public abstract class GameLoop {
    Stage stage;
    Scene scene;
    Group root;
    Group boundsGroup;
    Group componentsGroup;
    Image background;
    ImageView backgroundView;

    double stageWidth;
    double stageHeight;

    Player player;

    private boolean rightPressed;
    private boolean leftPressed;

    public GameLoop(Stage primaryStage, boolean scrollable) {
        primaryStage.setOnCloseRequest(e -> InterconnectedIsolation.closeProgram());
        stage = primaryStage;
        root = new Group();
        scene = new Scene(root, 1280, 720);
        componentsGroup = new Group();
        boundsGroup = new Group();
        root.getChildren().addAll(componentsGroup, boundsGroup);

        scene.setOnKeyPressed(onPressHandler);
        scene.setOnKeyReleased(onReleaseHandler);

        try {
            initBackground();
        } catch (IOException e) {
        }

        Rectangle rightBounds = new Rectangle();
        rightBounds.setHeight(scene.getHeight());
        rightBounds.setWidth(5);
        rightBounds.setTranslateX(scene.getWidth());

        Rectangle leftBounds = new Rectangle();
        leftBounds.setHeight(scene.getHeight());
        leftBounds.setWidth(5);
        leftBounds.setTranslateX(-5);

        boundsGroup.getChildren().addAll(leftBounds, rightBounds);

        initStage();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (scrollable) {
                    if (player.playerView.getBoundsInParent().getMaxX() >= rightBounds.getBoundsInParent().getMinX() - componentsGroup.getTranslateX() - 400) {
                        if (componentsGroup.getTranslateX() >= -(stageWidth - 1280))
                            componentsGroup.setTranslateX(componentsGroup.getTranslateX() - player.getSpeed());
                    } else if (player.playerView.getBoundsInParent().getMinX() <= leftBounds.getBoundsInParent().getMaxX() - componentsGroup.getTranslateX() + 400) {
                        if (componentsGroup.getTranslateX() <= -5)
                            componentsGroup.setTranslateX(componentsGroup.getTranslateX() + player.getSpeed());
                    }
                }

                if (rightPressed && player.getCanMoveRight()) {
                    player.moveRight();
                }

                if (leftPressed && player.getCanMoveLeft()) {
                    player.moveLeft();
                }
            }
        }.start();
    }

    public abstract void initStage();

    public abstract void initBackground() throws IOException;

    EventHandler onPressHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case D:
                    rightPressed = true;
                    break;
                case A:
                    leftPressed = true;
                    break;
            }
        }
    };

    EventHandler onReleaseHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case D:
                    rightPressed = false;
                    break;
                case A:
                    leftPressed = false;
                    break;
            }
        }
    };

    public void display() {
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
