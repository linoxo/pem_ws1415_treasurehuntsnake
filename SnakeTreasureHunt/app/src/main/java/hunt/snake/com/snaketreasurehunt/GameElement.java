package hunt.snake.com.snaketreasurehunt;

import java.util.LinkedList;

import hunt.snake.com.framework.Graphics;

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
    private LinkedList<GameElement> elements;
    private int color;
    private int width;
    private int height;
    private String name;
    private boolean hidden;

    public GameElement() {
        name = "";
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        if(elements != null && elements.size() > 0) {
            for(GameElement element : elements) {
                element.setTile(tile);
            }
        }
        tile.setGameElement(this);
        update();
    }

    public Tile getTile() {
        return tile;
    }

    public void setType(GameElementType type) {
        this.type = type;

        setWidth(type.getWidth());
        setHeight(type.getHeight());
        setColor(type.getColor());
    }

    public GameElementType getType() {
        return type;
    }

    public void addElement(GameElement element) {
        if(elements == null) {
            elements = new LinkedList<GameElement>();
        }

        if(tile != null)
            element.setTile(tile);

        elements.addFirst(element);
    }

    public void draw(Graphics g) {
        draw(g, 0, 0);
    }

    public void draw(Graphics g, int deltaX, int deltaY) {
        if(!isHidden()) {
            if (elements != null && elements.size() > 0) {
                for (GameElement element : elements) {
                    element.drawGameElement(g, deltaX, deltaY);
                }
            }
            drawGameElement(g, deltaX, deltaY);
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void changeColor(int color) {
        this.color = color;

        if(elements != null) {
            for(GameElement element : elements) {
                element.setColor(color);
            }
        }
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void hidden(boolean hidden) {
        this.hidden = hidden;

        if(elements != null) {
            for(GameElement element : elements)
                element.hidden(hidden);
        }
    }

    public boolean isHidden() {
        return hidden;
    }

    public int getColor() {
        return color;
    }

    public abstract void update();
    public abstract void drawGameElement(Graphics g);
    public abstract void drawGameElement(Graphics g, int deltaX, int deltaY);
    public abstract void setPosition(Position position);
}
