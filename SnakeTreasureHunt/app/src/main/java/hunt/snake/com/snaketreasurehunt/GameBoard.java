package hunt.snake.com.snaketreasurehunt;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hunt.snake.com.framework.Graphics;
import hunt.snake.com.framework.impl.AndroidGame;
import hunt.snake.com.snaketreasurehunt.communication.DataTransferHandler;
import hunt.snake.com.snaketreasurehunt.communication.MessageHandler;
import hunt.snake.com.snaketreasurehunt.communication.STHMessage;
import hunt.snake.com.snaketreasurehunt.stitching.SnakeGestureListener;
import hunt.snake.com.snaketreasurehunt.wifi.ClientService;

public class GameBoard {

    private static float TICK;                          // the snake moves every TICK seconds
    private static final float TICK_INIT = 1.0f;        // starting value for the tick
    private static final float TICK_DECREMENT = 0.15f;  // it gets this faster after eating
    private static final float TICK_MIN = 0.25f;        // the minimum tick time

    private static float STITCHING_THRESHOLD = 2.0f;
    private float stitchingTimer;

    private int boardWidth;         // width of the game board in tiles
    private int boardHeight;        // height of the game board in tiles
    private int screenWidth;        // how many tiles fit entirely into the screen's width
    private int screenHeight;       // how many tiles fit entirely into the screen's height
    private int marginRight;        // how many pixels are left on the right (< TILE_WIDTH)
    private int marginBottom;       // how many pixels are left at the bottom (< TILE_HEIGHT)

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
    private boolean gamePaused;
    private boolean gameOver;
    private int score;
    private Random random;

    private float tickTime = 0;
    private Snake.Direction stitchingDirection; // side of the phone where there was a swipe out
    private MessageHandler mHandler;

    public GameBoard() {
        topLeft = new Tile();
        bottomRight = new Tile();
        snake = new Snake();
        gameElements = new ArrayList<GameElement>();
        random = new Random();
    }

    public void init(){
        // calculate how many tiles fit into the smartphone screen
        screenWidth = AndroidGame.getScreenWidth() / Constants.TILE_WIDTH.getValue();
        screenHeight = AndroidGame.getScreenHeight() / Constants.TILE_HEIGHT.getValue();
        System.out.println("Screen width = " + screenWidth + " height = " + screenHeight);

        // calculate the margin (pixels at the end of the visible board that are seen on the screen)
        marginRight = AndroidGame.getScreenWidth() - screenWidth * Constants.TILE_WIDTH.getValue();
        marginBottom = AndroidGame.getScreenHeight() - screenHeight * Constants.TILE_HEIGHT.getValue();

        TICK = TICK_INIT;
        tickTime = 0.0f;
        stitchingTimer = 0.0f;
        gamePaused = false;
        gameOver = false;
        score = 0;

        System.out.println("before if");
        if(SnakeTreasureHuntGame.isControllingPhone) {
            generateGameBoard();
            sendGameStartMessage();
            test();
            mHandler.sendStitching();
            //mHandler.sendInitGame();
            //mHandler.test();
            System.out.println("after start");
        }
    }

