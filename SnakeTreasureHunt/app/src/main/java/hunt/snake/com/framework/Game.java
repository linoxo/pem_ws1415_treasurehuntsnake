package hunt.snake.com.framework;

public interface Game {

    public Input getInput();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();
}