package com.mobile.kiril.tagnote.touchListeners;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.mobile.kiril.tagnote.views.NoteView;

public class NoteActivityTouchListener implements View.OnTouchListener {
    private NoteView noteView;
    private final GestureDetector gestureDetector;

    public NoteActivityTouchListener(Context context, NoteView noteView) {
        this.noteView = noteView;
        gestureDetector = new GestureDetector(context, new NoteActivityTouchListener.GestureListener());
    }

    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private final int SWIPE_VELOCITY = 1500;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            float diffX = e1.getX() - e2.getX();

            Log.d("secondLog", "onFling");

            if(diffX < 0 && (int)velocityX > SWIPE_VELOCITY) {
                //rightSwipe
                Log.d("secondLog", "swipeRight");
                noteView.backToMainActivity();
                return true;
            }

            return result;
        }
    }
}
