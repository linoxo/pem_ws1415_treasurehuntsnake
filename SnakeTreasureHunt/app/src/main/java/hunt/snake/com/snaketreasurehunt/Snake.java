package hunt.snake.com.snaketreasurehunt;

import hunt.snake.com.framework.Graphics;

/**
 * Created by Tom on 11/23/14.
 */
public class Snake {

    private int length;
    private Tile headTile;
    private Tile[][] tiles;
    private GameElement.Position position;

    public Snake(Tile startTile, Tile[][] tiles, int length, GameElement.Position position) {
        this.length = length;
        this.tiles = tiles;
        this.position = position;
        headTile = startTile;

        init();
    }

    private void init() {

    }

    public void addLength(int amount) {
        length += amount;
    }

    public void addLength() {
        length++;
    }

    public void drawSnake(GameElement.Position position, Graphics g) {
        drawHead(position, g);

        int parts = 1;
        int startX = headTile.getPosX();
        int startY = headTile.getPosY();

        while(parts < length - 1) {
            switch(position) {
                case NORTH:
                    drawBodypart(tiles[startX][startY + parts], position, g);
                    break;
                case EAST:
                    drawBodypart(tiles[startX - parts][startY], position, g);
                    break;
                case SOUTH:
                    drawBodypart(tiles[startX][startY - parts], position, g);
                    break;
                case WEST:
                    drawBodypart(tiles[startX + parts][startY], position, g);
                    break;
                case NONE:
                    drawBodypart(tiles[startX - parts][startY], position, g);
                    break;
            }
            parts++;
        }

    }

    private void drawBodypart(Tile tile, GameElement.Position position, Graphics g) {
        GameElementType type;
        if(position == GameElement.Position.EAST || position == GameElement.Position.WEST)
            type = GameElementType.SNAKE_BODY_HORIZONTAL;
        else
            type = GameElementType.SNAKE_BODY_VERTICAL;

        RectangleGameElement body = new RectangleGameElement();
        body.setType(type);
        body.setTile(tile);
        body.setPosition(GameElement.Position.NONE);
        body.drawGameElement(g);
    }

    private void drawHead(GameElement.Position position, Graphics g) {
        //Head
        GameElement head = new OvalGameElement();
        head.setType(GameElementType.SNAKE_HEAD_HORIZONTAL);
        head.setTile(headTile);

        RectangleGameElement neck = new RectangleGameElement();
        neck.setType(GameElementType.SNAKE_BODY_HORIZONTAL_SHORT);
        neck.setTile(headTile);
        neck.setPosition(GameElement.Position.EAST);
        head.addElement(neck);

        head.drawGameElement(g);
    }
}
