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
import java.util.ArrayList;
import java.util.Arrays;
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
    DialogueOption dOptionZ, dOptionC, dOptionX;

    private boolean rightPressed;
    private boolean leftPressed;
    private boolean ePressed;
    private boolean enterPressed;
    private boolean zPressed;
    private boolean xPressed;
    private boolean cPressed;

    private boolean canInteract;
    private boolean canExit;

    private boolean hasArrow;
    private boolean hasArrowRed;
    private boolean hasObjects;
    private boolean hasDialogue;
    private boolean hasChoices;
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
    private boolean interactedComputer;
    ArrayList<ArrayList<Obj>> objects;
    private boolean talkedToMom;

    private int flowSceneNum;


    public GameLoop(Stage primaryStage, boolean scrollable, int sceneNum, int flowSceneNum) {

        objects = new ArrayList<>();
        objects.add(new ArrayList<>(Arrays.asList(new Obj[] {new Obj(510,670,100,"temporary microwave text."),new Obj(740,820,250,"Seat"), new Obj(890,980,130,"Jars"),new Obj(1070,1200,230,"fruit"),new Obj(1300,1410,190,"mom"),new Obj(1480,1560,80,"knives"),new Obj(1680,1730,120,"toaster")})));
        objects.add(new ArrayList<>(Arrays.asList(new Obj[] {new Obj(320,520,350,"laundry"),new Obj(550,750,200,"window"),new Obj(780,900,120,"guitar"),new Obj(950,1060,400,"trash"),new Obj(1100,1150,270,"picture"),new Obj(1190,1280,300,"plates"),new Obj(1340,1490,230,"computer"),new Obj(1600,1680,300,"homework"),new Obj(1840,2180,290,"bed"),new Obj(2250,2405,330,"dressed")})));
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
        dOptionZ = new DialogueOption('a');
        dOptionX = new DialogueOption('b');
        dOptionC = new DialogueOption('c');

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
                        for (Obj o : objects.get(sceneNum&1)) {
                            if (o.near) {
                                boolean allDone = false;
                                o.interacted = true;
                                if (o.dialogue.equals("computer")) {
                                    allDone = true;
                                    for (Obj o2: objects.get(sceneNum&1)) {
                                        if (o2 != o && !o2.interacted) {
                                            allDone = false;
                                            break;
                                        }
                                    }
                                    if (allDone) {
                                        dialogue.setDialogue("Okay time to hop on. " +
                                                "My teammates are probably home by now. " +
                                                "I think we were in the middle of a quest when someone had to leave? " +
                                                "I can’t really remember.");
                                    }
                                    else {
                                        dialogue.setDialogue("I should hop on soon. " +
                                                "My teammates usually get home a bit after I do. " +
                                                "They’re like my closest friends, even if I haven’t seen them in real life. " +
                                                "People at school don’t know me like they do.");
                                    }
                                }
                                else dialogue.setDialogue(o.dialogue);
                                if (allDone) new Level2(InterconnectedIsolation.window, 2405, 720, 1, 3).display();
                                break;
                            }
                        }
                        /*if (nearMicrowave) {
                            System.out.println("microwave");
                        } else if (nearLaundry) {
                            interactedLaundry = true;
                            dialogue.setDialogue("These clothes have been sitting here for a long time. " +
                                    "I’ve sort of gotten used to it. I mean it’s not bothering me, I don’t have that many clothes to begin with.");
                        } else if (nearWindow) {
                            interactedWindow = true;
                            dialogue.setDialogue("Mom took off the blinds a couple weeks ago. " +
                                    "She said I need more natural light in my room. " +
                                    "I don’t really like it. " +
                                    "It makes it so there’s always a glare on my monitor");
                        } else if (nearGuitar) {
                            interactedGuitar = true;
                            dialogue.setDialogue("I haven’t touched this thing in years. " +
                                    "It’s probably way out of tune by now. " +
                                    "I could tune it but that’s too much work and it’s not like I’m going to play it anytime soon. " +
                                    "I’d rather play games with my friends, not make my fingers hurt.");
                        } else if (nearTrash) {
                            interactedTrash = true;
                            dialogue.setDialogue("I should probably empty this thing. " +
                                    "It’s all just paper and food wrappers. " +
                                    "Maybe later.");
                        } else if (nearPicture) {
                            interactedPicture = true;
                            dialogue.setDialogue("I haven’t talked to these guys in so long. " +
                                    "I remember when we took this picture. " +
                                    "We went camping and we almost lit the tent on fire. " +
                                    "Fun times.");
                        } else if (nearPlates) {
                            interactedPlates = true;
                            dialogue.setDialogue("I keep forgetting to bring these to the kitchen. " +
                                    "They just keep piling up. " +
                                    "Should I bring them now? " +
                                    "Nah, it’s too much work.");
                        } else if (nearComputer) {
                            interactedComputer = true;
                            dialogue.setDialogue("I should hop on soon. " +
                                    "My teammates usually get home a bit after I do. " +
                                    "They’re like my closest friends, even if I haven’t seen them in real life. " +
                                    "People at school don’t know me like they do.");
                            if (interactedBed && interactedDresser && interactedGuitar && interactedHomework && interactedLaundry && interactedPicture && interactedPlates && interactedTrash && interactedWindow) {
                                dialogue.setDialogue("Okay time to hop on. " +
                                        "My teammates are probably home by now. " +
                                        "I think we were in the middle of a quest when someone had to leave? " +
                                        "I can’t really remember.");
                                //if (enterPressed) {
                                    new Level2(InterconnectedIsolation.window, 2405, 720, 1, 3).display();
                                //}
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
                        }*/
                        if (!hasDialogue) {
                            dOptionC.setOption("This is option C.");
                            dOptionX.setOption("This is option X.");
                            dOptionZ.setOption("This is option Z.");
                            root.getChildren().add(dialogue.dialogueGroup);
                            root.getChildren().add(dOptionC.optionGroup);
                            root.getChildren().add(dOptionX.optionGroup);
                            root.getChildren().add(dOptionZ.optionGroup);
                            hasDialogue = true;
                        }
                    }
                } else {
                    componentsGroup.getChildren().remove(arrow);
                    hasArrow = false;
                }


                if (enterPressed || zPressed || xPressed || cPressed) {
                    if (!enterPressed) System.out.println ("Option " + (zPressed ? "Z" : (xPressed ? "X" : "C")) + " was pressed.");
                    root.getChildren().remove(dialogue.dialogueGroup);
                    root.getChildren().remove(dOptionC.optionGroup);
                    root.getChildren().remove(dOptionX.optionGroup);
                    root.getChildren().remove(dOptionZ.optionGroup);
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
        boolean ret = false;
        for (Obj o : objects.get(sceneNum&1)) {
            if (player.getAverageX() > o.posl && player.getAverageX() < o.posr && !o.interacted) {
                o.near = true;
                arrow.setX((o.posl+o.posr)/2.0);
                arrow.setY(o.arrowY);
                ret = true;
            }
            else {
                o.near = false;
            }
        }
        return ret;
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
                case Z:
                    zPressed = true;
                    break;
                case X:
                    xPressed = true;
                    break;
                case C:
                    cPressed = true;
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
                case Z:
                    zPressed = false;
                    break;
                case X:
                    xPressed = false;
                    break;
                case C:
                    cPressed = false;
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
