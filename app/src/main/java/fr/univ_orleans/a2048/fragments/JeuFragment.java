package fr.univ_orleans.a2048.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import fr.univ_orleans.a2048.modele.ModeleJeu;
import fr.univ_orleans.a2048.R;

import static android.content.Context.MODE_PRIVATE;

public class JeuFragment extends Fragment implements View.OnClickListener/*, WinDialogFragment.WinDialogListener*/ {

    private static final String PREFS_NAME = "ScoreSharedPrefs";
    private static final String PREF_KEY_SCORE = "score";
    private static final String PREF_KEY_BEST_SCORE = "best_score";
    private static final String PREF_KEY_STATE = "state";
    private static final String PREF_KEY_MODEL = "model";
    private static final String DEF_VALUE_SCORE = "0";
    private static final String DEF_VALUE_BEST_SCORE = "0";
    private static final String DEF_VALUE_STATE = ModeleJeu.State.IN_GAME.toString();
    private static String DEF_VALUE_MODEL;


    private ModeleJeu mModele;
    private int taille = 4;

    private Button[][] mGridButtons;
//    private Button mButton_0_0;
//    private Button mButton_0_1;
//    private Button mButton_0_2;
//    private Button mButton_0_3;
//    private Button mButton_1_0;
//    private Button mButton_1_1;
//    private Button mButton_1_2;
//    private Button mButton_1_3;
//    private Button mButton_2_0;
//    private Button mButton_2_1;
//    private Button mButton_2_2;
//    private Button mButton_2_3;
//    private Button mButton_3_0;
//    private Button mButton_3_1;
//    private Button mButton_3_2;
//    private Button mButton_3_3;
//    private Button mButtonUndo;
//    private Button mButtonRestart;
    private Button mButtonScore;
    private Button mButtonBestScore;

    public static JeuFragment newInstance() {
        return new JeuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.jeu_fragment, container, false);
        mModele = ModeleJeu.newInstance(taille);
        DEF_VALUE_MODEL = modelToString(mModele.getGrille());
        Button mButtonUndo = view.findViewById(R.id.button_undo);
        mButtonUndo.setOnClickListener(this);
        Button mButtonRestart = view.findViewById(R.id.button_restart);
        mButtonRestart.setOnClickListener(this);
        mButtonScore = view.findViewById(R.id.button_score);
        mButtonBestScore = view.findViewById(R.id.button_best_score);
        mButtonBestScore.setText("0");
        Button mButton_0_0 = view.findViewById(R.id.button_0_0);
        Button mButton_0_1 = view.findViewById(R.id.button_0_1);
        Button mButton_0_2 = view.findViewById(R.id.button_0_2);
        Button mButton_0_3 = view.findViewById(R.id.button_0_3);
        Button mButton_1_0 = view.findViewById(R.id.button_1_0);
        Button mButton_1_1 = view.findViewById(R.id.button_1_1);
        Button mButton_1_2 = view.findViewById(R.id.button_1_2);
        Button mButton_1_3 = view.findViewById(R.id.button_1_3);
        Button mButton_2_0 = view.findViewById(R.id.button_2_0);
        Button mButton_2_1 = view.findViewById(R.id.button_2_1);
        Button mButton_2_2 = view.findViewById(R.id.button_2_2);
        Button mButton_2_3 = view.findViewById(R.id.button_2_3);
        Button mButton_3_0 = view.findViewById(R.id.button_3_0);
        Button mButton_3_1 = view.findViewById(R.id.button_3_1);
        Button mButton_3_2 = view.findViewById(R.id.button_3_2);
        Button mButton_3_3 = view.findViewById(R.id.button_3_3);
        mGridButtons = new Button[mModele.getTailleGrille()][mModele.getTailleGrille()];
        mGridButtons[0][0] = mButton_0_0;
        mGridButtons[0][1] = mButton_0_1;
        mGridButtons[0][2] = mButton_0_2;
        mGridButtons[0][3] = mButton_0_3;
        mGridButtons[1][0] = mButton_1_0;
        mGridButtons[1][1] = mButton_1_1;
        mGridButtons[1][2] = mButton_1_2;
        mGridButtons[1][3] = mButton_1_3;
        mGridButtons[2][0] = mButton_2_0;
        mGridButtons[2][1] = mButton_2_1;
        mGridButtons[2][2] = mButton_2_2;
        mGridButtons[2][3] = mButton_2_3;
        mGridButtons[3][0] = mButton_3_0;
        mGridButtons[3][1] = mButton_3_1;
        mGridButtons[3][2] = mButton_3_2;
        mGridButtons[3][3] = mButton_3_3;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        update();
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onPause() {
        save();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_undo) {
            undo();
        }
        if (v.getId() == R.id.button_restart) {
            restart();
        }
    }

    private void save() {
        String score = String.valueOf(mModele.getScore());
        String bestScore = mButtonBestScore.getText().toString();
        String model = modelToString(mModele.getGrille());
        String state = mModele.getState().toString();

        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editorSore = prefs.edit();
        editorSore.putString(PREF_KEY_SCORE, score);
        editorSore.putString(PREF_KEY_BEST_SCORE, bestScore);
        editorSore.putString(PREF_KEY_MODEL, model);
        editorSore.putString(PREF_KEY_STATE, state);
        editorSore.commit(); // Meme si Anroid Studio dit le contraire, préférer quand même commit a apply...
    }
    private void load() {
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mButtonBestScore.setText(prefs.getString(PREF_KEY_BEST_SCORE, DEF_VALUE_BEST_SCORE));

        int score = Integer.parseInt(prefs.getString(PREF_KEY_SCORE, DEF_VALUE_SCORE));
        ModeleJeu.State state = ModeleJeu.State.valueOf(prefs.getString(PREF_KEY_STATE, DEF_VALUE_STATE));
        int[][] grille = stringToModel(prefs.getString(PREF_KEY_MODEL, DEF_VALUE_MODEL));
        mModele.load(score, state, grille);

        update();
    }

    public void move(ModeleJeu.Mouvement mouvement) {
        mModele.move(mouvement);
        update();
        Log.e(getClass().getSimpleName(), mModele.getState().toString());
        if (gameWin() && mModele.getState() == ModeleJeu.State.WIN) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("You won!")
                    .setMessage("Score: " + mButtonScore.getText().toString())
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            restart();
                        }
                    });
            builder.show();
