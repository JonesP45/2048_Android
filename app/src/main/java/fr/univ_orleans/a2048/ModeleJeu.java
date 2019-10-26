package fr.univ_orleans.a2048;

import android.util.Pair;

import java.util.ArrayList;

public class ModeleJeu {

    public enum Mouvement {
        DROITE, GAUCHE, HAUT, BAS
    }

    private int taille;
    private int[][] grille;
    private int score;
    private ArrayList<Pair<Integer, Integer>> liste;
    private boolean grillemodifiee = false;

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
        int valeurCourante;
        int indice;
        grillemodifiee = false;
        Pair<Integer,Integer> indiceEtvaleurCourante;
        if(vertical == Mouvement.HAUT){
            for (int i = 0; i < taille; i++) {
                indice = 0;
                valeurCourante=-1;
                for (int j = 0; j < taille; j++) {
                    indiceEtvaleurCourante = mouvement(i,j,indice,i,valeurCourante,Mouvement.HAUT);
                    indice = indiceEtvaleurCourante.first;
                    valeurCourante = indiceEtvaleurCourante.second;
                }
            }
        }else if(vertical == Mouvement.BAS){
            for(int i =taille -1; i>=0; i--){
                indice = taille -1;
                valeurCourante =-1;
                for(int j=0; j<taille; j++){
                    indiceEtvaleurCourante = mouvement(i,j,indice,i,valeurCourante,Mouvement.BAS);
                    indice = indiceEtvaleurCourante.first;
                    valeurCourante = indiceEtvaleurCourante.second;
                }
            }
        }else if(horizontal == Mouvement.DROITE){
            for (int i = 0; i < taille; i++) {
                indice = taille -1;
                valeurCourante = -1;
                for (int j = taille - 1; j >= 0; j--) {
                    indiceEtvaleurCourante = mouvement(i,j,j,indice,valeurCourante,Mouvement.DROITE);
                    indice = indiceEtvaleurCourante.first;
                    valeurCourante = indiceEtvaleurCourante.second;
                }
            }
        }else if(horizontal == Mouvement.GAUCHE){
            for (int i = 0; i < taille; i++) {
                indice = 0;
                valeurCourante = -1;
                for (int j = 0; j <taille; j++) {
                    indiceEtvaleurCourante = mouvement(i,j,j,indice,valeurCourante,Mouvement.GAUCHE);
                    indice = indiceEtvaleurCourante.first;
                    valeurCourante = indiceEtvaleurCourante.second;
                }
            }
        }