    public void generateGameBoard() {
        // get the size of the game board
        boardWidth = Constants.BOARD_WIDTH.getValue();
        boardHeight = Constants.BOARD_HEIGHT.getValue();

        // set top left tile
        topLeft.setPositionOnBoard(0, 0);
        bottomRight.setPositionOnBoard(screenWidth, screenHeight);

        // create tiles
        createTiles();

        // create snake
        Snake.Direction startDirection = Snake.Direction.WEST;
        snake.init(tiles[2][3], tiles, 3, startDirection);

        //TEST FOR PARSING
        /*
        int[] x = {4,4,4,3,3,2,1,0,0,0,1,2,2,3,4,4,5,5,5};
        int[] y = {5,4,3,3,2,2,2,2,1,0,0,0,1,1,1,2,2,3,4};
        DataTransferHandler.setBodypartXPos(x);
        DataTransferHandler.setBodypartYPos(y);
        DataTransferHandler.setHeadDirection(Snake.Direction.SOUTH);
        startDirection = Snake.Direction.SOUTH;
        snake.init(tiles);
        */

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

        // check whether current phone is active
        //checkControlling();

        // manage the snakes possibility to turn
        if(!snakeCanTurn) {
            snakeCanTurnCounter -= deltaTime;
        }
        if(snakeCanTurnCounter <= 0.0f) {
            snakeCanTurnCounter = TICK;
            snakeCanTurn = true;
        }

        // update stitching timer
        if(stitchingTimer > 0.0f) {
            stitchingTimer -= deltaTime;
        } else {
            stitchingTimer = 0.0f;
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

            // check whether snake has eaten something
            if(snake.hasEaten() && !snakeHasEatenLock) {
                snakeHasEatenLock = true;

                // increase score, spawn new gutti, increase speed and send new gutti message
                score += Constants.SCORE_INCREMENT.getValue();
                removeFood();
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

    // check whether this phone is the controlling phone by locating the snake's head
    public boolean checkControlling() {
        boolean isSnakeHeadVisible = checkVisible(snake.getHeadTile().getPosX(), snake.getHeadTile().getPosY());
        SnakeTreasureHuntGame.isControllingPhone = isSnakeHeadVisible;
        return isSnakeHeadVisible;
    }

    public void draw(Graphics g) {
        drawTiles(g);
        drawGameElements(g);
        snake.drawSnake(g, -topLeft.getPosX(), -topLeft.getPosY());

        // if food is not visible (on the screen), then draw a halo for orientation help
        if(!isFoodVisible()) {
            drawHalo(g);
        }

        drawMargin(g);
    }

    public void createGameElements() {
        // create obstacle
        gameElements.add(new Obstacle(tiles[4][9], tiles, GameElementType.RECT_OBSTACLE, 4));

        // spawn food
        spawnFood();
    }

    // removes the food from our game elements list
    public void removeFood() {
        for(GameElement gameElement : gameElements) {
            if(gameElement.getType() == GameElementType.FOOD) {
                gameElements.remove(gameElement);
                break;
            }
        }
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
        if(topLeftX + cols > tiles.length) {
            cols += (tiles.length - topLeftX - screenWidth);
        }
        if(topLeftY + rows > tiles[0].length) {
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

    private void drawMargin(Graphics g) {
        int x = screenWidth * Constants.TILE_WIDTH.getValue();
        int y = screenHeight * Constants.TILE_HEIGHT.getValue();
        g.drawRect(x, 0, x + marginRight, AndroidGame.getScreenHeight(), Color.BLACK);
        g.drawRect(0, y, AndroidGame.getScreenWidth(), y + marginBottom, Color.BLACK);
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
                // add margin to radius to keep the halo visible
                radius += Math.max(marginRight, marginBottom);
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
            // add margin to radius to keep the halo visible
            radius += Math.max(marginRight, marginBottom);
        } else {
            // food is above the screen
            if(foodY < topLeft.getPosY()) {
                radius = (topLeft.getPosY() - foodY) * Constants.TILE_HEIGHT.getValue();

            // food is below the screen
            } else if(foodY > bottomRight.getPosY()) {
                radius = (foodY - bottomRight.getPosY()) * Constants.TILE_HEIGHT.getValue();
                // add margin to radius to keep the halo visible
                radius += Math.max(marginRight, marginBottom);
            }
        }

        radius += Constants.TILE_HEIGHT.getValue() / 2;
        g.drawHalo(centerX, centerY, radius, 10, Color.YELLOW);
    }

    boolean isGamePaused() {
        return gamePaused;
    }

    boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public void setNextSnakeDirection(Snake.Direction nextSnakeDirection) {
        if(snakeCanTurn) {
            snakeCanTurn = false;
            this.nextSnakeDirection = nextSnakeDirection;
        }
    }

    private boolean isFoodVisible() {
        return checkVisible(foodX, foodY);
    }

    // checks whether x and y are visible on the current phone screen
    private boolean checkVisible(int x, int y) {
        return x >= topLeft.getPosX() && x < bottomRight.getPosX() && y >= topLeft.getPosY() && y < bottomRight.getPosY();
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) { this.snake = snake; }

    public void setStitchingDirection(Snake.Direction stitchingDirection) {
        // 1=NORTH, 2=EAST, 3=SOUTH, 4=WEST
        this.stitchingDirection = stitchingDirection;
    }

    public void checkStitching(Snake.Direction stitchInDirection) {
        // check if stitching was complete within the stitching threshold
        if(stitchingTimer <= 0.0f) {
            return;
        }

        stitchingDirection = Snake.Direction.values()[DataTransferHandler.getStitchingDirection()];
        // check if the stitch was in the right direction
        if(stitchingDirection == stitchInDirection) {
            // STITCH !
            int topLeftXPos = DataTransferHandler.getTopLeftXPos();
            int topLeftYPos = DataTransferHandler.getTopLeftYPos();

            // set top left and bottom right tile
            topLeft.setPositionOnBoard(topLeftXPos, topLeftYPos);
            bottomRight.setPositionOnBoard(topLeftXPos + screenWidth, topLeftYPos + screenHeight);

            // phone is now active!
            SnakeTreasureHuntGame.isPhoneActive = true;
        }
    }

    public void setMessageHandler(MessageHandler mHandler) {
        this.mHandler = mHandler;
    }


    // ================================
    // === IN-GAME MESSAGE HANDLING ===
    // ================================

    // check whether a message was received and handle it if so
    public void handleMessages() {
        if(DataTransferHandler.hasReceivedMessage()) {
            // unset the flag that we received a message
            DataTransferHandler.setReceivedMessage(false);

            switch (DataTransferHandler.getMessageType()) {
                case STHMessage.GAMESTART_MESSAGE: handleGameStartMessage(); break;
                case STHMessage.STITCHING_MESSAGE: handleStitchOutMessage(); break;
                case STHMessage.NEWGUTTI_MESSAGE: handleNewGuttiMessage(); break;
                case STHMessage.GAMEPAUSE_START_MESSAGE: handlePauseMessage(); break;
                case STHMessage.GAMEPAUSE_STOP_MESSAGE: handleResumeMessage(); break;
                case STHMessage.GAMEOVER_MESSAGE: handleGameOverMessage(); break;
            }
        }
    }

    // stores required data for game start message and sends game start message
    public void sendGameStartMessage() {
        DataTransferHandler.setFieldWidth(boardWidth);
        DataTransferHandler.setFieldHeight(boardHeight);

        int numOfOccupiedFields = gameElements.size();
        for (GameElement gameElement : gameElements) {
            if (gameElement instanceof BigGameElement) {
                numOfOccupiedFields += ((BigGameElement) gameElement).getGameElements().size();
            }
        }

        int tileXPos[] = new int[numOfOccupiedFields];
        int tileYPos[] = new int[numOfOccupiedFields];
        int tileType[] = new int[numOfOccupiedFields];

        // Add all game elements as well as their inner game elements to the lists
        int i = -1;
        for (GameElement gameElement : gameElements) {
            i++;
            tileXPos[i] = gameElement.getTile().getPosX();
            tileYPos[i] = gameElement.getTile().getPosY();
            tileType[i] = gameElement.getTile().getGameElementType().ordinal();

            if (gameElement instanceof BigGameElement) {
                for(GameElement innerGameElement : (((BigGameElement) gameElement).getGameElements())) {
                    i++;
                    tileXPos[i] = innerGameElement.getTile().getPosX();
                    tileYPos[i] = innerGameElement.getTile().getPosY();
                    tileType[i] = innerGameElement.getTile().getGameElementType().ordinal();
                }
            }
        }

        DataTransferHandler.setNumOfOccupiedTiles(numOfOccupiedFields);
        DataTransferHandler.setTileXPos(tileXPos);
        DataTransferHandler.setTileYPos(tileYPos);
        DataTransferHandler.setTileType(tileType);

        // send message over MessageHandler
        mHandler.sendInitGame();
    }

    // defines what to do if we receive a game start message
    public void handleGameStartMessage() {
        boardWidth = DataTransferHandler.getFieldWidth();
        boardHeight = DataTransferHandler.getFieldHeight();

        createTiles();

        int numOfOccupiedTiles = DataTransferHandler.getNumOfOccupiedTiles();
        int tileXPos[] = DataTransferHandler.getTileXPos();
        int tileYPos[] = DataTransferHandler.getTileYPos();
        int tileType[] = DataTransferHandler.getTileType();

        gameElements.clear();
        for(int i = 0; i < numOfOccupiedTiles; i++) {
            if(tileType[i] == GameElementType.RECT_OBSTACLE.ordinal()) {
                gameElements.add(new Obstacle(tiles[tileXPos[i]][tileYPos[i]], tiles, GameElementType.RECT_OBSTACLE, 0));
            } else if(tileType[i] == GameElementType.FOOD.ordinal()) {
                gameElements.add(createGameElement(GameElementType.FOOD, tiles[tileXPos[i]][tileYPos[i]]));
            }
        }
    }

    public void test() {
        snake.parseToDataTransferHandler();
    }

    public void sendStitchOutMessage() {
        // if the current phone is not active, no stitching is possible
        if(!SnakeTreasureHuntGame.isPhoneActive) {
            return;
        }

        DataTransferHandler.setTickTime(tickTime);
        DataTransferHandler.setTimestamp(System.nanoTime() / 1000000000.0f);
        DataTransferHandler.setStitchingDirection(stitchingDirection.ordinal());

        int topLeftXPos = topLeft.getPosX();
        int topLeftYPos = topLeft.getPosY();
        switch(stitchingDirection) {
            case NORTH: topLeftYPos -= screenHeight; break;
            case EAST: topLeftXPos += screenWidth; break;
            case SOUTH: topLeftYPos += screenHeight; break;
            case WEST: topLeftXPos -= screenWidth; break;
        }
        DataTransferHandler.setTopLeftXPos(topLeftXPos);
        DataTransferHandler.setTopLeftYPos(topLeftYPos);

        // serialize snake
        snake.parseToDataTransferHandler();

        DataTransferHandler.setReceivedMessage(true);
        DataTransferHandler.setMessageType(STHMessage.STITCHING_MESSAGE);

        // send message over MessageHandler
        mHandler.sendStitching();
    }

    public void handleStitchOutMessage() {
        // if phone is already active, ignore the message
        /*if(SnakeTreasureHuntGame.isPhoneActive) {
            return;
        }*/

        float tickTime = DataTransferHandler.getTickTime();
        float timestamp = DataTransferHandler.getTimestamp();

        float currentTime = System.nanoTime() / 1000000000.0f;
        float timeDiff = currentTime - timestamp;
        this.tickTime = tickTime + timeDiff;

        // start stitching counter
        stitchingTimer = STITCHING_THRESHOLD;

        // parse snake
        snake.init(tiles);
        nextSnakeDirection = DataTransferHandler.getHeadDirection();
    }

    public void sendNewGuttiMessage() {
        DataTransferHandler.setFoodXPos(foodX);
        DataTransferHandler.setFoodYPos(foodY);

        // send message over MessageHandler
        mHandler.sendStitching();
    }

    public void handleNewGuttiMessage() {
        foodX = DataTransferHandler.getFoodXPos();
        foodY = DataTransferHandler.getFoodYPos();

        // remove old food and create new one
        removeFood();
        gameElements.add(createGameElement(GameElementType.FOOD, tiles[foodX][foodY]));
    }

    public void sendPauseMessage() {
        // send message over MessageHandler
        mHandler.sendPaused();
    }

    public void handlePauseMessage() {
        gamePaused = true;
    }

    public void sendResumeMessage() {
        DataTransferHandler.setTickTime(tickTime);

        // send message over MessageHandler
        mHandler.sendResumed();
    }

    public void handleResumeMessage() {
        tickTime = DataTransferHandler.getTickTime();
        gamePaused = false;
    }

    public void sendGameOverMessage() {
        DataTransferHandler.setScore(score);

        // send message over MessageHandler
        mHandler.sendGameOver();
    }

    public void handleGameOverMessage() {
        score = DataTransferHandler.getScore();
        gameOver = true;
    }
}
