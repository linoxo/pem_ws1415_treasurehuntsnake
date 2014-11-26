package hunt.snake.com.snaketreasurehunt;

import android.graphics.Rect;

import hunt.snake.com.framework.Graphics;

/**
 * Created by Tom on 11/23/14.
 */
public class RectangleGameElement extends GameElement {

    private Position position;
    private int[] offsets;
    private Rect rect;

    public RectangleGameElement() {
        rect = new Rect(0, 0, 0, 0);
    }

    @Override
    public void drawGameElement(Graphics g) {
        /*
        GameElementType type = getType();
        int left = getTile().getPosX() * Constants.TILE_WIDTH.getValue() + ((Constants.TILE_WIDTH.getValue() - type.getWidth()) / 2);
        int top = getTile().getPosY() * Constants.TILE_HEIGHT.getValue() + ((Constants.TILE_HEIGHT.getValue() - type.getHeight()) / 2);
        int width = getType().getWidth() + left;
        int height = getType().getHeight() + top;
        Paint paint = getType().getPaint();
        //rect = new Rect(left, top, width, height);
        */

        int color = getType().getColor();
        //System.out.println(rect);
        g.drawRect(rect.left, rect.top, rect.width(), rect.height(), color);

    }

    public void setPosition(Position position) {
        this.position = position;
        updateRectangle();
    }

    public Position getPosition() {
        return position;
    }

    private void updateRectangle() {
        GameElementType type = getType();

        int left = getTile().getPosX() * Constants.TILE_WIDTH.getValue() + ((Constants.TILE_WIDTH.getValue() - type.getWidth()) / 2);
        int top = getTile().getPosY() * Constants.TILE_HEIGHT.getValue() + ((Constants.TILE_HEIGHT.getValue() - type.getHeight()) / 2);
        int width = getType().getWidth();
        int height = getType().getHeight();

        /*
        switch(position) {
            case NORTH:
            top = getTile().getPosY() * Constants.TILE_HEIGHT.getValue();
                height = height - getType().getHeight() / 2 + getType().getWidth() / 2;
                break;
            case EAST:
                width = width - getType().getWidth() / 2 + getType().getHeight() / 2;
                break;
            case SOUTH:
                top = top + getType().getHeight() / 2 - getType().getWidth() / 2;
                break;
            case WEST:
                left = left + getType().getWidth() / 2 - getType().getHeight() / 2;
                break;
        }
        */

        switch(position) {
            case NORTH:
                top = getTile().getPosY() * Constants.TILE_HEIGHT.getValue();
                height = getType().getHeight();
                break;
            case EAST:
                left = getTile().getPosX() * Constants.TILE_WIDTH.getValue();
                width = getType().getWidth();
                break;
            case SOUTH:
                top = getTile().getPosY() * Constants.TILE_HEIGHT.getValue() + (Constants.TILE_HEIGHT.getValue() - getType().getHeight());
                height = getType().getHeight();
                break;
            case WEST:
                left = getTile().getPosX() * Constants.TILE_WIDTH.getValue() + (Constants.TILE_WIDTH.getValue() - getType().getWidth());
                width = getType().getWidth();
                break;
        }

        rect.set(left, top, left + width, top + height);
    }
}
