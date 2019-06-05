import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Keeps track of all the camera movements and player movements
 *
 * @author Julia Xie
 * @author Christopher Trevisan
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

    Dialogue dialogue;

    private boolean rightPressed;
    private boolean leftPressed;
    private boolean ePressed;
    private boolean enterPressed;

    private boolean canInteract;
    private boolean canExit;

    private boolean hasArrow;
    private boolean hasArrowRed;
    private boolean hasObjects;
    private boolean hasDialogue;

    private boolean nearDoor;

    private boolean nearLaundry;
    private boolean nearWindow;
    private boolean nearGuitar;
    private boolean nearTrash;
    private boolean nearPicture;
    private boolean nearPlates;
    private boolean nearComputer;
    private boolean nearHomework;
    private boolean nearBed;
    private boolean nearDresser;

    private boolean nearMicrowave;
    private boolean nearJars;
    private boolean nearSeat;
    private boolean nearFruit;
    private boolean nearMom;
    private boolean nearKnives;
    private boolean nearToaster;

    private boolean interactedLaundry;
    private boolean interactedWindow;
    private boolean interactedGuitar;
    private boolean interactedTrash;
    private boolean interactedPicture;
    private boolean interactedPlates;
    private boolean interactedHomework;
    private boolean interactedBed;
    private boolean interactedDresser;

    private boolean talkedToMom;

    private int flowSceneNum;


    public GameLoop(Stage primaryStage, boolean scrollable, int sceneNum, int flowSceneNum) {
        stage = primaryStage;
        root = new Group();
        scene = new Scene(root, 1280, 720, Color.BLACK);
        componentsGroup = new Group();
        boundsGroup = new Group();
        root.getChildren().addAll(componentsGroup, boundsGroup);

        scene.setOnKeyPressed(onPressHandler);
        scene.setOnKeyReleased(onReleaseHandler);

        this.sceneNum = sceneNum;
        this.flowSceneNum = flowSceneNum;
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

        dialogue = new Dialogue();

        initStage(flowSceneNum);

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
                    } catch (IOException e) {
                    }
                    hasObjects = true;
                }

                if (flowSceneNum == 1 && !talkedToMom) {

                }

                if (checkForInteraction() && canInteract) {
                    if (!hasArrow) {
                        if (sceneNum == 1)
                            componentsGroup.getChildren().add(7, arrow);
                        else
                            componentsGroup.getChildren().add(1, arrow);
                        hasArrow = true;
                    }
                    if (ePressed) {
                        if (nearMicrowave) {
                            System.out.println("microwave");
                        } else if (nearLaundry) {
                            interactedLaundry = true;
                            dialogue.setDialogue("Laundry");
                        } else if (nearWindow) {
                            interactedWindow = true;
                            dialogue.setDialogue("Window");
                        } else if (nearGuitar) {
                            interactedGuitar = true;
                            System.out.println("guitar");
                        } else if (nearTrash) {
                            interactedTrash = true;
                            System.out.println("trash");
                        } else if (nearPicture) {
                            interactedPicture = true;
                            System.out.println("picture");
                        } else if (nearPlates) {
                            interactedPlates = true;
                            System.out.println("plates");
                        } else if (nearComputer) {
                            System.out.println("computer");
                            if (interactedBed && interactedDresser && interactedGuitar && interactedHomework && interactedLaundry && interactedPicture && interactedPlates && interactedTrash && interactedWindow) {
                                new Level2 (InterconnectedIsolation.window, 2405, 720, 1, 3);
                            }
                        } else if (nearHomework) {
                            interactedHomework = true;
                            System.out.println("homework");
                        } else if (nearBed) {
                            interactedBed = true;
                            System.out.println("bed");
                        } else if (nearDresser) {
                            System.out.println("dresser");
                            interactedDresser = true;
                        }
                        if (!hasDialogue) {
                            root.getChildren().add(dialogue.dialogueGroup);
                            hasDialogue = true;
                        }
                    }
                } else {
                    componentsGroup.getChildren().remove(arrow);
                    hasArrow = false;
                }


                if (enterPressed) {
                    root.getChildren().remove(dialogue.dialogueGroup);
                    hasDialogue = false;
                }

                if (checkForDoor() && canExit) {
                    if (!hasArrowRed) {
                        componentsGroup.getChildren().add(arrowRed);
                        hasArrowRed = true;
                    }
                    if (ePressed) {
                        stop();
                        if (flowSceneNum == 1) {
                            componentsGroup.getChildren().remove(arrowRed);
                            new Level1(InterconnectedIsolation.window, 2405, 720, 1, 2).display();
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
            if (player.getAverageX() > 740 && player.getAverageX() < 820) {
                nearSeat = true;
                arrow.setX(760);
                arrow.setY(250);
            } else {
                nearSeat = false;
            }
            if (player.getAverageX() > 890 && player.getAverageX() < 980) {
                nearJars = true;
                arrow.setX(915);
                arrow.setY(130);
            } else {
                nearJars = false;
            }
            if (player.getAverageX() > 1070 && player.getAverageX() < 1200) {
                nearFruit = true;
                arrow.setX(1115);
                arrow.setY(230);
            } else {
                nearFruit = false;
            }
            if (player.getAverageX() > 1300 && player.getAverageX() < 1410) {
                nearMom = true;
                arrow.setX(1335);
                arrow.setY(190);
            } else {
                nearMom = false;
            }
            if (player.getAverageX() > 1480 && player.getAverageX() < 1560) {
                nearKnives = true;
                arrow.setX(1500);
                arrow.setY(80);
            } else {
                nearKnives = false;
            }
            if (player.getAverageX() > 1680 && player.getAverageX() < 1730) {
                nearToaster = true;
                arrow.setX(1685);
                arrow.setY(120);
            } else {
                nearToaster = false;
            }
        } else {
            if (player.getAverageX() > 320 && player.getAverageX() < 520 && !interactedLaundry) {
                nearLaundry = true;
                arrow.setX(400);
                arrow.setY(350);
            } else {
                nearLaundry = false;
            }
            if (player.getAverageX() > 550 && player.getAverageX() < 750 && !interactedWindow) {
                nearWindow = true;
                arrow.setX(630);
                arrow.setY(200);
            } else {
                nearWindow = false;
            }
            if (player.getAverageX() > 780 && player.getAverageX() < 900 && !interactedGuitar) {
                nearGuitar = true;
                arrow.setX(820);
                arrow.setY(120);
            } else {
                nearGuitar = false;
            }
            if (player.getAverageX() > 950 && player.getAverageX() < 1060 && !interactedTrash) {
                nearTrash = true;
                arrow.setX(985);
                arrow.setY(400);
            } else {
                nearTrash = false;
            }
            if (player.getAverageX() > 1100 && player.getAverageX() < 1150 && !interactedPicture) {
                nearPicture = true;
                arrow.setX(1105);
                arrow.setY(270);
            } else {
                nearPicture = false;
            }
            if (player.getAverageX() > 1190 && player.getAverageX() < 1280 && !interactedPlates) {
                nearPlates = true;
                arrow.setX(1215);
                arrow.setY(300);
            } else {
                nearPlates = false;
            }
            if (player.getAverageX() > 1340 && player.getAverageX() < 1490) {
                nearComputer = true;
                arrow.setX(1395);
                arrow.setY(230);
            } else {
                nearComputer = false;
            }
            if (player.getAverageX() > 1600 && player.getAverageX() < 1680 && !interactedHomework) {
                nearHomework = true;
                arrow.setX(1620);
                arrow.setY(300);
            } else {
                nearHomework = false;
            }
            if (player.getAverageX() > 1840 && player.getAverageX() < 2180 && !interactedBed) {
                nearBed = true;
                arrow.setX(1990);
                arrow.setY(290);
            } else {
                nearBed = false;
            }
            if (player.getAverageX() > 2250 && !interactedDresser) {
                nearDresser = true;
                arrow.setX(2300);
                arrow.setY(330);
            } else {
                nearDresser = false;
            }
        }
        return nearLaundry || nearWindow || nearGuitar || nearTrash || nearPicture || nearPlates || nearComputer || nearHomework || nearBed || nearDresser ||
                nearMicrowave || nearSeat || nearJars || nearFruit || nearMom || nearKnives || nearToaster;
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

    public void addObjects(int flowSceneNum) throws IOException {
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

    public abstract void initStage(int flowSceneNum);

    public abstract void initBackground(int sceneNum) throws IOException;

    EventHandler onPressHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (!hasDialogue) {
                switch (event.getCode()) {
                    case D:
                    case RIGHT:
                        rightPressed = true;
                        leftPressed = false;
                        break;
                    case A:
                    case LEFT:
                        leftPressed = true;
                        rightPressed = false;
                        break;
                    case E:
                        ePressed = true;
                        break;
                }
            }
            switch(event.getCode()) {
                case ENTER:
                case SPACE:
                    enterPressed = true;
                    break;
            }
        }
    };

    EventHandler onReleaseHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case D:
                case RIGHT:
                    rightPressed = false;
                    break;
                case A:
                case LEFT:
                    leftPressed = false;
                    break;
                case E:
                    ePressed = false;
                    break;
                case ENTER:
                case SPACE:
                    enterPressed = false;
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

    public void display() {
        FadeTransition fade = new FadeTransition(Duration.millis(4000), componentsGroup);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        stage.setScene(scene);
    }
}
