package fr.univ_orleans.a2048.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.Random;

import fr.univ_orleans.a2048.R;
import fr.univ_orleans.a2048.layouts.SquareLayout;

import static android.content.Context.MODE_PRIVATE;

public class SelectionGrilleFragment extends Fragment implements View.OnClickListener {

    private static final String PREFS_NAME = "ScoreSharedPrefs";
    private static final String PREF_KEY_TAILLE = "taille";
    private static final String DEF_VALUE_TAILLE = "4";

    private int taille;
    private SquareLayout mSquareLayout;
    private Button mButtonLess;
    private Button mButtonMore;

    public static SelectionGrilleFragment newInstance() {
        return new SelectionGrilleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selection_grille_fragment, container, false);
        mSquareLayout = view.findViewById(R.id.grille);
        mButtonLess = view.findViewById(R.id.button_less);
        mButtonLess.setOnClickListener(this);
        mButtonMore = view.findViewById(R.id.button_more);
        mButtonMore.setOnClickListener(this);
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

    private void save() {
        String taille = String.valueOf(this.taille);
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editorSore = prefs.edit();
        editorSore.putString(PREF_KEY_TAILLE, taille);
        editorSore.commit(); // Meme si Anroid Studio dit le contraire, préférer quand même commit a apply...
    }
    private void load() {
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        this.taille = Integer.parseInt(prefs.getString(PREF_KEY_TAILLE, DEF_VALUE_TAILLE));
        setGrille();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_less:
                taille--;
                if (taille < 3) taille = 8;
                setGrille();
                break;
            case R.id.button_more:
                taille++;
                if (taille > 8) taille = 3;
                setGrille();
                break;
        }
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
