package fr.univ_orleans.a2048.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.Random;

import fr.univ_orleans.a2048.layouts.SquareButton;
import fr.univ_orleans.a2048.layouts.SquareLayout;
import fr.univ_orleans.a2048.modele.ModeleJeu;
import fr.univ_orleans.a2048.R;

import static android.content.Context.MODE_PRIVATE;

public class JeuFragment extends Fragment implements View.OnClickListener {

    private static String PREFS_NAME_MODEL;
    private static final String PREF_KEY_SCORE = "score";
    private static final String PREF_KEY_BEST_SCORE = "best_score";
    private static final String PREF_KEY_STATE = "state";
    private static final String PREF_KEY_MODEL = "model";
    private static final String DEF_VALUE_SCORE = "0";
    private static final String DEF_VALUE_BEST_SCORE = "0";
    private static final String DEF_VALUE_STATE = ModeleJeu.State.IN_GAME.toString();
    private static String DEF_VALUE_MODEL;

    private static final String PREFS_NAME_SELECT_TAILLE = "SelectTailleSharedPrefs";
    private static final String PREF_KEY_TAILLE = "taille";
    private static final String DEF_VALUE_TAILLE = "4";

    private static final boolean MOUVEMENT = true;
    private static final boolean NON_MOUVEMENT = false;


    private ModeleJeu mModele;
    private int taille;

    private SquareLayout mSquareLayout;
    private Button[][] mGridButtons;
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
        SharedPreferences prefsSelect = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME_SELECT_TAILLE, MODE_PRIVATE);
        taille = Integer.parseInt(prefsSelect.getString(PREF_KEY_TAILLE, DEF_VALUE_TAILLE));
        PREFS_NAME_MODEL = "ModelGrille" + taille + "SharedPrefs";

        mModele = ModeleJeu.newInstance(taille);
        DEF_VALUE_MODEL = modelToString(mModele.getGrille());
