package hunt.snake.com.snaketreasurehunt;

import android.graphics.RectF;

import hunt.snake.com.framework.Graphics;

/**
 * Created by Tom on 11/23/14.
 */
public class OvalGameElement extends GameElement {

    private RectF rect;
    private float left;
    private float top;

    public OvalGameElement() {
    }

    @Override
    public void drawGameElement(Graphics g) {
        if(rect == null)
            update();
        int color = getColor();
        g.drawOval(rect, color);
    }

    @Override
    public void update() {
        left = left + getTile().getPosX() * Constants.TILE_WIDTH.getValue();
        top = top + getTile().getPosY() * Constants.TILE_HEIGHT.getValue();
        float right = left + getWidth();
        float bottom = top + getHeight();

        rect = new RectF(left, top, right, bottom);
    }

    @Override
    public void setPosition(Position position) {
        left = Constants.TILE_WIDTH.getValue() / 2 - getWidth() / 2;
        top = Constants.TILE_HEIGHT.getValue() / 2 - getHeight() / 2;

        if(getTile() != null)
            update();
    }

    public void setExactPosition(float x, float y) {
        left = x - getWidth() / 2;
        top = y - getHeight() / 2;

        if(getTile() != null)
            update();
    }
}
