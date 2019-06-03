import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a player
 *
 * @author Julia XIe
 * @version June 1, 2019
 */
public class Player {
    private double courseWidth;
    private double courseHeight;
    private double stateSpeed;
    private ArrayList<Image> animation, animation2, animation3, animation4;
    private Image playerStand, playerStandL, playerStandBag, playerStandBagL;
    ImageView playerView;

    static boolean hasBag;
    private boolean facingRight;
    private boolean stateOnRightEdgeOfScreen;
    private boolean stateOnLeftEdgeOfScreen;

    private boolean canMoveRight = true;
    private boolean canMoveLeft = true;
    private int rightAnimation = 0, leftAnimation = 0, rightcnt = 0, leftcnt = 0;

    public Player(Group componentsGroup, double cWidth, double cHeight) {
        courseWidth = cWidth;
        courseHeight = cHeight;
        stateSpeed = 5;
        animation = new ArrayList<>();
        animation2 = new ArrayList<>();
        animation3 = new ArrayList<>();
        animation4 = new ArrayList<>();
        try {
            playerStandL = new Image(new FileInputStream("assets/images/player_flip.png"));
            playerStand = new Image(new FileInputStream("assets/images/player.png"));
            playerStandBagL = new Image(new FileInputStream("assets/images/player_bag_flip.png"));
            playerStandBag = new Image(new FileInputStream("assets/images/player_bag.png"));
            for (int x = 1; x <= 8; x++)
                animation2.add(new Image(new FileInputStream("assets/images/walk_cycle_player_left/player_walking_" + x + ".png")));
            for (int x = 1; x <= 8; x++)
                animation.add(new Image(new FileInputStream("assets/images/walk_cycle_player/player_walking_" + x + ".png")));
            for (int x = 1; x <= 8; x++)
                animation4.add(new Image(new FileInputStream("assets/images/walk_cycle_player_bag_left/player_walking_bag_" + x + ".png")));
            for (int x = 1; x <= 8; x++)
                animation3.add(new Image(new FileInputStream("assets/images/walk_cycle_player_bag/player_walking_bag_" + x + ".png")));
        } catch (IOException e) {
            System.out.println("oopsio");
        }
        if (hasBag)
            playerView = new ImageView(playerStandBag);
        else
            playerView = new ImageView(playerStand);
        playerView.setFitHeight(320);
        playerView.setPreserveRatio(true);
        componentsGroup.getChildren().add(playerView);
        facingRight = true;
    }

    public double getMinX() {
        return playerView.getBoundsInParent().getMinX();
    }

    public double getMaxX() {
        return playerView.getBoundsInParent().getMaxX();
    }

    public double getAverageX() {
        return (playerView.getBoundsInParent().getMaxX() + playerView.getBoundsInParent().getMinX()) / 2;
    }

    public void reposition(double x, double y) {
        playerView.setTranslateX(Math.floor(x));
        playerView.setTranslateY(Math.floor(y));
    }

    public void moveRight() {
        playerView.setTranslateX(Math.floor(playerView.getTranslateX() + stateSpeed));
    }

    public void moveLeft() {
        playerView.setTranslateX(Math.floor(playerView.getTranslateX() - stateSpeed));
    }

    public boolean isOnRightEdge() {
        if (playerView.getBoundsInParent().getMaxX() >= courseWidth) {
            stateOnRightEdgeOfScreen = true;
            return stateOnRightEdgeOfScreen;
        } else {
            stateOnRightEdgeOfScreen = false;
            return stateOnRightEdgeOfScreen;
        }
    }

    public boolean isOnLeftEdge() {
        if (playerView.getBoundsInParent().getMinX() <= 0) {
            stateOnLeftEdgeOfScreen = true;
            return stateOnLeftEdgeOfScreen;
        } else {
            stateOnLeftEdgeOfScreen = false;
            return stateOnLeftEdgeOfScreen;
        }
    }

    public Bounds getBounds() {
        return playerView.getBoundsInParent();
    }

    public void setSpeed(double speed) {
        stateSpeed = speed;
    }

    public double getSpeed() {
        return stateSpeed;
    }

    public void setCanMoveRight(boolean canMove) {
        canMoveRight = canMove;
    }

    public boolean getCanMoveRight() {
        return canMoveRight;
    }

    public void setCanMoveLeft(boolean canMove) {
        canMoveLeft = canMove;
    }

    public boolean getCanMoveLeft() {
        return canMoveLeft;
    }

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

    public Image getPlayerStand() {
        leftcnt = 0;
        rightcnt = 0;
        leftAnimation = 0;
        rightAnimation = 0;
        if (hasBag)
            return facingRight ? playerStandBag : playerStandBagL;
        return facingRight ? playerStand : playerStandL;
    }
}
