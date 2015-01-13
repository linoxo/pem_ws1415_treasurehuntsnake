package com.example.stitching;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.stitchinbg.R;
import com.example.stitching.SnakeGestureListener.SimpleGestureListener;

public class MainActivity extends Activity implements SimpleGestureListener {

	private SnakeGestureListener detector;
	private SnakeGestureMethods methods;
	TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		WindowManager manager = ((WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE));

		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		localLayoutParams.gravity = Gravity.TOP;
		localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
				|

				// this is to enable the notification to recieve touch events
				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

				// Draws over status bar
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

		localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		localLayoutParams.height = (int) (50 * getResources()
				.getDisplayMetrics().scaledDensity);
		localLayoutParams.format = PixelFormat.TRANSPARENT;

		customViewGroup view = new customViewGroup(this);

		manager.addView(view, localLayoutParams);
		 getActionBar().hide();

		 View decorView = getWindow().getDecorView();
		 // Hide the status bar.
		 int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		 decorView.setSystemUiVisibility(uiOptions);
		 // Remember that you should never show the action bar if the
		 // status bar is hidden, so hide that too if necessary.
		 ActionBar actionBar = getActionBar();
		 actionBar.hide();

		// Detect touched area
		detector = new SnakeGestureListener(this, this);
		methods = new SnakeGestureMethods();
		text = (TextView) findViewById(R.id.textBox);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		// Call onTouchEvent of SimpleGestureFilter class
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		String str = "";

		switch (direction) {

		case SnakeGestureListener.SWIPE_RIGHT:
			methods.swipeRight(text);
			break;
		case SnakeGestureListener.SWIPE_LEFT:
			methods.swipeLeft(text);
			break;
		case SnakeGestureListener.SWIPE_DOWN:
			methods.swipeDown(text);
			break;
		case SnakeGestureListener.SWIPE_UP:
			methods.swipeUp(text);
			break;
		case SnakeGestureListener.SWIPEIN_LEFT:
			methods.swipeInLeft(text);
			break;
		case SnakeGestureListener.SWIPEIN_RIGHT:
			methods.swipeInRight(text);
			break;
		case SnakeGestureListener.SWIPEOUT_LEFT:
			methods.swipeOutLeft(text);
			break;
		case SnakeGestureListener.SWIPEIN_TOP:
			methods.swipeInTop(text);
			break;
		case SnakeGestureListener.SWIPEOUT_TOP:
			methods.swipeOutTop(text);
			break;
		case SnakeGestureListener.SWIPEOUT_BOTTOM:
			methods.swipeOutBottom(text);
			break;
		case SnakeGestureListener.SWIPEIN_BOTTOM:
			methods.swipeInBottom(text);
			break;
		case SnakeGestureListener.SWIPEOUT_RIGHT:
			methods.swipeOutRight(text);
			break;

		}
		// text.setText(str);
		// text.setText(detector.getDeviceDirection());
		// Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDoubleTap() {
		// Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
		text.setText("Game Paused");
	}

	@Override
	public void onSingleTapConfirmed() {
		// TODO Auto-generated method stub

		text.setText("Normal Tap");

	}

}

class customViewGroup extends ViewGroup {

	public customViewGroup(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.v("customViewGroup", "**********Intercepted");
		return true;
	}
}

// package com.example.stitching;
//
// import android.os.Bundle;
// import android.support.v7.app.ActionBarActivity;
// import android.view.Menu;
// import android.view.MenuItem;
// import android.view.View;
// import android.widget.TextView;
// import android.widget.Toast;
//
// import com.example.stitchinbg.R;
//
// public class MainActivity extends ActionBarActivity {
// TextView text;
// View view;
//
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.activity_main);
//
// text = (TextView) findViewById(R.id.textBox);
// view = findViewById(android.R.id.content);
// view.setOnTouchListener(new SwipeListener(MainActivity.this) {
// public void onSwipeTop() {
// text.setText("Oben");
// Toast.makeText(MainActivity.this, "Oben", Toast.LENGTH_SHORT).show();
// }
//
// public void onSwipeRight() {
// text.setText("Rechts");
// }
//
// public void onSwipeLeft() {
// text.setText("Links");
// }
//
// public void onSwipeBottom() {
// text.setText("Unten");
// }
//
// });
// }
//
// @Override
// public boolean onCreateOptionsMenu(Menu menu) {
// // Inflate the menu; this adds items to the action bar if it is present.
// getMenuInflater().inflate(R.menu.main, menu);
// return true;
// }
//
// @Override
// public boolean onOptionsItemSelected(MenuItem item) {
// // Handle action bar item clicks here. The action bar will
// // automatically handle clicks on the Home/Up button, so long
// // as you specify a parent activity in AndroidManifest.xml.
// int id = item.getItemId();
// if (id == R.id.action_settings) {
// return true;
// }
// return super.onOptionsItemSelected(item);
// }
// }
