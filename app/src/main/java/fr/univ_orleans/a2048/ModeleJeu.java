package fr.univ_orleans.a2048;

import android.util.Pair;

import java.util.ArrayList;

public class ModeleJeu {

    public enum Mouvement{
        DROITE, GAUCHE, HAUT, BAS
    }

    private int taille;
    private int[][] grille;
    private int score;
    private ArrayList<Pair<Integer, Integer>> liste;

    public ModeleJeu() {
        taille = 4;
        grille = new int[taille][taille];
        score = 0;
        liste = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = 0;
            }
        }
        generationAleatoire(2); //deux chiffres 2 généré aléatoirement au début
        generationAleatoire(2);
    }

    public void move(Mouvement horizontal, Mouvement vertical){
        int valeurcourante =- 1;
        int indice;
        if(vertical == Mouvement.HAUT){
            indice = 0;
            for (int i = 0; i < taille; i++) {
                for (int j = 0; j < taille; j++) {

                }
            }
        }else if(vertical == Mouvement.BAS){
            indice = taille -1;
            for(int i = taille - 1; i >= 0; i--){
                for(int j = 0; j < taille; j++){

                }
            }
        }else if(horizontal == Mouvement.DROITE){
            indice = taille -1;
            for (int i = 0; i < taille; i++) {
                for (int j = taille - 1; j >= 0; j--) {

                }
            }
        }else if(horizontal == Mouvement.GAUCHE){
            indice = 0;
            for (int i = 0; i < taille; i++) {
                for (int j = 0; j < taille; j++) {

                }
            }
        }
    }

    private void mouvement(int indiceH,int indiceV,int indice,int valeurcourante){

    }

    public boolean moveDroite() {
        boolean movement = false;
        int valeurIitiale = -1, valeurCourante = valeurIitiale;
        int indice = taille - 1;
        for (int i = 0; i < taille; i++) {
            for (int j = taille - 1; j >= 0; j--) {
                int caze = grille[i][j];
                if (caze > 0) {
                    if (!movement)
                        movement = true;
                    if (valeurCourante == caze) {
                        valeurCourante += caze;
                        score += valeurCourante;
                        grille[i][indice] = valeurCourante;
                        ajouterCaseVide(i, j);
                        valeurCourante = valeurIitiale;
                        indice--;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != caze
                        valeurCourante = caze;
                        supprimerCaseVide(i, indice);
                        ajouterCaseVide(i, j);
                    }
                    else { //valeurCourante != caze && valeurCourante != -1
                        indice--;
                        valeurCourante = caze;
                        grille[i][indice] = caze;
                        ajouterCaseVide(i, j);
                        supprimerCaseVide(i, indice);
                    }
                }
            }
            valeurCourante = valeurIitiale;
            indice = taille - 1;
        }
        return movement;
    }

    public boolean moveGauche() {
        return false;
    }

    public boolean moveHaut() {
        return false;
    }

    public boolean moveBas() {
        return false;
    }

    public void modifScore() {

    }

    public boolean isGameOver() {
        return liste.isEmpty() && !(moveHaut() && moveBas() && moveGauche() && moveDroite());
    }

    public void jeu() {

    }

    private void generationAleatoire(int nombre) {
        int nb = 0;
        if (nombre == 0) { //nombre aléatoire
            nb = (int) (Math.random() * 10);
            if (nb < 8)
                nb = 2;
            else
                nb = 4;
        }
        int indiceListe = (int) (Math.random() * liste.size());
        Pair<Integer, Integer> coordonneeCase = liste.get(indiceListe);
        grille[coordonneeCase.first][coordonneeCase.second] = nb;
        liste.remove(indiceListe);
    }

    private void ajouterCaseVide(int x, int y) {
        grille[x][y] = 0;
        int indiceListe = 0;
        while (x > liste.get(indiceListe).first)
            indiceListe++;
        while (y > liste.get(indiceListe).second)
            indiceListe++;
        liste.add(indiceListe, new Pair<>(x, y));
    }

    private void supprimerCaseVide(int x, int y) {
        liste.remove(new Pair<>(x, y));
    }

}
