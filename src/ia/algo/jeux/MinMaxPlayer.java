package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Player;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import java.util.List;

public class MinMaxPlayer extends Player {

    private static final int MAX_DEPTH = 5; // Profondeur maximale de recherche

    public MinMaxPlayer(Game game, boolean playerOne) {
        super(game, playerOne);
        this.name = "MinMax";
    }

    @Override
    public Action getMove(GameState state) {
        if (this.player == PLAYER1) {
            return maxValue(state, 0).getAction();
        } else {
            return minValue(state, 0).getAction();
        }
    }

    private ActionValuePair maxValue(GameState state, int depth) {
        this.incStateCounter();

        // Si l'état est final ou si la profondeur maximale est atteinte
        if (state.isFinalState() || depth >= MAX_DEPTH) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMax = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        List<Action> actions = game.getActions(state); // Obtenir toutes les actions possibles

        // Pour chaque action possible, on explore les futurs états du jeu
        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action); // Appliquer l'action
            ActionValuePair result = minValue(nextState, depth + 1); // Appeler minValue pour le joueur adverse

            if (result.getValue() > vMax) { // Maximiser la valeur pour MAX
                vMax = result.getValue();
                bestAction = action;
            }
        }

        return new ActionValuePair(bestAction, vMax); // Retourner l'action avec la meilleure valeur
    }

    private ActionValuePair minValue(GameState state, int depth) {
        this.incStateCounter();

        // Si l'état est final ou si la profondeur maximale est atteinte
        if (state.isFinalState() || depth >= MAX_DEPTH) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMin = Double.POSITIVE_INFINITY;
        Action bestAction = null;
        List<Action> actions = game.getActions(state); // Obtenir toutes les actions possibles

        // Pour chaque action possible, on explore les futurs états du jeu
        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action); // Appliquer l'action
            ActionValuePair result = maxValue(nextState, depth + 1); // Appeler maxValue pour le joueur adverse

            if (result.getValue() < vMin) { // Minimiser la valeur pour MIN
                vMin = result.getValue();
                bestAction = action;
            }
        }

        return new ActionValuePair(bestAction, vMin); // Retourner l'action avec la meilleure valeur
    }
}
