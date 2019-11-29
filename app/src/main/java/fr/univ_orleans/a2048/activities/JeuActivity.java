package fr.univ_orleans.a2048.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import fr.univ_orleans.a2048.gesturedetector.SwipeGestureDetector;
import fr.univ_orleans.a2048.fragments.JeuFragment;
import fr.univ_orleans.a2048.modele.ModeleJeu;
import fr.univ_orleans.a2048.R;

public class JeuActivity extends AppCompatActivity {

    private GestureDetector mGestureDetector;

    private JeuFragment mJeuFragment;

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
        mGestureDetector = new GestureDetector(this, new SwipeGestureDetector(this));
    }

    // redistribu tous les touchs au gestureDetector
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //pour l'instant permet juste de savoir par ou on swipe
    public void onSwipe(SwipeGestureDetector.SwipeDirection direction) {
        switch (direction) {
            case LEFT_TO_RIGHT:
                mJeuFragment.move(ModeleJeu.Mouvement.DROITE);
                break;
            case RIGHT_TO_LEFT:
                mJeuFragment.move(ModeleJeu.Mouvement.GAUCHE);
                break;
            case TOP_TO_BOTTOM:
                mJeuFragment.move(ModeleJeu.Mouvement.BAS);
                break;
            case BOTTOM_TO_TOP:
                mJeuFragment.move(ModeleJeu.Mouvement.HAUT);
                break;
        }
    }

}
