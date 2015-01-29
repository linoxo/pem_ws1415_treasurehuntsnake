package hunt.snake.com.snaketreasurehunt;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Looper;

import java.util.List;

import hunt.snake.com.framework.Game;
import hunt.snake.com.framework.Graphics;
import hunt.snake.com.framework.Input.TouchEvent;
import hunt.snake.com.framework.Screen;
import hunt.snake.com.framework.impl.AndroidGame;
import hunt.snake.com.snaketreasurehunt.communication.MessageHandler;
import hunt.snake.com.snaketreasurehunt.wifi.ClientService;

public class GameScreen extends Screen {
    enum GameState {
        READY,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private static final String READY_TEXT = "Ready?";
    private static final String READY_INSTRUCTION_TEXT = "Put phone down to start!";
    private static final String READY_CLIENT_TEXT = "Waiting...";
    private static final String PAUSED_TEXT = "Paused";
    private static final String PAUSED_INSTRUCTION_TEXT = "Put phone down to resume!";
    private static final String GAME_OVER_TEXT = "Game Over";
    private static final String GAME_OVER_INSTRUCTION_TEXT = "Tap to start a new game!";
    private static final String SCORE_TEXT = "Score: ";
    private static final String FOG_OF_WAR_TEXT = "Stitch to join!";
    private static final float COUNT_DOWN_TIME = 4.99f;
    private static final float ACCEL_TIME = 0.5f;
    private static final float ACCEL_THRESHOLD = 2.0f;
    private static final int DPadSize = 100;

    GameState state;
    GameBoard gameBoard;
    int oldScore;
    String score;
    RectF scoreBoard;
    boolean isCountingDown;
    float timer;
    float accelTimer;
    boolean isPhoneLifted;
    String inactivePhoneText;
    RectF DPad[] = new RectF[4];

    private ClientService client;
    private MessageHandler mHandler;

    public GameScreen(Game game) {
        super(game);
    }

    public void init() {
        gameBoard = new GameBoard();
        gameBoard.setMessageHandler(mHandler);
        //mHandler.setGameBoard(gameBoard);
        gameBoard.init();

        state = GameState.READY;

        // init d-pad
        int dPadNorthLeft = AndroidGame.getScreenWidth() - DPadSize * 5 / 2;
        int dPadNorthTop = AndroidGame.getScreenHeight() - DPadSize * 7 / 2;
        int dPadNorthRight = dPadNorthLeft + DPadSize;
        int dPadNorthBottom = dPadNorthTop + DPadSize;
        DPad[Snake.Direction.NORTH.ordinal()] = new RectF(dPadNorthLeft, dPadNorthTop, dPadNorthRight, dPadNorthBottom);
        DPad[Snake.Direction.EAST.ordinal()] = new RectF(dPadNorthRight, dPadNorthBottom, dPadNorthRight + DPadSize, dPadNorthBottom + DPadSize);
        DPad[Snake.Direction.SOUTH.ordinal()] = new RectF(dPadNorthLeft, dPadNorthBottom + DPadSize, dPadNorthRight, dPadNorthBottom + 2 * DPadSize);
        DPad[Snake.Direction.WEST.ordinal()] = new RectF(dPadNorthLeft - DPadSize, dPadNorthBottom, dPadNorthLeft, dPadNorthBottom + DPadSize);

        oldScore = 0;
        score = SCORE_TEXT + oldScore;
        scoreBoard = new RectF(25, 25, 290, 125);
        isCountingDown = false;
        timer = COUNT_DOWN_TIME;
        accelTimer = ACCEL_TIME;
        isPhoneLifted = true;
        inactivePhoneText = READY_CLIENT_TEXT;
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

        // wait until we are connected to the client
        if(!SnakeTreasureHuntGame.isConnectedToClient) {
            return;
        }

        // handle incoming messages
        if(gameBoard != null)
            gameBoard.handleMessages();

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
        if(state == GameState.GAME_OVER && SnakeTreasureHuntGame.isControllingPhone) {
            updateGameOver(touchEvents);
        }
    }

