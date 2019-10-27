package fr.univ_orleans.a2048;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)
        return view;
    }

    public interface OnFragmentInteractionListener {

    }
}
