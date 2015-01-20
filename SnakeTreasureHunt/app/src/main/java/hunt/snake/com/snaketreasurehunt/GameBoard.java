package hunt.snake.com.snaketreasurehunt;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hunt.snake.com.framework.Graphics;
import hunt.snake.com.framework.impl.AndroidGame;
import hunt.snake.com.snaketreasurehunt.communication.DataTransferHandler;
import hunt.snake.com.snaketreasurehunt.stitching.SnakeGestureListener;

public class GameBoard {

    private static float TICK;                          // the snake moves every TICK seconds
    private static final float TICK_INIT = 1.0f;        // starting value for the tick
    private static final float TICK_DECREMENT = 0.15f;  // it gets this faster after eating
    private static final float TICK_MIN = 0.25f;        // the minimum tick time

    private int boardWidth;         // width of the game board in tiles
    private int boardHeight;        // height of the game board in tiles
    private int screenWidth;        // how many tiles fit entirely into the screen's width
    private int screenHeight;       // how many tiles fit entirely into the screen's height

    private int foodX;              // the x position of the food in tiles
    private int foodY;              // the y position of the food in tiles

    private Tile[][] tiles;
    Tile topLeft;       // defines which part of the game board shall be shown
    Tile bottomRight;   // the bottom right tile
    private Snake snake;
    Snake.Direction nextSnakeDirection;
    private boolean snakeCanTurn;
    private float snakeCanTurnCounter;
    private boolean snakeHasEatenLock;
    private List<GameElement> gameElements;
    private boolean gameOver;
    private int score;
    private Random random;

    private float tickTime = 0;
    private int stitchingDirection; // side of the phone where there was a swipe out

    public GameBoard() {
        topLeft = new Tile();
        bottomRight = new Tile();
        snake = new Snake();
        gameElements = new ArrayList<GameElement>();
        random = new Random();
        init();
    }

    public void init(){
        // calculate how many tiles fit into the smartphone screen
        screenWidth = AndroidGame.getScreenWidth() / Constants.TILE_WIDTH.getValue();
        screenHeight = AndroidGame.getScreenHeight() / Constants.TILE_HEIGHT.getValue();
        System.out.println("Screen width = " + screenWidth + " height = " + screenHeight);

        // get the size of the game board
        boardWidth = Constants.BOARD_WIDTH.getValue();
        boardHeight = Constants.BOARD_HEIGHT.getValue();

        TICK = TICK_INIT;
        gameOver = false;
        score = 0;

        if(SnakeTreasureHuntGame.isActivePhone) {
            generateGameBoard();
            sendGameStartMessage();
        }
    }

    public void generateGameBoard() {
        // set top left tile
        topLeft.setPositionOnBoard(0, 0);
        bottomRight.setPositionOnBoard(screenWidth, screenHeight);

        // create tiles
        createTiles();

        // create snake
        Snake.Direction startDirection = Snake.Direction.WEST;
        snake.init(tiles[2][3], tiles, 3, startDirection);
        nextSnakeDirection = startDirection;
        snakeCanTurn = true;
        snakeCanTurnCounter = TICK;
        snakeHasEatenLock = false;

        // create obstacles and food
        gameElements.clear();
        createGameElements();
    }

    public void update(float deltaTime) {
        if (gameOver) {
            return;
        }

        // manage the snakes possibility to turn
        if(!snakeCanTurn) {
            snakeCanTurnCounter -= deltaTime;
        }
        if(snakeCanTurnCounter <= 0.0f) {
            snakeCanTurnCounter = TICK;
            snakeCanTurn = true;
        }

        tickTime += deltaTime;

        // updates game board every TICK seconds
        while (tickTime > TICK) {
            tickTime -= TICK;

            // move snake in the direction of its head
            if(!snake.move(nextSnakeDirection)) {
                gameOver = true;
                sendGameOverMessage();
                return;
            }

            Snake.Direction sideAtWhichSnakeIsOutOfScreen = snake.snakeOutOfScreen(topLeft, bottomRight);
            if(sideAtWhichSnakeIsOutOfScreen != null) {
                // snake is out of the screen --> show new part of game board on screen
                switch (sideAtWhichSnakeIsOutOfScreen) {
                    case NORTH:
                        topLeft.move(0, -screenHeight);
                        bottomRight.move(0, -screenHeight);
                        break;
                    case EAST:
                        topLeft.move(screenWidth, 0);
                        bottomRight.move(screenWidth, 0);
                        break;
                    case SOUTH:
                        topLeft.move(0, screenHeight);
                        bottomRight.move(0, screenHeight);
                        break;
                    case WEST:
                        topLeft.move(-screenWidth, 0);
                        bottomRight.move(-screenWidth, 0);
                        break;
                }
            }

            // check whether snake has eaten something
            if(snake.hasEaten() && !snakeHasEatenLock) {
                snakeHasEatenLock = true;

                // increase score, spawn new gutti, increase speed and send new gutti message
                score += Constants.SCORE_INCREMENT.getValue();
                spawnFood();
                if(TICK > TICK_MIN) {
                    TICK -= TICK_DECREMENT;
                }
                sendNewGuttiMessage();
            } else if(!snake.hasEaten() && snakeHasEatenLock) {
                snakeHasEatenLock = false;
            }
        }
    }

