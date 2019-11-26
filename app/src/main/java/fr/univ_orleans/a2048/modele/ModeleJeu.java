package fr.univ_orleans.a2048.modele;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

public class ModeleJeu {

    public enum Mouvement {
        DROITE, GAUCHE, HAUT, BAS
    }

    public enum State {
        IN_GAME, WIN, ALREADY_WIN, LOSE, SAVE
    }

    private State state;
    private int[][] grille;
    private int[][] grilleN_1;
    private int tailleGrille;
    private int score;
    private ArrayList<Pair<Integer, Integer>> listeCaseVide;
    private boolean premierCoupFait = false;
    private boolean undoDejaFait = false;


    public static ModeleJeu newInstance(int taille) {
        return new ModeleJeu(taille);
    }
    private ModeleJeu(int taille) {
        tailleGrille = taille;
        grille = new int[tailleGrille][tailleGrille];
        grilleN_1 = new int[tailleGrille][tailleGrille];
        listeCaseVide = new ArrayList<>();
        initialisation();
    }
    public void initialisation() {
        state = State.IN_GAME;
        score = 0;
        listeCaseVide.clear();
        for (int i = 0; i < tailleGrille; i++) {
            for (int j = 0; j < tailleGrille; j++) {
                grille[i][j] = 0;
                grilleN_1[i][j] = 0;
                listeCaseVide.add(new Pair<>(i, j));
            }
        }
        generationAleatoire(2); //deux chiffres 2 généré aléatoirement au début
        generationAleatoire(2);
    }

    public boolean move(Mouvement movement) {
        if (state == State.LOSE) return false;
        if (!premierCoupFait) premierCoupFait = true;
        for (int i = 0; i < tailleGrille; i++) {
            for (int j = 0; j < tailleGrille; j++) {
                grilleN_1[i][j] = grille[i][j];
            }
        }
        boolean changementDansGrille = false;

        if (movement == Mouvement.DROITE)
            changementDansGrille = moveDroite();
        if (movement == Mouvement.GAUCHE)
            changementDansGrille = moveGauche();
        if (movement == Mouvement.HAUT)
            changementDansGrille = moveHaut();
        if (movement == Mouvement.BAS)
            changementDansGrille = moveBas();

        if (changementDansGrille) {
            generationAleatoire(0);
            undoDejaFait = false;
        }
        return changementDansGrille;
    }
    private boolean moveDroite() {
        boolean movement = false;
        for (int i = 0; i < tailleGrille; i++) {
            int valeurIitiale = -1, valeurCourante = -1;
            int indice = tailleGrille - 1;
            for (int j = tailleGrille - 1; j >= 0; j--) {
                int cellule = grille[i][j];
                if (cellule > 0) {
                    if (valeurCourante == cellule) {
                        valeurCourante += cellule;
                        modifScore(valeurCourante);
                        ajouterCaseVide(i, j);
                        grille[i][indice] = valeurCourante;
                        valeurCourante = valeurIitiale;
                        indice--;
                        if (!movement) movement = true;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule;
                        if (indice != j) {
                            supprimerCaseVide(i, indice);
                            ajouterCaseVide(i, j);
                            grille[i][indice] = valeurCourante;
                            if (!movement) movement = true;
                        }
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice--;
                        valeurCourante = cellule;
                        if (indice != j) {
                            ajouterCaseVide(i, j);
                            supprimerCaseVide(i, indice);
                            grille[i][indice] = valeurCourante;
                            if (!movement) movement = true;
                        }
                    }
                }
            }
        }
        return movement;
    }
    private boolean moveGauche() {
        boolean movement = false;
        for (int i = 0; i < tailleGrille; i++) {
            int valeurIitiale = -1, valeurCourante = -1;
            int indice = 0;
            for (int j = 0; j < tailleGrille; j++) {
                int cellule = grille[i][j];
                if (cellule > 0) {
                    if (valeurCourante == cellule) {
                        valeurCourante += cellule;
                        modifScore(valeurCourante);
                        ajouterCaseVide(i, j);
                        grille[i][indice] = valeurCourante;
                        valeurCourante = valeurIitiale;
                        indice++;
                        if (!movement) movement = true;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule;
                        if (indice != j) {
                            supprimerCaseVide(i, indice);
                            ajouterCaseVide(i, j);
                            grille[i][indice] = valeurCourante;
                            if (!movement) movement = true;
                        }
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice++;
                        valeurCourante = cellule;
                        if (indice != j) {
                            ajouterCaseVide(i, j);
                            supprimerCaseVide(i, indice);
                            grille[i][indice] = valeurCourante;
                            if (!movement) movement = true;
                        }
                    }
                }
            }
        }
        return movement;
    }
    private boolean moveHaut() {
        boolean movement = false;
        for (int j = 0; j < tailleGrille; j++) {
            int valeurIitiale = -1, valeurCourante = -1;
            int indice = 0;
            for (int i = 0; i < tailleGrille; i++) {
                int cellule = grille[i][j];
                if (cellule > 0) {
                    if (valeurCourante == cellule) {
                        valeurCourante += cellule;
                        modifScore(valeurCourante);
                        ajouterCaseVide(i, j);
                        grille[indice][j] = valeurCourante;
                        valeurCourante = valeurIitiale;
                        indice++;
                        if (!movement) movement = true;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule;
                        if (indice != i) {
                            supprimerCaseVide(indice, j);
                            ajouterCaseVide(i, j);
                            grille[indice][j] = valeurCourante;
                            if (!movement) movement = true;
                        }
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice++;
                        valeurCourante = cellule;
                        if (indice != i) {
                            ajouterCaseVide(i, j);
                            supprimerCaseVide(indice, j);
                            grille[indice][j] = valeurCourante;
                            if (!movement) movement = true;
                        }
                    }
                }
            }
        }
        return movement;
    }
    private boolean moveBas() {
        boolean movement = false;
        for (int j = 0; j < tailleGrille; j++) {
            int valeurIitiale = -1, valeurCourante = -1;
            int indice = tailleGrille - 1;
            for (int i = tailleGrille - 1; i >= 0; i--) {
                int cellule = grille[i][j];
                if (cellule > 0) {
                    if (valeurCourante == cellule) {
                        valeurCourante += cellule;
                        modifScore(valeurCourante);
                        ajouterCaseVide(i, j);
                        grille[indice][j] = valeurCourante;
                        valeurCourante = valeurIitiale;
                        indice--;
                        if (!movement) movement = true;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule;
                        if (indice != i) {
                            supprimerCaseVide(indice, j);
                            ajouterCaseVide(i, j);
                            grille[indice][j] = valeurCourante;
                            if (!movement) movement = true;
                        }
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice--;
                        valeurCourante = cellule;
                        if (indice != i) {
                            ajouterCaseVide(i, j);
                            supprimerCaseVide(indice, j);
                            grille[indice][j] = valeurCourante;
                            if (!movement) movement = true;
                        }
                    }
                }
            }
        }
        return movement;
    }

