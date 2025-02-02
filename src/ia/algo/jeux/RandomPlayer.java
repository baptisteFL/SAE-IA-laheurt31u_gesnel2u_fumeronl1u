package ia.algo.jeux;


import ia.framework.common.Action;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;

/**
 * Définie un joueurAléatoire
 */

public class RandomPlayer extends Player {

    /**
     * Crée un joueur Aléatoire
     *
     * @param g  l'instance du jeux
     * @param p1 vrai si joueur 1
     */
    public RandomPlayer(Game g, boolean p1) {
        super(g, p1);
        name = "Random";
    }


    /**
     * {@inheritDoc}
     * <p>Retourn un coup aléatoire</p>
     */
    public Action getMove(GameState state) {
        this.incStateCounter();
        Action a = game.getRandomMove(state);

        if (a == null) {
            System.err.println("ERREUR: RandomPlayer n'a trouvé aucune action disponible !");
            System.err.println("État du jeu: " + state);
            System.err.println("Actions possibles: " + game.getActions(state));
            return null;  // On peut aussi retourner une action par défaut si besoin
        }

        return a;
    }



}
