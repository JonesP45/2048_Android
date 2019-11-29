package fr.univ_orleans.a2048.gesturedetector;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import fr.univ_orleans.a2048.activities.JeuActivity;

public class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {

    private final JeuActivity jeuActivity;

    private final int DELTA_MIN = 50;

    public enum SwipeDirection {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP
    }

    public SwipeGestureDetector(final JeuActivity context) {
        super();
        jeuActivity = context;
    }

    @Override
    //gère les swipes
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        Log.i("DEBUG", e1 + " - " + e2);

        float deltaX = e2.getX() - e1.getX();
        float deltaY = e2.getY() - e1.getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (Math.abs(deltaX) >= DELTA_MIN) {
                if (deltaX < 0)
                    jeuActivity.onSwipe(SwipeDirection.RIGHT_TO_LEFT);
                else
                    jeuActivity.onSwipe(SwipeDirection.LEFT_TO_RIGHT);
                return true;
            }
        } else {
            if (Math.abs(deltaY) >= DELTA_MIN) {
                if (deltaY < 0)
                    jeuActivity.onSwipe(SwipeDirection.BOTTOM_TO_TOP);
                else
                    jeuActivity.onSwipe(SwipeDirection.TOP_TO_BOTTOM);
                return true;
            }
        }
        return false;
    }
}