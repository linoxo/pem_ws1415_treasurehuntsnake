package hunt.snake.com.snaketreasurehunt;

import android.graphics.Rect;

import hunt.snake.com.framework.Graphics;

/**
 * Created by Tom on 11/23/14.
 */
public class RectangleGameElement extends GameElement {

    private Position position;
    private Rect rect;
    private int left;
    private int top;

    public RectangleGameElement() {}

    @Override
    public void drawGameElement(Graphics g) {
        if(rect == null)
            update();
        int color = getColor();
        g.drawRect(rect.left, rect.top, rect.width(), rect.height(), color);
    }

    /*
    public void setPosition(Position position) {
        this.position = position;
        update();
    }
    */

    public Position getPosition() {
        return position;
    }

    @Override
    public void update() {
        System.out.println(getName() + ": " + getTile().getPosX() + ", " + getTile().getPosY());
        left = left + getTile().getPosX() * Constants.TILE_WIDTH.getValue();
        top = top + getTile().getPosY() * Constants.TILE_HEIGHT.getValue();
        int right = left + getWidth();
        int bottom = top + getHeight();
        System.out.println(rect);
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
