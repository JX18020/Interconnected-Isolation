import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;

public class Player {
    double courseWidth;
    double courseHeight;
    double stateSpeed;

    Image playerRight;
    Image playerLeft;
    Image playerRightBag;
    Image playerLeftBag;
    ImageView playerView;

    private boolean stateOnRightEdgeOfScreen;
    private boolean stateOnLeftEdgeOfScreen;

    private boolean canMoveRight = true;
    private boolean canMoveLeft = true;

    public Player(Group componentsGroup, double cWidth, double cHeight) {
        courseWidth = cWidth;
        courseHeight = cHeight;
        stateSpeed = 5;

        try {
            playerRight = new Image(new FileInputStream("assets/images/player.png"));
            playerLeft = 
        } catch (IOException e) {
        }
        playerView = new ImageView(playerChar);
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

    public void reposition(double x, double y) {
        playerView.setTranslateX(x);
        playerView.setTranslateY(y);
    }

    public void moveRight() {
        playerView.setTranslateX(playerView.getTranslateX() + stateSpeed);
    }

    public void moveLeft() {
        playerView.setTranslateX(playerView.getTranslateX() - stateSpeed);
    }

    public boolean isOnRightEdge() {
        if (playerView.getBoundsInParent().getMaxX() >= courseWidth) {
            stateOnRightEdgeOfScreen = true;
            return stateOnRightEdgeOfScreen;
        }
        else {
            stateOnRightEdgeOfScreen = false;
            return stateOnRightEdgeOfScreen;
        }
    }

    public boolean isOnLeftEdge() {
        if (playerView.getBoundsInParent().getMinX() <= 0) {
            stateOnLeftEdgeOfScreen = true;
            return stateOnLeftEdgeOfScreen;
        }
        else {
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
}