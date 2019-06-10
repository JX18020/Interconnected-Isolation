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
import java.util.logging.Level;

/**
 * Keeps track of all the camera movements and player movements.
 * <p>
 * References:
 * <br>Dillon, A. (2015). Picasso (Version 2) [Software]. Retrieved from https://github.com/Hopding/Picasso.
 * </p>
 *
 * @author Julia Xie
 * @author Christopher Trevisan
 * @version 1.11
 *
 * <p>
 * 1.2 - Julia Xie
 * <br>Date: 2019/05/29
 * <br>Time Spent: 3 hours
 * <br>Added GameLoop constructor.
 * <br>Added EventHandler anonymous classes.
 * <br>Added abstract initStage() and initBackground() methods.
 * <br>Added display() method.
 * </p>
 * <p>
 * 1.3 - Julia Xie
 * <br>Date: 2019/05/30
 * <br>Time Spent: 2 hours
 * <br>Added checkForCollisions() method.
 * <br>Added checkForCollisionsOnRight() method.
 * <br>Added checkForCollisionsOnLeft() method.
 * <br>Added setStageWidth() method.
 * <br>Added setStageHeight() method.
 * </p>
 * <p>
 * 1.5 - Julia Xie
 * <br>Date: 2019/06/01
 * <br>Time Spent: 30 minutes
 * <br>Modified display() method.
 * <br>Modified constructor.
 * <br>Added checkForInteraction() method.
 * <br>Added checkForDoor() method.
 * </p>
 * <p>
 * 1.6 - Julia Xie
 * <br>Date: 2019/06/03
 * <br>Time Spent: 2 hours
 * <br>Added setCanExit() method.
 * <br>Added setCanInteract() method.
 * <br>Added addObjects() method.
 * </p>
 * <p>
 * 1.7 - Julia Xie
 * <br>Date: 2019/06/04
 * <br>Time Spent: 3 hours
 * <br>Modified addObjects() method.
 * <br>Modified checkForInteraction() method.
 * <br>Modified constructor.
 * </p>
 * <p>
 * 1.8 - Julia Xie
 * <br>Date: 2019/06/05
 * <br>Time Spent: 2 hours
 * <br>Modified constructor.
 * </p>
 * <p>
 * 1.8 - Chris Trevisan
 * <br>Date: 2019/06/05
 * <br>Time Spent: 1 hour
 * <br>Modified checkForInteraction() method.
 * <br>Modified constructor.
 * </p>
 * <p>
 * 1.9 - Chris Trevisan
 * <br>Date: 2019/06/06
 * <br>Time Spent: 1 hour
 * <br>Modified constructor
 * </p>
 * <p>
 * 1.9 - Julia Xie
 * <br>Date: 2019/06/06
 * <br>Time Spent: 2 hours
 * <br>Modified constructor.
 * </p>
 * <p>
 * 1.10 - Julia Xie
 * <br>Date: 2019/06/07
 * <br>Time Spent: 2 hours
 * <br>Modified constructor.
 * <br>Modified addObjects() method.
 * </p>
 * <p>
 * 1.10 - Chris Trevisan
 * <br>Date: 2019/06/07
 * <br>Time Spent: 1 hour
 * <br>Modified constructor.
 * </p>
 * <p>
 * 1.11 - Julia Xie
 * <br>Date: 2019/06/08
 * <br>Time Spent: 10 hours
 * <br>Modified constructor.
 * <br>Added setOptions() methods.
 * <br>Modified addObjects() method.
 * </p>
 * @since 1.2
 */
public abstract class GameLoop {
    /**
     * The stage which the game appears on.
     */
    private Stage stage;
    /**
     * The scene of the game.
     */
    Scene scene;
    /**
     * The main group of all the components of the game.
     */
    Group root;
    /**
     * The group which contains the boundaries of the player.
     */
    private Group boundsGroup;
    /**
     * The group which contains all components which move in the background.
     */
    Group componentsGroup;

    /**
     * The image of a blue arrow.
     */
    private ImageView arrow;
    /**
     * The image of a red arrow.
     */
    private ImageView arrowRed;

    /**
     * The scene number.
     */
    private int sceneNum;
    /**
     * The number of scene changes there has currently been.
     */
    private int flowSceneNum;

    /**
     * The width of the stage.
     */
    private double stageWidth;
    /**
     * The height of the stage.
     */
    private double stageHeight;

    /**
     * The movable player.
     */
    Player player;

    /**
     * The dialogue which appears in the game.
     */
    private Dialogue dialogue;
    /**
     * The 3 dialogue options which the player can choose.
     */
    private DialogueOption dOptionZ, dOptionC, dOptionX;

    /**
     * Whether or not the right key or d is pressed.
     */
    private boolean rightPressed;
    /**
     * Whether or not the left key or a is pressed.
     */
    private boolean leftPressed;
    /**
     * Whether or not e is pressed.
     */
    private boolean ePressed;
    /**
     * Whether or not ENTER or SPACE is pressed.
     */
    private boolean enterPressed;
    /**
     * Whether or not z is pressed.
     */
    private boolean zPressed;
    /**
     * Whether or not x is pressed.
     */
    private boolean xPressed;
    /**
     * Whether or not c is pressed.
     */
    private boolean cPressed;

    /**
     * Whether or not the player is able to interact with objects.
     */
    private boolean canInteract;
    /**
     * Whether or not the player is allowed to exit the scene.
     */
    private boolean canExit;

    /**
     * Whether or not the scene has an arrow already.
     */
    private boolean hasArrow;
    /**
     * Whether or not the scene has a red arrow already.
     */
    private boolean hasArrowRed;
    /**
     * Whether or not the scene has generated the objects already.
     */
    private boolean hasObjects;
    /**
     * Whether or not the screen has dialogue on it.
     */
    private boolean hasDialogue;
    /**
     * Whether or not the dialogue is choice-based.
     */
    private boolean isChoice;
    /**
     * Whether or not the player is near a door.
     */
    private boolean nearDoor;
    /**
     * Whether or not level 1 is done.
     */
    private boolean level1Done;
    /**
     * Whether or not a key has been let go.
     */
    private boolean letGo;

    /**
     * Whether or not the player has chosen to call a friend.
     */
    private boolean callFriend;

