package hunt.snake.com.snaketreasurehunt.stitching;

import hunt.snake.com.snaketreasurehunt.gamelogic.GameBoard;
import hunt.snake.com.snaketreasurehunt.gamelogic.GameScreen;
import hunt.snake.com.snaketreasurehunt.gamelogic.Snake;


public class SnakeGestureMethods {

    Stitching stitch;
    GameBoard board;

	//constructor
	public SnakeGestureMethods() {

	}

    public void swipeType(int direction, GameScreen screen){
        this.board = screen.getGameBoard();
        stitch = new Stitching(board);
        switch (direction) {

            case SnakeGestureListener.SWIPE_RIGHT:
                swipeRight();
                break;
            case SnakeGestureListener.SWIPE_LEFT:
                swipeLeft();
                break;
            case SnakeGestureListener.SWIPE_DOWN:
                swipeDown();
                break;
            case SnakeGestureListener.SWIPE_UP:
                swipeUp();
                break;
            case SnakeGestureListener.SWIPEIN_LEFT:
               swipeInLeft();
                board.checkStitching(Snake.Direction.EAST);
                break;
            case SnakeGestureListener.SWIPEIN_RIGHT:
                swipeInRight();
                board.checkStitching(Snake.Direction.WEST);
                break;
            case SnakeGestureListener.SWIPEOUT_LEFT:
                swipeOutLeft();
                board.setStitchingDirection(Snake.Direction.WEST);
                stitch.enableStitching();
                break;
            case SnakeGestureListener.SWIPEIN_TOP:
                swipeInTop();
                board.checkStitching(Snake.Direction.SOUTH);
                break;
            case SnakeGestureListener.SWIPEOUT_TOP:
                swipeOutTop();
                board.setStitchingDirection(Snake.Direction.NORTH);
                stitch.enableStitching();
                break;
            case SnakeGestureListener.SWIPEOUT_BOTTOM:
                swipeOutBottom();
                board.setStitchingDirection(Snake.Direction.SOUTH);
                stitch.enableStitching();
                break;
            case SnakeGestureListener.SWIPEIN_BOTTOM:
                swipeInBottom();
                board.checkStitching(Snake.Direction.NORTH);
                break;
            case SnakeGestureListener.SWIPEOUT_RIGHT:
                swipeOutRight();
                board.setStitchingDirection(Snake.Direction.EAST);
                stitch.enableStitching();
                break;

        }
    }
	
	//customary direction gestures
	public void swipeUp() {
		// up swipe
	}

	public void swipeDown ( ) {
		// down swipe
	}
	
	public void swipeLeft() {
		// left swipe
	}

	public void swipeRight() {
        // right swipe
	}

	
	// stitching gestures
	public void swipeOutTop() {
		// gesture on active device that induces stitching
		// saves game tile of last touch position to global variable
	}

	public void swipeOutBottom() {
		// gesture on active device that induces stitching
		// saves game tile of last touch position to global variable
	}

	public void swipeInBottom() {
		// device gets stitched and is on the right side of the previously
		// active
		// one
		// global game tile variable needs to get called

	}

	public void swipeInTop() {
		// device gets stitched and is on the left side of the previously active
		// one
		// global game tile variable needs to get called

	}
	
	public void swipeOutLeft() {
		// gesture on active device that induces stitching
		// saves game tile of last touch position to global variable
	}

	public void swipeOutRight() {
		// gesture on active device that induces stitching
		// saves game tile of last touch position to global variable
	}

	public void swipeInLeft() {
		// device gets stitched and is on the right side of the previously
		// active
		// one
		// global game tile variable needs to get called
	}

	public void swipeInRight() {
		// device gets stitched and is on the left side of the previously active
		// one
		// global game tile variable needs to get called
	}

}