    public void draw(Graphics g) {
        drawTiles(g);
        drawGameElements(g);
        snake.drawSnake(g, -topLeft.getPosX(), -topLeft.getPosY());

        // if food is not visible (on the screen), then draw a halo for orientation help
        if(!isFoodVisible()) {
            drawHalo(g);
        }
    }

    public void createGameElements() {
        // create obstacle
        gameElements.add(new Obstacle(tiles[4][9], tiles, GameElementType.RECT_OBSTACLE, 4));

        // spawn food
        spawnFood();
    }

    public void spawnFood() {
        // spawn food at random position inside the game board and not at an obstacle
        do {
            foodX = random.nextInt(boardWidth - 2) + 1;
            foodY = random.nextInt(boardHeight - 2) + 1;

            boolean foodIsOnObstacle = false;
            for (GameElement gameElement : gameElements) {
                if (gameElement.getType() == GameElementType.RECT_OBSTACLE) {
                    if (gameElement instanceof Obstacle && ((Obstacle)gameElement).isTileOccupiedByObstacle(foodX, foodY)) {
                        foodIsOnObstacle = true;
                    }
                }
            }
            if(!foodIsOnObstacle) {
                break;
            }
        } while(true);

        gameElements.add(createGameElement(GameElementType.FOOD, tiles[foodX][foodY]));
    }

    private GameElement createGameElement(GameElementType type, Tile tile) {
        GameElement element = type.getGameElement();
        element.setType(type);
        element.setTile(tile);
        return element;
    }

