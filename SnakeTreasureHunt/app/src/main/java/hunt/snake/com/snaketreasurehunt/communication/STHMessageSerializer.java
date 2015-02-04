
package hunt.snake.com.snaketreasurehunt.communication;


import com.google.gson.Gson;
import hunt.snake.com.snaketreasurehunt.messages.GameMessage;
import hunt.snake.com.snaketreasurehunt.messages.SnakeMessage;

public class STHMessageSerializer {

    public STHMessageSerializer() { }

    //Serialization of different Messages
    public String serialize(int messageType) {
        Gson gson = new Gson();
        String jsonMsg;

        GameMessage msg = new GameMessage(messageType);

        switch(messageType) {
            //first case
            case STHMessage.GAMESTART_MESSAGE:
                SnakeMessage snakeMessage = new SnakeMessage();
                snakeMessage.setBodypartXPos(DataTransferHandler.getBodypartXPos());
                snakeMessage.setBodypartYPos(DataTransferHandler.getBodypartYPos());
                snakeMessage.setHeadDirection(DataTransferHandler.getHeadDirection());

                msg.getGameStart().setFieldHeight(DataTransferHandler.getFieldHeight());
                msg.getGameStart().setFieldWidth(DataTransferHandler.getFieldWidth());
                msg.getGameStart().setTileXPos(DataTransferHandler.getTileXPos());
                msg.getGameStart().setTileYPos(DataTransferHandler.getTileYPos());
                msg.getGameStart().setTileType(DataTransferHandler.getTileType());
                msg.getGameStart().setSnake(snakeMessage);

                msg.getGameNewGutti().setFoodXPos(DataTransferHandler.getFoodXPos());
                msg.getGameNewGutti().setFoodYPos(DataTransferHandler.getFoodYPos());
                break;

            //second case
            case STHMessage.GAMEOVER_MESSAGE:

                msg.getGameOver().setScore(DataTransferHandler.getScore());

                break;

            //third case
            case STHMessage.NEWGUTTI_MESSAGE:

                msg.getGameNewGutti().setFoodXPos(DataTransferHandler.getFoodXPos());
                msg.getGameNewGutti().setFoodYPos(DataTransferHandler.getFoodYPos());
                msg.getGameNewGutti().setScore(DataTransferHandler.getScore());
                break;

            //fourth case
            case STHMessage.STITCHING_MESSAGE:

                msg.getGameStitching().setTickTime(DataTransferHandler.getTickTime());
                msg.getGameStitching().setTimestamp(DataTransferHandler.getTimestamp());
                msg.getGameStitching().setStitchingDirection(DataTransferHandler.getStitchingDirection());
                msg.getGameStitching().setTopLeftXPos(DataTransferHandler.getTopLeftXPos());
                msg.getGameStitching().setTopLeftYPos(DataTransferHandler.getTopLeftYPos());

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
