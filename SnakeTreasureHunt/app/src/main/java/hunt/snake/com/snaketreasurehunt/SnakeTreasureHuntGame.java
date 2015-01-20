package hunt.snake.com.snaketreasurehunt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import hunt.snake.com.framework.Screen;
import hunt.snake.com.framework.impl.AndroidGame;
import hunt.snake.com.snaketreasurehunt.stitching.*;
import hunt.snake.com.snaketreasurehunt.stitching.SnakeGestureListener.SimpleGestureListener;

public class SnakeTreasureHuntGame extends AndroidGame implements SimpleGestureListener {

    // is our phone the active phone (containing the snake's head)?
    public static boolean isActivePhone = true;

    SnakeGestureMethods methods= new SnakeGestureMethods();
    SnakeGestureListener detector;


    public Screen getStartScreen() {

        detector = new SnakeGestureListener(this,this);

        return new GameScreen(this);
    }


    @Override
    public void onSwipe(int direction) {

        methods.swipeType(direction);
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
