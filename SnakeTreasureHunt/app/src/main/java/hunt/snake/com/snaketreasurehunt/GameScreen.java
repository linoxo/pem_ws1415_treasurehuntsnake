package hunt.snake.com.snaketreasurehunt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Looper;

import java.util.List;

import hunt.snake.com.framework.Game;
import hunt.snake.com.framework.Graphics;
import hunt.snake.com.framework.Input.TouchEvent;
import hunt.snake.com.framework.Screen;
import hunt.snake.com.framework.impl.AndroidGame;
import hunt.snake.com.snaketreasurehunt.communication.MessageHandler;
import hunt.snake.com.snaketreasurehunt.wifi.Client;
import hunt.snake.com.snaketreasurehunt.wifi.ClientService;

public class GameScreen extends Screen {
    enum GameState {
        READY,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private static final String READY_TEXT = "Ready?";
    private static final String PAUSED_TEXT = "Paused";
    private static final String GAME_OVER_TEXT = "Game Over";
    private static final String SCORE_TEXT = "Score: ";
    private static final String FOG_OF_WAR_TEXT = "~ Fog of War ~";
    private static final float COUNT_DOWN_TIME = 2.99f;
    private static final float ACCEL_TIME = 0.5f;
    private static final float ACCEL_THRESHOLD = 1.0f;

    GameState state;
    GameBoard gameBoard;
    int oldScore;
    String score;
    RectF scoreBoard;
    boolean isCountingDown;
    float timer;
    float accelTimer;
    boolean isPhoneLifted;
    String accelX = "";
    String accelY = "";
    String accelZ = "";
    float accel[] = new float[3];
    float accelDiff[] = new float[3];

    private ClientService client;
    private MessageHandler mHandler;

    public GameScreen(Game game) {
        super(game);
    }

    public void init() {
        gameBoard = new GameBoard();
        gameBoard.setMessageHandler(mHandler);
        mHandler.setGameBoard(gameBoard);
        gameBoard.init();

        state = GameState.READY;

        oldScore = 0;
        score = SCORE_TEXT + oldScore;
        scoreBoard = new RectF(25, 25, 290, 125);
        isCountingDown = false;
        timer = COUNT_DOWN_TIME;
        accelTimer = ACCEL_TIME;
        isPhoneLifted = true;
    }

    public void setClient(ClientService client) {
        this.client = client;
        mHandler = client.getMessageHandler();
    }

    public void shareGame() {
        mHandler.sendInitGame();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        // if our phone is not controlling, we have nothing to do!
        if(!SnakeTreasureHuntGame.isControllingPhone) {
            return;
        }

        if(state == GameState.READY) {
            updateReady(touchEvents, deltaTime);
            return;
        }
        if(state == GameState.RUNNING) {
            updateRunning(touchEvents, deltaTime);
            return;
        }
        if(state == GameState.PAUSED) {
            updatePaused(touchEvents, deltaTime);
            return;
        }
        if(state == GameState.GAME_OVER) {
            updateGameOver(touchEvents);
        }
    }

    private void updateReady(List<TouchEvent> touchEvents, float deltaTime) {
        if(isCountingDown) {
            // IF THE "PLACE PHONE ON GROUND" FEATURE SHALL BE DISABLED,
            // COMMENT THE FOLLOWING IF CLAUSE OUT
            /*if(!isPhonePlacedOnGround(deltaTime)) {
                timer = COUNT_DOWN_TIME;
                isCountingDown = false;
                return;
            }*/
            timer -= deltaTime;
            if(timer <= 0.0f) {
                // change from "ready" to "running" if screen was touched
                state = GameState.RUNNING;
            }
        } else {
            // IF THE "PLACE PHONE ON GROUND" FEATURE SHALL BE DISABLED,
            // COMMENT THE FOLLOWING IF CLAUSE OUT AND UNCOMMENT THE ONE BELOW
            // start countdown when phone is placed on ground
            /*if(isPhonePlacedOnGround(deltaTime)) {
                isCountingDown = true;
            }*/
            // start countdown when screen is touched
            if (touchEvents.size() > 0) {
                isCountingDown = true;
            }
        }
    }

