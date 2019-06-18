package com.mobile.kiril.tagnote.touchListeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mobile.kiril.tagnote.views.MainView;

public class MenuCategoryTouchListener implements View.OnTouchListener {
    private MainView mainView;
    private View categoryView;
    private final GestureDetector gestureDetector;

    public MenuCategoryTouchListener(Context context, MainView mainView, View categoryView) {
        this.mainView = mainView;
        this.categoryView = categoryView;
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
            float diffX = e1.getX() - e2.getX();

            if(diffX > 0) {
                Log.d("secondLog", String.format("MenuCategoryTouchListener onFling; MotionEvent e1: x = %d, y = %d; MotionEvent e2: x = %d, y = %d.",
                        (int)e1.getX(), (int)e1.getY(), (int)e2.getX(), (int)e2.getY()));

                Log.d("secondLog", String.format("difference = %d", (int)(Math.max(e1.getY(), e2.getY()) - Math.min(e1.getY(), e2.getY()))));

                mainView.closeMenu();
                return true;
            }

            return result;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            //change
            Log.d("secondLog", "MenuCategoryTouchListener onLongPress");
            mainView.categoryLongPress(categoryView);
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //onClick
            Log.d("secondLog", "MenuCategoryTouchListener onSingleTapUp");
            mainView.categoryClick(categoryView);
            return true;
        }
    }
}
