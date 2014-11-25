package hunt.snake.com.snaketreasurehunt;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Tom on 11/23/14.
 */
public enum GameElementType {

    COIN {
        @Override
        public Paint getPaint() {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#FFFF00"));
            return paint;
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
            return new OvalGameElement();
        }
    },

    SNAKE_HEAD_HORIZONTAL {
        @Override
        public Paint getPaint() {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#CC00CC"));
            return paint;
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
            return new OvalGameElement();
        }
    },

    SNAKE_HEAD_VERTICAL {
        @Override
        public Paint getPaint() {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#CC00CC"));
            return paint;
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
            return new OvalGameElement();
        }
    },

    SNAKE_BODY_HORIZONTAL {
        @Override
        public Paint getPaint() {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#CC00CC"));
            return paint;
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
            return new RectangleGameElement();
        }
    },

    SNAKE_BODY_HORIZONTAL_SHORT {
        @Override
        public Paint getPaint() {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#CC00CC"));
            return paint;
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
            return new RectangleGameElement();
        }
    },

    SNAKE_BODY_VERTICAL {
        @Override
        public Paint getPaint() {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#CC00CC"));
            return paint;
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
            return new RectangleGameElement();
        }
    },

    SNAKE_BODY_VERTICAL_SHORT {
        @Override
        public Paint getPaint() {
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#CC00CC"));
            return paint;
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
            return new RectangleGameElement();
        }
    };

    public abstract Paint getPaint();
    public abstract int getHeight();
    public abstract int getWidth();
    public abstract GameElement getGameElement();
}
