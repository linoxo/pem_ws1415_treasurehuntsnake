package hunt.snake.com.snaketreasurehunt;

import android.graphics.Color;

public enum GameElementType {

    FOOD {
        @Override
        public int getColor() {
            return Color.parseColor("#D14719");
        }

        @Override
        public int getHeight() {
            return 45;
        }

        @Override
        public int getWidth() {
            return 45;
        }

        @Override
        public GameElement getGameElement() {
            //ham
            OvalGameElement gameElement = new OvalGameElement();
            gameElement.setType(this);
            gameElement.setColor(getColor());
            gameElement.setExactPosition(Constants.TILE_WIDTH.getValue() * 0.3f, Constants.TILE_HEIGHT.getValue() * 0.6f);

            //bone rect
            int boneColor = Color.parseColor("#FFFFCC");
            ComplexGameElement bone = new ComplexGameElement();
            bone.setColor(boneColor);
            bone.addPoint((int) (Constants.TILE_WIDTH.getValue() * 0.3f), (int) (Constants.TILE_HEIGHT.getValue() * 0.5f));
            bone.addPoint((int) (Constants.TILE_WIDTH.getValue() * 0.3f), (int) (Constants.TILE_HEIGHT.getValue() * 0.7f));
            bone.addPoint((int) (Constants.TILE_WIDTH.getValue() * 0.7f), (int) (Constants.TILE_HEIGHT.getValue() * 0.4f));
            bone.addPoint((int) (Constants.TILE_WIDTH.getValue() * 0.6f), (int) (Constants.TILE_HEIGHT.getValue() * 0.3f));
            gameElement.addElement(bone);

            //bone circles
            OvalGameElement firstCircle = new OvalGameElement();
            firstCircle.setColor(boneColor);
            firstCircle.setWidth(getWidth() / 2);
            firstCircle.setHeight(getHeight() / 2);
            firstCircle.setExactPosition((Constants.TILE_WIDTH.getValue() * 0.7f), (Constants.TILE_HEIGHT.getValue() * 0.4f));
            gameElement.addElement(firstCircle);

            OvalGameElement secondCircle = new OvalGameElement();
            secondCircle.setColor(boneColor);
            secondCircle.setWidth(getWidth() / 2);
            secondCircle.setHeight(getHeight() / 2);
            secondCircle.setExactPosition((Constants.TILE_WIDTH.getValue() * 0.6f), (Constants.TILE_HEIGHT.getValue() * 0.3f));
            gameElement.addElement(secondCircle);

            return gameElement;
        }
    },

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
            gameElement.setPosition(GameElement.Position.NONE);
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
            //needed to give a hierarchy/layers
            GameElement gameElement = new OvalGameElement();

            //head shape
            GameElement head = new OvalGameElement();
            head.setType(this);
            head.setColor(getColor());
            head.setPosition(GameElement.Position.NONE);

            //eyes
            int eyeSize = (int)(getHeight() * 0.2);

            OvalGameElement firstPupil = new OvalGameElement();
            firstPupil.setColor(Color.BLUE);
            firstPupil.setWidth((int) (eyeSize * 0.7));
            firstPupil.setHeight((int) (eyeSize * 0.7));
            firstPupil.setExactPosition(Constants.TILE_WIDTH.getValue() * 0.5f, Constants.TILE_HEIGHT.getValue() * 0.6f);

            OvalGameElement firstEye = new OvalGameElement();
            firstEye.setColor(Color.WHITE);
            firstEye.setWidth(eyeSize);
            firstEye.setHeight(eyeSize);
            firstEye.setExactPosition(Constants.TILE_WIDTH.getValue() * 0.5f, Constants.TILE_HEIGHT.getValue() * 0.6f);

            OvalGameElement secondPupil = new OvalGameElement();
            secondPupil.setColor(Color.BLUE);
            secondPupil.setWidth( (int) (eyeSize * 0.7));
            secondPupil.setHeight( (int) (eyeSize * 0.7));
            secondPupil.setExactPosition(Constants.TILE_WIDTH.getValue() * 0.5f, Constants.TILE_HEIGHT.getValue() * 0.4f);

            OvalGameElement secondEye = new OvalGameElement();
            secondEye.setColor(Color.WHITE);
            secondEye.setWidth(eyeSize);
            secondEye.setHeight(eyeSize);
            secondEye.setExactPosition(Constants.TILE_WIDTH.getValue() * 0.5f, Constants.TILE_HEIGHT.getValue() * 0.4f);

            gameElement.addElement(firstPupil);
            gameElement.addElement(secondPupil);
            gameElement.addElement(firstEye);
            gameElement.addElement(secondEye);
            gameElement.addElement(head);
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
            //needed to give a hierarchy/layers
            GameElement gameElement = new OvalGameElement();

            //head shape
            GameElement head = new OvalGameElement();
            head.setType(this);
            head.setColor(getColor());
            head.setPosition(GameElement.Position.NONE);

            //eyes
            int eyeSize = (int)(getHeight() * 0.2);

            OvalGameElement firstPupil = new OvalGameElement();
            firstPupil.setColor(Color.BLUE);
            firstPupil.setWidth((int) (eyeSize * 0.7));
            firstPupil.setHeight((int) (eyeSize * 0.7));
            firstPupil.setExactPosition(Constants.TILE_WIDTH.getValue() * 0.6f, Constants.TILE_HEIGHT.getValue() * 0.5f);

            OvalGameElement firstEye = new OvalGameElement();
            firstEye.setColor(Color.WHITE);
            firstEye.setWidth(eyeSize);
            firstEye.setHeight(eyeSize);
            firstEye.setExactPosition(Constants.TILE_WIDTH.getValue() * 0.6f, Constants.TILE_HEIGHT.getValue() * 0.5f);

            OvalGameElement secondPupil = new OvalGameElement();
            secondPupil.setColor(Color.BLUE);
            secondPupil.setWidth( (int) (eyeSize * 0.7));
            secondPupil.setHeight( (int) (eyeSize * 0.7));
            secondPupil.setExactPosition(Constants.TILE_WIDTH.getValue() * 0.4f, Constants.TILE_HEIGHT.getValue() * 0.5f);

            OvalGameElement secondEye = new OvalGameElement();
            secondEye.setColor(Color.WHITE);
            secondEye.setWidth(eyeSize);
            secondEye.setHeight(eyeSize);
            secondEye.setExactPosition(Constants.TILE_WIDTH.getValue() * 0.4f, Constants.TILE_HEIGHT.getValue() * 0.5f);

            gameElement.addElement(firstPupil);
            gameElement.addElement(secondPupil);
            gameElement.addElement(firstEye);
            gameElement.addElement(secondEye);
            gameElement.addElement(head);

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
            gameElement.setPosition(GameElement.Position.NONE);
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
            gameElement.setPosition(GameElement.Position.NONE);
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
    },

    RECT_OBSTACLE {
        @Override
        public int getColor() {
            return Color.parseColor("#472400");
        }

        @Override
        public int getHeight() {
            return Constants.TILE_HEIGHT.getValue();
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
            gameElement.setPosition(GameElement.Position.NONE);
            return gameElement;
        }
    };

    public abstract int getColor();
    public abstract int getHeight();
    public abstract int getWidth();
    public abstract GameElement getGameElement();
}
