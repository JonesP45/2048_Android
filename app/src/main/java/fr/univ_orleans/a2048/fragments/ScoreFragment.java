package fr.univ_orleans.a2048.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import fr.univ_orleans.a2048.R;

import static android.content.Context.MODE_PRIVATE;

public class ScoreFragment extends Fragment {

    private TextView trois;
    private TextView quatre;
    private TextView cinq;
    private TextView six;
    private TextView sept;
    private TextView huit;

    public static ScoreFragment newInstance() {
        return new ScoreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.score_fragment, container, false);
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("ModelGrille4SharedPrefs", MODE_PRIVATE);
        quatre = view.findViewById(R.id.quatreXquatre);
        String score = prefs.getString("best_score","0");
        quatre.setText(score);


        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("ModelGrille5SharedPrefs", MODE_PRIVATE);
        cinq = view.findViewById(R.id.cinqXcinq);
        score = prefs.getString("best_score","0");
        cinq.setText(score);

        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("ModelGrille6SharedPrefs", MODE_PRIVATE);
        six = view.findViewById(R.id.sixXsix);
        score = prefs.getString("best_score","0");
        six.setText(score);

        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("ModelGrille7SharedPrefs", MODE_PRIVATE);
        sept = view.findViewById(R.id.septXsept);
        score = prefs.getString("best_score","0");
        sept.setText(score);

        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("ModelGrille8SharedPrefs", MODE_PRIVATE);
        huit = view.findViewById(R.id.huitXhuit);
        score = prefs.getString("best_score","0");
        huit.setText(score);


        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("ModelGrille3SharedPrefs", MODE_PRIVATE);
        trois = view.findViewById(R.id.troisXtrois);
        score = prefs.getString("best_score","0");
        trois.setText(score);



        return view;
    }



}
