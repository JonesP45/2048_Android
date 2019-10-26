package fr.univ_orleans.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SwipeGestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new SwipeGestureDetector(this);
    }

    @Override
    // redistribu tous les touch au gesturedetector
    public boolean dispatchTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    //pour l'instant permet juste de savoir par ou on swipe
    public void onSwipe(SwipeGestureDetector.SwipeDirection direction) {
        String message = "";
        switch (direction) {
            case LEFT_TO_RIGHT:
                message = "Left to right swipe";
                break;
            case RIGHT_TO_LEFT:
                message = "Right to left swipe";
                break;
            case TOP_TO_BOTTOM:
                message = "Top to bottom swipe";
                break;
            case BOTTOM_TO_TOP:
                message = "Bottom to top swipe";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
