package hunt.snake.com.framework;

import android.content.Context;

public interface Game {

    public Context getContext();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public Audio getAudio();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}