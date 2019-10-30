package fr.univ_orleans.a2048.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import fr.univ_orleans.a2048.Fragments.MenuFragment;
import fr.univ_orleans.a2048.R;

public class MenuActivity extends AppCompatActivity  implements MenuFragment.OnButtonClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    // --------------
    // CallBack
    // --------------

    @Override
    public void onNewGameButtonClicked(View view) {
        Log.e(getClass().getSimpleName(),"Button clicked !");
        startActivity(new Intent(this, JeuActivity.class));
    }

    @Override
    public void onScoreButtonClicked(View view) {
        Log.e(getClass().getSimpleName(),"Button clicked !");
        startActivity(new Intent(this, ScoreActivity.class));
    }

}
