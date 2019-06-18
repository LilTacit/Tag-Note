package com.mobile.kiril.tagnote.touchListeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mobile.kiril.tagnote.themes.ThemePreviewMovement;

public class ThemePreviewTouchListener implements View.OnTouchListener {
    private ThemePreviewMovement activity;
    private final GestureDetector gestureDetector;

    public ThemePreviewTouchListener(Context context, ThemePreviewMovement activity) {
        this.activity = activity;
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e1.getX() - e2.getX();

            if(diffX > 0) {
                Log.d("secondLog", "ThemePreviewTouchListener: moveRight");
                activity.moveThemePreview(true);
                return true;
            } else {
                Log.d("secondLog", "ThemePreviewTouchListener: moveLeft");
                activity.moveThemePreview(false);
                return true;
            }
        }
    }
}
