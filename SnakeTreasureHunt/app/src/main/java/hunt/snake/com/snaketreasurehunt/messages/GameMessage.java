package hunt.snake.com.snaketreasurehunt.messages;

import hunt.snake.com.snaketreasurehunt.Snake;
import hunt.snake.com.snaketreasurehunt.communication.STHMessage;

public class GameMessage extends Message {

    private GameStart gameStart;
    private GameRunning gameRunning;
    private GameOver gameOver;
    private GameStitching gameStitching;
    private GamePauseStart gamePauseStart;
    private GamePauseStop gamePauseStop;
    private GameNewGutti gameNewGutti;
    private GameSnakeMovement gameSnakeMovement;


    public GameMessage(int messageType) {


        switch (messageType) {
            case STHMessage.GAMESTART_MESSAGE:
                setType(STHMessage.GAMESTART_MESSAGE);
                gameStart = new GameStart();
                gameNewGutti = new GameNewGutti();
                break;
            case STHMessage.GAMERUNNING_MESSAGE:
                setType(STHMessage.GAMERUNNING_MESSAGE);
                gameRunning = new GameRunning();
                break;
            case STHMessage.GAMEOVER_MESSAGE:
                setType(STHMessage.GAMEOVER_MESSAGE);
                gameOver = new GameOver();
                break;
            case STHMessage.STITCHING_MESSAGE:
                setType(STHMessage.STITCHING_MESSAGE);
                gameStitching = new GameStitching();
                break;
            case STHMessage.NEWGUTTI_MESSAGE:
                setType(STHMessage.NEWGUTTI_MESSAGE);
                gameNewGutti = new GameNewGutti();
                break;
            case STHMessage.GAMEPAUSE_START_MESSAGE:
                setType(STHMessage.GAMEPAUSE_START_MESSAGE);
                gamePauseStart = new GamePauseStart();
                break;
            case STHMessage.GAMEPAUSE_STOP_MESSAGE:
                setType(STHMessage.GAMEPAUSE_STOP_MESSAGE);
                gamePauseStop = new GamePauseStop();
                break;
            case STHMessage.MOVEMENT_MESSAGE:
                setType(STHMessage.MOVEMENT_MESSAGE);
                gameSnakeMovement = new GameSnakeMovement();
                break;

        }
    }

    public GameStart getGameStart() {
        return gameStart;
    }

    public GameRunning getGameRunning() {
        return gameRunning;
    }

    public GameOver getGameOver() { return gameOver; }

    public GameStitching getGameStitching() { return gameStitching; }

    public GamePauseStart getGamePauseStart() { return gamePauseStart; }

    public GamePauseStop getGamePauseStop() { return gamePauseStop; }

    public GameNewGutti getGameNewGutti() { return gameNewGutti; }

    public GameSnakeMovement getGameSnakeMovement() { return gameSnakeMovement; }


    public class GameStart {

        private int fieldHeight;
        private int fieldWidth;
        private int[] tileXPos;
        private int[] tileYPos;
        private int[] tileType;
        private SnakeMessage snake;

        public GameStart() {}

        public void setFieldWidth(int fieldWidth) {
            this.fieldWidth = fieldWidth;
        }

        public int getFieldWidth() {
            return fieldWidth;
        }

        public void setFieldHeight(int fieldHeight) {
            this.fieldHeight = fieldHeight;
        }

        public int getFieldHeight() {
            return fieldHeight;
        }

        public void setTileXPos(int[] tileXPos) { this.tileXPos = tileXPos; }

        public int[] getTileXPos() { return tileXPos; }

        public void setTileYPos(int[] tileYPos) { this.tileYPos = tileYPos; }

        public int[] getTileYPos() { return tileYPos; }

        public void setTileType(int[] tileType) { this.tileType = tileType; }

        public int[] getTileType() { return tileType; }

        public void setSnake(SnakeMessage snake) { this.snake = snake; }

        public SnakeMessage getSnake() { return snake; }

    }

    public class GameRunning {

        public GameRunning() {

        }

    }

    public class GameOver {

        private int score;

        public GameOver() {}

        public void setScore(int score) { this.score = score; }

        public int getScore() { return score; }

    }

    public class GameStitching {

        private float tickTime;
        private float timestamp;
        private int stitchingDirection;
        private int topLeftXPos;
        private int topLeftYPos;
        private SnakeMessage snake;


        public GameStitching() {}

        public float getTickTime() {
            return tickTime;
        }

        public void setTickTime(float tickTime) {
            this.tickTime = tickTime;
        }

        public float getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(float timestamp) {
            this.timestamp = timestamp;
        }

        public int getStitchingDirection() {
            return stitchingDirection;
        }

        public void setStitchingDirection(int stitchingDirection) {
            this.stitchingDirection = stitchingDirection;
        }

        public int getTopLeftXPos() {
            return topLeftXPos;
        }

        public void setTopLeftXPos(int topLeftXPos) {
            this.topLeftXPos = topLeftXPos;
        }

        public int getTopLeftYPos() {
            return topLeftYPos;
        }

        public void setTopLeftYPos(int topLeftYPos) {
            this.topLeftYPos = topLeftYPos;
        }

        public SnakeMessage getSnake() { return snake; }

        public void setSnake(SnakeMessage snake) { this.snake = snake; }

    }

    public class GamePauseStart {

        public GamePauseStart() {}

    }

    public class GamePauseStop {

        private float tickTime;

        public GamePauseStop() {}

        public void setTickTime(float tickTime) { this.tickTime = tickTime; }

        public float getTickTime() { return tickTime; }

    }

    public class GameNewGutti {

        private int foodXPos;
        private int foodYPos;
        private int score;

        public GameNewGutti() {}

        public int getFoodXPos() {
            return foodXPos;
        }

        public void setFoodXPos(int foodXPos) {
            this.foodXPos = foodXPos;
        }

        public int getFoodYPos() {
            return foodYPos;
        }

        public void setFoodYPos(int foodYPos) {
            this.foodYPos = foodYPos;
        }

        public void setScore(int score) { this.score = score; }

        public int getScore() { return score; }
    }

    public class GameSnakeMovement {
        private Snake.Direction direction;
        private SnakeMessage snake;

        public GameSnakeMovement() {}

        public Snake.Direction getMovementDirection() { return direction; }

        public void setMovementDirection(Snake.Direction direction) { this.direction = direction; }
    }
}
