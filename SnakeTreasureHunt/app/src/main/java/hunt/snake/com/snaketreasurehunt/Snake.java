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
    private GameElement.Orientation orientation;

    private List<GameElement.Orientation> list;

    public Snake(Canvas canvas, Tile startTile, Tile[][] tiles, int length, GameElement.Orientation orientation) {
        this.length = length;
        this.canvas = canvas;
        this.tiles = tiles;
        this.orientation = orientation;
        headTile = startTile;

        init();
    }

    private void init() {
        initSnake(orientation);
    }

    public void addLength(int amount) {
        length += amount;
    }

    public void addLength() {
        length++;
    }

    private void initSnake(GameElement.Orientation orientation) {
        drawHead(orientation);
        list.add(orientation);

        int parts = 1;
        int startX = headTile.getPosX();
        int startY = headTile.getPosY();

        while(parts < length - 1) {
            switch(orientation) {
                case NORTH:
                    drawBodypart(tiles[startX][startY + parts], orientation);
                    break;
                case EAST:
                    drawBodypart(tiles[startX - parts][startY], orientation);
                    break;
                case SOUTH:
                    drawBodypart(tiles[startX][startY - parts], orientation);
                    break;
                case WEST:
                    drawBodypart(tiles[startX + parts][startY], orientation);
                    break;
                case NONE:
                    drawBodypart(tiles[startX - parts][startY], orientation);
                    break;
            }
            parts++;
        }

    }

    private void drawBodypart(Tile tile, GameElement.Orientation orientation) {
        GameElementType type;
        if(orientation == GameElement.Orientation.EAST || orientation == GameElement.Orientation.WEST)
            type = GameElementType.SNAKE_BODY_HORIZONTAL;
        else
            type = GameElementType.SNAKE_BODY_VERTICAL;

        GameElement body = new RectangleGameElement(canvas);
        body.setType(type);
        body.setTile(tile);
        body.setOrientation(GameElement.Orientation.NONE);
        body.drawGameElement();
    }

    private void drawHead(GameElement.Orientation orientation) {
        //Head
        GameElement head = new OvalGameElement(canvas);
        head.setType(GameElementType.SNAKE_HEAD);
        head.setTile(headTile);
        head.setOrientation(orientation);
        head.drawGameElement();

        //"Neck"
        head = new RectangleGameElement(canvas);
        head.setType(GameElementType.SNAKE_BODY_HORIZONTAL);
        head.setTile(headTile);
        head.setOrientation(orientation);
        head.drawGameElement();
    }
}
