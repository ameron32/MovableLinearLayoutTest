package com.ameron32.tr;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MovableLinearLayout extends LinearLayout {

//	public MovableLinearLayout(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		// TODO Auto-generated constructor stub
//	}

	public MovableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MovableLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private final List<View> movableViews = new ArrayList<View>();

	public void setMovableView(View movableView) {
		movableViews.add(movableView);
	}
	
	private List<View> touchedOnDown;
	private List<View> touchedOnUp;
	public void handleTouch(View v, MotionEvent e) {
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchedOnDown = eventDownInViews(e);
			if (touchedOnDown.size() > 1 || touchedOnDown.size() <= 0) {
				Log.e("Multiple Views touched", "number touchedDown:" + touchedOnDown.size());
			}
			break;
		case MotionEvent.ACTION_UP:
			touchedOnUp = eventUpInViews(e);
			if (touchedOnUp.size() > 1 || touchedOnUp.size() <= 0) {
				Log.e("Multiple Views touched", "number touchedUp:" + touchedOnUp.size());
			}
			
			if (topHalf) {
				moveViewToAheadView(touchedOnDown.get(0), touchedOnUp.get(0));
				Log.i("AheadOrBehind", "Ahead" + topHalf + ":" + bottomHalf);
			} else if (bottomHalf) {
				moveViewToBehindView(touchedOnDown.get(0), touchedOnUp.get(0));
				Log.i("AheadOrBehind", "Behind" + topHalf + ":" + bottomHalf);
			}

			break;
		}
	}
	
	private List<View> eventDownInViews(MotionEvent e) {
		final List<View> viewTouched = new ArrayList<View>();
		for (View v : movableViews) {
			if (isViewContains(v, (int) e.getRawX(), (int) e.getRawY())) {
				viewTouched.add(v);
			}
		}
		return viewTouched;
	}
	
	private boolean isViewContains(View view, int rx, int ry) {
	    int[] l = new int[2];
	    view.getLocationOnScreen(l);
	    int x = l[0];
	    int y = l[1];
	    int w = view.getWidth();
	    int h = view.getHeight();

	    if (rx < x || rx > x + w || ry < y || ry > y + h) {
	        return false;
	    }
	    return true;
	}
	
	boolean topHalf, bottomHalf;
	private List<View> eventUpInViews(MotionEvent e) {
		topHalf = false;
		bottomHalf = false;
		final List<View> viewTouched = new ArrayList<View>();
		for (View v : movableViews) {
			if (isTopHalf(v, (int) e.getRawX(), (int) e.getRawY())) {
				viewTouched.add(v);
				topHalf = true;
			}
			if (isBottomHalf(v, (int) e.getRawX(), (int) e.getRawY())) {
				viewTouched.add(v);
				bottomHalf = true;
			}
		}
		return viewTouched;
	}
		
	// is top or bottom?
	private boolean isTopHalf(View view, int rx, int ry) {
	    int[] l = new int[2];
	    view.getLocationOnScreen(l);
	    int x = l[0];
	    int y = l[1];
	    int w = view.getWidth();
	    int h = view.getHeight();

//	    if (rx < x || rx > x + w || ry < y || ry > y + h) {
//	        return false;
//	    }
	    if (rx < x || rx > x + w || ry < y || ry > y + (h/2) ) {
	    	return false;
	    }
	    return true;
	}
	
	// is top or bottom?
	private boolean isBottomHalf(View view, int rx, int ry) {
	    int[] l = new int[2];
	    view.getLocationOnScreen(l);
	    int x = l[0];
	    int y = l[1];
	    int w = view.getWidth();
	    int h = view.getHeight();

//	    if (rx < x || rx > x + w || ry < y || ry > y + h) {
//	        return false;
//	    }
	    if (rx < x || rx > x + w || ry < y + (h/2) || ry > y + h ) {
	    	return false;
	    }
	    return true;
	}
	
	
	private void moveViewToBehindView(View toMove, View toBehindView) {
		removeView(toMove);
		
		int behindView = 0;
		for ( ; behindView < getChildCount(); behindView++) {
			if (getChildAt(behindView).hashCode() == toBehindView.hashCode()) {
				behindView += 1;
				break;
			}
		}
		
		addView(toMove, behindView);
	}
	
	private void moveViewToAheadView(View toMove, View toAheadView) {
		removeView(toMove);
		
		int aheadView = 0;
		for ( ; aheadView < getChildCount(); aheadView++) {
			if (getChildAt(aheadView).hashCode() == toAheadView.hashCode()) {
				break;
			}
		}
		
		addView(toMove, aheadView);
	}
}
