package hunt.snake.com.snaketreasurehunt.communication;

import android.content.Intent;

import com.google.gson.Gson;

import hunt.snake.com.snaketreasurehunt.GameBoard;
import hunt.snake.com.snaketreasurehunt.SnakeTreasureHuntGame;
import hunt.snake.com.snaketreasurehunt.messages.GameMessage;
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
    }

    public MessageHandler(ClientService client) {
        this.client = client;
        serializer = new STHMessageSerializer();
        parser = new STHMessageParser();
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
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

    public void sendGameRunning() {
        String msg = serializer.serialize(STHMessage.GAMERUNNING_MESSAGE);
        client.sendMessage(msg);
    }

    public void sendStitching() {
        String msg = serializer.serialize(STHMessage.STITCHING_MESSAGE);
        client.sendMessage(msg);
    }

    public void sendGameOver() {
        String msg = serializer.serialize(STHMessage.GAMEOVER_MESSAGE);
        client.sendMessage(msg);
    }

    public void sendNewGutti() {
        String msg = serializer.serialize(STHMessage.NEWGUTTI_MESSAGE);
        client.sendMessage(msg);
    }

    public void sendPaused() {
        String msg = serializer.serialize(STHMessage.GAMEPAUSE_START_MESSAGE);
        client.sendMessage(msg);
    }

    public void sendResumed() {
        String msg = serializer.serialize(STHMessage.GAMEPAUSE_STOP_MESSAGE);
        client.sendMessage(msg);
    }

    public void sendMovement() {
        String msg = serializer.serialize(STHMessage.MOVEMENT_MESSAGE);
        client.sendMessage(msg);
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
            System.out.println("Parser: " + parser + " MSG: " + in);
            int type = parser.deserializeSTHMessage(in);
            System.out.println("Message Type: " + type);

            if(type == STHMessage.GAMESTART_MESSAGE) {
                startGame();
            }
        }
    }

    private void startGame() {
        Intent intent = new Intent(client.getCurrentActivity(), SnakeTreasureHuntGame.class);
        intent.putExtra("isHost", false);
        client.getCurrentActivity().startActivity(intent);
    }

    public void test() {
        client.sendMessage("Hallo World!");
        //serializer = new STHMessageSerializer();
        //serializer.serialize(STHMessage.GAMESTART_MESSAGE);
    }

}
