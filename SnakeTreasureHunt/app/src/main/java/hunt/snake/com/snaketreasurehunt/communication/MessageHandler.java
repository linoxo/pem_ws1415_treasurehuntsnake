package hunt.snake.com.snaketreasurehunt.communication;

import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Map;

import hunt.snake.com.snaketreasurehunt.GameBoard;
import hunt.snake.com.snaketreasurehunt.SnakeTreasureHuntGame;
import hunt.snake.com.snaketreasurehunt.messages.GameStartMessage;
import hunt.snake.com.snaketreasurehunt.wifi.ClientService;

/**
 * Created by Tom on 1/19/15.
 */
public class MessageHandler {

    private ClientService client;
    private STHMessageParser parser;
    private STHMessageSerializer serializer;
    private GameBoard gameBoard;
    private Gson gson;

    public MessageHandler(ClientService client, GameBoard gameBoard) {
        this.client = client;
        this.gameBoard = gameBoard;
        init();
    }

    public MessageHandler(ClientService client) {
        this.client = client;
        serializer = new STHMessageSerializer();
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        init();
    }

    private void init() {
        parser = new STHMessageParser(gameBoard);
    }

    public void setClient(ClientService client) {
        this.client = client;
    }

    public void sendInitGame() {
        //JsonObject message = serializer.serialize(gameBoard, STHMessage.GAMESTART_MESSAGE);
        //client.sendMessage(message);
        System.out.println("Serializer: " + serializer);
        String msg = serializer.serialize(STHMessage.GAMESTART_MESSAGE);
        System.out.println("Json: " + msg);
        client.sendMessage(msg);
        client.sendMessage("GameStarted");
    }

    public void handleIncoming(Object obj) {
        String in = (String) obj;
        System.out.println("In incoming Handler: " + in);
        /*
        JsonObject message = (JsonObject) obj;

        parser.deserializeSTHMessage(message);
        */


        if((in.charAt(0))=='{') {
            //startGame();
            Gson gson = new Gson();

            GameStartMessage msg = gson.fromJson(in, GameStartMessage.class);
            System.out.println("Width: " + msg.getGameBoard().getFieldWidth());
            System.out.println("Height: " + msg.getGameBoard().getFieldHeight());
            System.out.println("Type: " + msg.getType());
            System.out.println("GAME START!!!!");
        }
    }

    private void startGame() {
        Intent intent = new Intent(client.getCurrentActivity(), SnakeTreasureHuntGame.class);
        client.getCurrentActivity().startActivity(intent);
    }

    public void test() {
        client.sendMessage("Hallo World!");
        //serializer = new STHMessageSerializer();
        //serializer.serialize(STHMessage.GAMESTART_MESSAGE);
    }

}
