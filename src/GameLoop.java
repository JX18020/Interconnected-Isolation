import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    private boolean isChoice;
    private boolean nearDoor;
    private boolean level1Done;

    ArrayList<ArrayList<Obj>> objects;

    private boolean talkedToMom;

    private int flowSceneNum;
    private int dialogueNumScene1 = 0;
    private int dialogueNumScene2 = 0;

    private double lastComputerUsage;
    private double lastDialogue;

    public GameLoop(Stage primaryStage, boolean scrollable, int sceneNum, int flowSceneNum) {

        objects = new ArrayList<>();
        objects.add(new ArrayList<>(Arrays.asList(new Obj[]{
                new Obj("microwave", 510, 670, 100, "temporary microwave text."),
                new Obj("seat", 740, 820, 250, "Seat"),
                new Obj("jars", 890, 980, 130, "Jars"),
                new Obj("fruit", 1070, 1200, 230, "fruit"),
                new Obj("mom", 1300, 1410, 190, "mom"),
                new Obj("knives", 1480, 1560, 80, "knives"),
                new Obj("toaster", 1680, 1730, 120, "toaster")})));
        objects.add(new ArrayList<>(Arrays.asList(new Obj[]{
                new Obj("laundry", 320, 520, 350,
                        "These clothes have been sitting here for a long time. " +
                                "I’ve sort of gotten used to it. " +
                                "I mean it’s not bothering me, " +
                                "I don’t have that many clothes to begin with."),
                new Obj("window", 550, 750, 200,
                        "Mom took off the blinds a couple weeks ago. " +
                                "She said I need more natural light in my room. " +
                                "I don’t really like it. " +
                                "It makes it so there’s always a glare on my monitor."),
                new Obj("guitar", 780, 900, 120,
                        "I haven’t touched this thing in years. " +
                                "It’s probably way out of tune by now. " +
                                "I could tune it but that’s too much work and it’s not like I’m going to play it anytime soon. " +
                                "I’d rather play games with my friends, not make my fingers hurt."),
                new Obj("trash", 950, 1060, 400,
                        "I should probably empty this thing. " +
                                "It’s all just paper and food wrappers. " +
                                "Maybe later."),
                new Obj("picture", 1100, 1150, 270,
                        "I haven’t talked to these guys in so long. " +
                                "I remember when we took this picture. " +
                                "We went camping and we almost lit the tent on fire. " +
                                "Fun times."),
                new Obj("plates", 1190, 1280, 300,
                        "I keep forgetting to bring these to the kitchen. " +
                                "They just keep piling up. " +
                                "Should I bring them now? " +
                                "Nah, it’s too much work."),
                new Obj("computer", 1340, 1490, 230, "computer"),
                new Obj("homework", 1600, 1680, 300,
                        "Ugh I hate homework. " +
                                "I hate school. " +
                                "I don’t even know why I go. " +
                                "I’m never happy when I’m there, my teachers hate me, and my friends think my hobbies are weird. " +
                                "I mean hobby. " +
                                "Singular."),
                new Obj("bed", 1840, 2180, 290,
                        "Sleep is so overrated. " +
                                "Why sleep when you game and have fun? " +
                                "Like I’m not even conscious when I sleep so how can I enjoy it? " +
                                "I guess that’s the definition of sleeping. " +
                                "Being unconscious I mean."),
                new Obj("dresser", 2250, 2405, 330, "Why do I even have this? " +
                        "There are barely any clothes in here. " +
                        "I mean it sort of makes a good nightstand right? " +
                        "I’m too lazy to move it anyways.")
        })));
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
                if (flowSceneNum != 3)
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
                /* ------------------------------------------------------------------------------------------------------------------------------
                This part is broken, it doesn't go in order properly
                 --------------------------------------------------------------------------------------------------------------------------------*/
                if (flowSceneNum == 3) {
                    componentsGroup.setTranslateX(-800);
                    if (!hasDialogue && System.currentTimeMillis() - lastDialogue > 200) {
                        switch (dialogueNumScene1) {
                            case 0:
                                dialogue.setDialogue("hello");
                                break;
                            case 1:
                                dialogue.setDialogue("bye");
                                break;
                            case 2:
                                dialogue.setDialogue("asjdflka");
                                break;
                            case 3:
                                dialogue.setDialogue("3");
                                break;
                            case 4:
                                dialogue.setDialogue("4");
                                break;
                        }
                        lastDialogue = System.currentTimeMillis();
                        if (!isChoice)
                            root.getChildren().add(dialogue.dialogueGroup);
                        else {
                            root.getChildren().add(dOptionC.optionGroup);
                            root.getChildren().add(dOptionX.optionGroup);
                            root.getChildren().add(dOptionZ.optionGroup);
                        }
                        isChoice = false;
                        hasDialogue = true;
                        System.out.println (dialogueNumScene1);
                        dialogueNumScene1++;
                    }
                }

                if (flowSceneNum != 3 && checkForInteraction() && canInteract) {
                    if (!hasArrow) {
                        if (sceneNum == 1)
                            componentsGroup.getChildren().add(7, arrow);
                        else
                            componentsGroup.getChildren().add(1, arrow);
                        hasArrow = true;
                    }
                    if (ePressed) {
                        for (Obj o : objects.get(sceneNum & 1)) {
                            if (o.near) {
                                boolean allDone = false;
                                o.interacted = true;
                                if (o.objName.equals("computer")) {
                                    allDone = true;
                                    for (Obj o2 : objects.get(sceneNum & 1)) {
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
                                    } else {
                                        dialogue.setDialogue("I should hop on soon. " +
                                                "My teammates usually get home a bit after I do. " +
                                                "They’re like my closest friends, even if I haven’t seen them in real life. " +
                                                "People at school don’t know me like they do.");
                                    }
                                    lastComputerUsage = System.currentTimeMillis();
                                } else dialogue.setDialogue(o.dialogue);
                                if (allDone) {
                                    level1Done = true;
                                }
                            }
                        }
                        if (!hasDialogue) {
                            if (!isChoice)
                                root.getChildren().add(dialogue.dialogueGroup);
                            else {
                                root.getChildren().add(dOptionC.optionGroup);
                                root.getChildren().add(dOptionX.optionGroup);
                                root.getChildren().add(dOptionZ.optionGroup);
                            }
                            isChoice = false;
                            hasDialogue = true;
                        }
                    }
                } else {
                    componentsGroup.getChildren().remove(arrow);
                    hasArrow = false;
                }

                if (flowSceneNum == 5 && !hasDialogue) {
                    switch (dialogueNumScene2) {
                        case 0:

                    }
                }

                if (level1Done && enterPressed && System.currentTimeMillis() - lastComputerUsage > 200) {
                    stop();
                    new Cutscene(InterconnectedIsolation.window, 2405, 720, 1, 3).display();
                }


                if (enterPressed || zPressed || xPressed || cPressed) {
                    if (!enterPressed)
                        System.out.println("Option " + (zPressed ? "Z" : (xPressed ? "X" : "C")) + " was pressed.");
                    root.getChildren().remove(dialogue.dialogueGroup);
                    root.getChildren().remove(dOptionC.optionGroup);
                    root.getChildren().remove(dOptionX.optionGroup);
                    root.getChildren().remove(dOptionZ.optionGroup);
                    hasDialogue = false;
                }

                if (flowSceneNum != 3 && checkForDoor() && canExit) {
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

                if (flowSceneNum != 3) {
                    if (rightPressed && player.getCanMoveRight()) {
                        player.playerView.setImage(player.getPlayerRight());
                        player.moveRight();
                    } else if (leftPressed && player.getCanMoveLeft()) {
                        player.playerView.setImage(player.getPlayerLeft());
                        player.moveLeft();
                    } else player.playerView.setImage(player.getPlayerStand());
                }
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
        for (Obj o : objects.get(sceneNum & 1)) {
            if (player.getAverageX() > o.posl && player.getAverageX() < o.posr && (!o.interacted || (o.objName.equals("computer") && System.currentTimeMillis() - lastComputerUsage > 200))) {
                o.near = true;
                arrow.setX((o.posl + o.posr) / 2.0);
                arrow.setY(o.arrowY);
                ret = true;
            } else {
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
        if (flowSceneNum == 2 || flowSceneNum == 3 || flowSceneNum == 4) {
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

            if (flowSceneNum == 3 || flowSceneNum == 4) {
                ImageView bag = new ImageView(new Image(new FileInputStream("assets/images/bag.png")));
                bag.setFitHeight(117);
                bag.setPreserveRatio(true);
                bag.setX(1565);
                bag.setY(505);

                componentsGroup.getChildren().add(1, bag);

                if (flowSceneNum == 3) {
                    ImageView playerAtComputer = new ImageView(new Image(new FileInputStream("assets/images/player_sitting_at_chair.png")));
                    playerAtComputer.setFitHeight(315);
                    playerAtComputer.setPreserveRatio(true);
                    playerAtComputer.setX(1323);
                    playerAtComputer.setY(288);

                    componentsGroup.getChildren().add(1, playerAtComputer);
                }
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
            switch (event.getCode()) {
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
        FadeTransition fade = new FadeTransition(Duration.millis(2000), componentsGroup);
        FadeTransition fade2 = new FadeTransition(Duration.millis(2000), root);
        fade.setFromValue(0);
        fade2.setFromValue(0);
        fade.setToValue(1);
        fade2.setToValue(1);
        fade.play();
        fade2.play();
        stage.setScene(scene);
    }
}
