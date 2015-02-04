package hunt.snake.com.snaketreasurehunt.communication;

import android.content.Intent;

import com.google.gson.Gson;

import hunt.snake.com.snaketreasurehunt.SnakeTreasureHuntGame;
import hunt.snake.com.snaketreasurehunt.messages.GameMessage;
import hunt.snake.com.snaketreasurehunt.wifi.ClientService;

public class MessageHandler {

    private ClientService client;
    private STHMessageSerializer serializer;

    public MessageHandler(ClientService client) {
        this.client = client;
        serializer = new STHMessageSerializer();
    }

    public void sendInitGame() {
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

        if((in.charAt(0))=='{') {

            Gson gson = new Gson();
            GameMessage msg = gson.fromJson(in, GameMessage.class);

            if(msg.getType() == STHMessage.GAMESTART_MESSAGE) {
                startGame();
            }
            DataTransferHandler.pushMessage(msg);
        }
    }

    private void startGame() {
        Intent intent = new Intent(client.getCurrentActivity(), SnakeTreasureHuntGame.class);
        intent.putExtra("isHost", false);
        client.getCurrentActivity().startActivity(intent);
    }
}
