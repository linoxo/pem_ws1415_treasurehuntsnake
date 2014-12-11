package hunt.snake.com.snaketreasurehunt;

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
    },

    TEXT_COLOR {
        @Override
        public int getValue() { return 0xFF0000FF; }
    },

    LAYER_COLOR {
        @Override
        public int getValue() { return 0x77000000; }
    },

    TEXT_SIZE_XS {
        @Override
        public int getValue() { return 10; }
    },

    TEXT_SIZE_S {
        @Override
        public int getValue() { return 40; }
    },

    TEXT_SIZE_XXL {
        @Override
        public int getValue() { return 100; }
    },

    SCORE_INCREMENT {
        @Override
        public int getValue() { return 10; }
    };

    public abstract int getValue();

}
