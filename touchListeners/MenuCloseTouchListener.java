package com.mobile.kiril.tagnote.touchListeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mobile.kiril.tagnote.views.MainView;

public class MenuCloseTouchListener implements View.OnTouchListener {
    private MainView mainView;
    private final GestureDetector gestureDetector;

    public MenuCloseTouchListener(Context context, MainView mainView) {
        this.mainView = mainView;
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;

            try {
                Log.d("secondLog", "MenuCloseTouchListener onFling");
                float diffX = e1.getX() - e2.getX();
                float diffY = e1.getY() - e2.getY();

                if(Math.abs(diffX) > Math.abs(diffY)) {
                    if(diffX > 0) {
                        mainView.closeMenu();
                        return true;
                    }
                }
            } catch (NullPointerException e) {}

            return result;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