    private void drawGameElements(Graphics g) {
        int topLeftX = topLeft.getPosX();
        int topLeftY = topLeft.getPosY();

        for (GameElement element : gameElements) {
            if(element.getType() != GameElementType.FOOD || isFoodVisible()) {
                element.draw(g, -topLeftX, -topLeftY);
            }
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
        int topLeftX = topLeft.getPosX();
        int topLeftY = topLeft.getPosY();

        // how many tiles are visible?
        int cols = screenWidth;
        int rows = screenHeight;
        if(topLeftX + screenWidth > tiles.length) {
            cols += (tiles.length - topLeftX - screenWidth);
        }
        if(topLeftY + screenHeight > tiles[0].length) {
            rows += (tiles[0].length - topLeftY - screenHeight);
        }

        // shift the game board that is drawn according to the position of the top left tile
        // only draw the visible tiles!
        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < rows; y++) {
                Tile tile = tiles[topLeftX + x][topLeftY + y];
                tile.drawTile(g, -topLeftX, -topLeftY);
            }
        }
    }

    private void drawHalo(Graphics g) {
        int centerX = (int)((foodX - topLeft.getPosX() + 0.5) * Constants.TILE_WIDTH.getValue());
        int centerY = (int)((foodY - topLeft.getPosY() + 0.5) * Constants.TILE_HEIGHT.getValue());
        int radius = 0;

        // calculate the radius of the halo
        if(foodX < topLeft.getPosX()) {
            // food is left of the screen
            if(foodY >= topLeft.getPosY() && foodY <= bottomRight.getPosY()) {
                radius = (topLeft.getPosX() - foodX) * Constants.TILE_WIDTH.getValue();

            // food is left above or left below of the screen
            } else {
                int distanceX = topLeft.getPosX() - foodX;
                int distanceY;
                if(foodY < topLeft.getPosY()) {
                    distanceY = topLeft.getPosY() - foodY;
                } else {
                    distanceY = foodY - bottomRight.getPosY();
                }
                distanceX *= Constants.TILE_WIDTH.getValue();
                distanceY *= Constants.TILE_HEIGHT.getValue();

                radius = (int)Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            }
        } else if (foodX > bottomRight.getPosX()){
            // food is right of the screen
            if(foodY >= topLeft.getPosY() && foodY <= bottomRight.getPosY()) {
                radius = (foodX - bottomRight.getPosX()) * Constants.TILE_WIDTH.getValue();

            // food is right above or right below of the screen
            } else {
                int distanceX = foodX - bottomRight.getPosX();
                int distanceY;
                if(foodY < topLeft.getPosY()) {
                    distanceY = topLeft.getPosY() - foodY;
                } else {
                    distanceY = foodY - bottomRight.getPosY();
                }
                distanceX *= Constants.TILE_WIDTH.getValue();
                distanceY *= Constants.TILE_HEIGHT.getValue();

                radius = (int)Math.sqrt(distanceX * distanceX + distanceY * distanceY);
            }
        } else {
            // food is above the screen
            if(foodY < topLeft.getPosY()) {
                radius = (topLeft.getPosY() - foodY) * Constants.TILE_HEIGHT.getValue();

            // food is below the screen
            } else if(foodY > bottomRight.getPosY()) {
                radius = (foodY - bottomRight.getPosY()) * Constants.TILE_HEIGHT.getValue();
            }
        }

        radius += Constants.TILE_HEIGHT.getValue() / 2;
        g.drawHalo(centerX, centerY, radius, 10, Color.YELLOW);
    }

    boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) { this.score = score; }

    public void setNextSnakeDirection(Snake.Direction nextSnakeDirection) {
        if(snakeCanTurn) {
            snakeCanTurn = false;
            this.nextSnakeDirection = nextSnakeDirection;
        }
    }

    private boolean isFoodVisible() {
        return foodX >= topLeft.getPosX() && foodX <= bottomRight.getPosX() && foodY >= topLeft.getPosY() && foodY <= bottomRight.getPosY();
    }

    //HERE GETTER AND SETTER TO CHANGE VARIABLES DURING GAME
    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) { this.snake = snake; }

    public List<GameElement> getGameElements() { return gameElements; }

    public void setGameElements(List<GameElement> gameElements) { this.gameElements = gameElements; }

    public Tile[][] getTiles() { return tiles; }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public int getFoodX() { return foodX; }

    public void setFoodX(int foodX) { this.foodX = foodX; }

    public int getFoodY() { return foodY; }

    public void setFoodY(int foodY) { this.foodY = foodY; }

    // Methods for sending the messages during the game
    public void sendGameStartMessage() {
        DataTransferHandler.setFieldWidth(boardWidth);
        DataTransferHandler.setFieldHeight(boardHeight);
        // TODO: store occupied tiles
    }

    public void sendStitchOutMessage() {
        DataTransferHandler.setTickTime(tickTime);
        DataTransferHandler.setTimestamp(System.nanoTime() / 1000000000.0f);
        DataTransferHandler.setStitchingDirection(stitchingDirection);

        int topLeftXPos = topLeft.getPosX();
        int topLeftYPos = topLeft.getPosY();
        switch(stitchingDirection) {
            case SnakeGestureListener.SWIPEOUT_TOP: topLeftYPos -= screenHeight; break;
            case SnakeGestureListener.SWIPEOUT_LEFT: topLeftXPos -= screenWidth; break;
            case SnakeGestureListener.SWIPEOUT_RIGHT: topLeftXPos += screenWidth; break;
            case SnakeGestureListener.SWIPEOUT_BOTTOM: topLeftYPos += screenHeight; break;
        }
        DataTransferHandler.setTopLeftXPos(topLeftXPos);
        DataTransferHandler.setTopLeftYPos(topLeftYPos);
        // TODO: store snake
    }

    public void sendNewGuttiMessage() {
        DataTransferHandler.setFoodXPos(foodX);
        DataTransferHandler.setFoodYPos(foodY);
    }

    public void sendGameOverMessage() {
        DataTransferHandler.setScore(score);
    }
}
