package hunt.snake.com.snaketreasurehunt.messages;

import hunt.snake.com.snaketreasurehunt.Snake;

public class SnakeMessage {

    private int[] bodypartXPos;
    private int[] bodypartYPos;
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

    public Snake.Direction getHeadDirection() { return headDirection; }

    public void setHeadDirection(Snake.Direction headDirection) { this.headDirection = headDirection; }
}
