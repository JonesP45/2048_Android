package fr.univ_orleans.a2048.layouts;

import android.content.res.Resources;

import fr.univ_orleans.a2048.R;

public class Cellule {

    private Resources resources = Resources.getSystem();
    private final int couleur0 = resources.getColor(R.color.btnColor);
//    private final int couleur2 = resources.getColor(R.color.btnColor);
//    private final int couleur4 = resources.getColor(R.color.btnColor);
//    private final int couleur8 = resources.getColor(R.color.btnColor);
//    private final int couleur16 = resources.getColor(R.color.btnColor);
//    private final int couleur32 = resources.getColor(R.color.btnColor);
//    private final int couleur64 = resources.getColor(R.color.btnColor);

    private int valeur, ligne, colonne;
    private int couleur;

    public Cellule(int valeur, int ligne, int colonne, int couleur) {
//        Resources res = getResources();
        int color = resources.getColor(R.color.btnColor);
        this.valeur = valeur;
        this.ligne = ligne;
        this.colonne = colonne;
        switch (valeur) {
            case 0:
                this.couleur = couleur0;
            case 2:
//                this.couleur = couleur2;
                break;
            case 4:
//                this.couleur = couleur4;
                break;
            case 8:
//                this.couleur = couleur8;
                break;
            case 16:
//                this.couleur = couleur16;
                break;
            case 32:
//                this.couleur = couleur32;
                break;
            case 64:
//                this.couleur = couleur64;
                break;
        }
    }

    public int getValeur() {
        return valeur;
    }
    public int getLigne() {
        return ligne;
    }
    public int getColonne() {
        return colonne;
    }
    public int getCouleur() {
        return couleur;
    }

}
