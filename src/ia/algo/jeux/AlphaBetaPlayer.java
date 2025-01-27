package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Game;
import ia.framework.jeux.Player;

import java.util.List;

public class AlphaBetaPlayer extends Player {

    private final int maxDepth;

    public AlphaBetaPlayer(Game game, boolean playerOne, int maxDepth) {
        super(game, playerOne);
        this.maxDepth = maxDepth;
        this.name = "AlphaBeta";
    }

    @Override
    public Action getMove(GameState state) {
        if (this.player == PLAYER1) {
            return maxValue(state, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        } else {
            return minValue(state, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        }
    }

    private ActionValuePair maxValue(GameState state, int depth, double alpha, double beta) {
        this.incStateCounter();

        if (state.isFinalState() || depth == maxDepth) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMax = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        List<Action> actions = game.getActions(state);

        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action); // Nous pouvons utiliser GameState directement
            ActionValuePair result = minValue(nextState, depth + 1, alpha, beta);

            if (result.getValue() > vMax) {
                vMax = result.getValue();
                bestAction = action;
            }

            alpha = Math.max(alpha, vMax);
            if (vMax >= beta) {
                break; // Coupure Alpha-Beta
            }
        }

        return new ActionValuePair(bestAction, vMax);
    }

    private ActionValuePair minValue(GameState state, int depth, double alpha, double beta) {
        this.incStateCounter();

        if (state.isFinalState() || depth == maxDepth) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMin = Double.POSITIVE_INFINITY;
        Action bestAction = null;
        List<Action> actions = game.getActions(state);

        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action); // Nous pouvons utiliser GameState directement
            ActionValuePair result = maxValue(nextState, depth + 1, alpha, beta);

            if (result.getValue() < vMin) {
                vMin = result.getValue();
                bestAction = action;
            }

            beta = Math.min(beta, vMin);
            if (vMin <= alpha) {
                break; // Coupure Alpha-Beta
            }
        }

        return new ActionValuePair(bestAction, vMin);
    }
}