    private boolean twoOptions;

    /**
     * An ArrayList which contains 2 ArrayLists of displayable objects.
     */
    private ArrayList<ArrayList<Obj>> objects;

    /**
     * The number of dialogue prompts which have been displayed.
     */
    private int dialogueNum;

    /**
     * The number of options available in a choice dialogue.
     */
    private int numOfOpt;
    /**
     * The choice chosen in a choice dialogue.
     */
    private char dialogueChoice;
    /**
     * The name of the object where a choice has been made about it.
     */
    private String objChoiceName = "";
    /**
     * Whether or not the player choose the "it's fun" option in one of the choice dialogues.
     */
    private boolean itsFun;

    /**
     * The time in milliseconds where something was last used.
     */
    private double lastUsage;
    /**
     * The time in milliseconds where the dialogue was last skipped.
     */
    private double lastDialogue;
    /**
     * The time in milliseconds that the scene started.
     */
    private double time;

    /**
     * Images of the laundry, homework, plates and trash.
     */
    private ImageView laundry, homework, plates, trash;

    /**
     * Constructor for the GameLoop class.
     * Initializes all objects and runs an AnimationTimer which controls the game.
     * <p>
     * 1.5 - Julia Xie
     * <br>Added blue arrow if the player is near an object and a red arrow if the player is near a door.
     * <br>Added a mechanic where if the player is near an object and pressed e on the keyboard, an event is triggered.
     * </p>
     * <p>
     * 1.7 - Julia Xie
     * <br>Added blue arrow for more objects.
     * </p>
     * <p>
     * 1.8 - Julia Xie
     * <br>Added showing the dialogue with the dialogue box when something is interacted with.
     * <br>Added the specific dialogue options for objects.
     * </p>
     * <p>
     * 1.8 - Chris Trevisan
     * <br>Created ArrayLists which contained all of the objects.
     * </p>
     * <p>
     * 1.9 - Chris Trevisan
     * <br>Hard-coded special dialogue case for the computer object.
     * <br>Added in the dialogue options mechanic.
     * </p>
     * <p>
     * 1.9 - Julia Xie
     * <br>Added all dialogue, object coordinates, arrow coordinates, and names to the objects in scene 1.
     * </p>
     * <p>
     * 1.10 - Julia Xie
     * <br>Added a cutscene with dialogue.
     * </p>
     * <p>
     * 1.10 - Chris Trevisan
     * <br>Fixed bug with the cutscene dialogue and added dialogue progression for dialogue options.
     * </p>
     * <p>
     * 1.11 - Julia Xie
     * <br>Added all dialogue, object coordinates, arrow coordinates, and names to the objects in scene 2.
     * <br>Added dialogue when the player first enters the door.
     * <br>Added dialogue for the cutscene.
     * <br>Added all dialogue for Level 2, including the choice dialogues, which can lead to different dialogue from the mom.
     * <br>Added flow through all the doors, interacting with objects, and the option to sit in Level 2 scene 2.
     * <br>Added all dialogue for Level 3.
     * <br>Added the cutscene at the end of the game.
     * </p>
     *
     * @param primaryStage the stage which the game appears in
     * @param scrollable   whether or not the scene is scrollable
     * @param sceneNum     the scene number
     * @param flowSceneNum the number of scene changes so far
     * @since 1.2
     */
    public GameLoop(Stage primaryStage, boolean scrollable, int sceneNum, int flowSceneNum) {
        letGo = true;
        objects = new ArrayList<>();
        objects.add(new ArrayList<>(Arrays.asList(new Obj[]{
                new Obj("microwave", 510, 670, 100,
                        "If I ever move out, the microwave is probably the only thing I’d ever use in a kitchen. " +
                                "It’s just so convenient."),
                new Obj("seat", 740, 820, 250, "seat"),
                new Obj("jars", 890, 980, 130,
                        "Mom keeps random things in these jars. " +
                                "We’ve probably had these jars for a couple years now."),
                new Obj("fruit", 1070, 1200, 230,
                        "I like bananas. " +
                                "Not much else though."),
                new Obj("mom", 1300, 1410, 190,
                        "Mom: Have a seat, " + player.getName() + "."),
                new Obj("knives", 1480, 1560, 80,
                        "I don’t trust myself around knives. " +
                                "Nope, not at all."),
                new Obj("toaster", 1680, 1730, 120,
                        "Hmm, my toast is still in there from this morning.")
        })));
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
                                "It makes it so there’s always a glare on my monitor.",
                        "It's a nice day outside."),
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
                                "Being unconscious I mean.",
                        "Maybe I'll sleep at 10 tonight."),
                new Obj("dresser", 2250, 2405, 330, "Why do I even have this? " +
                        "There are barely any clothes in here. " +
                        "I mean it sort of makes a good nightstand right? " +
                        "I’m too lazy to move it anyways.")
        })));
        twoOptions = false;
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
            arrow = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/arrow.png")));
            arrowRed = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/arrow_red.png")));
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

        time = System.currentTimeMillis();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (scrollable) {
                    if (Math.floor(player.playerView.getBoundsInParent().getMaxX()) >= Math.floor(rightBounds.getBoundsInParent().getMinX() - componentsGroup.getTranslateX() - 560)) {
                        if (Math.floor(componentsGroup.getTranslateX()) >= -(stageWidth - 1280))
                            componentsGroup.setTranslateX(Math.floor(componentsGroup.getTranslateX() - player.getSpeed()));
                    } else if (Math.floor(player.playerView.getBoundsInParent().getMinX()) <= Math.floor(leftBounds.getBoundsInParent().getMaxX() - componentsGroup.getTranslateX() + 560)) {
                        if (Math.floor(componentsGroup.getTranslateX()) <= -5)
                            componentsGroup.setTranslateX(Math.floor(componentsGroup.getTranslateX() + player.getSpeed()));
                    }
                }
                if (flowSceneNum != 3 && flowSceneNum != 4 && flowSceneNum != 7 && flowSceneNum != 8 && flowSceneNum < 11)
                    checkForCollisions();

                if (!hasObjects) {
                    try {
                        addObjects(flowSceneNum);
                    } catch (IOException e) {
                    }
                    hasObjects = true;
                }

                if (flowSceneNum == 1 || flowSceneNum == 9) {
                    if (!hasDialogue && System.currentTimeMillis() - lastDialogue > 200 && letGo) {
                        enterPressed = false;
                        if (dialogueNum == 0) {
                            dialogue.setDialogue("Mom: Hello, " + player.getName() + "! How was school?");
                        }
                        if (flowSceneNum == 9) {
                            switch (dialogueNum) {
                                case 1:
                                    setOptions("Respond", "Ignore");
                                    twoOptions = true;
                                    break;
                                case 2:
                                    if (dialogueChoice == 'z') {
                                        dialogue.setDialogue("You: It was good.");
                                        InterconnectedIsolation.setImproveNum(InterconnectedIsolation.getImproveNum() + 1);
                                    } else
                                        dialogueNum++;
                                    break;
                            }
                        }

                        lastDialogue = System.currentTimeMillis();
                        if (flowSceneNum == 1 && dialogueNum == 0 || flowSceneNum == 9 && dialogueNum <= 2) {
                            hasDialogue = true;
                            if (!isChoice)
                                root.getChildren().add(dialogue.dialogueGroup);
                            else {
                                root.getChildren().addAll(dOptionZ.optionGroup, dOptionX.optionGroup);
                                if (!twoOptions) root.getChildren().add(dOptionC.optionGroup);
                            }
                            dialogueNum++;
                            letGo = false;
                        }
                    }
                }

                if (flowSceneNum == 3) {
                    componentsGroup.setTranslateX(-800);
                    if (!hasDialogue && System.currentTimeMillis() - lastDialogue > 200 && letGo) {
                        switch (dialogueNum) {
                            case 0:
                                dialogue.setDialogue("You: ARE YOU KIDDING ME? I just lost everything...");
                                break;
                            case 1:
                                dialogue.setDialogue("Mom: Is everything okay?");
                                break;
                            case 2:
                                dialogue.setDialogue("You: Yeah everything's fine.");
                                break;
                            case 3:
                                dialogue.setDialogue("Mom: Well dinner's ready, " + player.getName() + ". Bring those plates while you're at it.");
                                break;
                            case 4:
                                dialogue.setDialogue("You: Yes, yes, okay. Can you please go now?");
                                break;
                        }
                        lastDialogue = System.currentTimeMillis();
                        root.getChildren().add(dialogue.dialogueGroup);
                        hasDialogue = true;
                        if (dialogueNum++ > 4) {
                            stop();
                            new Cutscene(InterconnectedIsolation.getWindow(), 2405, 720, 1, 4).display();
                        }
                        letGo = false;
                    }
                }

                if (flowSceneNum == 4) {
                    componentsGroup.setTranslateX(-800);
                    if (!hasDialogue && System.currentTimeMillis() - lastDialogue > 200 && letGo) {
                        switch (dialogueNum) {
                            case 0:
                                dialogue.setDialogue("Mom: " + player.getName() + ", it's dark out and you still haven't eaten. " +
                                        "I've cleaned everything up already so if you want food, you're going to have to make it yourself.");
                                break;
                            case 1:
                                dialogue.setDialogue("You: Yeah, yeah, okay.");
                                break;
                            case 2:
                                dialogue.setDialogue("Mom: Are you even listening? " +
                                        "What are you doing right now on that computer?");
                                break;
                            case 3:
                                dialogue.setDialogue("You: Uh... homework?");
                                break;
                            case 4:
                                dialogue.setDialogue("Mom: Are you trying to lie to me? " +
                                        "I can clearly see that you’re playing games. " +
                                        "Come to the kitchen, we need to have a talk. ");
                                break;
                            case 5:
                                dialogue.setDialogue("You: What? " +
                                        "No, I’m in the middle of a game right now and I can’t just pause it.");
                                break;
                            case 6:
                                dialogue.setDialogue("Mom: I don’t care if you can’t pause it. " +
                                        "Come to the kitchen, NOW.");
                                break;
                            case 7:
                                dialogue.setDialogue("You: Fine.");
                                break;

                        }
                        root.getChildren().add(dialogue.dialogueGroup);
                        hasDialogue = true;
                        if (dialogueNum++ > 7) {
                            stop();
                            new Level2(InterconnectedIsolation.getWindow(), true, 2405, 720, 1, 5).display();
                        }
                        letGo = false;
                    }
                }

                if (flowSceneNum == 7) {
                    componentsGroup.setTranslateX(-440);
                    if (!hasDialogue && System.currentTimeMillis() - lastDialogue > 200 && letGo) {
                        switch (dialogueNum) {
                            case 0:
                                dialogue.setDialogue("Mom: I've let this get too far, we need to talk, " + player.getName() + ".");
                                break;
                            case 1:
                                dialogue.setDialogue("You: About what?");
                                break;
                            case 2:
                                dialogue.setDialogue("Mom: You know what. " +
                                        "You’re on your computer 24/7 playing games, " +
                                        "you never eat dinner with the family anymore, " +
                                        "you get so irritated when I try to talk to you, " +
                                        "your grades are dropping, " +
                                        "and who knows how many hours of sleep you get per night. " +
                                        "It’s a problem, " + player.getName() + ".");
                                break;
                            case 3:
                                setOptions("I don’t really see how that’s a problem. " +
                                                "I’m not hurting anyone am I?",
                                        "Why do you care about what I do so much? " +
                                                "It’s my life.",
                                        "...");
                                twoOptions = false;
                                break;
                            case 4:
                                if (dialogueChoice == 'z')
                                    dialogue.setDialogue("Mom: You’re hurting yourself, " + player.getName() +
                                            ". I need you to understand that.");
                                else if (dialogueChoice == 'x')
                                    dialogue.setDialogue("Mom: Of course I care, " + player.getName() +
                                            ". I’m your mom after all. " +
                                            "And yes it’s your life but I have to intervene when I see a problem.");
                                else
                                    dialogue.setDialogue("Mom: I’m not going to just sit here and rant at you. " +
                                            "I need you to show me that you understand what I’m saying.");
                                break;
                            case 5:
                                dialogue.setDialogue("You: Fine, whatever.");
                                break;
                            case 6:
                                dialogue.setDialogue("Mom: Okay good. " +
                                        "First, I need you to be open to talking about everything. " +
                                        "It’s not going to help anyone if you don’t. " +
                                        "I’m going to ask you questions and I want you to answer honestly. " +
                                        "Do you understand?");
                                break;
                            case 7:
                                setOptions("Yes", "No");
                                twoOptions = true;
                            case 8:
                                if (dialogueChoice == 'z')
                                    dialogueNum++;
                                else {
                                    dialogue.setDialogue("Mom: Well then, just answer if I ask you a question.");
                                    break;
                                }
                            case 9:
                                dialogue.setDialogue("Mom: Okay first, do you think you are addicted to gaming?");
                                break;
                            case 10:
                                setOptions("No, how am I addicted? It's just a hobby.", "Yeah, but it's not like I'm doing drugs or something.");
                                twoOptions = true;
                                break;
                            case 11:
                                if (dialogueChoice == 'z')
                                    dialogue.setDialogue("Mom: No, it's not just a hobby. It's interfering with your life.");
                                else
                                    dialogue.setDialogue("Mom: Yes, you're not doing drugs, but any addiction is dangerous if you don't try to get better.");
                                break;
                            case 12:
                                dialogue.setDialogue("Mom: I did some research while you were in your room. " +
                                        "For something to count as an addiction, it needs to severely impact your life and cause impairment to everyday activities. " +
                                        "Do you think gaming is impairing your ability to do normal things?  ");
                                break;
                            case 13:
                                setOptions("No, not at all.", "Yeah, I guess.");
                                twoOptions = true;
                                break;
                            case 14:
                                if (dialogueChoice == 'z')
                                    dialogue.setDialogue("Mom: Hmm, you need to think a little bit harder about that. " +
                                            "Maybe you can't see if from your own perspective, but from an outsider's perspective, it's clear that gaming is negatively impacting your life. ");
                                else
                                    dialogue.setDialogue("Mom: I'm glad that you understand that. ");
                                break;
                            case 15:
                                dialogue.setDialogue("Mom: Maybe you can't see this yourself, so I'm going to explain exactly how I see gaming is affecting you. " +
                                        "Firstly, you get super irritated whenever I try to talk to you. " +
                                        "Do you notice that?");
                                break;
                            case 16:
                                setOptions("Not really, I'm not yelling at you or anything.", "Yeah, maybe. But it's only because I'm in the middle of something.");
                                twoOptions = true;
                                break;
                            case 17:
                                if (dialogueChoice == 'z')
                                    dialogue.setDialogue("Mom: You're not yelling, but you seem really angry whenever I interrupt you. " +
                                            "And you barely ever talk to me other than to tell me to go.");
                                else
                                    dialogue.setDialogue("Mom: The problem is that you're always in the middle of something. " +
                                            "One of the signs of gaming addiction is irritated whenever someone tries to take gaming away. ");
                                break;
                            case 18:
                                dialogue.setDialogue("Mom: Secondly, you're neglecting yourself and your well-being. " +
                                        "When's the last time you ate a meal at the correct time? " +
                                        "And how much sleep do you really get each night? " +
                                        "Do you see how this is a problem?");
                                break;
                            case 19:
                                setOptions("I'm still eating and sleeping enough to get me through the day.", "I just forget sometimes. ");
                                twoOptions = true;
                                break;
                            case 20:
                                if (dialogueChoice == 'z')
                                    dialogue.setDialogue("Mom: The problem isn't whether you're eating or sleeping. " +
                                            "The problem is that gaming is affecting your daily schedule and when you do things. ");
                                else
                                    dialogue.setDialogue("Mom: Yes, and that's a problem. " +
                                            "Gaming causes you to completely forget to take care of yourself.");
                                break;
                            case 21:
                                dialogue.setDialogue("Mom: Also, I can see that your grades are dropping. " +
                                        "Do you play games when you're supposed to be doing homework?");
                                break;
                            case 22:
                                setOptions("I just really don't like homework.", "Gaming is far better than doing homework.");
                                twoOptions = true;
                                break;
                            case 23:
                                dialogue.setDialogue("Mom: It's directly affecting your grades at school. " +
                                        "Just because you don't like it doesn't mean that it's not important for you to do. ");
                                break;
                            case 24:
                                dialogue.setDialogue("Mom: Okay last question for now. Why do you play games so much, " + player.getName() + "?");
                                break;
                            case 25:
                                if (!itsFun) {
                                    setOptions("It's fun.", "I'd rather not think about anything else.", "I like talking to me friends there.");
                                } else
                                    setOptions("No really, it's fun.", "I'd rather not think about anything else.", "I like talking to me friends there.");
                                twoOptions=false;
                                break;
                            case 26:
                                if (dialogueChoice == 'z') {
                                    if (!itsFun) {
                                        dialogue.setDialogue("Come on, it can't just be because you think it's fun.");
                                        dialogueNum -= 2;
                                        itsFun = true;
                                    } else {
                                        dialogue.setDialogue("Mom: Well I guess so then. " +
                                                "These games are designed to keep the player engaged. " +
                                                "Even if that means keeping them engaged over other activities.");
                                        dialogueNum += 2;
                                    }
                                } else if (dialogueChoice == 'x') {
                                    dialogue.setDialogue("Mom: So you use them to distract yourself huh? " +
                                            player.getName() + ", if you're every having problems with anything, I'm always here for you to talk to.");
                                    dialogueNum += 2;
                                } else
                                    dialogue.setDialogue("Mom: Why don't you talk to your friends at school then?");
                                break;
                            case 27:
                                setOptions("My internet friends are more fun to talk to.", "I have more in common with my internet friends.");
                                twoOptions = true;
                                break;
                            case 28:
                                dialogue.setDialogue("Mom: You could try to get to know your friends at school better. " +
                                        "Maybe you'll find more in common with them than you thought. ");
                                break;
                            case 29:
                                dialogue.setDialogue("Mom: Now that you understand that what you're doing is a problem, there are some measures both you and I need to take.");
                                break;
                            case 30:
                                dialogue.setDialogue("You: What? What measures?");
                                break;
                            case 31:
                                dialogue.setDialogue("Mom: First off, I'm going to have to restrict the amount of time you are able to use your computer using parental controls. " +
                                        "That means you have to manage your time well. " +
                                        "What do you think of that?");
                                break;
                            case 32:
                                setOptions("Why though? I can control myself.", "Okay fine.");
                                twoOptions = true;
                                break;
                            case 33:
                                if (dialogueChoice == 'z')
                                    dialogue.setDialogue("Mom: For the past year, you demonstrated to me that you cannot control yourself. " +
                                            "This is for your own good.");
                                else
                                    dialogue.setDialogue("Mom: I'm glad we're on the same page.");
                                break;
                            case 34:
                                dialogue.setDialogue("Mom: You will be able to use your computer for 2 hours every day at any time between 4 in the afternoon and 10 and night. " +
                                        "On the weekends, you will be able to access the computer for 4 hours between 8 in the morning and 10 at night.");
                                break;
                            case 35:
                                setOptions("That's so strict.", "Is there any way I'd be able to use it more?");
                                twoOptions = true;
                                break;
                            case 36:
                                if (dialogueChoice == 'z')
                                    dialogue.setDialogue("Mom: It's strict because you need to be able to control yourself. " +
                                            "But there are ways you can earn more time.");
                                else
                                    dialogue.setDialogue("Mom: Yes, I was getting to that. " +
                                            "There are ways which you can earn more time on the computer.");
                                break;
                            case 37:
                                dialogue.setDialogue("Mom: So, if you do all your chores over the week you'll be able to use your computer for an extra hour over the weekend.");
                                break;
                            case 38:
                                dialogue.setDialogue("You: Is that it? What about the weekdays?");
                                break;
                            case 39:
                                dialogue.setDialogue("Mom: You need to get your grades up. " +
                                        "So, every time your average goes up by 2 percent, you'll be able to use the computer for 30 more minutes each day.");
                                break;
                            case 40:
                                dialogue.setDialogue("You: So what if I don't have enough time each day for homework?");
                                break;
                            case 41:
                                dialogue.setDialogue("Mom: That's the thing. " +
                                        "You need to regulate your time. " +
                                        "If you have more homework one day then you'll have to spend less time gaming. " +
                                        "And if you still need more time after that, then I may allow you to use it more.  ");
                                break;
                            case 42:
                                dialogue.setDialogue("You: Is that it now?");
                                break;
                            case 43:
                                dialogue.setDialogue("Mom: Yes, " + player.getName() + ", you're good to go now.");
                                break;
                        }
                        if (!isChoice)
                            root.getChildren().add(dialogue.dialogueGroup);
                        else {
                            root.getChildren().addAll(dOptionZ.optionGroup, dOptionX.optionGroup);
                            if (!twoOptions) root.getChildren().add(dOptionC.optionGroup);
                        }
                        hasDialogue = true;
                        if (dialogueNum++ > 43) {
                            stop();
                            new Level3(InterconnectedIsolation.getWindow(), false, 2298, 720, 0, 8, true, true, true, false).display();
                        }
                        letGo = false;
                    }

                }

                if (flowSceneNum == 8 && System.currentTimeMillis() - time > 8000) {
                    stop();
                    new Level3(InterconnectedIsolation.getWindow(), true, 2298, 720, 2, 9, true, true, true, false).display();
                }

                if (flowSceneNum == 10 && callFriend) {
                    if (!hasDialogue && System.currentTimeMillis() - lastDialogue > 200 && letGo) {
                        switch (dialogueNum) {
                            case 0:
                            case 1:
                            case 2:
                                dialogue.setDialogue("...");
                                break;
                            case 3:
                                dialogue.setDialogue("Friend: Hello?");
                                break;
                            case 4:
                                dialogue.setDialogue("You: Hi.");
                                break;
                            case 5:
                                dialogue.setDialogue("Friend: Who is this? " +
                                        "I don’t have this number saved.");
                                break;
                            case 6:
                                dialogue.setDialogue("You: It's " + player.getName() + ".");
                                break;
                            case 7:
                                dialogue.setDialogue("Friend: Oh hey " + player.getName() + ", it’s been a while. " +
                                        "What’s going on?");
                                break;
                            case 8:
                                dialogue.setDialogue("You: Nothing much, I just wanted to check in with you since it’s been a long time.");
                                break;
                            case 9:
                                dialogue.setDialogue("Friend: Yeah it’s nice to talk to you again. " +
                                        "I’m glad you’re doing alright.");
                                break;
                            case 10:
                                dialogue.setDialogue("You: We should meet up sometime. " +
                                        "School’s almost over.");
                                break;
                            case 11:
                                dialogue.setDialogue("Friend: Yeah that’s a great idea. " +
                                        "Hey I’ve got to go now. Bye!");
                                break;
                            case 12:
                                dialogue.setDialogue("You: Bye.");
                                break;
                        }

                        lastDialogue = System.currentTimeMillis();
                        hasDialogue = true;
                        root.getChildren().add(dialogue.dialogueGroup);
                        if (dialogueNum++ > 11) {
                            callFriend = false;
                        }
                        letGo = false;
                    }
                }

                if (flowSceneNum == 11) {
                    componentsGroup.setTranslateX(-750);
                    if (!hasDialogue && System.currentTimeMillis() - lastDialogue > 200 && letGo) {
                        if (dialogueNum == 0) {
                            dialogue.setDialogue("Mom: Hey " + player.getName() + ". " +
                                    "I just came to check on you. " +
                                    "It’s been a few months since we’ve had that talk, and I just wanted to see if you’ve improved on your situation or not.");
                        }
                        if (Level3.isPlayedGames()) {
                            if (dialogueNum == 1) {
                                dialogue.setDialogue("Mom: It would be great if you stopped playing for a little bit and talked to me.");
                            }
                        }

                        lastDialogue = System.currentTimeMillis();
                        hasDialogue = true;
                        root.getChildren().add(dialogue.dialogueGroup);
                        if (dialogueNum > 1 && Level3.isPlayedGames() || dialogueNum > 0 && !Level3.isPlayedGames()) {
                            stop();
                            new Level3(InterconnectedIsolation.getWindow(), false, 2405, 720, 1, 12, componentsGroup.getChildren().contains(homework), componentsGroup.getChildren().contains(plates), componentsGroup.getChildren().contains(trash), false).display();
                        }
                        dialogueNum++;
                        letGo = false;
                    }
                }

                if (flowSceneNum == 12) {
                    componentsGroup.setTranslateX(-750);
                    if (!hasDialogue && System.currentTimeMillis() - lastDialogue > 200 && letGo) {
                        if (InterconnectedIsolation.getImproveNum() < 5)
                            dialogue.setDialogue("Mom: I see you’ve tried to make an effort to improve. " +
                                    "I know it’s not easy but I think you can improve a lot more. ");
                        else
                            dialogue.setDialogue("Mom: I’m really proud of how far you’ve come. " +
                                    "I know it’s not easy. I think that both you and me have benefited from the changes you’ve decided to make.");

                        lastDialogue = System.currentTimeMillis();
                        hasDialogue = true;
                        root.getChildren().add(dialogue.dialogueGroup);
                        if (dialogueNum++ > 0) {
                            stop();
                            InterconnectedIsolation.getWindow().setScene(new EndScreen().window());
                        }
                        letGo = false;
                    }
                }

                if (flowSceneNum != 3 && flowSceneNum != 4 && flowSceneNum != 7 && flowSceneNum != 8 && flowSceneNum < 11 && checkForInteraction() && canInteract) {
                    if (!hasArrow) {
                        if (sceneNum == 1)
                            componentsGroup.getChildren().add(componentsGroup.getChildren().size() - 1, arrow);
                        else
                            componentsGroup.getChildren().add(1, arrow);
                        hasArrow = true;
                    }
                    if (ePressed) {
                        leftPressed = false;
                        rightPressed = false;
                        enterPressed = false;
                        for (Obj o : objects.get(sceneNum & 1)) {
                            if (o.isNear()) {
                                if (flowSceneNum == 10) {
                                    switch (o.getObjName()) {
                                        case "laundry":
                                            setOptions("Do laundry", "Leave it");
                                            twoOptions = true;
                                            break;
                                        case "homework":
                                            setOptions("Do homework", "Leave it for tomorrow");
                                            twoOptions = true;
                                            break;
                                        case "dresser":
                                            setOptions("Organize clothes in dresser", "Leave it how it is");
                                            twoOptions = true;
                                            break;
                                        case "plates":
                                            setOptions("Clean up", "Leave them there");
                                            twoOptions = true;
                                            break;
                                        case "picture":
                                            setOptions("Call friend", "Put it back on the shelf");
                                            twoOptions = true;
                                            break;
                                        case "trash":
                                            setOptions("Empty bin", "Leave it for later");
                                            twoOptions = true;
                                            break;
                                        case "guitar":
                                            setOptions("Tune it", "Leave");
                                            twoOptions = true;
                                    }
                                }
                                boolean allDone = false;
                                o.setInteracted(true);
                                if (o.getObjName().equals("computer")) {
                                    allDone = true;
                                    for (Obj o2 : objects.get(sceneNum & 1)) {
                                        if (o2 != o && !o2.isInteracted()) {
                                            allDone = false;
                                            break;
                                        }
                                    }
                                    if (flowSceneNum == 2) {
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
                                    } else if (flowSceneNum == 10) {
                                        if (allDone) {
                                            setOptions("Play games", "Leave it");
                                            twoOptions = true;
                                        } else {
                                            dialogue.setDialogue("Hmm, not right now.");
                                        }
                                    }
                                    lastUsage = System.currentTimeMillis();
                                } else {
                                    if (flowSceneNum == 2 || flowSceneNum == 6)
                                        dialogue.setDialogue(o.getDialogue());
                                    else if (flowSceneNum == 10)
                                        dialogue.setDialogue(o.getDialogue2());
                                }
                                if (allDone) {
                                    level1Done = true;
                                }
                                if (o.getObjName().equals("seat")) {
                                    setOptions("Sit down", "Keep looking around");
                                    twoOptions = true;
                                    lastUsage = System.currentTimeMillis();
                                }
                                objChoiceName = o.getObjName();
                            }
                        }
                        if (!hasDialogue) {
                            if (!isChoice)
                                root.getChildren().add(dialogue.dialogueGroup);
                            else {
                                root.getChildren().addAll(dOptionZ.optionGroup, dOptionX.optionGroup);
                                if (!twoOptions) root.getChildren().add(dOptionC.optionGroup);
                            }
                            hasDialogue = true;
                        }
                    }
                } else {
                    componentsGroup.getChildren().remove(arrow);
                    hasArrow = false;
                }

                if (level1Done && (enterPressed || zPressed || xPressed || cPressed) && System.currentTimeMillis() - lastUsage > 200) {
                    stop();
                    new Cutscene(InterconnectedIsolation.getWindow(), 2405, 720, 1, 3).display();
                }


                if (enterPressed || zPressed || xPressed || cPressed) {
                    if (!enterPressed && isChoice) {
                        if (numOfOpt == 3 || numOfOpt == 2 && !cPressed) {
                            root.getChildren().removeAll(dOptionZ.optionGroup, dOptionX.optionGroup, dOptionC.optionGroup);
                            hasDialogue = false;
                            if (objChoiceName.equals("seat") && zPressed) {
                                stop();
                                new Level2(InterconnectedIsolation.getWindow(), false, 2298, 720, 2, 7).display();
                            } else if (!objChoiceName.equals("") && zPressed) {
                                switch (objChoiceName) {
                                    case "laundry":
                                        componentsGroup.getChildren().remove(laundry);
                                        InterconnectedIsolation.setImproveNum(InterconnectedIsolation.getImproveNum() + 1);
                                        break;
                                    case "homework":
                                        componentsGroup.getChildren().remove(homework);
                                        InterconnectedIsolation.setImproveNum(InterconnectedIsolation.getImproveNum() + 1);
                                        break;
                                    case "plates":
                                        componentsGroup.getChildren().remove(plates);
                                        InterconnectedIsolation.setImproveNum(InterconnectedIsolation.getImproveNum() + 1);
                                        break;
                                    case "trash":
                                        componentsGroup.getChildren().remove(trash);
                                        InterconnectedIsolation.setImproveNum(InterconnectedIsolation.getImproveNum() + 1);
                                        break;
                                    case "picture":
                                        callFriend = true;
                                        InterconnectedIsolation.setImproveNum(InterconnectedIsolation.getImproveNum() + 1);
                                        break;
                                    case "computer":
                                        new Level3(InterconnectedIsolation.getWindow(), false, 2405, 720, 1, 11, componentsGroup.getChildren().contains(homework), componentsGroup.getChildren().contains(plates), componentsGroup.getChildren().contains(trash), true).display();
                                        break;
                                }
                            } else if (objChoiceName.equals("computer") && xPressed) {
                                InterconnectedIsolation.setImproveNum(InterconnectedIsolation.getImproveNum() + 1);
                                new Level3(InterconnectedIsolation.getWindow(), false, 2405, 720, 1, 11, componentsGroup.getChildren().contains(homework), componentsGroup.getChildren().contains(plates), componentsGroup.getChildren().contains(trash), false).display();
                            }
                            objChoiceName = "";
                            isChoice = false;
                            if (zPressed)
                                dialogueChoice = 'z';
                            else if (xPressed)
                                dialogueChoice = 'x';
                            else
                                dialogueChoice = 'c';
                        }
                    } else if (enterPressed && !isChoice) {
                        root.getChildren().remove(dialogue.dialogueGroup);
                        hasDialogue = false;
                    }
                }

                if (flowSceneNum != 3 && flowSceneNum != 4 && flowSceneNum != 7 && flowSceneNum != 8 && flowSceneNum < 11 && checkForDoor() && canExit) {
                    if (!hasArrowRed) {
                        componentsGroup.getChildren().add(arrowRed);
                        hasArrowRed = true;
                    }
                    if (ePressed) {
                        stop();
                        if (flowSceneNum == 1) {
                            new Level1(InterconnectedIsolation.getWindow(), 2405, 720, 1, 2).display();
                        } else if (flowSceneNum == 5) {
                            new Level2(InterconnectedIsolation.getWindow(), true, 2298, 720, 2, 6).display();
                        } else if (flowSceneNum == 9) {
                            new Level3(InterconnectedIsolation.getWindow(), true, 2405, 720, 1, 10, true, true, true, false).display();
                        }
                    }
                } else {
                    componentsGroup.getChildren().remove(arrowRed);
                    hasArrowRed = false;
                    nearDoor = false;
                }

                if (flowSceneNum != 3 && flowSceneNum != 4 && flowSceneNum != 7 && flowSceneNum != 8 && flowSceneNum < 11) {
                    if (rightPressed && player.getCanMoveRight()) {
                        player.playerView.setImage(InterconnectedIsolation.moonwalk ? player.getPlayerLeft() : player.getPlayerRight());
                        player.moveRight();
                    } else if (leftPressed && player.getCanMoveLeft()) {
                        player.playerView.setImage(InterconnectedIsolation.moonwalk ? player.getPlayerRight() : player.getPlayerLeft());
                        player.moveLeft();
                    } else player.playerView.setImage(player.getPlayerStand());
                }
            }
        }.start();
    }

    /**
     * Checks if the player is colliding with something.
     * If there is a collision on the left, the player can no longer move left, and id there is a collision on the right, the player can no longer move right.
     *
     * @since 1.3
     */
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

    /**
     * @return whether or not the player is colliding with the wall on the right.
     * @since 1.3
     */
    public boolean checkForCollisionOnRight() {
        if (player.getMaxX() >= stageWidth - 20)
            return true;
        return false;
    }

    /**
     * @return whether or not the player is colliding with the wall on the left.
     * @since 1.3
     */
    public boolean checkForCollisionOnLeft() {
        if (player.getMinX() <= 120)
            return true;
        return false;
    }

    /**
     * Checks if the player is near an object.
     * If the player is near an object, the x and y coordinates of the arrow are set appropriately.
     * <p>
     * 1.7 - Julia Xie
     * <br>Added checking for more objects.
     * <br>Added mechanic where the player is only able to interact with objects once.
     * </p>
     * <p>
     * 1.8 - Chris Trevisan
     * <br>Combined all objects into one ArrayList
     * </p>
     *
     * @return whether or not the player is near an object
     * @since 1.5
     */
    public boolean checkForInteraction() {
        boolean ret = false;
        for (Obj o : objects.get(sceneNum & 1)) {
            if (player.getAverageX() > o.getPosl() && player.getAverageX() < o.getPosr() && (!o.isInteracted() ||
                    (o.getObjName().equals("computer") || o.getObjName().equals("seat"))
                            && System.currentTimeMillis() - lastUsage > 200 && !hasDialogue)) {
                o.setNear(true);
                arrow.setX((o.getPosl() + o.getPosr()) / 2.0);
                arrow.setY(o.getArrowY());
                ret = true;
            } else {
                o.setNear(false);
            }
        }
        return ret;
    }

    /**
     * Checks if the player is near a door.
     * If the player is near a door, the x and y coordinates of the red arrow are set appropriately.
     *
     * @return whether or not the player is near a door
     * @since 1.5
     */
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

    /**
     * Sets the dialogue options.
     * @param z the first dialogue option
     * @param x the second dialogue option
     * @since 1.11
     */
    public void setOptions(String z, String x) {
        isChoice = true;
        dOptionZ.setOption(z);
        dOptionX.setOption(x);
        dOptionC.setOption("");
        numOfOpt = 2;
    }

    /**
     * Sets the dialogue options.
     * @param z the first dialogue option
     * @param x the second dialogue option
     * @param c the third dialogue option
     * @since 1.11
     */
    public void setOptions(String z, String x, String c) {
        isChoice = true;
        dOptionZ.setOption(z);
        dOptionX.setOption(x);
        dOptionC.setOption(c);
        numOfOpt = 3;
    }

    /**
     * Creates all the object sprites and adds them to the background if the scene needs it.
     * <p>
     * 1.7 - Julia Xie
     * <br>Added laundry, guitar, picture, plates, homework, trash, and bag sprites.
     * </p>
     * <p>
     * 1.10 - Julia Xie
     * <br>Added more sprites.
     * </p>
     * <p>
     * 1.11 - Julia Xie
     * <br>Added the rest of the sprites for the game.
     * </p>
     *
     * @param flowSceneNum the number of scene changes
     * @throws IOException if there is an input or output exception when looking for an image
     * @since 1.6
     */
    public void addObjects(int flowSceneNum) throws IOException {
        if (flowSceneNum == 1 || flowSceneNum == 9) {
            ImageView mom = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/mom_flip.png")));
            mom.setFitHeight(315);
            mom.setPreserveRatio(true);
            mom.setX(600);
            mom.setY(280);

            componentsGroup.getChildren().add(1, mom);
        }

        if (flowSceneNum >= 2 && flowSceneNum <= 5 || flowSceneNum >= 10) {
            laundry = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/laundry.png")));
            laundry.setFitHeight(153);
            laundry.setPreserveRatio(true);
            laundry.setX(279);
            laundry.setY(441);

            ImageView guitar = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/guitar.png")));
            guitar.setFitHeight(396);
            guitar.setPreserveRatio(true);
            guitar.setX(774);
            guitar.setY(205);

            ImageView picture = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/picture.png")));
            picture.setFitHeight(63);
            picture.setPreserveRatio(true);
            picture.setX(1098);
            picture.setY(369);

            if (flowSceneNum < 11 || Level3.isHasPlates()) {
                plates = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/plates.png")));
                plates.setFitHeight(72);
                plates.setPreserveRatio(true);
                plates.setX(1179);
                plates.setY(396);
                componentsGroup.getChildren().add(1, plates);
            }

            if (flowSceneNum < 11 || Level3.isHasHomework()) {
                homework = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/unfinished_homework.png")));
                homework.setFitHeight(72);
                homework.setPreserveRatio(true);
                homework.setX(1583);
                homework.setY(396);
                componentsGroup.getChildren().add(1, homework);
            }

            if (flowSceneNum < 11 || Level3.isHasTrash()) {
                trash = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/trash.png")));
                trash.setFitHeight(135);
                trash.setPreserveRatio(true);
                trash.setX(927);
                trash.setY(486);
                componentsGroup.getChildren().add(1, trash);
            }

            componentsGroup.getChildren().add(1, laundry);
            componentsGroup.getChildren().add(1, guitar);
            componentsGroup.getChildren().add(1, picture);

        }
        if (flowSceneNum == 3 || flowSceneNum == 4 || flowSceneNum == 5 || flowSceneNum == 11 || flowSceneNum == 12) {
            ImageView bag = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/bag.png")));
            bag.setFitHeight(117);
            bag.setPreserveRatio(true);
            bag.setX(1565);
            bag.setY(505);

            componentsGroup.getChildren().add(1, bag);

            if (flowSceneNum == 3 || flowSceneNum == 4 || flowSceneNum == 11 && Level3.isPlayedGames()) {
                ImageView playerAtComputer = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/player_sitting_at_chair.png")));
                playerAtComputer.setFitHeight(315);
                playerAtComputer.setPreserveRatio(true);
                playerAtComputer.setX(1323);
                playerAtComputer.setY(288);

                componentsGroup.getChildren().add(1, playerAtComputer);
            }
            if (flowSceneNum == 11 || flowSceneNum == 12) {
                ImageView mom = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/mom.png")));
                mom.setFitHeight(315);
                mom.setPreserveRatio(true);
                mom.setX(900);
                mom.setY(358);

                componentsGroup.getChildren().add(componentsGroup.getChildren().size() - 2, mom);
                if (flowSceneNum == 11 && !Level3.isPlayedGames() || flowSceneNum == 12) {
                    ImageView player = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/player_flip.png")));
                    player.setFitHeight(333);
                    player.setPreserveRatio(true);
                    player.setX(1360);
                    player.setY(340);

                    componentsGroup.getChildren().add(componentsGroup.getChildren().size() - 2, player);
                }
            }
        }
        if (flowSceneNum == 6 || flowSceneNum == 7) {
            ImageView momAtTable = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/mom_sitting_at_table.png")));
            momAtTable.setFitHeight(315);
            momAtTable.setPreserveRatio(true);
            momAtTable.setX(1269);
            momAtTable.setY(288);

            componentsGroup.getChildren().add(1, momAtTable);
            if (flowSceneNum == 7) {
                ImageView playerAtTable = new ImageView(new Image(new FileInputStream("Interconnected-Isolation/assets/images/player_sitting_at_table.png")));
                playerAtTable.setFitHeight(315);
                playerAtTable.setPreserveRatio(true);
                playerAtTable.setX(763);
                playerAtTable.setY(279);

                componentsGroup.getChildren().add(1, playerAtTable);
            }
        }
    }

    /**
     * Initializes the stage with the player.
     * Sets the player's position.
     *
     * @param flowSceneNum the number of stage changes so far
     * @since 1.2
     */
    public abstract void initStage(int flowSceneNum);

    /**
     * Initializes the background with a picture or colour.
     * Sets constraints based on the scene number.
     *
     * @param sceneNum the scene number
     * @throws IOException if an input or output exception occurs when looking for an image
     * @since 1.2
     */
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
                    letGo = true;
                    break;
                case Z:
                    zPressed = false;
                    letGo = true;
                    break;
                case X:
                    xPressed = false;
                    letGo = true;
                    break;
                case C:
                    cPressed = false;
                    letGo = true;
                    break;
            }
        }
    };

    /**
     * @param width the width of the scene
     * @since 1.3
     */
    public void setStageWidth(int width) {
        stageWidth = width;
    }

    /**
     * @param stageHeight the height of the scene
     * @since 1.3
     */
    public void setStageHeight(double stageHeight) {
        this.stageHeight = stageHeight;
    }

    /**
     * @param canExit whether or not the player can exit the scene
     * @since 1.6
     */
    public void setCanExit(boolean canExit) {
        this.canExit = canExit;
    }

    /**
     * @param canInteract whether or not the player can interact with objects in the scene
     * @since 1.6
     */
    public void setCanInteract(boolean canInteract) {
        this.canInteract = canInteract;
    }

    /**
     * Displays the scene on the stage.
     * <p>
     * 1.5 - Julia Xie
     * <br>Added fade transition between scenes
     * </p>
     *
     * @since 1.2
     */
    public void display() {
        FadeTransition fade = new FadeTransition(Duration.millis(4000), root);
        fade.setFromValue(0);
        fade.setToValue(1);
        if (flowSceneNum == 8) {
            fade.setCycleCount(2);
            fade.setAutoReverse(true);
        }
        fade.play();
        stage.setScene(scene);
    }
}
