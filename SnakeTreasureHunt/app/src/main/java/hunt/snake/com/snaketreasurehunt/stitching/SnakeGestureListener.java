package com.example.stitching;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.WindowManager;

public class SnakeGestureListener extends SimpleOnGestureListener {

	public final static int SWIPE_UP = 1;
	public final static int SWIPE_DOWN = 2;
	public final static int SWIPE_LEFT = 3;
	public final static int SWIPE_RIGHT = 4;
	public final static int SWIPEIN_RIGHT = 5;
	public final static int SWIPEOUT_RIGHT = 6;
	public final static int SWIPEIN_LEFT = 7;
	public final static int SWIPEOUT_LEFT = 8;
	public final static int SWIPEIN_TOP = 9;
	public final static int SWIPEOUT_TOP = 10;
	public final static int SWIPEIN_BOTTOM = 11;
	public final static int SWIPEOUT_BOTTOM = 12;

	public final static int MODE_TRANSPARENT = 0;
	public final static int MODE_SOLID = 1;
	public final static int MODE_DYNAMIC = 2;

	private final static int ACTION_FAKE = -13;
	private int swipe_Min_Distance = 200;
	private int swipe_Max_Distance = 2000;
	private int swipe_Min_Velocity = 10;

	private int width;
	private int height;

	private int rightEdge;
	private int leftEdge;
	private int topEdge;
	private int bottomEdge;

	private int mode = MODE_DYNAMIC;
	private boolean running = true;
	private boolean tapIndicator = false;

	private Activity context;
	private GestureDetector detector;
	private SimpleGestureListener listener;

	private String deviceDirection = "center";

	public SnakeGestureListener(Activity context, SimpleGestureListener sgl) {

		this.context = context;
		this.detector = new GestureDetector(context, this);
		this.listener = sgl;
	}

	public void onTouchEvent(MotionEvent event) {

		if (!this.running)
			return;

		boolean result = this.detector.onTouchEvent(event);

		if (this.mode == MODE_SOLID)
			event.setAction(MotionEvent.ACTION_CANCEL);
		else if (this.mode == MODE_DYNAMIC) {

			if (event.getAction() == ACTION_FAKE)
				event.setAction(MotionEvent.ACTION_UP);
			else if (result)
				event.setAction(MotionEvent.ACTION_CANCEL);
			else if (this.tapIndicator) {
				event.setAction(MotionEvent.ACTION_DOWN);
				this.tapIndicator = false;
			}

		}
		// else just do nothing, it's Transparent
	}

	public void setMode(int m) {
		this.mode = m;
	}

	public int getMode() {
		return this.mode;
	}

	public void setEnabled(boolean status) {
		this.running = status;
	}

	public void setSwipeMaxDistance(int distance) {
		this.swipe_Max_Distance = distance;
	}

	public void setSwipeMinDistance(int distance) {
		this.swipe_Min_Distance = distance;
	}

	public void setSwipeMinVelocity(int distance) {
		this.swipe_Min_Velocity = distance;
	}

	public int getSwipeMaxDistance() {
		return this.swipe_Max_Distance;
	}

	public int getSwipeMinDistance() {
		return this.swipe_Min_Distance;
	}

	public int getSwipeMinVelocity() {
		return this.swipe_Min_Velocity;
	}

	// delivers the side of the stitched device (relative to previously active
	// device)
	public String getDeviceDirection() {
		return deviceDirection;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		final float xDistance = Math.abs(e1.getX() - e2.getX());
		final float yDistance = Math.abs(e1.getY() - e2.getY());

		// Log.d("DEBUG", "E1: " + String.valueOf(e1.getY()));
		// Log.d("DEBUG", "E2: " + String.valueOf(e1.getX()));
		// Log.d("DEBUG","VelocityX: " + String.valueOf(velocityX));
		// Log.d("DEBUG","VelocityY: " + String.valueOf(velocityY));

		if (xDistance > this.swipe_Max_Distance
				|| yDistance > this.swipe_Max_Distance)
			return false;

		velocityX = Math.abs(velocityX);
		velocityY = Math.abs(velocityY);
		boolean result = false;

		// delivers screen dimensions
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		// screen width and height
		width = size.x;
		height = size.y;
		rightEdge = width - 60;
		leftEdge = 60;
		topEdge = 300;
		bottomEdge = height - 60;

		// horizontal swipes
		if (velocityX > this.swipe_Min_Velocity
				&& xDistance > this.swipe_Min_Distance) {

			if (e1.getX() > e2.getX() && e1.getX() > rightEdge) { //

				deviceDirection = "left";
				this.listener.onSwipe(SWIPEIN_RIGHT);

			} else if (e1.getX() > e2.getX() && e2.getX() <= leftEdge) {

				this.listener.onSwipe(SWIPEOUT_LEFT);

			} else if (e1.getX() > e2.getX()) {
				this.listener.onSwipe(SWIPE_LEFT);

			} else if (e1.getX() < e2.getX() && e1.getX() < leftEdge) {
				deviceDirection = "right";
				this.listener.onSwipe(SWIPEIN_LEFT);

			} else if (e1.getX() < e2.getX() && e2.getX() >= rightEdge) {
				this.listener.onSwipe(SWIPEOUT_RIGHT);
			}

			else
				this.listener.onSwipe(SWIPE_RIGHT);

			result = true;

			// vertical swipes
		} else if (velocityY > this.swipe_Min_Velocity
				&& yDistance > this.swipe_Min_Distance) {
			if (e1.getY() < e2.getY() && e1.getY() < topEdge) // bottom to up
				this.listener.onSwipe(SWIPEIN_TOP);
			else if (e1.getY() < e2.getY() && e2.getY() >= bottomEdge)
				this.listener.onSwipe(SWIPEOUT_BOTTOM);
			else if (e1.getY() < e2.getY())
				this.listener.onSwipe(SWIPE_DOWN);
			else if (e1.getY() > e2.getY() && e1.getY() > bottomEdge)
				this.listener.onSwipe(SWIPEIN_BOTTOM);
			else if (e1.getY() > e2.getY() && e2.getY() <= topEdge)
				this.listener.onSwipe(SWIPEOUT_TOP);
			else
				this.listener.onSwipe(SWIPE_UP);

			result = true;
		}
		return result;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		this.tapIndicator = true;

		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent arg) {
		this.listener.onDoubleTap();
		;
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent arg) {
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent arg) {

		if (this.mode == MODE_DYNAMIC) { // we owe an ACTION_UP, so we fake an
			arg.setAction(ACTION_FAKE); // action which will be converted to an
										// ACTION_UP later.
			this.context.dispatchTouchEvent(arg);
		}
		this.listener.onSingleTapConfirmed();
		return false;
	}

	static interface SimpleGestureListener {
		void onSwipe(int direction);

		void onDoubleTap();

		void onSingleTapConfirmed();
	}

}