//            AlertDialog alertDialog = builder.show();
        }
        else if (gameOver() && mModele.getState() == ModeleJeu.State.LOSE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("You lost!")
                    .setMessage("Score: " + mButtonScore.getText().toString())
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            restart();
                        }
                    });
            builder.show();
//            AlertDialog alertDialog = builder.show();
        }

    }

    private boolean gameWin() {
        return mModele.isWin();
    }
    private boolean gameOver() {
        return mModele.isGameOver();
    }

    private void undo() {
        mModele.undo();
        update();
    }
    private void restart() {
        mModele.initialisation();
        update();
    }

    private void update() {
        updateTextButtons();
        updateScoreButton();
    }
    private void updateTextButtons() {
        for (int i = 0; i < mModele.getTailleGrille(); i++) {
            for (int j = 0; j < mModele.getTailleGrille(); j++) {
                mGridButtons[i][j].setText("");
                int cellule = mModele.getGrille()[i][j];
                if (cellule > 0) {
                    mGridButtons[i][j].setText(String.valueOf(cellule));
                }
                mGridButtons[i][j].setBackground(updateBackgroundButtons(cellule));
            }
        }
    }
    private Drawable updateBackgroundButtons(int valeur) {
        switch (valeur) {
            case 2:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_2);
            case 4:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_4);
            case 8:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_8);
            case 16:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_16);
            case 32:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_32);
            case 64:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_64);
            case 128:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_128);
            case 256:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_256);
            case 512:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_512);
            case 1024:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_1024);
            case 2048:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_2048);
            default:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_0);
        }
    }
    private void updateScoreButton() {
        String score = String.valueOf(mModele.getScore());
        mButtonScore.setText(score);
        if (Integer.parseInt(mButtonScore.getText().toString()) > Integer.parseInt(mButtonBestScore.getText().toString())) {
            mButtonBestScore.setText(score);
        }
    }

    private String modelToString(int[][] model) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[0].length; j++) {
                str.append(model[i][j]);
                str.append("-");
            }
        }
        return str.toString();
    }
    private int[][] stringToModel(String model) {
        int[][] grille = new int[taille][taille];
        int i = 0, j = 0;
        StringBuilder str = new StringBuilder();
        for (int c = 0; c < model.length(); c++) {
            Log.e(getClass().getSimpleName(), "str = " + str.toString() + ", c = " + model.charAt(c) + ", i = " + i + ", j = " + j);
            if (model.charAt(c) != '-') {
                Log.e(getClass().getSimpleName(), "if");
                str.append(model.charAt(c));
            } else {
                Log.e(getClass().getSimpleName(), "else");
                grille[i][j] = Integer.parseInt(str.toString());
                str = new StringBuilder();
                j++;
                if (j % taille == 0) {
                    i++;
                    j = 0;
                }
            }
        }
        return grille;
    }

}