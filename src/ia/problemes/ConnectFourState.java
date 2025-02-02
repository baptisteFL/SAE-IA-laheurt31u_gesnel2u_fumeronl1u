package ia.problemes;

import java.util.Random;
import java.util.Arrays;

import ia.framework.common.Action;
import ia.framework.common.State;
import ia.framework.common.Misc;
import ia.framework.jeux.GameState;

public class ConnectFourState extends AbstractMnkGameState {
    
	public ConnectFourState() {
        super(6, 7, 4);
    }

    public ConnectFourState cloneState() {
		ConnectFourState new_s = new ConnectFourState();
        new_s.board = this.board.clone();
        new_s.player_to_move = this.player_to_move;
        new_s.game_value = this.game_value;
        if( this.last_action != null )
            new_s.last_action = this.last_action.clone();
        for (Pair p: this.winning_move)
            new_s.winning_move.add(p.clone());
        
        return new_s;
    }
 


    /**
     * 
     * {@inheritDoc}
     * <p>Surchargé car les actions ne sont pas standards<p>
     * @param idx indice de la colonne
     * @return vrai si encore de l'espace dans cette colonne
     */ 
    public boolean isLegal(int idx) {
        return this.getFreeRow(idx) != -1;
    }


    /**
     * {@inheritDoc}
     * <p>Pour ce jeu on choisi la colonne, et la pièce tombe.</p>
     * 
     * @param col l'index de la case
     */
    
	public void play(int col) {
                
        // récupérer la première ligne vide a cette colonne 
        int row = this.getFreeRow(col);
        
        // poser la pièce
        this.board[getPositionAt(row,col)] = (char) player_to_move;

        // enregistrer le coup (pour l'affichage) 
        this.last_action = new Pair (row, col);
        
        // Mettre a jour la valeur du jeu 
        this.updateGameValue();
        
        // change de joueur
        this.player_to_move = ( this.player_to_move == X ? O : X); 
    }

    /**
     * Fonction d'évaluation pour Connect Four.
     * Plus il y a de séquences potentielles gagnantes pour un joueur, plus la valeur est élevée.
     * Le joueur X cherche à maximiser cette valeur, tandis que O cherche à la minimiser.
     *
     * @return Score d'évaluation de l'état du jeu.
     */
    protected double evaluationFunction() {
        int scoreX = evaluateForPlayer(X);
        int scoreO = evaluateForPlayer(O);
        return scoreX - scoreO; // Différence entre opportunités pour X et O
    }

    /**
     * Évalue l'avantage d'un joueur en fonction des séquences ouvertes sur la grille.
     */
    private int evaluateForPlayer(int player) {
        int score = 0;

        // Vérifie les lignes, colonnes et diagonales
        score += countPotentialLines(player, 1, 0);  // Lignes horizontales
        score += countPotentialLines(player, 0, 1);  // Colonnes verticales
        score += countPotentialLines(player, 1, 1);  // Diagonales montantes
        score += countPotentialLines(player, 1, -1); // Diagonales descendantes

        return score;
    }

    /**
     * Compte les séquences ouvertes pour un joueur dans une direction donnée.
     *
     * @param player Joueur (X ou O)
     * @param dRow Déplacement en ligne
     * @param dCol Déplacement en colonne
     * @return Score basé sur les lignes partiellement remplies
     */
    private int countPotentialLines(int player, int dRow, int dCol) {
        int count = 0;

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                int consecutive = 0;
                int empty = 0;

                for (int i = 0; i < this.streak; i++) {
                    int r = row + i * dRow;
                    int c = col + i * dCol;

                    if (r >= 0 && r < this.rows && c >= 0 && c < this.cols) {
                        char cell = this.getValueAt(r, c);

                        if (cell == player) consecutive++; // Pion du joueur
                        else if (cell == EMPTY) empty++;  // Case vide
                    }
                }

                // Seules les lignes ouvertes sont comptabilisées
                if (consecutive > 0 && consecutive + empty == this.streak) {
                    count += Math.pow(10, consecutive); // Priorité aux lignes plus longues
                }
            }
        }

        return count;
    }

    /**
	 * Retourne la premier case vide de la colonne col 
     * -1 si c'est full
     */
	private int getFreeRow(int col) {
        if (col>=0 && col <this.cols)
            for (int r=0; r<this.rows; r++)
                if(this.getValueAt(r, col) == EMPTY)
                    return r;
        return -1;
    }
}
