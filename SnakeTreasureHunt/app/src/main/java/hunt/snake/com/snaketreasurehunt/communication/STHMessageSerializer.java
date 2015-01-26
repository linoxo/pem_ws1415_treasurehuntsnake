
package hunt.snake.com.snaketreasurehunt.communication;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hunt.snake.com.snaketreasurehunt.messages.GameMessage;
import hunt.snake.com.snaketreasurehunt.messages.SnakeMessage;

/**
 * Created by lino on 19.01.15.
 */
public class STHMessageSerializer {

    public STHMessageSerializer() { }

    //Serialization of different Messages
    public String serialize(int messageType) {
        JsonObject obj = new JsonObject();
        Gson gson = new Gson();
        String jsonMsg = "";

        GameMessage msg = new GameMessage(messageType);

        switch(messageType) {
            //first case
            case STHMessage.GAMESTART_MESSAGE:
                SnakeMessage snakeMessage = new SnakeMessage();
                snakeMessage.setBodypartXPos(DataTransferHandler.getBodypartXPos());
                snakeMessage.setBodypartYPos(DataTransferHandler.getBodypartYPos());
                snakeMessage.setHeadDirection(DataTransferHandler.getHeadDirection());

                msg.getGameStart().setSnake(snakeMessage);
                msg.getGameStart().setFieldHeight(DataTransferHandler.getFieldHeight());
                msg.getGameStart().setFieldWidth(DataTransferHandler.getFieldWidth());
                msg.getGameStart().setTileXPos(DataTransferHandler.getTileXPos());
                msg.getGameStart().setTileYPos(DataTransferHandler.getTileYPos());
                msg.getGameStart().setTileType(DataTransferHandler.getTileType());

                break;

            //second case
            case STHMessage.GAMEOVER_MESSAGE:

                msg.getGameOver().setScore(DataTransferHandler.getScore());

                break;

            //third case
            case STHMessage.NEWGUTTI_MESSAGE:

                msg.getGameNewGutti().setFoodXPos(DataTransferHandler.getFoodXPos());
                msg.getGameNewGutti().setFoodYPos(DataTransferHandler.getFoodYPos());

                break;

            //fourth case
            case STHMessage.STITCHING_MESSAGE:

                msg.getGameStitching().setTickTime(DataTransferHandler.getTickTime());
                msg.getGameStitching().setTimestamp(DataTransferHandler.getTimestamp());
                msg.getGameStitching().setStitchingDirection(DataTransferHandler.getStitchingDirection());
                msg.getGameStitching().setTopLeftXPos(DataTransferHandler.getTopLeftXPos());
                msg.getGameStitching().setTopLeftYPos(DataTransferHandler.getTopLeftYPos());
                /* LINHS PART
                msg.getGameStitching().getSnake().setBodypartXPos(DataTransferHandler.getBodypartXPos());
                msg.getGameStitching().getSnake().setBodypartYPos(DataTransferHandler.getBodypartYPos());
                msg.getGameStitching().getSnake().setCornerPart(DataTransferHandler.getCornerPart());
                msg.getGameStitching().getSnake().setOrigin(DataTransferHandler.getOrigin());
                msg.getGameStitching().getSnake().setDirection(DataTransferHandler.getDirection());
                */
                //TOMS PART
                SnakeMessage snakeMsg = new SnakeMessage();
                snakeMsg.setBodypartXPos(DataTransferHandler.getBodypartXPos());
                snakeMsg.setBodypartYPos(DataTransferHandler.getBodypartYPos());
                snakeMsg.setHeadDirection(DataTransferHandler.getHeadDirection());
                msg.getGameStitching().setSnake(snakeMsg);
                break;

            case STHMessage.GAMEPAUSE_START_MESSAGE:

                break;

            case STHMessage.GAMEPAUSE_STOP_MESSAGE:

                msg.getGamePauseStop().setTickTime(DataTransferHandler.getTickTime());

                break;

            case STHMessage.GAMERUNNING_MESSAGE:

                break;

            case STHMessage.MOVEMENT_MESSAGE:
                msg.getGameSnakeMovement().setMovementDirection(DataTransferHandler.getMovementDirection());
                break;
        }

        jsonMsg = gson.toJson(msg);
        System.out.println(jsonMsg);
        return jsonMsg;
    }

}
