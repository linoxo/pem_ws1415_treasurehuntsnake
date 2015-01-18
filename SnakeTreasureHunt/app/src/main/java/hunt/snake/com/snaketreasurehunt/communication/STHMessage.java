package hunt.snake.com.snaketreasurehunt.communication;

import org.json.JSONException;
import org.json.JSONObject;

import hunt.snake.com.snaketreasurehunt.GameBoard;
import hunt.snake.com.snaketreasurehunt.Snake;

/**
 * Created by lino on 18.01.15.
 */
public class STHMessage extends JSONObject {

    public static final int GAMEOVER_MESSAGE = 10024;

    public static final int GAMESTART_MESSAGE = 10034;

    public static final int NEWGUTTI_MESSAGE = 10044;

    public static final int STITCHOUT_MESSAGE = 10054;

    public static final int STITCHIN_MESSAGE = 10064;

    private GameBoard board;
    private Snake snake;

    public STHMessage(GameBoard board, int messagetype) {

        this.board = board;
        this.snake = board.getSnake();

        try {
            this.put("type", messagetype);
            handleMessageType(messagetype);

        } catch(JSONException je) {
            je.printStackTrace();
        }
    }

    private void handleMessageType(int messageType) {
        try {
            switch (messageType) {
                case GAMESTART_MESSAGE:
                    this.put("board", board);
                    break;
                case GAMEOVER_MESSAGE:
                    this.put("highscore", board.getScore());
                    break;
                case NEWGUTTI_MESSAGE:
                    this.put("foodX", board.getFoodX());
                    this.put("foodY", board.getFoodY());
                    break;
                case STITCHIN_MESSAGE:
                    this.put("broadcast", "STITCHIN");
                    break;
                case STITCHOUT_MESSAGE:
                    this.put("broadcast", "STITCHOUT");
                    this.put("board", board);
                    break;
            }
        } catch(JSONException je) {
            je.printStackTrace();
        }
    }

}
