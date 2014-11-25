package hunt.snake.com.snaketreasurehunt;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Tom on 11/23/14.
 */
public class OvalGameElement extends GameElement {

    public OvalGameElement(Canvas canvas) {
        super(canvas);
    }

    public OvalGameElement() {

    }

    @Override
    public void drawGameElement() {
        float circlePosX = getTile().getPosX() * Constants.TILE_WIDTH.getValue() + Constants.TILE_WIDTH.getValue() / 2;
        float circlePosY = getTile().getPosY() * Constants.TILE_HEIGHT.getValue() + Constants.TILE_HEIGHT.getValue() / 2;
        float radius = getType().getWidth();

        GameElementType type = getType();

        float left = getTile().getPosX() * Constants.TILE_WIDTH.getValue() + Constants.TILE_WIDTH.getValue() / 2 - type.getWidth() / 2;
        float top = getTile().getPosY() * Constants.TILE_HEIGHT.getValue() + Constants.TILE_HEIGHT.getValue() / 2 - type.getHeight() / 2;
        float width = left + type.getWidth();
        float height = top + type.getHeight();


        Paint paint = getType().getPaint();
        RectF rect = new RectF(left, top, width, height);
        getCanvas().drawOval(rect, paint);
    }

    @Override
    public void setOrientation(RectangleGameElement.Orientation orientation) {}
}
