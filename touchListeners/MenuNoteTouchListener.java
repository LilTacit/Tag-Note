package com.mobile.kiril.tagnote.touchListeners;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mobile.kiril.tagnote.views.MainView;

public class MenuNoteTouchListener implements View.OnTouchListener {
    private MainView mainView;
    private View noteView;
    private final GestureDetector gestureDetector;

    public MenuNoteTouchListener(Context context, MainView mainView, View noteView) {
        this.mainView = mainView;
        this.noteView = noteView;
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            //change
            mainView.noteLongPress(noteView);
            super.onLongPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //onClick
            mainView.noteClick(noteView);
            return super.onSingleTapUp(e);
        }
    }
}
