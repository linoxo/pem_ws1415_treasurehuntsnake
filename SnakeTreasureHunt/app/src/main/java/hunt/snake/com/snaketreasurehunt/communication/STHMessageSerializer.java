package hunt.snake.com.snaketreasurehunt.communication;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

import hunt.snake.com.snaketreasurehunt.GameBoard;

/**
 * Created by lino on 19.01.15.
 */
public class STHMessageSerializer {

    public STHMessageSerializer() { }

    public JsonElement serialize(final GameBoard board, int messageType) {
        JsonObject obj = new JsonObject();
        Gson gson = new Gson();


        switch(messageType) {
            case STHMessage.GAMESTART_MESSAGE:

                obj.add("type", new JsonPrimitive(STHMessage.GAMESTART_MESSAGE));

                String tileGson = gson.toJson(board.getTiles());
                obj.add("tiles", new JsonPrimitive(tileGson));

                String entities = gson.toJson(board.getGameElements());
                obj.add("entities", new JsonPrimitive(entities));

                obj.add("foodX", new JsonPrimitive(board.getFoodX()));
                obj.add("foodY", new JsonPrimitive(board.getFoodY()));

                String snake = gson.toJson(board.getSnake());
                obj.add("snake", new JsonPrimitive(snake));

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
