package hunt.snake.com.snaketreasurehunt;

import android.graphics.Canvas;

import java.util.List;

/**
 * Created by Tom on 11/23/14.
 */
public class Snake {

    private int length;
    private Tile headTile;
    private Tile[][] tiles;
    private Canvas canvas;
    private GameElement.Position position;

    public Snake(Canvas canvas, Tile startTile, Tile[][] tiles, int length, GameElement.Position posiiton) {
        this.length = length;
        this.canvas = canvas;
        this.tiles = tiles;
        this.position = posiiton;
        headTile = startTile;

        init();
    }

    private void init() {
        initSnake(position);
    }

    public void addLength(int amount) {
        length += amount;
    }

    public void addLength() {
        length++;
    }

    private void initSnake(GameElement.Position position) {
        drawHead(position);

        int parts = 1;
        int startX = headTile.getPosX();
        int startY = headTile.getPosY();

        while(parts < length - 1) {
            switch(position) {
                case NORTH:
                    drawBodypart(tiles[startX][startY + parts], position);
                    break;
                case EAST:
                    drawBodypart(tiles[startX - parts][startY], position);
                    break;
                case SOUTH:
                    drawBodypart(tiles[startX][startY - parts], position);
                    break;
                case WEST:
                    drawBodypart(tiles[startX + parts][startY], position);
                    break;
                case NONE:
                    drawBodypart(tiles[startX - parts][startY], position);
                    break;
            }
            parts++;
        }

    }

    private void drawBodypart(Tile tile, GameElement.Position position) {
        GameElementType type;
        if(position == GameElement.Position.EAST || position == GameElement.Position.WEST)
            type = GameElementType.SNAKE_BODY_HORIZONTAL;
        else
            type = GameElementType.SNAKE_BODY_VERTICAL;

        RectangleGameElement body = new RectangleGameElement(canvas);
        body.setType(type);
        body.setTile(tile);
        body.setPosition(GameElement.Position.NONE);
        body.drawGameElement();
    }

    private void drawHead(GameElement.Position position) {
        //Head
        GameElement head = new OvalGameElement(canvas);
        head.setType(GameElementType.SNAKE_HEAD_HORIZONTAL);
        head.setTile(headTile);

        RectangleGameElement neck = new RectangleGameElement();
        neck.setType(GameElementType.SNAKE_BODY_HORIZONTAL_SHORT);
        neck.setPosition(GameElement.Position.EAST);
        head.addElement(neck);

        head.drawGameElement();
    }
}
