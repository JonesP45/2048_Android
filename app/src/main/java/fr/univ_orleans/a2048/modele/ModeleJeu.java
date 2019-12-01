package fr.univ_orleans.a2048.modele;

import android.util.Pair;

import java.util.ArrayList;

public class ModeleJeu {

    public enum Mouvement {
        DROITE, GAUCHE, HAUT, BAS
    }

    public enum State {
        IN_GAME, WIN, ALREADY_WIN, LOSE
    }

    private State state;
    private Cellule[][] grille;
    private Cellule[][] grilleN_1;
    private int tailleGrille;
    private int score;
    private int scoreN_1;
    private ArrayList<Pair<Integer, Integer>> listeCaseVide;
    private boolean premierCoupFait = false;
    private boolean undoDejaFait = false;


    public static ModeleJeu newInstance(int taille) {
        return new ModeleJeu(taille);
    }
    private ModeleJeu(int taille) {
        tailleGrille = taille;
        grille = new Cellule[tailleGrille][tailleGrille];
        grilleN_1 = new Cellule[tailleGrille][tailleGrille];
        listeCaseVide = new ArrayList<>();
        initialisation();
    }
    public void initialisation() {
        state = State.IN_GAME;
        score = 0;
        scoreN_1 = 0;
        listeCaseVide.clear();
        for (int i = 0; i < tailleGrille; i++) {
            for (int j = 0; j < tailleGrille; j++) {
                grille[i][j]=new Cellule();
                grilleN_1[i][j] = new Cellule();

                listeCaseVide.add(new Pair<>(i, j));
            }
        }
        generationAleatoire(2); //deux chiffres 2 généré aléatoirement au début
        generationAleatoire(2);
    }

