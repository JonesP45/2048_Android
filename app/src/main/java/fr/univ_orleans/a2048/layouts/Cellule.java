package fr.univ_orleans.a2048.layouts;

public class Cellule {

    private int valeur, ligne, colonne, valeur_ant, ligne_ant, colonne_ant;

    public Cellule(int ligne, int colonne) {
        this.valeur = 0;
        this.ligne = ligne;
        this.colonne = colonne;
        this.valeur_ant = -1;
        this.ligne_ant = -1;
        this.colonne_ant = -1;
    }

    public void move(int nouvelleValeur, int i, int j) {
        valeur = nouvelleValeur;
        ligne_ant = ligne;
        colonne_ant = colonne;
        ligne = i;
        colonne = j;
    }

    public void undo() {
        valeur = valeur_ant;
        valeur_ant = -1;
        ligne = ligne_ant;
        ligne_ant = -1;
        colonne = colonne_ant;
        colonne_ant = -1;
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
    public int getLigne_ant() {
        return ligne_ant;
    }
    public int getColonne_ant() {
        return colonne_ant;
    }

}