    public void undo() {
        if (premierCoupFait && !undoDejaFait) {
            undoDejaFait = true;
            for (int i = 0; i < tailleGrille; i++) {
                for (int j = 0; j < tailleGrille; j++) {
                    grille[i][j] = grilleN_1[i][j];
                }
            }
        }
    }

    private void modifScore(int ajout) { score += ajout; }

    public boolean isWin() {
        if (state == State.IN_GAME) {
            for (int i = 0; i < tailleGrille; i++) {
                for (int j = 0; j < tailleGrille; j++) {
                    if (grille[i][j] == 2048) {
                        state = State.WIN;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isGameOver() {
        Log.e(getClass().getSimpleName(), state.toString());
        return listeCaseVide.isEmpty() && verifGameOver();
    }
    private boolean verifGameOver(){
//        for (int i = 1; i < tailleGrille; i++) {
//            for (int j = 1; j < tailleGrille; j++) {
//                if (grille[i - 1][j] == grille[i][j] || grille[i + 1][j] == grille[i][j] ||
//                        grille[i][j - 1] == grille[i][j] || grille[i][j + 1] == grille[i][j])
//                    return false;
//            }
//        }
//        for (int i = 1; i < tailleGrille - 1; i++) {
//            for (int j = 1; j < tailleGrille - 1; j++) {
//                if (grille[i - 1][j] == grille[i][j] || grille[i + 1][j] == grille[i][j] ||
//                        grille[i][j - 1] == grille[i][j] || grille[i][j + 1] == grille[i][j])
//                    return false;
//            }
//        }
        for (int i = 0; i < tailleGrille; i++) {
            for (int j = 0; j < tailleGrille; j++) {
                if (!((i == 0 && j == 0) || (i == 0 && j == tailleGrille - 1) ||
                        (i == tailleGrille - 1 && j == 0) || (i == tailleGrille - 1 && j == tailleGrille - 1))) {

                    if (i == 0) {

                    }
                    else if (i == tailleGrille - 1) {

                    }
                    else if (j == 0) {

                    }
                    else if (j == tailleGrille - 1) {

                    }
                    else {
                        if (grille[i - 1][j] == grille[i][j] || grille[i + 1][j] == grille[i][j] ||
                                grille[i][j - 1] == grille[i][j] || grille[i][j + 1] == grille[i][j])
                            return false;
                    }
                }
            }
        }
//        if (!(moveGauche() && moveDroite() && moveHaut() && moveBas()))
        state = State.LOSE;
        return true;
    }
//    public void setStateAlreadyWin() {
//        state = State.ALREADY_WIN;
//    }

    private void generationAleatoire(int nombre) {
        int nb = nombre;
        if (nb == 0) { //nombre aléatoire
            nb = (int) (Math.random() * 10);
            if (nb < 8)
                nb = 2;
            else
                nb = 4;
        }
        int indiceListe = (int) (Math.random() * listeCaseVide.size());
        Pair<Integer, Integer> coordonneeCase = listeCaseVide.get(indiceListe);
        grille[coordonneeCase.first][coordonneeCase.second] = nb;
        listeCaseVide.remove(indiceListe);
    }

    private void ajouterCaseVide(int x, int y) {
        grille[x][y] = 0;
        listeCaseVide.add(new Pair<>(x, y));
    }
    private void supprimerCaseVide(int x, int y) {
        listeCaseVide.remove(new Pair<>(x, y));
    }

    public int getScore(){
        return score;
    }
    public int getTailleGrille() {
        return tailleGrille;
    }
    public int[][] getGrille() {
        return grille;
    }
    public State getState() {
        return state;
    }

}