    public void load(int score, State state, Cellule[][] grille) {
        this.score = score;
        this.state = state;
        this.grille = grille;
    }

//    public boolean move(Mouvement movement) {
    public void move(Mouvement movement) {
        if (state == State.LOSE) return;
        if (!premierCoupFait) premierCoupFait = true;
        Cellule[][] temp = new Cellule[tailleGrille][tailleGrille];
        for (int i = 0; i < tailleGrille; i++) {
            for (int j = 0; j < tailleGrille; j++) {
                temp[i][j] = new Cellule(grille[i][j].getValeur());
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
            grilleN_1 = temp;

        }
    }
    private boolean moveDroite() {
        boolean movement = false;
        for (int i = 0; i < tailleGrille; i++) {
            int valeurIitiale = -1, valeurCourante = -1;
            int indice = tailleGrille - 1;
            for (int j = tailleGrille - 1; j >= 0; j--) {
                Cellule cellule = grille[i][j];
                if (cellule.getValeur() > 0) {
                    if (valeurCourante == cellule.getValeur()) {
                        valeurCourante += cellule.getValeur();
                        modifScore(valeurCourante);
                        ajouterCaseVide(i, j);
                        grille[i][indice].setValeur(valeurCourante);
                        valeurCourante = valeurIitiale;
                        indice--;
                        if (!movement) movement = true;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule.getValeur();
                        if (indice != j) {
                            supprimerCaseVide(i, indice);
                            ajouterCaseVide(i, j);
                            grille[i][indice] = new Cellule(valeurCourante);
                            if (!movement) movement = true;
                        }
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice--;
                        valeurCourante = cellule.getValeur();
                        if (indice != j) {
                            ajouterCaseVide(i, j);
                            supprimerCaseVide(i, indice);
                            grille[i][indice] = new Cellule(valeurCourante);
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
                Cellule cellule = grille[i][j];
                if (cellule.getValeur() > 0) {
                    if (valeurCourante == cellule.getValeur()) {
                        valeurCourante += cellule.getValeur();
                        modifScore(valeurCourante);
                        ajouterCaseVide(i, j);
                        grille[i][indice].setValeur(valeurCourante);
                        valeurCourante = valeurIitiale;
                        indice++;
                        if (!movement) movement = true;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule.getValeur();
                        if (indice != j) {
                            supprimerCaseVide(i, indice);
                            ajouterCaseVide(i, j);
                            grille[i][indice] = new Cellule(valeurCourante);
                            if (!movement) movement = true;
                        }
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice++;
                        valeurCourante = cellule.getValeur();
                        if (indice != j) {
                            ajouterCaseVide(i, j);
                            supprimerCaseVide(i, indice);
                            grille[i][indice] = new Cellule(valeurCourante);
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
                Cellule cellule = grille[i][j];
                if (cellule.getValeur() > 0) {
                    if (valeurCourante == cellule.getValeur()) {
                        valeurCourante += cellule.getValeur();
                        modifScore(valeurCourante);
                        ajouterCaseVide(i, j);
                        grille[indice][j].setValeur(valeurCourante);
                        valeurCourante = valeurIitiale;
                        indice++;
                        if (!movement) movement = true;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule.getValeur();
                        if (indice != i) {
                            supprimerCaseVide(indice, j);
                            ajouterCaseVide(i, j);
                            grille[indice][j] = new Cellule(valeurCourante);
                            if (!movement) movement = true;
                        }
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice++;
                        valeurCourante = cellule.getValeur();
                        if (indice != i) {
                            ajouterCaseVide(i, j);
                            supprimerCaseVide(indice, j);
                            grille[indice][j] = new Cellule(valeurCourante);
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
                Cellule cellule = grille[i][j];
                if (cellule.getValeur() > 0) {
                    if (valeurCourante == cellule.getValeur()) {
                        valeurCourante += cellule.getValeur();
                        modifScore(valeurCourante);
                        ajouterCaseVide(i, j);
                        grille[indice][j].setValeur(valeurCourante);
                        valeurCourante = valeurIitiale;
                        indice--;
                        if (!movement) movement = true;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule.getValeur();
                        if (indice != i) {
                            supprimerCaseVide(indice, j);
                            ajouterCaseVide(i, j);
                            grille[indice][j] = new Cellule(valeurCourante);
                            if (!movement) movement = true;
                        }
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice--;
                        valeurCourante = cellule.getValeur();
                        if (indice != i) {
                            ajouterCaseVide(i, j);
                            supprimerCaseVide(indice, j);
                            grille[indice][j] = new Cellule(valeurCourante);
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
            score = scoreN_1;
            for (int i = 0; i < tailleGrille; i++) {
                for (int j = 0; j < tailleGrille; j++) {
                    grille[i][j] = grilleN_1[i][j];
                }
            }
        }
        if (state == State.LOSE) {
            state = State.IN_GAME;
            for (int i = 0; i < tailleGrille; i++) {
                for (int j = 0; j < tailleGrille; j++) {
                    if (grille[i][j].getValeur() >= 2048) {
                        state = State.ALREADY_WIN;
                    }
                }
            }
        }
    }

    private void modifScore(int ajout) {
        scoreN_1 = score;
        score += ajout;
    }

    public boolean isWin() {
        if (state == State.WIN) {
            state = State.ALREADY_WIN;
            return true;
        }
        else if (state == State.IN_GAME) {
            for (int i = 0; i < tailleGrille; i++) {
                for (int j = 0; j < tailleGrille; j++) {
                    if (grille[i][j].getValeur() == 2048) {
                        state = State.WIN;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isGameOver() {
        if (state != State.LOSE) {
            if (listeCaseVide.isEmpty() && verifGameOver()) {
                state = State.LOSE;
                return true;
            }
        }
        return false;
    }
    private boolean verifGameOver(){
        for (int i = 0; i < tailleGrille; i++) {
            for (int j = 0; j < tailleGrille; j++) {
                if (!((i == 0 && j == 0) || (i == 0 && j == tailleGrille - 1) ||
                        (i == tailleGrille - 1 && j == 0) || (i == tailleGrille - 1 && j == tailleGrille - 1))) {
                    if (i == 0) {
                        if (grille[i + 1][j] == grille[i][j] || grille[i][j - 1] == grille[i][j] || grille[i][j + 1] == grille[i][j])
                            return false;
                    }
                    else if (i == tailleGrille - 1) {
                        if (grille[i - 1][j] == grille[i][j] || grille[i][j - 1] == grille[i][j] || grille[i][j + 1] == grille[i][j])
                            return false;
                    }
                    else if (j == 0) {
                        if (grille[i - 1][j] == grille[i][j] || grille[i + 1][j] == grille[i][j] || grille[i][j + 1] == grille[i][j])
                            return false;
                    }
                    else if (j == tailleGrille - 1) {
                        if (grille[i - 1][j] == grille[i][j] || grille[i + 1][j] == grille[i][j] || grille[i][j - 1] == grille[i][j])
                            return false;
                    }
                    else {
                        if (grille[i - 1][j] == grille[i][j] || grille[i + 1][j] == grille[i][j] ||
                                grille[i][j - 1] == grille[i][j] || grille[i][j + 1] == grille[i][j])
                            return false;
                    }
                }
            }
        }
        return true;
    }

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
        grille[coordonneeCase.first][coordonneeCase.second]= new Cellule(nb);
        listeCaseVide.remove(indiceListe);
    }

    private void ajouterCaseVide(int x, int y) {
        grille[x][y] = new Cellule();
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
    public Cellule[][] getGrille() {
        return grille;
    }
    public State getState() {
        return state;
    }

}
