package hunt.snake.com.snaketreasurehunt;

import android.graphics.Rect;

import hunt.snake.com.framework.Graphics;

public class RectangleGameElement extends GameElement {

    private Rect rect;
    private int left;
    private int top;

    public RectangleGameElement() {}

    @Override
    public void drawGameElement(Graphics g) {
        drawGameElement(g, 0, 0);
    }

    @Override
    public void drawGameElement(Graphics g, int deltaX, int deltaY) {
        if(rect == null)
            update();
        int color = getColor();

        int left = rect.left + deltaX * Constants.TILE_WIDTH.getValue();
        int top = rect.top + deltaY * Constants.TILE_HEIGHT.getValue();
        g.drawRect(left, top, rect.width(), rect.height(), color);
    }

    @Override
    public void update() {
        left = left + getTile().getPosX() * Constants.TILE_WIDTH.getValue();
        top = top + getTile().getPosY() * Constants.TILE_HEIGHT.getValue();
        int right = left + getWidth();
        int bottom = top + getHeight();
        rect = new Rect(left, top, right, bottom);
    }

    @Override
    public void setPosition(Position position) {
        left = (Constants.TILE_WIDTH.getValue() - getWidth()) / 2;
        top = (Constants.TILE_HEIGHT.getValue() - getHeight()) / 2;

        switch(position) {
            case NORTH:
                top = 0;
                break;
            case WEST:
                left = 0;
                break;
            case SOUTH:
                top = (Constants.TILE_HEIGHT.getValue() - getHeight());
                break;
            case EAST:
                left = (Constants.TILE_WIDTH.getValue() - getWidth());
                break;
            default:
                break;
        }

        if(getTile() != null)
            update();
    }
}
