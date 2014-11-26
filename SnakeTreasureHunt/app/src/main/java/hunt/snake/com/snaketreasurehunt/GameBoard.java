package hunt.snake.com.snaketreasurehunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hunt.snake.com.framework.Graphics;
import hunt.snake.com.framework.impl.AndroidGame;

/**
 * Created by Tom on 11/23/14.
 */
public class GameBoard {

    private static final float TICK = 1.0f;

    private int boardWidth;
    private int boardHeight;

    private Tile[][] tiles;
    private Snake snake;
    private List<GameElement> gameElements;
    private boolean gameOver;
    private int score;
    private Random random;

    private float tickTime = 0;

    public GameBoard() {
        init();
        createTiles();
        snake = new Snake(tiles[3][4], tiles, 4, GameElement.Position.EAST);
        gameElements = new ArrayList<GameElement>();
        createGameElements();
        random = new Random();
    }

    public void init(){
        // fill whole screen up with tiles
        boardWidth = AndroidGame.getScreenWidth() / Constants.TILE_WIDTH.getValue();
        boardHeight = AndroidGame.getScreenHeight() / Constants.TILE_HEIGHT.getValue();

        gameOver = false;
        score = 0;
    }

    public void update(float deltaTime) {
        if (gameOver) {
            return;
        }

        tickTime += deltaTime;

        // updates game board every TICK seconds
        while (tickTime > TICK) {
            tickTime -= TICK;

            // ============================
            // == INSERT GAME LOGIC HERE ==
            // ============================

            // dummy game logic: coin jumps around
            int size = gameElements.size();
            for (int i = 0; i < size; i++) {
                GameElement element = gameElements.get(i);
                if(element.getType() == GameElementType.COIN) {
                    int x = random.nextInt(boardWidth);
                    int y = random.nextInt(boardHeight);
                    element.setTile(tiles[x][y]);
                }
            }
        }
    }

    public void draw(Graphics g) {
        drawTiles(g);
        drawGameElements(g);
        snake.drawSnake(GameElement.Position.EAST, g);
    }

    public void createGameElements() {
        //createGameElement(GameElementType.COIN, tiles[3][1], GameElement.Position.NONE);
        //createGameElement(GameElementType.SNAKE_HEAD, tiles[2][4], GameElement.Position.EAST);
        //createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[2][4], GameElement.Position.EAST);
        // createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[4][4]);

        gameElements.add(createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[0][4], GameElement.Position.WEST));
        gameElements.add(createGameElement(GameElementType.SNAKE_BODY_VERTICAL, tiles[0][4], GameElement.Position.NORTH));
        gameElements.add(createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[0][3], GameElement.Position.WEST));
        gameElements.add(createGameElement(GameElementType.SNAKE_BODY_VERTICAL, tiles[0][3], GameElement.Position.SOUTH));
        gameElements.add(createGameElement(GameElementType.SNAKE_BODY_HORIZONTAL, tiles[1][3], GameElement.Position.NONE));

        gameElements.add(createGameElement(GameElementType.COIN, tiles[3][1]));
    }

    private GameElement createGameElement(GameElementType type, Tile tile, GameElement.Position orientation) {
        GameElement element = type.getGameElement();
        element.setTile(tile);
        element.setType(type);
        return element;
    }

    private GameElement createGameElement(GameElementType type, Tile tile) {
        GameElement element = type.getGameElement();
        element.setTile(tile);
        element.setType(type);
        return element;
    }

    private void drawGameElements(Graphics g) {
        int size = gameElements.size();
        for (int i = 0; i < size; i++) {
            GameElement element = gameElements.get(i);
            element.drawGameElement(g);
        }
    }

    private void createTiles() {
        tiles = new Tile[boardWidth][boardHeight];

        Tile tile;

        int cols = tiles[0].length;
        int rows = tiles.length;

        System.out.println(cols);
        System.out.println(rows);

        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < cols; y++) {
                tile = new Tile();
                tile.setPositionOnBoard(x, y);

                if(x == 0)
                    tile.setIsLeftBorder(true);

                if(y == 0)
                    tile.setIsTopBorder(true);

                if(x == rows - 1)
                    tile.setIsRightBorder(true);

                if(y == cols - 1)
                    tile.setIsBottomBorder(true);

                tiles[x][y] = tile;
            }
        }
    }

    private void drawTiles(Graphics g) {
        int cols = tiles[0].length;
        int rows = tiles.length;

        for(int x = 0; x < rows; x++) {
            for(int y = 0; y < cols; y++) {
                Tile tile = tiles[x][y];
                tile.drawTile(g);
            }
        }
    }

    boolean isGameOver() {
        return gameOver;
    }

    int getScore() {
        return score;
    }
}