        if(grillemodifiee) generationAleatoire(0);

/*
        if(horizontal == Mouvement.DROITE)
            moveDroite();
        if(horizontal == Mouvement.GAUCHE)
            moveGauche();
        if(vertical == Mouvement.HAUT)
            moveHaut();
        if(vertical == Mouvement.BAS)
            moveBas();
*/
    }

    private Pair<Integer,Integer> mouvement(int i,int j,int indiceH, int indiceV,int valeurCourante, Mouvement mouv){
        int cellule = grille[i][j];
        if (cellule > 0) {
            if (valeurCourante == cellule) {
                valeurCourante += cellule;
                score += valeurCourante;
                grille[indiceV][indiceH] = valeurCourante;
                ajouterCaseVide(i, j);
                valeurCourante = -1;
                grillemodifiee = true;
                if(mouv == Mouvement.DROITE)
                    return new Pair<>(indiceH-1,valeurCourante);
                if(mouv == Mouvement.GAUCHE)
                    return new Pair<>(indiceH+1,valeurCourante);
                if(mouv == Mouvement.BAS)
                    return new Pair<>(indiceV-1,valeurCourante);

                return new Pair<>(indiceH+1,valeurCourante);

            } //bon
            else if (valeurCourante == -1) { //valeurCourante != cellule
                valeurCourante = cellule;
                supprimerCaseVide(indiceV, indiceH);
                ajouterCaseVide(i, j);
                grillemodifiee = true;
                return new Pair<>(indiceH,cellule);
            }
            else { //valeurCourante != cellule && valeurCourante != -1
                if(mouv == Mouvement.DROITE)
                    indiceH--;
                if(mouv == Mouvement.GAUCHE)
                    indiceH++;
                if(mouv == Mouvement.BAS)
                    indiceV--;
                if(mouv == Mouvement.HAUT)
                    indiceV++;
                valeurCourante = cellule;
                grille[indiceV][indiceH] = cellule;
                ajouterCaseVide(i, j);
                supprimerCaseVide(indiceV, indiceH);
                grillemodifiee = true;
                if(mouv == Mouvement.DROITE)
                    return new Pair<>(indiceH,valeurCourante);
                if(mouv == Mouvement.GAUCHE)
                    return new Pair<>(indiceH,valeurCourante);
                if(mouv == Mouvement.BAS)
                    return new Pair<>(indiceV,valeurCourante);

                return new Pair<>(indiceV,valeurCourante);
            }
        }
        if(mouv == Mouvement.DROITE)
            return new Pair<>(indiceH,valeurCourante);
        if(mouv == Mouvement.GAUCHE)
            return new Pair<>(indiceH,valeurCourante);
        if(mouv == Mouvement.BAS)
            return new Pair<>(indiceV,valeurCourante);
        
        return new Pair<>(indiceV,valeurCourante);



    }

    private boolean moveDroite() {
        boolean movement = false;
        int valeurIitiale = -1, valeurCourante = valeurIitiale;
        int indice = taille - 1;
        for (int i = 0; i < taille; i++) {
            for (int j = taille - 1; j >= 0; j--) {
                int cellule = grille[i][j];
                if (cellule > 0) {
                    if (!movement)
                        movement = true;
                    if (valeurCourante == cellule) {
                        valeurCourante += cellule;
                        score += valeurCourante;
                        grille[i][indice] = valeurCourante;
                        ajouterCaseVide(i, j);
                        valeurCourante = valeurIitiale;
                        indice--;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule;
                        supprimerCaseVide(i, indice);
                        ajouterCaseVide(i, j);
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice--;
                        valeurCourante = cellule;
                        grille[i][indice] = cellule;
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

    private boolean moveGauche() {
        boolean movement = false;
        int valeurIitiale = -1, valeurCourante = valeurIitiale;
        int indice = 0; //modif
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille - 1; j++) { //modif
                int cellule = grille[i][j];
                if (cellule > 0) {
                    if (!movement)
                        movement = true;
                    if (valeurCourante == cellule) {
                        valeurCourante += cellule;
                        score += valeurCourante;
                        grille[i][indice] = valeurCourante; //pb avec haut/bas
                        ajouterCaseVide(i, j);
                        valeurCourante = valeurIitiale;
                        indice++;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule;
                        supprimerCaseVide(i, indice);
                        ajouterCaseVide(i, j);
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice++;
                        valeurCourante = cellule;
                        grille[i][indice] = cellule;
                        ajouterCaseVide(i, j);
                        supprimerCaseVide(i, indice);
                    }
                }
            }
            valeurCourante = valeurIitiale;
            indice = 0; //modif
        }
        return movement;
    }

    private boolean moveHaut() {
        boolean movement = false;
        int valeurIitiale = -1, valeurCourante = valeurIitiale;
        int indice = 0; //modif
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille - 1; j++) { //modif
                int cellule = grille[i][j];
                if (cellule > 0) {
                    if (!movement)
                        movement = true;
                    if (valeurCourante == cellule) {
                        valeurCourante += cellule;
                        score += valeurCourante;
                        grille[indice][j] = valeurCourante; //pb avec haut/bas
                        ajouterCaseVide(i, j);
                        valeurCourante = valeurIitiale;
                        indice++;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule;
                        supprimerCaseVide(indice, j);
                        ajouterCaseVide(i, j);
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice++;
                        valeurCourante = cellule;
                        grille[indice][j] = cellule;
                        ajouterCaseVide(i, j);
                        supprimerCaseVide(indice, j);
                    }
                }
            }
            valeurCourante = valeurIitiale;
            indice = 0; //modif
        }
        return movement;
    }

    private boolean moveBas() {
        boolean movement = false;
        int valeurIitiale = -1, valeurCourante = valeurIitiale;
        int indice = taille - 1; //modif
        for (int i = 0; i < taille; i++) {
            for (int j = taille - 1; j >= 0; j--) { //modif
                int cellule = grille[i][j];
                if (cellule > 0) {
                    if (!movement)
                        movement = true;
                    if (valeurCourante == cellule) {
                        valeurCourante += cellule;
                        score += valeurCourante;
                        grille[indice][j] = valeurCourante; //pb avec haut/bas
                        ajouterCaseVide(i, j);
                        valeurCourante = valeurIitiale;
                        indice--;
                    } //bon
                    else if (valeurCourante == -1) { //valeurCourante != cellule
                        valeurCourante = cellule;
                        supprimerCaseVide(indice, j);
                        ajouterCaseVide(i, j);
                    }
                    else { //valeurCourante != cellule && valeurCourante != -1
                        indice--;
                        valeurCourante = cellule;
                        grille[indice][j] = cellule;
                        ajouterCaseVide(i, j);
                        supprimerCaseVide(indice, j);
                    }
                }
            }
            valeurCourante = valeurIitiale;
            indice = 0; //modif
        }
        return movement;
    }

    public void modifScore() {

    }

    public int getScore(){
        return score;
    }

    private boolean verifGameOver(){
        for(int i = 1;  i<taille; i++){
            for(int j = 1; j<taille; j++){
                if(grille[i-1][j] == grille[i][j] || grille[i][j-1]==grille[i][j])
                    return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return liste.isEmpty() && verifGameOver();
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
