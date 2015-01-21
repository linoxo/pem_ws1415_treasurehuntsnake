package hunt.snake.com.snaketreasurehunt.communication;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import hunt.snake.com.snaketreasurehunt.GameBoard;

/**
 * Created by lino on 19.01.15.
 */
public class STHMessageSerializer {

    public STHMessageSerializer() { }

    public JsonObject serialize(GameBoard board, int messageType) {
        JsonObject obj = new JsonObject();
        Gson gson = new Gson();


        switch(messageType) {
            case STHMessage.GAMESTART_MESSAGE:
                System.out.println("Before first");
                obj.add("type", new JsonPrimitive(STHMessage.GAMESTART_MESSAGE));

                System.out.println("Before tileGson");
                //String tileGson = gson.toJson(board.getTiles());
                //System.out.println("Tile: " + tileGson);
                //obj.add("tiles", new JsonPrimitive(tileGson));

                //String entities = gson.toJson(board.getGameElements());
                //obj.add("entities", new JsonPrimitive(entities));

                obj.add("foodX", new JsonPrimitive(board.getFoodX()));
                obj.add("foodY", new JsonPrimitive(board.getFoodY()));

                int[][] array = {{3, 5, 4},{3, 2, 4},{3, 1, 4},{3, 4, 4},{8,0 ,98 ,9 , 9887, 9}};
                String as = gson.toJson(array);

                System.out.println("AS: " + as);

                obj.add("ARRAY", new JsonPrimitive(as));

                System.out.println("OBJ: " + obj.toString());

                //String snake = gson.toJson(board.getSnake());
                //obj.add("snake", new JsonPrimitive(snake));

                break;

            case STHMessage.GAMEOVER_MESSAGE:

                obj.add("type", new JsonPrimitive(STHMessage.GAMEOVER_MESSAGE));
                obj.add("score", new JsonPrimitive(board.getScore()));

                break;

            case STHMessage.NEWGUTTI_MESSAGE:

                obj.add("type", new JsonPrimitive(STHMessage.NEWGUTTI_MESSAGE));
                obj.add("foodX", new JsonPrimitive(board.getFoodX()));
                obj.add("foodY", new JsonPrimitive(board.getFoodY()));

                break;

            case STHMessage.STITCHIN_MESSAGE:

                break;

            case STHMessage.STITCHOUT_MESSAGE:

                break;

        }

        return obj;
    }

}
