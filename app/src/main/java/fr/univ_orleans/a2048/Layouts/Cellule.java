package fr.univ_orleans.a2048.Layouts;

public class Cellule {

    private final String couleur2 = "";
    private final String couleur4 = "";
    private final String couleur8 = "";
    private final String couleur16 = "";
    private final String couleur32 = "";
    private final String couleur64 = "";

    private int valeur, ligne, colonne;
    private String couleur;

    public Cellule(int valeur, int ligne, int colonne, String couleur) {
        this.valeur = valeur;
        this.ligne = ligne;
        this.colonne = colonne;
        switch (valeur) {
            case 2:
                this.couleur = couleur2;
                break;
            case 4:
                this.couleur = couleur4;
                break;
            case 8:
                this.couleur = couleur8;
                break;
            case 16:
                this.couleur = couleur16;
                break;
            case 32:
                this.couleur = couleur32;
                break;
            case 64:
                this.couleur = couleur64;
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
    public String getCouleur() {
        return couleur;
    }

}
