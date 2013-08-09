package com.ameron32.tr;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {

	private MovableLinearLayout mLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLayout = (MovableLinearLayout) findViewById(R.id.mLayout);
		
		int view = 0;
		for ( ; view < mLayout.getChildCount(); view++) {
			final View v = mLayout.getChildAt(view);
			mLayout.setMovableView(v);
			v.setOnTouchListener(this);
		}
		Log.d("setMovableView.count", "[" + view + "]");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mLayout.handleTouch(v, event);
		boolean touched = false;
		
		String eventString = event.getAction() + "";
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			eventString += " Down";
			touched = true;
			break;
		case MotionEvent.ACTION_POINTER_UP:
			eventString += " PUp";
			touched = true;
			break;
		case MotionEvent.ACTION_UP:
			eventString += " Up";
			touched = true;
			break;
		}
//		Toast.makeText(MainActivity.this, eventString, Toast.LENGTH_SHORT).show();
		Log.i("onTouch", eventString);
		return touched;
	}

}
