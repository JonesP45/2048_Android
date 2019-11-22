package fr.univ_orleans.a2048.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.univ_orleans.a2048.R;
import fr.univ_orleans.a2048.fragments.ScoreFragment;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ScoreFragment.newInstance())
                    .commitNow();
        }
    }

}
