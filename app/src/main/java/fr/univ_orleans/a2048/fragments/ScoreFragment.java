package fr.univ_orleans.a2048.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import fr.univ_orleans.a2048.R;

public class ScoreFragment extends Fragment {

    public ScoreFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.score_fragment, container, false);
    }

}
