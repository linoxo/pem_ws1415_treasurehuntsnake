package hunt.snake.com.snaketreasurehunt.communication;

import hunt.snake.com.snaketreasurehunt.gamelogic.Snake;
import hunt.snake.com.snaketreasurehunt.messages.GameMessage;

public class STHMessageParser {

    public STHMessageParser(){}

    public int deserializeSTHMessage(GameMessage msg) {

        int messageType = -1;
        if(msg != null) {

            messageType = msg.getType();

            // set flag that we received a message and store the message type
            DataTransferHandler.setReceivedMessage(true);
            DataTransferHandler.setMessageType(messageType);

            switch (messageType) {
                case STHMessage.GAMESTART_MESSAGE:

                    int fieldheight = msg.getGameStart().getFieldHeight();
                    int fieldwidth = msg.getGameStart().getFieldWidth();
                    int[] tileXPos = msg.getGameStart().getTileXPos();
                    int[] tileYPos = msg.getGameStart().getTileYPos();
                    int[] tileType = msg.getGameStart().getTileType();
                    int[] bodyPartsX = msg.getGameStart().getSnake().getBodypartXPos();
                    int[] bodyPartsY = msg.getGameStart().getSnake().getBodypartYPos();
                    Snake.Direction headDir = msg.getGameStart().getSnake().getHeadDirection();

                    System.out.println("Width: " + fieldwidth);
                    System.out.println("Height: " + fieldheight);
                    System.out.println("tileX: " + tileXPos + ", tileY: " + tileYPos + ", tileType: " + tileType);
                    for(int i : tileXPos) {
                       System.out.println("x: " + i);
                    }

                    DataTransferHandler.setFieldHeight(fieldheight);
                    DataTransferHandler.setFieldWidth(fieldwidth);
                    DataTransferHandler.setTileXPos(tileXPos);
                    DataTransferHandler.setTileYPos(tileYPos);
                    DataTransferHandler.setTileType(tileType);

                    DataTransferHandler.setBodypartXPos(bodyPartsX);
                    DataTransferHandler.setBodypartYPos(bodyPartsY);
                    DataTransferHandler.setHeadDirection(headDir);


                    DataTransferHandler.setFoodXPos(msg.getGameNewGutti().getFoodXPos());
                    DataTransferHandler.setFoodYPos(msg.getGameNewGutti().getFoodYPos());

                    break;

                case STHMessage.GAMEOVER_MESSAGE:

                    int score = msg.getGameOver().getScore();

                    DataTransferHandler.setScore(score);

                    break;
                case STHMessage.STITCHING_MESSAGE:

                    float tickTime = msg.getGameStitching().getTickTime();
                    float timestamp = msg.getGameStitching().getTimestamp();
                    int stitchingDirection = msg.getGameStitching().getStitchingDirection();
                    int topLeftXPos = msg.getGameStitching().getTopLeftXPos();
                    int topLeftYPos = msg.getGameStitching().getTopLeftYPos();

                    int[] bodypartXPos = msg.getGameStitching().getSnake().getBodypartXPos();
                    int[] bodypartYPos = msg.getGameStitching().getSnake().getBodypartYPos();
                    Snake.Direction headDirection = msg.getGameStitching().getSnake().getHeadDirection();


                    System.out.println("tickTime: " + tickTime + ", timeStamp: " + timestamp);
                    System.out.println("stitching: " + stitchingDirection + ", topleft: " + topLeftXPos + ", " + topLeftYPos);
                    System.out.println("snake: " + msg.getGameStitching().getSnake());


                    DataTransferHandler.setTickTime(tickTime);
                    DataTransferHandler.setTimestamp(timestamp);
                    DataTransferHandler.setStitchingDirection(stitchingDirection);
                    DataTransferHandler.setTopLeftXPos(topLeftXPos);
                    DataTransferHandler.setTopLeftYPos(topLeftYPos);

                    DataTransferHandler.setBodypartXPos(bodypartXPos);
                    DataTransferHandler.setBodypartYPos(bodypartYPos);
                    DataTransferHandler.setHeadDirection(headDirection);

                    break;

                case STHMessage.NEWGUTTI_MESSAGE:

                    int foodXPos = msg.getGameNewGutti().getFoodXPos();
                    int foodYPos = msg.getGameNewGutti().getFoodYPos();

                    System.out.println("food: " + foodXPos + ", " + foodYPos);

                    DataTransferHandler.setFoodXPos(foodXPos);
                    DataTransferHandler.setFoodYPos(foodYPos);
                    DataTransferHandler.setScore(msg.getGameNewGutti().getScore());

                    break;

                case STHMessage.GAMEPAUSE_START_MESSAGE:

                    break;

                case STHMessage.GAMEPAUSE_STOP_MESSAGE:

                    float ticks = msg.getGamePauseStop().getTickTime();

                    System.out.println("ticks: " + ticks);

                    DataTransferHandler.setTickTime(ticks);

                    break;

                case STHMessage.GAMERUNNING_MESSAGE:

                    break;

                case STHMessage.MOVEMENT_MESSAGE:
                    Snake.Direction direction = msg.getGameSnakeMovement().getMovementDirection();
                    DataTransferHandler.setMovementDirection(direction);
                    break;
            }
        }
        return messageType;
    }
}
