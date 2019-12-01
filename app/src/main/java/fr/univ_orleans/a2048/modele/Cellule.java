package fr.univ_orleans.a2048.modele;

public class Cellule {

    private int valeur;
    private boolean modifie;

    public Cellule(){
        valeur=0;
        modifie=false;
    }

    public Cellule(int val){
        valeur=val;
        modifie=false;
    }

    public Cellule setValeur(int valeur) {
        this.valeur = valeur;
        modifie = true;
        return this;
    }

    public int getValeur() {
        return valeur;
    }
    public void resetModifie(){
        modifie = false;
    }

    public boolean isModifie() {
        return modifie;
    }
}
