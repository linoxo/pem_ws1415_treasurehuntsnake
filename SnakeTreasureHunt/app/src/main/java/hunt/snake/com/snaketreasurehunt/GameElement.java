package hunt.snake.com.snaketreasurehunt;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 11/23/14.
 */
public abstract class GameElement {

    public enum Position {
        NORTH,
        EAST,
        SOUTH,
        WEST,
        NONE;
    }

    private Canvas canvas;
    private Tile tile;
    private GameElementType type;
    private Position position;
    private List<GameElement> elements;
    private Paint paint;

    public GameElement(Canvas canvas) {
        this.canvas = canvas;
    }

    public GameElement() {

    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setType(GameElementType type) {
        this.type = type;
    }

    public GameElementType getType() {
        return type;
    }

    public void setPaint(Paint paint) { this.paint = paint; }

    public Paint getPaint() { return paint; }

    public void addElement(GameElement element) {
        if(elements != null)
            elements = new ArrayList<GameElement>();
        elements.add(element);
    }

    public void draw() {
        drawGameElement();
        if(elements.size() > 0) {
            for(GameElement element : elements)
                element.drawGameElement();
        }
    }

    public abstract void drawGameElement();
}
