package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.jeux.Player;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import java.util.List;

public class AlphaBetaPlayer extends Player {

    public AlphaBetaPlayer(Game game, boolean playerOne) {
        super(game, playerOne);
        this.name = "AlphaBeta";
    }

    @Override
    public Action getMove(GameState state) {
        if (this.player == PLAYER1) {
            return maxValue(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        } else {
            return minValue(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).getAction();
        }
    }

    private ActionValuePair maxValue(GameState state, double alpha, double beta) {
        this.incStateCounter();
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMax = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        List<Action> actions = game.getActions(state);

        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action);
            ActionValuePair result = minValue(nextState, alpha, beta);

            if (result.getValue() > vMax) {
                vMax = result.getValue();
                bestAction = action;
            }

            if (vMax > alpha) {
                alpha = vMax;
            }

            if (vMax >= beta) {
                return new ActionValuePair(bestAction, vMax);
            }
        }

        return new ActionValuePair(bestAction, vMax);
    }

    private ActionValuePair minValue(GameState state, double alpha, double beta) {
        this.incStateCounter();
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMin = Double.POSITIVE_INFINITY;
        Action bestAction = null;
        List<Action> actions = game.getActions(state);

        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action);
            ActionValuePair result = maxValue(nextState, alpha, beta);

            if (result.getValue() < vMin) {
                vMin = result.getValue();
                bestAction = action;
            }

            if (vMin < beta) {
                beta = vMin;
            }

            if (vMin <= alpha) {
                return new ActionValuePair(bestAction, vMin);
            }
        }

        return new ActionValuePair(bestAction, vMin);
    }
}