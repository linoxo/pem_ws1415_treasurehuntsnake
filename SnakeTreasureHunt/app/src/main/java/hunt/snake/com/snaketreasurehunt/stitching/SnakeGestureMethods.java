package com.example.stitching;

import android.widget.TextView;

public class SnakeGestureMethods {

	//constructor
	public SnakeGestureMethods() {
	}

	
	//customary direction gestures
	public void swipeUp(TextView text) {
		// up swipe
		text.setText("Swipe Up");
	}

	public void swipeDown (TextView text) {
		// down swipe
		text.setText("Swipe Down");
	}
	
	public void swipeLeft(TextView text) {
		// left swipe
		text.setText("Swipe Left");
	}

	public void swipeRight(TextView text) {
		// right swipe
		text.setText("Swipe Right");
	}

	
	// stitching gestures
	public void swipeOutTop(TextView text) {
		// gesture on active device that induces stitching
		// saves game tile of last touch position to global variable
		text.setText("Swipe Out Top");
	}

	public void swipeOutBottom(TextView text) {
		// gesture on active device that induces stitching
		// saves game tile of last touch position to global variable
		text.setText("Swipe Out Bottom");
	}

	public void swipeInBottom(TextView text) {
		// device gets stitched and is on the right side of the previously
		// active
		// one
		// global game tile variable needs to get called
		text.setText("Swipe In Bottom");
	}

	public void swipeInTop(TextView text) {
		// device gets stitched and is on the left side of the previously active
		// one
		// global game tile variable needs to get called
		text.setText("Swipe In Top");
	}
	
	public void swipeOutLeft(TextView text) {
		// gesture on active device that induces stitching
		// saves game tile of last touch position to global variable
		text.setText("Swipe Out Left");
	}

	public void swipeOutRight(TextView text) {
		// gesture on active device that induces stitching
		// saves game tile of last touch position to global variable
		text.setText("Swipe Out Right");
	}

	public void swipeInLeft(TextView text) {
		// device gets stitched and is on the right side of the previously
		// active
		// one
		// global game tile variable needs to get called
		text.setText("Swipe In Left");
	}

	public void swipeInRight(TextView text) {
		// device gets stitched and is on the left side of the previously active
		// one
		// global game tile variable needs to get called
		text.setText("Swipe In Right");
	}

}
