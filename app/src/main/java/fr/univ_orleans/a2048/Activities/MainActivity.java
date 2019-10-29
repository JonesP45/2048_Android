package fr.univ_orleans.a2048.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import fr.univ_orleans.a2048.Fragments.MenuFragment;
import fr.univ_orleans.a2048.R;

public class MainActivity extends AppCompatActivity  implements MenuFragment.OnButtonClickedListener {

//    private SwipeGestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        gestureDetector = new SwipeGestureDetector(this);
    }

    // --------------
    // CallBack
    // --------------

//    @Override
//    public void onButtonClicked(View view) {
//        Log.e(getClass().getSimpleName(),"Button clicked !");
//        Toast.makeText(this, "Button clicked !", Toast.LENGTH_SHORT).show();
//    }
    @Override
    public void onButtonClicked(View view) {
        Log.e(getClass().getSimpleName(),"Button clicked !");
        startActivity(new Intent(this, JeuActivity.class));
    }

//    @Override
//    // redistribu tous les touch au gesturedetector
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }
//
//    //pour l'instant permet juste de savoir par ou on swipe
//    public void onSwipe(SwipeGestureDetector.SwipeDirection direction) {
//        String message = "";
//        switch (direction) {
//            case LEFT_TO_RIGHT:
//                message = "Left to right swipe";
//                break;
//            case RIGHT_TO_LEFT:
//                message = "Right to left swipe";
//                break;
//            case TOP_TO_BOTTOM:
//                message = "Top to bottom swipe";
//                break;
//            case BOTTOM_TO_TOP:
//                message = "Bottom to top swipe";
//                break;
//        }
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }

}
