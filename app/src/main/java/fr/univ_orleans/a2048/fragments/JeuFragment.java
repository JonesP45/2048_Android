package fr.univ_orleans.a2048.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import fr.univ_orleans.a2048.modele.ModeleJeu;
import fr.univ_orleans.a2048.R;

public class JeuFragment extends Fragment implements View.OnClickListener {

    private class WinDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You win");
//                    .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // FIRE ZE MISSILES!
//                        }
//                    })
//                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // User cancelled the dialog
//                        }
//                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    private ModeleJeu mModele;

    private Button mButtonUndo;
    private Button mButtonRestart;
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

    public static JeuFragment newInstance() {
        return new JeuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.jeu_fragment, container, false);
        mModele = ModeleJeu.newInstance(4);
        mButtonUndo = view.findViewById(R.id.button_undo);
        mButtonUndo.setOnClickListener(this);
        mButtonRestart = view.findViewById(R.id.button_restart);
        mButtonRestart.setOnClickListener(this);
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
        updateTextButtons();
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
        updateTextButtons();
        if (gameWin()) {
//            Toast.makeText(getContext(), "win", Toast.LENGTH_SHORT).show();
//            WinDialogFragment winDialogFragment = new WinDialogFragment();
//            winDialogFragment.show();
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setMessage("You win")
//                    .setTitle("Win");
//            AlertDialog dialog = builder.create();
//            dialog.show();
        }
        else if (gameOver()) {

        }

    }

    private boolean gameWin() {
        return mModele.isWin();
    }

    private boolean gameOver() {
        return mModele.isGameOver();
    }

    public void undo() {
        mModele.undo();
        updateTextButtons();
    }

    public void restart() {
        mModele.initialisation();
        updateTextButtons();
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

}