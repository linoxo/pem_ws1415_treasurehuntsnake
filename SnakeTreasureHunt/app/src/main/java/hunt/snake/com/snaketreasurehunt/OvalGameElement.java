package hunt.snake.com.snaketreasurehunt;

import android.graphics.RectF;

import hunt.snake.com.framework.Graphics;

/**
 * Created by Tom on 11/23/14.
 */
public class OvalGameElement extends GameElement {

    public OvalGameElement() {

    }

    @Override
    public void drawGameElement(Graphics g) {
        float circlePosX = getTile().getPosX() * Constants.TILE_WIDTH.getValue() + Constants.TILE_WIDTH.getValue() / 2;
        float circlePosY = getTile().getPosY() * Constants.TILE_HEIGHT.getValue() + Constants.TILE_HEIGHT.getValue() / 2;
        float radius = getType().getWidth();

        GameElementType type = getType();

        float left = getTile().getPosX() * Constants.TILE_WIDTH.getValue() + Constants.TILE_WIDTH.getValue() / 2 - type.getWidth() / 2;
        float top = getTile().getPosY() * Constants.TILE_HEIGHT.getValue() + Constants.TILE_HEIGHT.getValue() / 2 - type.getHeight() / 2;
        float width = left + type.getWidth();
        float height = top + type.getHeight();

        int color = getType().getColor();
        RectF rect = new RectF(left, top, width, height);
        g.drawOval(rect, color);
    }
}
