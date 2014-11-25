package hunt.snake.com.snaketreasurehunt;

import hunt.snake.com.framework.Game;
import hunt.snake.com.framework.Screen;

public class PausedScreen extends Screen {
    public PausedScreen(Game game) {
        super(game);
    }

    public void update(float deltaTime) {
        game.setScreen(new PausedScreen(game));
    }

    public void present(float deltaTime) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
