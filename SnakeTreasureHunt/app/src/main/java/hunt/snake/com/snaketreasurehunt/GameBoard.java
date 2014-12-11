package hunt.snake.com.snaketreasurehunt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hunt.snake.com.framework.Graphics;
import hunt.snake.com.framework.impl.AndroidGame;

public class GameBoard {

    private static final float TICK = 1.0f;

    private int boardWidth;
    private int boardHeight;

    private Tile[][] tiles;
    Snake snake;
    Snake.Direction nextSnakeDirection;
    private boolean snakeCanTurn;
    private List<GameElement> gameElements;
    private boolean gameOver;
    private int score;
    private Random random;

    private float tickTime = 0;

    public GameBoard() {
        snake = new Snake();
        gameElements = new ArrayList<GameElement>();
        random = new Random();
        init();
    }

    public void init(){
        // fill whole screen up with tiles
        boardWidth = AndroidGame.getScreenWidth() / Constants.TILE_WIDTH.getValue();
        boardHeight = AndroidGame.getScreenHeight() / Constants.TILE_HEIGHT.getValue();

        createTiles();
        Snake.Direction startDirection = Snake.Direction.WEST;
        snake.init(tiles[2][3], tiles, 3, startDirection);
        nextSnakeDirection = startDirection;
        snakeCanTurn = true;
        gameElements.clear();
        gameOver = false;
        score = 0;
        createGameElements();
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

            // move snake in the direction of its head
            if(!snake.move(nextSnakeDirection)) {
                gameOver = true;
                return;
            }

            // after moving, the snake can be turned again
            snakeCanTurn = true;

            // check whether snake has eaten something
            // if(snake has eaten something) {
            //      score += Constants.SCORE_INCREMENT.getValue();
            //      spawn new food at random position
            // }

            // dummy game logic: coin jumps around
            /* int size = gameElements.size();
            for (int i = 0; i < size; i++) {
                GameElement element = gameElements.get(i);
                if(element.getType() == GameElementType.COIN) {
                    int x = random.nextInt(boardWidth);
                    int y = random.nextInt(boardHeight);
                    element.setTile(tiles[x][y]);
                }
            }*/
        }
    }

    public void draw(Graphics g) {
        drawTiles(g);
        drawGameElements(g);
        snake.drawSnake(g);
    }

    public void createGameElements() {
        gameElements.add(new Obstacle(tiles[4][9], tiles, GameElementType.RECT_OBSTACLE, 4));
        int x = random.nextInt(boardWidth);
        int y = random.nextInt(boardHeight);
        gameElements.add(createGameElement(GameElementType.FOOD, tiles[x][y]));
    }

    private GameElement createGameElement(GameElementType type, Tile tile) {
        GameElement element = type.getGameElement();
        element.setType(type);
        element.setTile(tile);
        return element;
    }

    private void drawGameElements(Graphics g) {
        int size = gameElements.size();
        for (int i = 0; i < size; i++) {
            GameElement element = gameElements.get(i);
            element.draw(g);
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

    public void setNextSnakeDirection(Snake.Direction nextSnakeDirection) {
        if(snakeCanTurn) {
            snakeCanTurn = false;
            this.nextSnakeDirection = nextSnakeDirection;
        }
    }
}
