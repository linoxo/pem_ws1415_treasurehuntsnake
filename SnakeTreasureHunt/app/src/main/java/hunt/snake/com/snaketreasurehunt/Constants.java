package hunt.snake.com.snaketreasurehunt;

/**
 * Created by Tom on 11/23/14.
 */
public enum Constants {

    TILE_WIDTH {
        @Override
        public int getValue() {
            return 100;
        }
    },

    TILE_HEIGHT {
        @Override
        public int getValue() {
            return 100;
        }
    },

    SNAKE_WIDTH {
        @Override
        public int getValue() {
            return 25;
        }
    };

    public abstract int getValue();

}
