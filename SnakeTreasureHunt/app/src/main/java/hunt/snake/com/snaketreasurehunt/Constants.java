package hunt.snake.com.snaketreasurehunt;

public enum Constants {

    TILE_WIDTH {
        @Override
        public int getValue() {
            return 100;
        }
    },

    TILE_HEIGHT { // TODO: device independent pixel !!!
        @Override
        public int getValue() {
            return 100;
        }
    },

    BOARD_WIDTH { // width of the game board in tiles
        @Override
        public int getValue() { return 50; }
    },

    BOARD_HEIGHT { // height of the game board in tiles
        @Override
        public int getValue() { return 75; }
    },

    SNAKE_WIDTH {
        @Override
        public int getValue() {
            return 25;
        }
    },

    TEXT_COLOR {
        @Override
        public int getValue() { return 0xFFFFFFFF; }
    },

    LAYER_COLOR {
        @Override
        public int getValue() { return 0x88000000; }
    },

    TEXT_SIZE_XS {
        @Override
        public int getValue() { return 10; }
    },

    TEXT_SIZE_S {
        @Override
        public int getValue() { return 40; }
    },

    TEXT_SIZE_M {
        @Override
        public int getValue() { return 50; }
    },

    TEXT_SIZE_XL {
        @Override
        public int getValue() { return 75; }
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
