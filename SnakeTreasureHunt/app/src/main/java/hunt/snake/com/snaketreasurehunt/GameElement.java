package hunt.snake.com.snaketreasurehunt;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import hunt.snake.com.framework.Graphics;

/**
 * Created by Tom on 11/23/14.
 */
public abstract class GameElement {

    public enum Position {
        NORTH,
        EAST,
        SOUTH,
        WEST,
        NONE
    }

    private Tile tile;
    private GameElementType type;
    private List<GameElement> elements;
    private int color;

    public GameElement() {
    }

    public void setTile(Tile tile) {
        this.tile = tile;

        if(elements != null && elements.size() > 0) {
            for(GameElement element : elements)
                element.setTile(tile);
        }
    }

    public Tile getTile() {
        return tile;
    }

    public void setType(GameElementType type) {
        this.type = type;
    }

    public GameElementType getType() {
        return type;
    }

    public void addElement(GameElement element) {
        if(elements == null) {
            elements = new ArrayList<GameElement>();
        }
        element.setTile(tile);
        elements.add(element);
    }

    public void draw(Graphics g) {
        drawGameElement(g);
        if(elements != null && elements.size() > 0) {
            for(GameElement element : elements) {
                element.drawGameElement(g);
            }
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void redraw(Graphics g) {
        drawGameElement(g);
    }

    public abstract void drawGameElement(Graphics g);
    public abstract void setPosition(Position position);
}
