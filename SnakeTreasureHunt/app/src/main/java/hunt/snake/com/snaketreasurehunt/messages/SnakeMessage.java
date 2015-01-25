package hunt.snake.com.snaketreasurehunt.messages;

import hunt.snake.com.snaketreasurehunt.Snake;

/**
 * Created by lino on 23.01.15.
 */
public class SnakeMessage {

    private int[] bodypartXPos;
    private int[] bodypartYPos;
    private boolean[] cornerPart;
    private int[] origin;
    private int[] direction;
    private Snake.Direction headDirection;

    public SnakeMessage() {}

    public int[] getBodypartXPos() {
        return bodypartXPos;
    }

    public void setBodypartXPos(int[] bodypartXPos) {
        this.bodypartXPos = bodypartXPos;
    }

    public int[] getBodypartYPos() {
        return bodypartYPos;
    }

    public void setBodypartYPos(int[] bodypartYPos) {
        this.bodypartYPos = bodypartYPos;
    }

    public boolean[] getCornerPart() {
        return cornerPart;
    }

    public void setCornerPart(boolean[] cornerPart) {
        this.cornerPart = cornerPart;
    }

    public int[] getOrigin() {
        return origin;
    }

    public void setOrigin(int[] origin) {
        this.origin = origin;
    }

    public int[] getDirection() {
        return direction;
    }

    public void setDirection(int[] direction) {
        this.direction = direction;
    }

    public Snake.Direction getHeadDirection() { return headDirection; }

    public void setHeadDirection(Snake.Direction headDirection) { this.headDirection = headDirection; }
}
