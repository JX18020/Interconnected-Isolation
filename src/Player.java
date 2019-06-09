import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a movable player.
 * <p>
 * References:
 * <br>Dillon, A. (2015). Picasso (Version 2) [Software]. Retrieved from https://github.com/Hopding/Picasso.
 * </p>
 *
 * @author Julia Xie
 * @author Christopher Trevisan
 * @version 1.11
 * <p>
 * 1.2 - Julia Xie
 * <br>Date: 2019/05/29
 * <br>Time Spent: 1 hour
 * <br>Added constructor.
 * <br>Added getMinX() method.
 * <br>Added getMaxX() method.
 * <br>Added reposition() method.
 * <br>Added moreRight() method.
 * <br>Added moveLeft() method.
 * <br>Added isOnRightEdge() method.
 * <br>Added isOnLeftEdge() method.
 * <br>Added getBounds() method.
 * <br>Added setSpeed() method.
 * <br>Added getSpeed() method.
 * <br>Added setCanMoveRight() method.
 * <br>Added getCanMoveRight() method.
 * <br>Added setCanMoveLeft() method.
 * <br>Added getCanMoveLeft() method.
 * </p>
 * <p>
 * 1.3 - Julia Xie
 * <br>Date: 2019/05/30
 * <br>Time Spent: 30 minutes
 * <br>Added getAverageX() method.
 * <br>Added getPlayerRight() method.
 * <br>Added getPlayerLeft() method.
 * </p>
 * <p>
 * 1.3 - Chris Trevisan
 * <br>Date: 2019/05/30
 * <br>Time Spent: 30 minutes
 * <br>Modified constructor.
 * <br>Modified getPlayerRight() method.
 * <br>Modified getPlayerRight() method.
 * <br>Added getPlayerStand() method.
 * </p>
 * <p>
 * 1.4 - Chris Trevisan
 * <br>Date: 2019/05/31
 * <br>Time Spent: 30 minutes
 * <br>Modified constructor.
 * </p>
 * <p>
 * 1.3 - Julia Xie
 * <br>Date: 2019/06/01
 * <br>Time Spent: 1 hour
 * <br>Modified constructor.
 * </p>
 * <p>
 * 1.11 - Julia Xie
 * <br>Date: 2019/06/08
 * <br>Time Spent: 10 minutes
 * <br>Added setName() method.
 * <br>Added getName() method.
 * </p>
 * @since 1.2
 */
public class Player {
    /**
     * The width of the stage.
     */
    private double courseWidth;
    /**
     * The height of the stage.
     */
    private double courseHeight;
    /**
     * The speed of the player.
     */
    private double stateSpeed;
    /**
     * An ArrayList of Images containing the walking animations.
     */
    private ArrayList<Image> animation, animation2, animation3, animation4;
    /**
     * Images which are the standing positions
     */
    private Image playerStand, playerStandL, playerStandBag, playerStandBagL;
    /**
     * The image currently in view.
     */
    ImageView playerView;
    /**
     * The name of the player.
     */
    private static String name;

    /**
     * Whether or not the player is carrying a bag.
     */
    private static boolean hasBag;
    /**
     * Whether or not the player is facing right.
     */
    private boolean facingRight;
    /**
     * Whether or not the player is on the right side of the screen.
     */
    private boolean stateOnRightEdgeOfScreen;
    /**
     * Whether or not the player is on the left side of the screen.
     */
    private boolean stateOnLeftEdgeOfScreen;

    /**
     * Whether or not the player is able to move right.
     */
    private boolean canMoveRight = true;
    /**
     * Whether or not the player is able to move left.
     */
    private boolean canMoveLeft = true;
    /**
     * The animation counters.
     */
    private int rightAnimation = 0, leftAnimation = 0, rightcnt = 0, leftcnt = 0;

    /**
     * Constructor for the Player class.
     * Initializes all variables adds it to the scene.
     * <p>
     * 1.3 - Chris Trevisan
     * <br>Added player walking animation into an ArrayList.
     * </p>
     * <p>
     * 1.4 - Chris Trevisan
     * <br>Added left walking animation into an ArrayList.
     * </p>
     * <p>
     * 1.3 - Julia Xie
     * <br>Added walking animation with a bag into an ArrayList.
     * </p>
     *
     * @param componentsGroup the group which the player is added to
     * @param cWidth          the width of the scene
     * @param cHeight         the height of the scene
     * @since 1.2
     */
    public Player(Group componentsGroup, double cWidth, double cHeight) {
        courseWidth = cWidth;
        courseHeight = cHeight;
        stateSpeed = 5;
        animation = new ArrayList<>();
        animation2 = new ArrayList<>();
        animation3 = new ArrayList<>();
        animation4 = new ArrayList<>();
        try {
            playerStandL = new Image(new FileInputStream("../assets/images/player_flip.png"));
            playerStand = new Image(new FileInputStream("../assets/images/player.png"));
            playerStandBagL = new Image(new FileInputStream("../assets/images/player_bag_flip.png"));
            playerStandBag = new Image(new FileInputStream("../assets/images/player_bag.png"));
            for (int x = 1; x <= 8; x++)
                animation2.add(new Image(new FileInputStream("../assets/images/walk_cycle_player_left/player_walking_" + x + ".png")));
            for (int x = 1; x <= 8; x++)
                animation.add(new Image(new FileInputStream("../assets/images/walk_cycle_player/player_walking_" + x + ".png")));
            for (int x = 1; x <= 8; x++)
                animation4.add(new Image(new FileInputStream("../assets/images/walk_cycle_player_bag_left/player_walking_bag_" + x + ".png")));
            for (int x = 1; x <= 8; x++)
                animation3.add(new Image(new FileInputStream("../assets/images/walk_cycle_player_bag/player_walking_bag_" + x + ".png")));
        } catch (IOException e) {
            System.out.println("oopsio");
        }
        if (hasBag)
            playerView = new ImageView(playerStandBag);
        else
            playerView = new ImageView(playerStand);
        playerView.setFitHeight(333);
        playerView.setPreserveRatio(true);
        componentsGroup.getChildren().add(playerView);
        facingRight = true;
    }