    private boolean isPhonePlacedOnGround(float deltaTime) {
        if(isPhoneLifted) {
            accelTimer -= deltaTime;
            if(Math.abs(game.getInput().getAccelX()) > ACCEL_THRESHOLD || Math.abs(game.getInput().getAccelY()) > ACCEL_THRESHOLD) {
                accelTimer = ACCEL_TIME;
            }
            if(accelTimer < 0.0f) {
                isPhoneLifted = false;
            }
        } else {
            if(Math.abs(game.getInput().getAccelX()) > ACCEL_THRESHOLD || Math.abs(game.getInput().getAccelY()) > ACCEL_THRESHOLD) {
                accelTimer = ACCEL_TIME;
                isPhoneLifted = true;
            }
        }

        return !isPhoneLifted;
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int size = touchEvents.size();
        for(int i = 0; i < size; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                System.out.println("Touch at x=" + event.x + "/" + AndroidGame.getScreenWidth() + "and y=" + event.y + "/" + AndroidGame.getScreenHeight());
                if(gameBoard.nextSnakeDirection == Snake.Direction.NORTH || gameBoard.nextSnakeDirection == Snake.Direction.SOUTH) {
                    if(event.x > AndroidGame.getScreenWidth() / 2) {
                        gameBoard.setNextSnakeDirection(Snake.Direction.EAST);
                        System.out.println("Going EAST");
                    } else {
                        gameBoard.setNextSnakeDirection(Snake.Direction.WEST);
                        System.out.println("Going WEST");
                    }
                } else {
                    if(event.y > AndroidGame.getScreenHeight() / 2) {
                        gameBoard.setNextSnakeDirection(Snake.Direction.SOUTH);
                        System.out.println("Going SOUTH");
                        client.sendMessage("GOING SOUTH");
                    } else {
                        gameBoard.setNextSnakeDirection(Snake.Direction.NORTH);
                        System.out.println("Going NORTH");
                    }
                }
            }
        }

        // IF THE "PLACE PHONE ON GROUND" FEATURE SHALL BE DISABLED,
        // COMMENT THE FOLLOWING IF CLAUSE OUT
        // change from "running" to "paused" if phone was lifted (controlling phone)
        // change from active to non-active (fog of war) if phone was lifted (active phone)
        /*if(!isPhonePlacedOnGround(deltaTime)) {
            SnakeTreasureHuntGame.isPhonePlacedOnGround = false;

            if(SnakeTreasureHuntGame.isControllingPhone) {
                state = GameState.PAUSED;
                // TODO: send message that game is paused
            } else if(SnakeTreasureHuntGame.isPhoneActive) {
                SnakeTreasureHuntGame.isPhoneActive = false;
            }
            return;
        } else {
            SnakeTreasureHuntGame.isPhonePlacedOnGround = true;
        }*/

        // update the game board and check whether the game is over
        gameBoard.update(deltaTime);
        if(gameBoard.isGameOver()) {
            state = GameState.GAME_OVER;
        }

        // only update score if it has changed --> avoid unnecessary String object creation
        if(oldScore != gameBoard.getScore()) {
            oldScore = gameBoard.getScore();
            score = SCORE_TEXT + oldScore;
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents, float deltaTime) {
        int size = touchEvents.size();
        for(int i = 0; i < size; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                // change from "paused" to "running" if screen was touched
                // state = GameState.RUNNING;
                // return;
            }
        }

        // IF THE "PLACE PHONE ON GROUND" FEATURE SHALL BE DISABLED,
        // COMMENT THE FOLLOWING IF CLAUSE OUT
        // change from "paused" to "running" if phone is placed on ground
        /*if(isPhonePlacedOnGround(deltaTime)) {
            state = GameState.RUNNING;
        }*/
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int size = touchEvents.size();
        for(int i = 0; i < size; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                // change from "game over" to "ready" if screen was touched
                gameBoard.init();
                init();
                return;
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        // if our phone is not active, we just display a "fog of war"
        if(!SnakeTreasureHuntGame.isPhoneActive) {
            drawFogOfWar(g);
            return;
        }

        // clear the background
        g.clear(Color.BLACK);

        // draw the game board
        drawGameBoard(g);

        if(state == GameState.READY) {
            drawReadyUI(g);
        }
        if(state == GameState.RUNNING) {
            drawRunningUI(g, deltaTime);
        }
        if(state == GameState.PAUSED) {
            drawPausedUI(g);
        }
        if(state == GameState.GAME_OVER) {
            drawGameOverUI(g);
        }
    }

