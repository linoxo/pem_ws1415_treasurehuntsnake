package hunt.snake.com.snaketreasurehunt.stitching;

import hunt.snake.com.snaketreasurehunt.gamelogic.GameBoard;
import hunt.snake.com.snaketreasurehunt.gamelogic.SnakeTreasureHuntGame;

public class Stitching {
    boolean stitchingEnabled = false;
    private GameBoard board;

    public Stitching(GameBoard board){
        this.board = board;
    }
    public void enableStitching() {
        //is called when player swipes out of the active device enables stitching
        if (SnakeTreasureHuntGame.isPhoneActive) {
            stitchingEnabled = true;
            board.sendStitchOutMessage();
        }
    }
}