//        Log.e(getClass().getSimpleName(), DEF_VALUE_MODEL);

        mSquareLayout = view.findViewById(R.id.grille);
        Button mButtonUndo = view.findViewById(R.id.button_undo);
        mButtonUndo.setOnClickListener(this);
        Button mButtonRestart = view.findViewById(R.id.button_restart);
        mButtonRestart.setOnClickListener(this);
        mButtonScore = view.findViewById(R.id.button_score);
        mButtonBestScore = view.findViewById(R.id.button_best_score);
        mButtonBestScore.setText("0");

        mGridButtons = new Button[mModele.getTailleGrille()][mModele.getTailleGrille()];

        setGrille();
        update(NON_MOUVEMENT);
        return view;
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

    @SuppressLint("ApplySharedPref")
    private void save() {
        String score = String.valueOf(mModele.getScore());
        String bestScore = mButtonBestScore.getText().toString();
        String model = modelToString(mModele.getGrille());
        String state = mModele.getState().toString();

        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME_MODEL, MODE_PRIVATE);
        SharedPreferences.Editor editorSore = prefs.edit();
        editorSore.putString(PREF_KEY_SCORE, score);
        editorSore.putString(PREF_KEY_BEST_SCORE, bestScore);
        editorSore.putString(PREF_KEY_MODEL, model);
        editorSore.putString(PREF_KEY_STATE, state);
        editorSore.commit(); // Meme si Android Studio dit le contraire, préférer quand même commit a apply...
    }
    private void load() {
        SharedPreferences prefsModel = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME_MODEL, MODE_PRIVATE);
        mButtonBestScore.setText(prefsModel.getString(PREF_KEY_BEST_SCORE, DEF_VALUE_BEST_SCORE));
        int score = Integer.parseInt(prefsModel.getString(PREF_KEY_SCORE, DEF_VALUE_SCORE));
        ModeleJeu.State state = ModeleJeu.State.valueOf(prefsModel.getString(PREF_KEY_STATE, DEF_VALUE_STATE));
        int[][] grille = stringToModel(prefsModel.getString(PREF_KEY_MODEL, DEF_VALUE_MODEL));
        mModele.load(score, state, grille);
        update(NON_MOUVEMENT);
    }

    public void move(ModeleJeu.Mouvement mouvement) {
        mModele.move(mouvement);
        update(MOUVEMENT);
//        Log.e(getClass().getSimpleName(), mModele.getState().toString());
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
        update(NON_MOUVEMENT);
    }
    private void restart() {
        mModele.initialisation();
        update(NON_MOUVEMENT);
    }

    private void update(boolean action) {
        if(action)
            animation();
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
    private void animation(){
        Animation zoom = AnimationUtils.loadAnimation(this.getContext(),R.anim.zoom);
        for(int i = 0; i< mModele.getTailleGrille(); i++){
            for(int j= 0; j<mModele.getTailleGrille();j++){
                Button bout = mGridButtons[i][j];
                String valBoutS = bout.getText().toString();
                int valBout = valBoutS.equals("")?0:Integer.parseInt(valBoutS);
                System.out.println(valBout);
                int valCell = mModele.getGrille()[i][j];
                if((valBout * 2) == valCell && valBout!=0){
                    bout.startAnimation(zoom);
                }
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
//        Log.e(getClass().getSimpleName(), model);
        int[][] grille = new int[taille][taille];
        int i = 0, j = 0;
        StringBuilder str = new StringBuilder();
        for (int c = 0; c < model.length(); c++) {
//            Log.e(getClass().getSimpleName(), "str = " + str.toString() + ", c = " + model.charAt(c) + ", i = " + i + ", j = " + j);
            if (model.charAt(c) != '-') {
//                Log.e(getClass().getSimpleName(), "if");
                str.append(model.charAt(c));
            } else {
//                Log.e(getClass().getSimpleName(), "else");
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


    private void setGrille() {
        mSquareLayout.removeAllViews();
        for (int i = 0; i < taille; i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.weight = 5;
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < taille; j++) {
                Button button = new Button(getContext());
                LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );
                int margin = (int) ((4.0 / (double) taille) * mSquareLayout.getPaddingRight());
                paramsButton.setMargins(margin, margin, margin, margin);
                paramsButton.weight = 5;
                button.setLayoutParams(paramsButton);
                button.setText(setTextButton());
                setTextSizeButton(button);
                setBackgroundButton(button);
                button.setClickable(false);
                linearLayout.addView(button);
                mGridButtons[i][j] = button;
            }
            mSquareLayout.addView(linearLayout);
        }
    }

    private String setTextButton() {
        String str = "";
        Random random = new Random();
        int alea = random.nextInt(2);
        if (alea == 1) {
            int[] tab = {2, 4, 8, 16, 32, 64};
            alea = random.nextInt(tab.length);
            str = String.valueOf(tab[alea]);
        }
        return str;
    }

    private void setTextSizeButton(Button button) {
        int size;
        switch (taille) {
//            case 3:
//                size = 16;
//                break;
//            case 4:
//                size = 14;
//                break;
//            case 5:
//                size = 12;
//                break;
//            case 6:
//                size = 10;
//                break;
            case 7:
                size = 12;
                break;
            case 8:
                size = 8;
                break;
            default:
                size = 14;
        }
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    private void setBackgroundButton(Button button) {
        Drawable drawable;
        switch (button.getText().toString()) {
            case "2":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_2);
                break;
            case "4":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_4);
                break;
            case "8":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_8);
                break;
            case "16":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_16);
                break;
            case "32":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_32);
                break;
            case "64":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_64);
                break;
            case "128":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_128);
                break;
            case "256":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_256);
                break;
            case "512":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_512);
                break;
            case "1024":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_1024);
                break;
            case "2048":
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_2048);
                break;
            default:
                drawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_0);
        }
        button.setBackground(drawable);
    }


}