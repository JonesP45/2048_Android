package fr.univ_orleans.a2048.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import fr.univ_orleans.a2048.modele.ModeleJeu;
import fr.univ_orleans.a2048.R;

public class JeuFragment extends Fragment implements View.OnClickListener/*, WinDialogFragment.WinDialogListener*/ {

    private ModeleJeu mModele;

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

    private Toast toast;

    public static JeuFragment newInstance() {
        return new JeuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.jeu_fragment, container, false);
        toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);

        mModele = ModeleJeu.newInstance(4);
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
        // TODO: Use the ViewModel
        update();
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

    public void move(ModeleJeu.Mouvement mouvement) {
        mModele.move(mouvement);
        update();
        Log.e(getClass().getSimpleName(), mModele.getState().toString());
        if (gameWin() && mModele.getState() == ModeleJeu.State.WIN) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("You win!")
                    .setMessage("Score: " + mButtonScore.getText().toString())
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            setState();
                        }
                    })
                    .setNegativeButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            restart();
                        }
                    });
            AlertDialog alertDialog = builder.show();
        }
        else if (gameOver()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("You lose!")
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
            AlertDialog alertDialog = builder.show();
        }

    }

//    private void setState() {
//        mModele.setStateAlreadyWin();
//    }

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

    public void restart() {
        mModele.initialisation();
//        if (Integer.parseInt(mButtonScore.getText().toString()) > Integer.parseInt(mButtonBestScore.getText().toString())) {
//            mButtonBestScore.setText(mButtonScore.getText());
//        }
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
            case 0:
                return ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.border_square_button_0);
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

}