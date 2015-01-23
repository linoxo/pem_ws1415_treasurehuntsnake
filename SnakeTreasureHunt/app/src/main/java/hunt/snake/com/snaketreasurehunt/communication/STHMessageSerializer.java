
package hunt.snake.com.snaketreasurehunt.communication;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hunt.snake.com.snaketreasurehunt.messages.GameMessage;

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


                msg.getGameStart().setFieldHeight(DataTransferHandler.getFieldHeight());
                msg.getGameStart().setFieldHeight(DataTransferHandler.getFieldWidth());
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
                msg.getGameStitching().getSnake().setBodypartXPos(DataTransferHandler.getBodypartXPos());
                msg.getGameStitching().getSnake().setBodypartYPos(DataTransferHandler.getBodypartYPos());
                msg.getGameStitching().getSnake().setCornerPart(DataTransferHandler.getCornerPart());
                msg.getGameStitching().getSnake().setOrigin(DataTransferHandler.getOrigin());
                msg.getGameStitching().getSnake().setDirection(DataTransferHandler.getDirection());

                break;

            case STHMessage.GAMEPAUSE_START_MESSAGE:

                break;

            case STHMessage.GAMEPAUSE_STOP_MESSAGE:

                msg.getGamePauseStop().setTickTime(DataTransferHandler.getTickTime());

                break;
        }

        jsonMsg = gson.toJson(msg);
        System.out.println(jsonMsg);
        return jsonMsg;
    }

}
