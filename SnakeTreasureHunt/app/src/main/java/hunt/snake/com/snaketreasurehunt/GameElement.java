package hunt.snake.com.snaketreasurehunt;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 11/23/14.
 */
public abstract class GameElement {

    public enum Orientation {
        NORTH,
        EAST,
        SOUTH,
        WEST,
        NONE;
    }

    private Canvas canvas;
    private Tile tile;
    private GameElementType type;
    private Orientation orientation;
    private List<GameElement> elements;

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

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void addElement(GameElementType type) {
        if(elements != null)
            elements = new ArrayList<GameElement>();
        GameElement element = type.getGameElement();
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
