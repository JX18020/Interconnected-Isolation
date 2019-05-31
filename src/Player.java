import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private double courseWidth;
    private double courseHeight;
    private double stateSpeed;
    private ArrayList<Image> animation;
    private Image playerStand;
    ImageView playerView;

    boolean hasBag;

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
        try {
            playerStand = (new Image(new FileInputStream("assets/images/player.png"),195,481,true,true));
            for (int x = 1; x <= 8; x++) animation.add(new Image(new FileInputStream("assets/images/walk_cycle_player/player_walking_" + x + ".png")));
        } catch (IOException e) {
        }
        playerView = new ImageView(playerStand);
        playerView.setFitHeight(320);
        playerView.setPreserveRatio(true);
        componentsGroup.getChildren().add(playerView);
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
        playerView.setTranslateX(x);
        playerView.setTranslateY(y);
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
        if (++rightcnt == 5) {rightcnt = 0; rightAnimation++;}
        leftAnimation = 0;rightAnimation %= 8;leftcnt = 0;
        return animation.get(rightAnimation);
    }

    public Image getPlayerLeft() {
        if (++leftcnt == 5) {leftcnt = 0; leftAnimation++;}
        rightAnimation = 0;leftAnimation%=8;rightcnt=0;
        return animation.get(leftAnimation);
    }
    public Image getPlayerStand() {
        leftcnt = 0;rightcnt = 0;leftAnimation=0;rightAnimation=0;
        return playerStand;
    }
}
