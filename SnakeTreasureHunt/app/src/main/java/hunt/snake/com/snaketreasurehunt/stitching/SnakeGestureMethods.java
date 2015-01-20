package hunt.snake.com.snaketreasurehunt.stitching;


import android.util.Log;

import hunt.snake.com.snaketreasurehunt.GameScreen;


public class SnakeGestureMethods {

    Stitching stitch;

	//constructor
	public SnakeGestureMethods() {
        stitch = new Stitching();
	}

    public void swipeType(int direction){

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
                if (stitch.isStitchingEnabled()) {
                    stitch.completeStitching();
                }
                break;
            case SnakeGestureListener.SWIPEIN_RIGHT:
                swipeInRight();
                if (stitch.isStitchingEnabled()) {
                    stitch.completeStitching();
                }
                break;
            case SnakeGestureListener.SWIPEOUT_LEFT:
                swipeOutLeft();
                stitch.enableStitching();
                break;
            case SnakeGestureListener.SWIPEIN_TOP:
                swipeInTop();
                if (stitch.isStitchingEnabled()) {
                    stitch.completeStitching();
                }
                break;
            case SnakeGestureListener.SWIPEOUT_TOP:
                swipeOutTop();
                stitch.enableStitching();
                break;
            case SnakeGestureListener.SWIPEOUT_BOTTOM:
                swipeOutBottom();
                stitch.enableStitching();
                break;
            case SnakeGestureListener.SWIPEIN_BOTTOM:
                swipeInBottom();
                if (stitch.isStitchingEnabled()) {
                    stitch.completeStitching();
                }
                break;
            case SnakeGestureListener.SWIPEOUT_RIGHT:
                swipeOutRight();
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
        Log.d("SWIPE", "Right Swipe");

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
