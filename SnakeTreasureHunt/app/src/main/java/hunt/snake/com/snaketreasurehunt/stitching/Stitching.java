package hunt.snake.com.snaketreasurehunt.stitching;

import android.os.Handler;
import android.util.Log;

import hunt.snake.com.snaketreasurehunt.GameBoard;

public class Stitching {
    boolean stitchingEnabled = false;
    boolean isActiveDevice = false;
    private Handler mHandler;
    private GameBoard board;

    public Stitching(GameBoard board){
       mHandler = new Handler();
        this.board = board;
    }
    public void enableStitching() {
        //is called when player swipes out of the active device enables stitching
        if (isActiveDevice) {
            stitchingEnabled = true;

            board.sendStitchOutMessage();

            //send gameboard data to all devices
            Log.d("DEBUG", "Stitch Mode aktiv.");
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    stitchingEnabled = false;
                    Log.d("DEBUG", "Stitch Mode wieder inaktiv.");
                }
            }, 1000);


        }
    }
    public void completeStitching(){
        if (stitchingEnabled && !isActiveDevice){
            //new device gets active
            //new active device gets gameboard
        }
    }

    public boolean isStitchingEnabled(){
        return stitchingEnabled;
    }

}