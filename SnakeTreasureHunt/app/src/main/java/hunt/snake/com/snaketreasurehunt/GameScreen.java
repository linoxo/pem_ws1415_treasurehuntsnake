package hunt.snake.com.snaketreasurehunt;

import android.graphics.Paint;

import java.util.List;

import hunt.snake.com.framework.Game;
import hunt.snake.com.framework.Graphics;
import hunt.snake.com.framework.Input.TouchEvent;
import hunt.snake.com.framework.Screen;
import hunt.snake.com.framework.impl.AndroidGame;

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
    private static final float COUNT_DOWN_TIME = 3.0f;

    GameState state;
    GameBoard gameBoard;
    int oldScore;
    String score;
    boolean isCountingDown;
    float countDownTimer;

    public GameScreen(Game game) {
        super(game);
        gameBoard = new GameBoard();
        init();
    }

    private void init() {
        state = GameState.READY;
        gameBoard.init();
        oldScore = 0;
        score = SCORE_TEXT + oldScore;
        isCountingDown = false;
        countDownTimer = COUNT_DOWN_TIME;
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(state == GameState.READY) {
            updateReady(touchEvents, deltaTime);
            return;
        }
        if(state == GameState.RUNNING) {
            updateRunning(touchEvents, deltaTime);
            return;
        }
        if(state == GameState.PAUSED) {
            updatePaused(touchEvents);
            return;
        }
        if(state == GameState.GAME_OVER) {
            updateGameOver(touchEvents);
        }
    }

    private void updateReady(List<TouchEvent> touchEvents, float deltaTime) {
        if(isCountingDown) {
            countDownTimer -= deltaTime;
            if(countDownTimer <= 0.0f) {
                // change from "ready" to "running" if screen was touched
                state = GameState.RUNNING;
            }
        } else {
            if (touchEvents.size() > 0) {
                isCountingDown = true;
            }
        }
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
                    } else {
                        gameBoard.setNextSnakeDirection(Snake.Direction.NORTH);
                        System.out.println("Going NORTH");
                    }
                }
                // change from "running" to "paused" if screen was touched
                // state = GameState.PAUSED;
                // return;
            }
        }

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

    private void updatePaused(List<TouchEvent> touchEvents) {
        int size = touchEvents.size();
        for(int i = 0; i < size; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                // change from "paused" to "running" if screen was touched
                state = GameState.RUNNING;
                return;
            }
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
                return;
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        drawGameBoard(g);

        if(state == GameState.READY) {
            drawReadyUI(g);
        }
        if(state == GameState.RUNNING) {
            drawRunningUI(g);
        }
        if(state == GameState.PAUSED) {
            drawPausedUI(g);
        }
        if(state == GameState.GAME_OVER) {
            drawGameOverUI(g);
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
            String countDownText = String.valueOf((int)countDownTimer + 1);
            g.drawText(countDownText, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
        } else {
            // draw ready text
            g.drawText(READY_TEXT, AndroidGame.getScreenWidth() / 2, AndroidGame.getScreenHeight() / 2, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_XXL.getValue(), Paint.Align.CENTER);
        }
    }

    private void drawRunningUI(Graphics g) {
        // draw score
        g.drawText(score, 50, AndroidGame.getScreenHeight() - 25, Constants.TEXT_COLOR.getValue(), Constants.TEXT_SIZE_S.getValue(), Paint.Align.LEFT);
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