    /**
     * @return the minimum x coordinate of the player
     * @since 1.2
     */
    public double getMinX() {
        return playerView.getBoundsInParent().getMinX();
    }

    /**
     * @return the maximum x coordinate of the player
     * @since 1.2
     */
    public double getMaxX() {
        return playerView.getBoundsInParent().getMaxX();
    }

    public double getAverageX() {
        return (playerView.getBoundsInParent().getMaxX() + playerView.getBoundsInParent().getMinX()) / 2;
    }

    /**
     * Repositions the player to the specified coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @since 1.2
     */
    public void reposition(double x, double y) {
        playerView.setTranslateX(Math.floor(x));
        playerView.setTranslateY(Math.floor(y));
    }

    /**
     * Moves the player to the right.
     *
     * @since 1.2
     */
    public void moveRight() {
        playerView.setTranslateX(Math.floor(playerView.getTranslateX() + stateSpeed));
    }

    /**
     * Moves the player to the left.
     *
     * @since 1.2
     */
    public void moveLeft() {
        playerView.setTranslateX(Math.floor(playerView.getTranslateX() - stateSpeed));
    }

    /**
     * @return if the player is on the right edge of the scene
     * @since 1.2
     */
    public boolean isOnRightEdge() {
        if (playerView.getBoundsInParent().getMaxX() >= courseWidth) {
            stateOnRightEdgeOfScreen = true;
            return stateOnRightEdgeOfScreen;
        } else {
            stateOnRightEdgeOfScreen = false;
            return stateOnRightEdgeOfScreen;
        }
    }

    /**
     * @return if the player is on the left edge of the scene
     * @since 1.2
     */
    public boolean isOnLeftEdge() {
        if (playerView.getBoundsInParent().getMinX() <= 0) {
            stateOnLeftEdgeOfScreen = true;
            return stateOnLeftEdgeOfScreen;
        } else {
            stateOnLeftEdgeOfScreen = false;
            return stateOnLeftEdgeOfScreen;
        }
    }

    /**
     * @return the player's boundaries.
     * @since 1.2
     */
    public Bounds getBounds() {
        return playerView.getBoundsInParent();
    }

    /**
     * @param speed the speed at which the player moves
     * @since 1.2
     */
    public void setSpeed(double speed) {
        stateSpeed = speed;
    }

    /**
     * @return the speed at which the player moves
     * @since 1.2
     */
    public double getSpeed() {
        return stateSpeed;
    }

    /**
     * @param canMove whether or not the player can move right
     * @since 1.2
     */
    public void setCanMoveRight(boolean canMove) {
        canMoveRight = canMove;
    }

    /**
     * @return whether or not the player can move right
     * @since 1.2
     */
    public boolean getCanMoveRight() {
        return canMoveRight;
    }

    /**
     * @param canMove whether or not the player can move left
     * @since 1.2
     */
    public void setCanMoveLeft(boolean canMove) {
        canMoveLeft = canMove;
    }

    /**
     * @return whether or not the player can move left
     * @since 1.2
     */
    public boolean getCanMoveLeft() {
        return canMoveLeft;
    }

    /**
     * <p>
     * 1.3 - Chris Trevisan
     * <br>Modified it so it returns the walking animation.
     * </p>
     *
     * @return the image of the player facing right
     * @since 1.3
     */
    public Image getPlayerRight() {
        if (++rightcnt == 5) {
            rightcnt = 0;
            rightAnimation++;
        }
        leftAnimation = 0;
        rightAnimation %= 8;
        leftcnt = 0;
        facingRight = true;
        if (hasBag)
            return animation3.get(rightAnimation);
        return animation.get(rightAnimation);
    }

    /**
     * <p>
     * 1.3 - Chris Trevisan
     * <br>Modified it so it returns the walking animation.
     * </p>
     *
     * @return the image of the player facing left
     * @since 1.3
     */
    public Image getPlayerLeft() {
        if (++leftcnt == 5) {
            leftcnt = 0;
            leftAnimation++;
        }
        rightAnimation = 0;
        leftAnimation %= 8;
        rightcnt = 0;
        facingRight = false;
        if (hasBag)
            return animation4.get(leftAnimation);
        return animation2.get(leftAnimation);
    }

    /**
     * @return the image of the player standing
     * @since 1.3
     */
    public Image getPlayerStand() {
        leftcnt = 0;
        rightcnt = 0;
        leftAnimation = 0;
        rightAnimation = 0;
        if (hasBag)
            return facingRight ? playerStandBag : playerStandBagL;
        return facingRight ? playerStand : playerStandL;
    }

    /**
     * @param playerName the name of the player
     * @since 1.11
     */
    public static void setName(String playerName) {
        name = playerName;
    }

    /**
     * @param hasBag if the player has a bag
     * @since 1.11
     */
    public static void setHasBag(boolean hasBag) {
        Player.hasBag = hasBag;
    }

    /**
     * @return the name of the player
     * @since 1.11
     */
    public static String getName() {
        return name;
    }
}