    private void updateReady(List<TouchEvent> touchEvents, float deltaTime) {
        // check whether the game is running (non-controlling phone)
        if(!SnakeTreasureHuntGame.isControllingPhone && gameBoard.isGameRunning()) {
            state = GameState.RUNNING;

        } else if (SnakeTreasureHuntGame.isControllingPhone) {
            if (isCountingDown) {
                if (!isPhonePlacedOnGround(deltaTime)) {
                    timer = COUNT_DOWN_TIME;
                    isCountingDown = false;
                    return;
                }
                timer -= deltaTime;
                if (timer <= 0.0f) {
                    // change from "ready" to "running" if screen was touched
                    state = GameState.RUNNING;
                    // signalize to other phones that game is running
                    gameBoard.sendGameRunningMessage();
                }
            } else {
                // start countdown when phone is placed on ground
                if (isPhonePlacedOnGround(deltaTime)) {
                    isCountingDown = true;
                }
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
        if(SnakeTreasureHuntGame.isControllingPhone) {
            int size = touchEvents.size();
            for (int i = 0; i < size; i++) {
                TouchEvent event = touchEvents.get(i);
                if (event.type == TouchEvent.TOUCH_UP) {
                    System.out.println("Touch at x=" + event.x + "/" + AndroidGame.getScreenWidth() + "and y=" + event.y + "/" + AndroidGame.getScreenHeight());
                    for(int j = 0; j < Snake.Direction.values().length; j++) {
                        if(DPad[j].contains(event.x, event.y)) {
                            gameBoard.setNextSnakeDirection(Snake.Direction.values()[j]);
                        }
                    }
                }
            }
        }

        // change from "running" to "paused" if phone was lifted (controlling phone)
        // change from active to non-active if phone was lifted (active phone)
        if(!isPhonePlacedOnGround(deltaTime)) {
            SnakeTreasureHuntGame.isPhonePlacedOnGround = false;

            if(SnakeTreasureHuntGame.isControllingPhone) {
                state = GameState.PAUSED;
                gameBoard.sendPauseMessage();
            } else if(SnakeTreasureHuntGame.isPhoneActive) {
                SnakeTreasureHuntGame.isPhoneActive = false;
            }
            return;
        } else {
            SnakeTreasureHuntGame.isPhonePlacedOnGround = true;
        }

        // update the game board if it is active and check whether the game is over
        if(gameBoard.isGamePaused()) {
            state = GameState.PAUSED;
            return;
        }
        if(SnakeTreasureHuntGame.isPhoneActive || gameBoard.hasReceivedStitchoutMessage) {
            gameBoard.update(deltaTime);
        }
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
        // change from "paused" to "running" if phone is placed on ground (controlling phone)
        if(SnakeTreasureHuntGame.isControllingPhone && isPhonePlacedOnGround(deltaTime)) {
            SnakeTreasureHuntGame.isPhonePlacedOnGround = true;
            state = GameState.RUNNING;
            gameBoard.sendResumeMessage();
        }
        // change from "paused" to "running" if we received the according message (not-controlling phone)
        if(!SnakeTreasureHuntGame.isControllingPhone && !gameBoard.isGamePaused()) {
            state = GameState.RUNNING;
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int size = touchEvents.size();
        for(int i = 0; i < size; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                // change from "game over" to "ready" if screen was touched
                gameBoard.init();
                init();
                gameBoard.sendGameStartMessage();
                return;
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        // wait until we are connected to the client
        if(!SnakeTreasureHuntGame.isConnectedToClient) {
            return;
        }

        // if our phone is not active, we draw a special UI
        if(!SnakeTreasureHuntGame.isPhoneActive) {
            drawInactivePhoneUI(g);
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

    private void drawInactivePhoneUI(Graphics g) {
        // grey background
        g.clear(Color.DKGRAY);

        // draw text of inactive phone depending on the game state
        if(state == GameState.READY) {
            inactivePhoneText = READY_CLIENT_TEXT;
        } else if (state == GameState.RUNNING) {
            inactivePhoneText = FOG_OF_WAR_TEXT;
        } else if (state == GameState.PAUSED) {
            inactivePhoneText = PAUSED_TEXT;
        } else if (state == GameState.GAME_OVER) {
            inactivePhoneText = GAME_OVER_TEXT;
        }

        g.drawText(inactivePhoneText, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XL.getValue(), Paint.Align.CENTER);

        // if the game is over, also draw the score
        if(state == GameState.GAME_OVER) {
            g.drawText(score, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() * 2 / 3, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XL.getValue(), Paint.Align.CENTER);
        }
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
            g.drawText(READY_INSTRUCTION_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() * 2 / 3, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_M.getValue(), Paint.Align.CENTER);
        }
    }

    private void drawRunningUI(Graphics g, float deltaTime) {
        // draw score
        g.drawRoundRect(scoreBoard, 25, 25, 0x99000000);
        g.drawText(score, 50, 95, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_M.getValue(), Paint.Align.LEFT);

        if(SnakeTreasureHuntGame.isControllingPhone) {
            // draw d-pad
            for (int i = 0; i < Snake.Direction.values().length; i++) {
                g.drawRoundRect(DPad[i], 25, 25, 0x99FFFFFF);
            }
        }
    }

    private void drawPausedUI(Graphics g) {
        // darken screen with transparent layer
        g.drawRect(0, 0, AndroidGame.getScreenWidth(), AndroidGame.getScreenHeight(), Constants.LAYER_COLOR.getValue());

        // draw paused text
        g.drawText(PAUSED_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
        if(SnakeTreasureHuntGame.isControllingPhone) {
            g.drawText(PAUSED_INSTRUCTION_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() * 2 / 3, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_M.getValue(), Paint.Align.CENTER);
        }
    }

    private void drawGameOverUI(Graphics g) {
        // darken screen with transparent layer
        g.drawRect(0, 0, AndroidGame.getScreenWidth(), AndroidGame.getScreenHeight(), Constants.LAYER_COLOR.getValue());

        // draw game over text and score
        g.drawText(GAME_OVER_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 3, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
        g.drawText(score, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XL.getValue(), Paint.Align.CENTER);
        if(SnakeTreasureHuntGame.isControllingPhone) {
            g.drawText(GAME_OVER_INSTRUCTION_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() * 3 / 4, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_M.getValue(), Paint.Align.CENTER);
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public void pause() {
        // pause game if app is closed and continues operating in background
        if(state == GameState.RUNNING) {
            state = GameState.PAUSED;
            gameBoard.sendPauseMessage();
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}