    private void drawFogOfWar(Graphics g) {
        // grey background
        g.clear(Color.DKGRAY);

        // "fog of war" text
        g.drawText(FOG_OF_WAR_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
    }

    private void drawGameBoard(Graphics g) {
        gameBoard.draw(g);
    }

    private void drawReadyUI(Graphics g) {
        // darken screen with transparent layer
        g.drawRect(0, 0, AndroidGame.getScreenWidth(), AndroidGame.getScreenHeight(), Constants.LAYER_COLOR.getValue());

        if(isCountingDown) {
            // draw countdown
            String countDownText = String.valueOf((int)timer + 1);
            g.drawText(countDownText, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
        } else {
            // draw ready text
            g.drawText(READY_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
        }
    }

    private void drawRunningUI(Graphics g, float deltaTime) {
        // draw score
        g.drawRoundRect(scoreBoard, 25, 25, 0x99000000);
        g.drawText(score, 50, 95, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_M.getValue(), Paint.Align.LEFT);

        // draw accelerometer values
        /*timer -= deltaTime;
        if(timer < 0.0f) {
            accelDiff[0] = Math.abs(accel[0] - game.getInput().getAccelX());
            accelDiff[1] = Math.abs(accel[1] - game.getInput().getAccelY());
            accelDiff[2] = Math.abs(accel[2] - game.getInput().getAccelZ());
            accel[0] = game.getInput().getAccelX();
            accel[1] = game.getInput().getAccelY();
            accel[2] = game.getInput().getAccelZ();
            accelX = "x = " + accel[0];
            accelY = "y = " + accel[1];
            accelZ = "z = " + accel[2];
            timer = 0.5f;
        }
        g.drawText(accelX, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() - 55, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XS.getValue() * 2, Paint.Align.LEFT);
        g.drawText("" + accelDiff[0], AndroidGame.getScreenWidth() * 3 / 4, AndroidGame.getScreenHeight() - 55, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XS.getValue() * 2, Paint.Align.LEFT);
        g.drawText(accelY, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() - 35, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XS.getValue() * 2, Paint.Align.LEFT);
        g.drawText("" + accelDiff[1], AndroidGame.getScreenWidth() * 3 / 4, AndroidGame.getScreenHeight() - 35, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XS.getValue() * 2, Paint.Align.LEFT);
        g.drawText(accelZ, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() - 15, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XS.getValue() * 2, Paint.Align.LEFT);
        g.drawText("" + accelDiff[2], AndroidGame.getScreenWidth() * 3 / 4, AndroidGame.getScreenHeight() - 15, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XS.getValue() * 2, Paint.Align.LEFT);*/
    }

    private void drawPausedUI(Graphics g) {
        // darken screen with transparent layer
        g.drawRect(0, 0, AndroidGame.getScreenWidth(), AndroidGame.getScreenHeight(), Constants.LAYER_COLOR.getValue());

        // draw paused text
        g.drawText(PAUSED_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
    }

    private void drawGameOverUI(Graphics g) {
        // darken screen with transparent layer
        g.drawRect(0, 0, AndroidGame.getScreenWidth(), AndroidGame.getScreenHeight(), Constants.LAYER_COLOR.getValue());

        // draw game over text
        g.drawText(GAME_OVER_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
    }


    public GameBoard getGameBoard(){
        return gameBoard;
    }
    @Override
    public void pause() {
        // pause game if app is closed and continues operating in background
        if(state == GameState.RUNNING) {
            state = GameState.PAUSED;
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}