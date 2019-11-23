package fr.univ_orleans.a2048.fragments;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.univ_orleans.a2048.ModeleJeu;
import fr.univ_orleans.a2048.R;

public class JeuFragment extends Fragment {

    private ModeleJeu mModele;

    private Button[][] mGridButtons;
    private Button mButton_0_0;
    private Button mButton_0_1;
    private Button mButton_0_2;
    private Button mButton_0_3;
    private Button mButton_1_0;
    private Button mButton_1_1;
    private Button mButton_1_2;
    private Button mButton_1_3;
    private Button mButton_2_0;
    private Button mButton_2_1;
    private Button mButton_2_2;
    private Button mButton_2_3;
    private Button mButton_3_0;
    private Button mButton_3_1;
    private Button mButton_3_2;
    private Button mButton_3_3;

    public static JeuFragment newInstance() {
        return new JeuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jeu_fragment, container, false);
        mModele = ModeleJeu.newInstance(4);
        mButton_0_0 = view.findViewById(R.id.button_0_0);
        mButton_0_1 = view.findViewById(R.id.button_0_1);
        mButton_0_2 = view.findViewById(R.id.button_0_2);
        mButton_0_3 = view.findViewById(R.id.button_0_3);
        mButton_1_0 = view.findViewById(R.id.button_1_0);
        mButton_1_1 = view.findViewById(R.id.button_1_1);
        mButton_1_2 = view.findViewById(R.id.button_1_2);
        mButton_1_3 = view.findViewById(R.id.button_1_3);
        mButton_2_0 = view.findViewById(R.id.button_2_0);
        mButton_2_1 = view.findViewById(R.id.button_2_1);
        mButton_2_2 = view.findViewById(R.id.button_2_2);
        mButton_2_3 = view.findViewById(R.id.button_2_3);
        mButton_3_0 = view.findViewById(R.id.button_3_0);
        mButton_3_1 = view.findViewById(R.id.button_3_1);
        mButton_3_2 = view.findViewById(R.id.button_3_2);
        mButton_3_3 = view.findViewById(R.id.button_3_3);
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

    public void move(ModeleJeu.Mouvement mouvement) {
        mModele.move(mouvement);
        updateTextButtons();
    }

    public void updateTextButtons() {
        for (int i = 0; i < mModele.getTailleGrille(); i++) {
            for (int j = 0; j < mModele.getTailleGrille(); j++) {
                mGridButtons[i][j].setText("");
                int cellule = mModele.getGrille()[i][j];
                if (cellule > 0) {
//                    ColorDrawable colorDrawable = new ColorDrawable();
//                    colorDrawable.setColor(getResources().getColor(R.color.btnColorBis));
//                    mGridButtons[i][j].setForeground(colorDrawable);
                    mGridButtons[i][j].setText(Integer.toString(cellule));
                }
            }
        }
    }

}