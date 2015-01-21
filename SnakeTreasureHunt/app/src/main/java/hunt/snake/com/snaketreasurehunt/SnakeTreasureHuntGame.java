package hunt.snake.com.snaketreasurehunt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import hunt.snake.com.framework.Screen;
import hunt.snake.com.framework.impl.AndroidGame;
import hunt.snake.com.snaketreasurehunt.wifi.ClientService;

public class SnakeTreasureHuntGame extends AndroidGame {
    private GameScreen screen;

    public Screen getStartScreen() {
        screen = new GameScreen(this);
        initClient();

        return screen;
    }

    private ClientService clientService;

    private void initClient() {
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                ClientService.ClientBinder binder = (ClientService.ClientBinder) service;
                clientService = binder.getClient();

                clientService.sendMessage("In Game");

                screen.setClient(clientService);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {}
        };

        Intent intent = new Intent(this, ClientService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
}
