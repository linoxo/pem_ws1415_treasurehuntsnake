package hunt.snake.com.snaketreasurehunt.communication;

import android.content.Intent;

import com.google.gson.JsonObject;

import hunt.snake.com.snaketreasurehunt.GameBoard;
import hunt.snake.com.snaketreasurehunt.SnakeTreasureHuntGame;
import hunt.snake.com.snaketreasurehunt.wifi.ClientService;

/**
 * Created by Tom on 1/19/15.
 */
public class MessageHandler {

    private ClientService client;
    private STHMessageParser parser;
    private STHMessageSerializer serializer;
    private GameBoard gameBoard;

    public MessageHandler(ClientService client, GameBoard gameBoard) {
        this.client = client;
        this.gameBoard = gameBoard;
        init();
    }

    public MessageHandler(ClientService client) { this.client = client; }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        init();
    }

    private void init() {
        parser = new STHMessageParser(gameBoard);
        serializer = new STHMessageSerializer();
    }

    public void setClient(ClientService client) {
        this.client = client;
    }

    public void sendInitGame() {
        JsonObject message = serializer.serialize(gameBoard, STHMessage.GAMESTART_MESSAGE);
        //client.sendMessage(message);

        client.sendMessage("GameStarted");
    }

    public void handleIncoming(Object obj) {
        String in = (String) obj;
        System.out.println("In incoming Handler: " + in);
        /*
        JsonObject message = (JsonObject) obj;

        parser.deserializeSTHMessage(message);
        */

        if(obj.equals("GameStarted")) {
            startGame();
        }
    }

    private void startGame() {
        Intent intent = new Intent(client.getCurrentActivity(), SnakeTreasureHuntGame.class);
        client.getCurrentActivity().startActivity(intent);
    }

}
