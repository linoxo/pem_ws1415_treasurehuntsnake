package hunt.snake.com.snaketreasurehunt;

import android.graphics.Color;

/**
 * Created by Tom on 11/23/14.
 */
public enum GameElementType {

    COIN {
        @Override
        public int getColor() {
            return Color.parseColor("#FFFF00");
        }

        @Override
        public int getHeight() {
            return 40;
        }

        @Override
        public int getWidth() {
            return 40;
        }

        @Override
        public GameElement getGameElement() {
            GameElement gameElement = new OvalGameElement();
            gameElement.setType(this);
            gameElement.setColor(getColor());
            return gameElement;
        }
    },

    SNAKE_HEAD_HORIZONTAL {
        @Override
        public int getColor() {
            return Color.parseColor("#CC00CC");
        }

        @Override
        public int getHeight() {
            return 50;
        }

        @Override
        public int getWidth() {
            return 60;
        }

        @Override
        public GameElement getGameElement() {
            GameElement gameElement = new OvalGameElement();
            gameElement.setType(this);
            gameElement.setColor(getColor());
            return gameElement;
        }
    },

    SNAKE_HEAD_VERTICAL {
        @Override
        public int getColor() {
            return Color.parseColor("#CC00CC");
        }

        @Override
        public int getHeight() {
            return 60;
        }

        @Override
        public int getWidth() {
            return 50;
        }

        @Override
        public GameElement getGameElement() {
            GameElement gameElement = new OvalGameElement();
            gameElement.setType(this);
            gameElement.setColor(getColor());
            return gameElement;
        }
    },

    SNAKE_BODY_HORIZONTAL {
        @Override
        public int getColor() {
            return Color.parseColor("#CC00CC");
        }

        @Override
        public int getHeight() {
            return 25;
        }

        @Override
        public int getWidth() {
            return Constants.TILE_WIDTH.getValue();
        }

        @Override
        public GameElement getGameElement() {
            GameElement gameElement = new RectangleGameElement();
            gameElement.setType(this);
            gameElement.setColor(getColor());
            return gameElement;
        }
    },

    SNAKE_BODY_HORIZONTAL_SHORT {
        @Override
        public int getColor() {
            return Color.parseColor("#CC00CC");
        }

        @Override
        public int getHeight() {
            return 25;
        }

        @Override
        public int getWidth() {
            return Constants.TILE_WIDTH.getValue() / 2 + (getHeight() / 2);
        }

        @Override
        public GameElement getGameElement() {
            GameElement gameElement = new RectangleGameElement();
            gameElement.setType(this);
            gameElement.setColor(getColor());
            return gameElement;
        }
    },

    SNAKE_BODY_VERTICAL {
        @Override
        public int getColor() {
            return Color.parseColor("#CC00CC");
        }

        @Override
        public int getHeight() {
            return Constants.TILE_HEIGHT.getValue();
        }

        @Override
        public int getWidth() {
            return 25;
        }

        @Override
        public GameElement getGameElement() {
            GameElement gameElement = new RectangleGameElement();
            gameElement.setType(this);
            gameElement.setColor(getColor());
            return gameElement;
        }
    },

    SNAKE_BODY_VERTICAL_SHORT {
        @Override
        public int getColor() {
            return Color.parseColor("#CC00CC");
        }

        @Override
        public int getHeight() {
            return Constants.TILE_HEIGHT.getValue() / 2 + (getWidth() / 2);
        }

        @Override
        public int getWidth() {
            return 25;
        }

        @Override
        public GameElement getGameElement() {
            GameElement gameElement = new RectangleGameElement();
            gameElement.setType(this);
            gameElement.setColor(getColor());
            return gameElement;
        }
    };

    public abstract int getColor();
    public abstract int getHeight();
    public abstract int getWidth();
    public abstract GameElement getGameElement();
}
