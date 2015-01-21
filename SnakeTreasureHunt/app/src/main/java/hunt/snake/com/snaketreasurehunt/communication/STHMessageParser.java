package hunt.snake.com.snaketreasurehunt.communication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

import hunt.snake.com.snaketreasurehunt.GameBoard;
import hunt.snake.com.snaketreasurehunt.GameElement;
import hunt.snake.com.snaketreasurehunt.Snake;
import hunt.snake.com.snaketreasurehunt.Tile;

/**
 * Created by lino on 18.01.15.
 */
public class STHMessageParser {

    private GameBoard board;

    public STHMessageParser(GameBoard board) {
        this.board = board;
    }

    public void deserializeSTHMessage(JsonObject incomingMessage) {

            Gson gson = new Gson();

            JsonObject message = gson.fromJson(incomingMessage, JsonObject.class);

            System.out.println("MA " + message.get("ARRAY"));

            System.out.println("IA " + incomingMessage.get("ARRAY"));

            int[][] array = gson.fromJson(message.get("ARRAY"), int[][].class);
            System.out.println("DE: " + array);



            //int messageType = message.get("type").getAsInt();
            int messageType = -1;

            switch (messageType) {
                case STHMessage.GAMESTART_MESSAGE:

                    Tile[][] tiles = gson.fromJson(message.get("tiles"), Tile[][].class);
                    board.setTiles(tiles);

                    Type listType = new TypeToken<ArrayList<GameElement>>(){}.getType();
                    ArrayList<GameElement> list = gson.fromJson(message.get("entities"), listType);
                    board.setGameElements(list);

                    Snake snake = gson.fromJson(message.get("snake"), Snake.class);
                    board.setSnake(snake);

                    int foodX = message.get("foodX").getAsInt();
                    int foodY = message.get("foodY").getAsInt();
                    board.setFoodX(foodX);
                    board.setFoodY(foodY);

                    break;
                case STHMessage.GAMEOVER_MESSAGE:

                    int score = incomingMessage.get("score").getAsInt();

                    board.setScore(score);

                    break;
                case STHMessage.STITCHIN_MESSAGE:


                    break;
                case STHMessage.STITCHOUT_MESSAGE:

                    break;
                case STHMessage.NEWGUTTI_MESSAGE:

                    int foodx = message.get("foodX").getAsInt();
                    int foody = message.get("foodY").getAsInt();

                    board.setFoodX(foodx);
                    board.setFoodY(foody);

                    break;

            }
    }
}
