import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Keeps track of all the camera movements and player movements
 *
 * @author Julia Xie
 * @author Christopher Trevisan
 *
 * @version June 3, 2019
 */
public abstract class GameLoop {
    private Stage stage;
    Scene scene;
    private Group root;
    private Group boundsGroup;
    Group componentsGroup;

    private ImageView arrow;
    private ImageView arrowRed;

    private int sceneNum;

    private double stageWidth;
    private double stageHeight;

    Player player;

    private boolean rightPressed;
    private boolean leftPressed;
    private boolean ePressed;

    private boolean canInteract;
    private boolean canExit;

    private boolean hasArrow;
    private boolean hasArrowRed;
    private boolean hasObjects;

    private boolean nearDoor;
    private boolean nearMicrowave;
    private boolean nearLaundry;
    private boolean nearGuitar;

    private boolean enteredDoor;

    private int flowSceneNum;


    public GameLoop(Stage primaryStage, boolean scrollable, int sceneNum) {
        primaryStage.setOnCloseRequest(e -> InterconnectedIsolation.closeProgram());
        stage = primaryStage;
        root = new Group();
        scene = new Scene(root, 1280, 720, Color.BLACK);
        componentsGroup = new Group();
        boundsGroup = new Group();
        root.getChildren().addAll(componentsGroup, boundsGroup);

        scene.setOnKeyPressed(onPressHandler);
        scene.setOnKeyReleased(onReleaseHandler);
        scene.setOnMouseClicked(onMouseHandler);

        this.sceneNum = sceneNum;
        try {
            arrow = new ImageView(new Image(new FileInputStream("assets/images/arrow.png")));
            arrowRed = new ImageView(new Image(new FileInputStream("assets/images/arrow_red.png")));
            initBackground(sceneNum);
        } catch (IOException e) {
        }
        arrow.setFitWidth(40);
        arrow.setPreserveRatio(true);
        arrowRed.setFitWidth(40);
        arrowRed.setPreserveRatio(true);

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
                    if (Math.floor(player.playerView.getBoundsInParent().getMaxX()) >= Math.floor(rightBounds.getBoundsInParent().getMinX() - componentsGroup.getTranslateX() - 570)) {
                        if (Math.floor(componentsGroup.getTranslateX()) >= -(stageWidth - 1280))
                            componentsGroup.setTranslateX(Math.floor(componentsGroup.getTranslateX() - player.getSpeed()));
                    } else if (Math.floor(player.playerView.getBoundsInParent().getMinX()) <= Math.floor(leftBounds.getBoundsInParent().getMaxX() - componentsGroup.getTranslateX() + 570)) {
                        if (Math.floor(componentsGroup.getTranslateX()) <= -5)
                            componentsGroup.setTranslateX(Math.floor(componentsGroup.getTranslateX() + player.getSpeed()));
                    }
                }
                checkForCollisions();

                if (!hasObjects) {
                    try {
                        addObjects(flowSceneNum);
                    } catch (IOException e) {}
                    hasObjects = true;
                }

                if (checkForInteraction() && canInteract) {
                    if (!hasArrow) {
                        componentsGroup.getChildren().add(arrow);
                        hasArrow = true;
                    }
                    if (nearMicrowave && ePressed) {
                        System.out.println("microwave");
                    }
                } else {
                    componentsGroup.getChildren().remove(arrow);
                    hasArrow = false;
                }

                if (checkForDoor() && canExit) {
                    if (!hasArrowRed) {
                        componentsGroup.getChildren().add(arrowRed);
                        hasArrowRed = true;
                    }
                    if (ePressed) {
                        enteredDoor = true;
                        stop();
                        if (flowSceneNum == 1) {
                            componentsGroup.getChildren().remove(arrowRed);
                            new Level1(InterconnectedIsolation.window, 2405, 720, 1).display();
                        }
                    }
                } else {
                    componentsGroup.getChildren().remove(arrowRed);
                    hasArrowRed = false;
                    nearDoor = false;
                }

