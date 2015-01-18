package hunt.snake.com.snaketreasurehunt.communication;

import org.json.JSONException;

import hunt.snake.com.snaketreasurehunt.GameBoard;

/**
 * Created by lino on 18.01.15.
 */
public class STHMessageParser {

    STHMessage incomingMessage;

    public STHMessageParser(STHMessage incomingMessage) {

        this.incomingMessage = incomingMessage;

    }

    private void deserializeSTHMessage(GameBoard board) {
        try {

            int messageType = incomingMessage.getInt("type");

            switch (messageType) {
                case STHMessage.GAMESTART_MESSAGE:

                    GameBoard gb = (GameBoard) incomingMessage.get("board");

                    board.setTiles(gb.getTiles());
                    board.setGameElements(gb.getGameElements());
                    board.setSnake(gb.getSnake());


                    break;
                case STHMessage.GAMEOVER_MESSAGE:

                    int score = incomingMessage.getInt("highscore");

                    board.setScore(score);

                    break;
                case STHMessage.STITCHIN_MESSAGE:


                    break;
                case STHMessage.STITCHOUT_MESSAGE:

                    break;
                case STHMessage.NEWGUTTI_MESSAGE:

                    int foodX = incomingMessage.getInt("foodX");
                    int foodY = incomingMessage.getInt("foodY");

                    board.setFoodX(foodX);
                    board.setFoodY(foodY);

                    break;

            }
        } catch(JSONException je) {
            je.printStackTrace();
        }
    }
}
