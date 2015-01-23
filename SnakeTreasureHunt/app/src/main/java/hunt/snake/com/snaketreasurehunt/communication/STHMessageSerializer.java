
package hunt.snake.com.snaketreasurehunt.communication;


import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hunt.snake.com.snaketreasurehunt.GameBoard;
import hunt.snake.com.snaketreasurehunt.messages.GameStartMessage;

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


        switch(messageType) {
            //first case
            case STHMessage.GAMESTART_MESSAGE:

                GameStartMessage msg = new GameStartMessage();
                msg.gameBoard.setFieldHeight(DataTransferHandler.getFieldHeight());
                msg.gameBoard.setFieldHeight(DataTransferHandler.getFieldWidth());

                jsonMsg = gson.toJson(msg);
                System.out.println(jsonMsg);

                /*
                obj.addProperty("messagetype", STHMessage.GAMESTART_MESSAGE);

                JsonObject spielfeld = new JsonObject();

                spielfeld.addProperty("fieldheight", DataTransferHandler.getFieldHeight());
                spielfeld.addProperty("fieldwidth", DataTransferHandler.getFieldWidth());

                JsonObject tiles = new JsonObject();
                for(int i = 0; i < DataTransferHandler.getNumOfOccupiedTiles(); i++) {

                    JsonObject tile = new JsonObject();

                    int posx = DataTransferHandler.getTileXPos()[i];
                    int posy = DataTransferHandler.getTileYPos()[i];
                    int type = DataTransferHandler.getTileType()[i];

                    tile.addProperty("x", posx);
                    tile.addProperty("y",posy);
                    tile.addProperty("type",type);

                    tiles.add("tile"+i,tile);


                }

                //spielfeld.add("tiles",tiles);
                obj.add("spielfeld", spielfeld);
                */
                break;
            //second case
            case STHMessage.GAMEOVER_MESSAGE:
                obj.addProperty("messagetype", STHMessage.GAMEOVER_MESSAGE);
                obj.addProperty("highscore", DataTransferHandler.getScore());

                break;

            //third case
            case STHMessage.NEWGUTTI_MESSAGE:
                obj.addProperty("messagetype", STHMessage.NEWGUTTI_MESSAGE);

                JsonObject newGutti = new JsonObject();
                newGutti.addProperty("foodX", DataTransferHandler.getFoodXPos());
                newGutti.addProperty("foodY", DataTransferHandler.getFoodYPos());

                obj.add("food",newGutti);
                obj.addProperty("score", DataTransferHandler.getScore());
                break;

            //fourth case
            case STHMessage.STITCHING_MESSAGE:

                JsonObject snake = new JsonObject();

                JsonObject bodyParts = new JsonObject();

                for(int i = 0; i < DataTransferHandler.getNumOfSnakeBodyparts(); i++) {
                    JsonObject bodyPart = new JsonObject();

                    int posx = DataTransferHandler.getBodypartXPos()[i];
                    int posy = DataTransferHandler.getBodypartYPos()[i];
                    boolean ecke = DataTransferHandler.getCornerPart()[i];
                    int direction = DataTransferHandler.getDirection()[i];

                    if(ecke) {
                        int origin = DataTransferHandler.getOrigin()[i];

                        bodyPart.addProperty("x", posx);
                        bodyPart.addProperty("y", posy);
                        bodyPart.addProperty("corner", true);
                        bodyPart.addProperty("origin", origin);
                        bodyPart.addProperty("direction", direction);
                    }
                    else {
                        bodyPart.addProperty("x", posx);
                        bodyPart.addProperty("y", posy);
                        bodyPart.addProperty("corner", false);
                        bodyPart.addProperty("direction", direction);
                    }

                    bodyParts.add("bodypart"+i, bodyPart);
                }

                snake.add("bodyparts", bodyParts);

                obj.add("snake", snake);

                obj.addProperty("ticktime", DataTransferHandler.getTickTime());
                obj.addProperty("timestamp", DataTransferHandler.getTimestamp());
                obj.addProperty("stitchingdirection", DataTransferHandler.getStitchingDirection());
                obj.addProperty("topleftX", DataTransferHandler.getTopLeftXPos());
                obj.addProperty("topleftY", DataTransferHandler.getTopLeftYPos());

                break;

            case STHMessage.GAMEPAUSE_START_MESSAGE:

                break;

            case STHMessage.GAMEPAUSE_STOP_MESSAGE:

                break;
        }
        System.out.println("obj: " + obj);
        //return obj;
        return jsonMsg;
    }

}