                if (rightPressed && player.getCanMoveRight()) {
                    player.playerView.setImage(player.getPlayerRight());
                    player.moveRight();
                } else if (leftPressed && player.getCanMoveLeft()) {
                    player.playerView.setImage(player.getPlayerLeft());
                    player.moveLeft();
                } else player.playerView.setImage(player.getPlayerStand());
            }
        }.start();
    }

    private void checkForCollisions() {
        if (checkForCollisionOnLeft()) {
            player.setCanMoveLeft(false);
        } else {
            player.setCanMoveLeft(true);
        }
        if (checkForCollisionOnRight()) {
            player.setCanMoveRight(false);
        } else {
            player.setCanMoveRight(true);
        }
    }

    public boolean checkForCollisionOnRight() {
        if (player.getMaxX() >= stageWidth - 20)
            return true;
        return false;
    }

    public boolean checkForCollisionOnLeft() {
        if (player.getMinX() <= 120)
            return true;
        return false;
    }

    public boolean checkForInteraction() {
        if (sceneNum == 2) {
            if (player.getAverageX() > 510 && player.getAverageX() < 670) {
                nearMicrowave = true;
                arrow.setX(580);
                arrow.setY(100);
            } else {
                nearMicrowave = false;
            }
        } else {
            if (player.getAverageX() > 320 && player.getAverageX() < 520) {
                nearLaundry = true;
                arrow.setX(400);
                arrow.setY(350);
            } else {
                nearLaundry = false;
            }
        }
        return nearMicrowave || nearLaundry;
    }

    public boolean checkForDoor() {
        if (sceneNum == 2) {
            if (player.getAverageX() > 2020 && player.getAverageX() < 2220) {
                nearDoor = true;
                arrowRed.setX(2100);
                arrowRed.setY(130);
            } else {
                nearDoor = false;
            }
        } else {
            if (player.getAverageX() > 130 && player.getAverageX() < 230) {
                nearDoor = true;
                arrowRed.setX(150);
                arrowRed.setY(130);
            } else {
                nearDoor = false;
            }
        }
        return nearDoor;
    }

    public void addObjects(int flowSceneNum) throws IOException{
        if (flowSceneNum == 2 || flowSceneNum == 3) {
            ImageView laundry = new ImageView(new Image(new FileInputStream("assets/images/laundry.png")));
            laundry.setFitHeight(153);
            laundry.setPreserveRatio(true);
            laundry.setX(279);
            laundry.setY(441);

            ImageView guitar = new ImageView(new Image(new FileInputStream("assets/images/guitar.png")));
            guitar.setFitHeight(396);
            guitar.setPreserveRatio(true);
            guitar.setX(774);
            guitar.setY(205);

            ImageView picture = new ImageView(new Image(new FileInputStream("assets/images/picture.png")));
            picture.setFitHeight(63);
            picture.setPreserveRatio(true);
            picture.setX(1098);
            picture.setY(369);

            ImageView plates = new ImageView(new Image(new FileInputStream("assets/images/plates.png")));
            plates.setFitHeight(72);
            plates.setPreserveRatio(true);
            plates.setX(1179);
            plates.setY(396);

            ImageView homework = new ImageView(new Image(new FileInputStream("assets/images/unfinished_homework.png")));
            homework.setFitHeight(72);
            homework.setPreserveRatio(true);
            homework.setX(1583);
            homework.setY(396);

            ImageView trash = new ImageView(new Image(new FileInputStream("assets/images/trash.png")));
            trash.setFitHeight(135);
            trash.setPreserveRatio(true);
            trash.setX(927);
            trash.setY(486);

            componentsGroup.getChildren().add(1, laundry);
            componentsGroup.getChildren().add(1, guitar);
            componentsGroup.getChildren().add(1, picture);
            componentsGroup.getChildren().add(1, plates);
            componentsGroup.getChildren().add(1, homework);
            componentsGroup.getChildren().add(1, trash);

            if (flowSceneNum == 3) {
                ImageView bag = new ImageView(new Image(new FileInputStream("assets/images/bag.png")));
                bag.setFitHeight(117);
                bag.setPreserveRatio(true);
                bag.setX(1565);
                bag.setY(505);

                componentsGroup.getChildren().add(1, bag);
            }
        }
    }

    public abstract void initStage();

    public abstract void initBackground(int sceneNum) throws IOException;

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
                case E:
                    ePressed = true;
                    break;

            }
        }
    };

    EventHandler onMouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            int x = (int) (event.getSceneX() - componentsGroup.getTranslateX()), y = (int) event.getSceneY();
            if (x >= 485 && x <= 726 && y >= 169 && y <= 278)
                System.out.println("Clicked on the microwave!");
            else if (x >= player.playerView.getTranslateX() && x <= player.playerView.getTranslateX() + 39)
                System.out.println("Clicked on player!");
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
                case E:
                    ePressed = false;
                    break;
            }
        }
    };

    public void setStageWidth(int width) {
        stageWidth = width;
    }

    public void setStageHeight(double stageHeight) {
        this.stageHeight = stageHeight;
    }

    public void setCanExit(boolean canExit) {
        this.canExit = canExit;
    }

    public void setCanInteract(boolean canInteract) {
        this.canInteract = canInteract;
    }

    public void setFlowSceneNum(int flowSceneNum) {
        this.flowSceneNum = flowSceneNum;
    }

    public void display() {
        FadeTransition fade = new FadeTransition(Duration.millis(4000), componentsGroup);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        stage.setScene(scene);
    }
}
