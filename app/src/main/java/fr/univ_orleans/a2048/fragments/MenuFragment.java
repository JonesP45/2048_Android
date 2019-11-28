package fr.univ_orleans.a2048.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.univ_orleans.a2048.R;

public class MenuFragment extends Fragment implements View.OnClickListener {

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    //2 - Declare callback
    private OnButtonClickedListener mCallback;

    // 1 - Declare our interface that will be implemented by any container activity
    public interface OnButtonClickedListener {
        void onPlayButtonClicked(View view);
//        void onNewGameButtonClicked(View view);
        void onScoreButtonClicked(View view);
    }

    // --------------

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Inflate the layout of MainFragment
        View result = inflater.inflate(R.layout.menu_fragment, container, false);

        //Set onClickListener to button "SHOW ME DETAILS"
//        result.findViewById(R.id.fragment_menu_bouton_reprendre_jeu).setOnClickListener(this);
//        result.findViewById(R.id.fragment_menu_bouton_nouveau_jeu).setOnClickListener(this);
        result.findViewById(R.id.fragment_menu_bouton_jouer).setOnClickListener(this);
        result.findViewById(R.id.fragment_menu_bouton_meilleur_score).setOnClickListener(this);

        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 4 - Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    // --------------
    // ACTIONS
    // --------------

    @Override
    public void onClick(View v) {
        // 5 - Spread the click to the parent activity
        switch (v.getId()) {
//            case R.id.fragment_menu_bouton_reprendre_jeu:
//                mCallback.onPlayButtonClicked(v);
//                break;
//            case R.id.fragment_menu_bouton_nouveau_jeu:
//                mCallback.onNewGameButtonClicked(v);
//                break;
            case R.id.fragment_menu_bouton_jouer:
                mCallback.onPlayButtonClicked(v);
                break;
            case R.id.fragment_menu_bouton_meilleur_score:
                mCallback.onScoreButtonClicked(v);
                break;
            default:
                break;
        }
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
