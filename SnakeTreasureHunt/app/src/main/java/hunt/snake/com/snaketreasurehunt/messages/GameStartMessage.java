package hunt.snake.com.snaketreasurehunt.messages;

import hunt.snake.com.snaketreasurehunt.GameBoard;
import hunt.snake.com.snaketreasurehunt.communication.STHMessage;

/**
 * Created by Tom on 1/23/15.
 */
public class GameStartMessage extends Message {

    public GameBoard gameBoard;

    public GameStartMessage() {
        gameBoard = new GameBoard();
        setType(STHMessage.GAMESTART_MESSAGE);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public class GameBoard {

        private int fieldHeight;
        private int fieldWidth;

        public GameBoard() {}

        public void setFieldWidth(int fieldWidth) {
            this.fieldWidth = fieldWidth;
        }

        public void setFieldHeight(int fieldHeight) {
            this.fieldHeight = fieldHeight;
        }

        public int getFieldHeight() {
            return fieldHeight;
        }

        public int getFieldWidth() {
            return fieldWidth;
        }
    }



}
