package fr.univ_orleans.a2048.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.Random;

import fr.univ_orleans.a2048.R;
import fr.univ_orleans.a2048.layouts.SquareLayout;

import static android.content.Context.MODE_PRIVATE;

public class MenuFragment extends Fragment implements View.OnClickListener {

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    //2 - Declare callback
    private OnButtonClickedListener mCallback;

    // 1 - Declare our interface that will be implemented by any container activity
    public interface OnButtonClickedListener {
        void onPlayButtonClicked(View view);
        void onScoreButtonClicked(View view);
    }

    // --------------

    private static final String PREFS_NAME = "SelectTailleSharedPrefs";
    private static final String PREF_KEY_TAILLE = "taille";
    private static final String DEF_VALUE_TAILLE = "4";

    private int taille;
    private SquareLayout mSquareLayout;
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Inflate the layout of MainFragment
        View view = inflater.inflate(R.layout.menu_fragment, container, false);

        mSquareLayout = view.findViewById(R.id.grille);
        mTextView = view.findViewById(R.id.textview);
        Button mButtonLess = view.findViewById(R.id.button_less);
        mButtonLess.setOnClickListener(this);
        Button mButtonMore = view.findViewById(R.id.button_more);
        mButtonMore.setOnClickListener(this);

        view.findViewById(R.id.fragment_menu_bouton_jouer).setOnClickListener(this);
        view.findViewById(R.id.fragment_menu_bouton_meilleur_score).setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 4 - Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    public void onResume() {
        super.onResume();
        load();
        setTextView();
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void onPause() {
        save();
        super.onPause();
    }

    @SuppressLint("ApplySharedPref")
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

    // --------------
    // ACTIONS
    // --------------

    @Override
    public void onClick(View v) {
        // 5 - Spread the click to the parent activity
        switch (v.getId()) {
            case R.id.fragment_menu_bouton_jouer:
                mCallback.onPlayButtonClicked(v);
                break;
            case R.id.fragment_menu_bouton_meilleur_score:
                mCallback.onScoreButtonClicked(v);
                break;
            case R.id.button_less:
                taille--;
                if (taille < 3) taille = 8;
                setGrille();
                setTextView();
                break;
            case R.id.button_more:
                taille++;
                if (taille > 8) taille = 3;
                setGrille();
                setTextView();
                break;
            default:
                break;
        }
    }

    private void setTextView() {
        String str = taille + "x" + taille;
        mTextView.setText(str);
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

    // --------------
    // FRAGMENT SUPPORT
    // --------------

    // 3 - Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

}

//Explications :
//
//      1.  Création de l'interface : Nous créons ici une interface dans le but d'obliger toute
//      activité souhaitant communiquer avec ce fragment à implémenter la méthode onButtonClicked(View).
//
//      2.  Déclaration de notre Callback : En déclarant ce Callback comme variable au sein du
//      fragment, nous allons pouvoir créer un lien direct avec notre activité parente.
//
//      3.  Création du Callback pointant vers notre activité : Nous allons lier notre Callback
//      avec notre activité parente en y souscrivant depuis le fragment enfant. Cependant, il faudra
//      que notre activité parente (donc qui contient ce fragment) implémente l'interface OnButtonClickedListener.
//
//      4.  Nous appelons ici la méthode de création du callback depuis onAttach(), car c'est à ce
//      moment uniquement que l'on sait avec certitude que notre fragment est bien attaché à son
//      activité parente.
//
//      5.  Propagation du clic : Nous allons ici propager le clic de notre utilisateur directement
//      à notre activité parente via la méthode onButtonClicked(View).
