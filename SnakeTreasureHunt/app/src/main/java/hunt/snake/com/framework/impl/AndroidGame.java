package hunt.snake.com.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import hunt.snake.com.framework.Audio;
import hunt.snake.com.framework.FileIO;
import hunt.snake.com.framework.Game;
import hunt.snake.com.framework.Graphics;
import hunt.snake.com.framework.Input;
import hunt.snake.com.framework.Screen;

public abstract class AndroidGame extends Activity implements Game {

    private static int screenWidth;
    private static int screenHeight;

    Context context;
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        int frameBufferWidth = isLandscape ? size.y : size.x;
        int frameBufferHeight = isLandscape ? size.x : size.y;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);

        context = getApplicationContext();
        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, 1, 1);
        screen = getStartScreen();
        setContentView(renderView);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    public Context getContext() {
        return context;
    }

    public Input getInput() {
        return input;
    }

    public FileIO getFileIO() {
        return fileIO;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {
        return screen;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }
}
