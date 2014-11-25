package hunt.snake.com.snaketreasurehunt;

import hunt.snake.com.framework.Screen;
import hunt.snake.com.framework.impl.AndroidGame;

public class SnakeTreasureHuntGame extends AndroidGame {
    public Screen getStartScreen() {
        return new ReadyScreen(this);
    }
}
