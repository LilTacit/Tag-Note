package com.mobile.kiril.tagnote.touchListeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mobile.kiril.tagnote.views.MainView;

public class MenuOpenTouchListener implements View.OnTouchListener {
    private int screenXSize, screenYSize, menuXSize;
    private MainView mainView;
    private final GestureDetector gestureDetector;

    public MenuOpenTouchListener(Context context, MainView mainView, int screenXSize, int screenYSize, int menuXSize) {
        this.screenXSize = screenXSize;
        this.screenYSize = screenYSize;
        this.menuXSize = menuXSize;
        this.mainView = mainView;
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public boolean onTouch(View v, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        if(gestureDetector.onTouchEvent(event)){
            return true;
        } else {
            switch(event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN :
                    mainView.openMenu();
                    break;
                case MotionEvent.ACTION_MOVE :
                    if(X >= menuXSize/100*98) mainView.outFixMenu(X, Y, true);
                    else mainView.moveMenu(X, Y);
                    break;
                case MotionEvent.ACTION_UP :
                    mainView.outFixMenu(X, Y, false);
                    break;
            }

            return true;
        }
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private final int SWIPE_VELOCITY = 1500;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            float diffX = e1.getX() - e2.getX();

            if(diffX < 0 && (int)velocityX > SWIPE_VELOCITY) {
                //rightSwipe
                Log.d("log", "VelocityX = " + (int)velocityX + ", VelocityY = " + (int)velocityY);
                mainView.outFixMenu((int)e2.getRawX(), (int)e2.getRawY(), true);
                return true;
            } else if(diffX > 0 && (int)velocityX < -SWIPE_VELOCITY) {
                //leftSwipe
                Log.d("log", "VelocityX = " + (int)velocityX + ", VelocityY = " + (int)velocityY);
                mainView.outFixMenu((int)e2.getRawX(), (int)e2.getRawY(), false);
                return true;
            }

            return result;
        }
    }
}
