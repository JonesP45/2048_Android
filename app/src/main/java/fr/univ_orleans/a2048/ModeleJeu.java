package fr.univ_orleans.a2048;

import android.util.Pair;

import java.util.ArrayList;

public class ModeleJeu {

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

    public void moveDroite() {
        int valeurCourante = -1;
        int indice = taille - 1;
        for (int i = 0; i < taille; i++) {
            for (int j = taille - 1; j >= 0; j--) {
                int caze = grille[i][j];
                if (valeurCourante == -1) {
//                    valeurCourante = caze;
//                    indice--;
                }
                else if (caze == valeurCourante) {
                    score += valeurCourante;
                    grille[i][indice] = 2 * caze;
                    ajouterCaseVide(i, j);
                    valeurCourante = 0;
                    indice--;
                }
                else if (caze != valeurCourante) {
                    valeurCourante = caze;
                    grille[i][indice] = valeurCourante;
                    supprimerCaseVide(i,indice);
                    ajouterCaseVide(i, j);
                }
//                else if (caze != valeurCourante && )
            }
            valeurCourante = -1;
            indice = taille;
        }
    }

    public void moveGauche() {

    }

    public void moveHaut() {

    }

    public void moveBas() {

    }

    public void modifScore() {

    }

    public boolean verifFinJeu() {
        return false;
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
