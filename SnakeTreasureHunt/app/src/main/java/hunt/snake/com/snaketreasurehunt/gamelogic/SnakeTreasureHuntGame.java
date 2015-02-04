package hunt.snake.com.snaketreasurehunt.gamelogic;

import hunt.snake.com.snaketreasurehunt.wifi.ClientService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import hunt.snake.com.framework.Screen;
import hunt.snake.com.framework.impl.AndroidGame;
import android.view.MotionEvent;
import hunt.snake.com.snaketreasurehunt.stitching.*;
import hunt.snake.com.snaketreasurehunt.stitching.SnakeGestureListener.SimpleGestureListener;

public class SnakeTreasureHuntGame extends AndroidGame implements SimpleGestureListener {

    // is our phone the controlling phone (containing the snake's head)?
    public static boolean isControllingPhone = true;
    // is our phone active (showing a part of the game board)?
    public static boolean isPhoneActive = true;
    // is our phone placed on the ground (and therefore ready for stitching)?
    public static boolean isPhonePlacedOnGround = false;
    // are we connected to the client?
    public static boolean isConnectedToClient = false;

    SnakeGestureMethods methods = new SnakeGestureMethods();
    SnakeGestureListener detector;
    private GameScreen screen;

    public Screen getStartScreen() {
        boolean isHost = getIntent().getBooleanExtra("isHost", false);
        System.out.println("Is Host? " + isHost);
        isControllingPhone = isHost;
        isPhoneActive = isHost;

        screen = new GameScreen(this);
        initClient();
        detector = new SnakeGestureListener(this,this);

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
                screen.init();
                isConnectedToClient = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
            }
        };

        Intent intent = new Intent(this, ClientService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onSwipe(int direction) {

        methods.swipeType(direction, screen);
    }

    @Override
    public void onDoubleTap() {

    }

    @Override
    public void onSingleTapConfirmed() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
}
