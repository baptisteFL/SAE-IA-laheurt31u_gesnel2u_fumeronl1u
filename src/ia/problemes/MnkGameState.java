package ia.problemes;

import java.util.Arrays;
import java.util.ArrayList;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.common.Misc;
import ia.framework.jeux.GameState;

/**
 * Représente un état d'un jeu générique m,n,k Game 
 */

public class MnkGameState extends AbstractMnkGameState {

    /**
     * Construire une grille vide de la bonne taille
     *
     * @param r nombre de lignes
     * @param c nombre de colonnes
     */
    public MnkGameState(int r, int c, int s) {
        super(r,c,s);
    }

    public MnkGameState cloneState() {
        MnkGameState new_s = new MnkGameState(this.rows, this.cols, this.streak);
        new_s.board = this.board.clone();
        new_s.player_to_move = player_to_move;
        new_s.game_value = game_value;
        if(this.last_action != null)
            new_s.last_action = this.last_action.clone();
        for (Pair p: this.winning_move)
            new_s.winning_move.add(p.clone());
        return new_s;
    }

    /**
     * Fonction d'évaluation qui estime la valeur de l'état du jeu
     *
     * @return une valeur positive si favorable à X, négative si favorable à O
     */
    protected double evaluationFunction() {
        int pos_x = this.possibleLines(X);
        int pos_o = this.possibleLines(O);
        return pos_x - pos_o;
    }

    // Compte le nombre de lignes possibles pour un joueur donné
    private int possibleLines(int player) {
        return this.possibleVerticalLines(player) +
                this.possibleHorizontalLines(player) +
                this.possibleDiagonalLines(player);
    }

    private int possibleVerticalLines(int player) {
        int res = 0;
        for(int c=0; c<this.cols; c++) {
            for(int r=0; r<=this.rows-this.streak; r++) {
                int counter = 0;
                for(int k=0; k<this.streak; k++) {
                    if(this.getValueAt(r+k,c) == this.otherPlayer(player)) {
                        counter = 0;
                        break;
                    } else if(this.getValueAt(r+k,c) == player) {
                        counter++;
                    } else {
                        counter++;
                    }
                }
                if(counter > 0) res++;
            }
        }
        return res;
    }

    private int possibleHorizontalLines(int player) {
        int res = 0;
        for(int r=0; r<this.rows; r++) {
            for(int c=0; c<=this.cols-this.streak; c++) {
                int counter = 0;
                for(int k=0; k<this.streak; k++) {
                    if(this.getValueAt(r,c+k) == this.otherPlayer(player)) {
                        counter = 0;
                        break;
                    } else if(this.getValueAt(r,c+k) == player) {
                        counter++;
                    } else {
                        counter++;
                    }
                }
                if(counter > 0) res++;
            }
        }
        return res;
    }

    private int possibleDiagonalLines(int player) {
        int res = 0;
        for(int c=0; c<=this.cols-this.streak; c++) {
            for(int r=0; r<=this.rows-this.streak; r++) {
                int counter = 0;
                for(int k=0; k<this.streak; k++) {
                    if(this.getValueAt(r+k,c+k) == this.otherPlayer(player)) {
                        counter = 0;
                        break;
                    } else if(this.getValueAt(r+k,c+k) == player) {
                        counter++;
                    } else {
                        counter++;
                    }
                }
                if(counter > 0) res++;
            }
        }
        for(int c=0; c<=this.cols-this.streak; c++) {
            for(int r=this.streak-1; r<this.rows; r++) {
                int counter = 0;
                for(int k=0; k<this.streak; k++) {
                    if(this.getValueAt(r-k,c+k) == this.otherPlayer(player)) {
                        counter = 0;
                        break;
                    } else if(this.getValueAt(r-k,c+k) == player) {
                        counter++;
                    } else {
                        counter++;
                    }
                }
                if(counter > 0) res++;
            }
        }
        return res;
    }
}
