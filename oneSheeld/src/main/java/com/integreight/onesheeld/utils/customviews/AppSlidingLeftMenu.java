package com.integreight.onesheeld.utils.customviews;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AppSlidingLeftMenu extends SlidingPaneLayout {
    private boolean canSlide = true;

    // public boolean isNotDraggable = false;

    public AppSlidingLeftMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        try {
            return canSlide ? super.onInterceptTouchEvent(arg0) : false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canSlide() {
        return canSlide;
    }

    public void setCanSlide(boolean canSlide) {
        this.canSlide = canSlide;
    }

}
