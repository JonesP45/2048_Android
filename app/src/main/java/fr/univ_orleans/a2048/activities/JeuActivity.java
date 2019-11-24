package fr.univ_orleans.a2048.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fr.univ_orleans.a2048.fragments.JeuFragment;
import fr.univ_orleans.a2048.ModeleJeu;
import fr.univ_orleans.a2048.R;
import fr.univ_orleans.a2048.SwipeGestureDetector;

public class JeuActivity extends AppCompatActivity /*implements JeuFragment.OnButtonClickedListener*/ {

    private SwipeGestureDetector mGestureDetector;
    private JeuFragment mJeuFragment;
//    private Button mButtonUndo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jeu_activity);
        mJeuFragment = JeuFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mJeuFragment)
                    .commitNow();
        }
//        mButtonUndo = mJeuFragment.getmButton_undo();
//        mButtonUndo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e(getClass().getSimpleName(),"undo clicked !");
//                mJeuFragment.undo();
//            }
//        });
        mGestureDetector = new SwipeGestureDetector(this);
    }

    // redistribu tous les touchs au gestureDetector
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

//    @Override
//    public void onUndoClicked(View view) {
//        Log.e(getClass().getSimpleName(),"undo clicked !");
//        mJeuFragment.undo();
//    }
//
//    @Override
//    public void onRestartClicked(View view) {
//        Log.e(getClass().getSimpleName(),"restart clicked !");
//        mJeuFragment.restart();
//    }

    //    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.button_undo) {
//            Toast.makeText(this, "undo", Toast.LENGTH_SHORT).show();
//            mJeuFragment.undo();
//        }
//        if (v.getId() == R.id.button_restart) {
//            Toast.makeText(this, "restart", Toast.LENGTH_SHORT).show();
//            mJeuFragment.restart();
//        }
//    }

    //pour l'instant permet juste de savoir par ou on swipe
    public void onSwipe(SwipeGestureDetector.SwipeDirection direction) {
        String message = "";
        switch (direction) {
            case LEFT_TO_RIGHT:
                mJeuFragment.move(ModeleJeu.Mouvement.DROITE);
                message = "Left to right swipe";
                break;
            case RIGHT_TO_LEFT:
                mJeuFragment.move(ModeleJeu.Mouvement.GAUCHE);
                message = "Right to left swipe";
                break;
            case TOP_TO_BOTTOM:
                mJeuFragment.move(ModeleJeu.Mouvement.BAS);
                message = "Top to bottom swipe";
                break;
            case BOTTOM_TO_TOP:
                mJeuFragment.move(ModeleJeu.Mouvement.HAUT);
                message = "Bottom to top swipe";
                break;
        }
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
