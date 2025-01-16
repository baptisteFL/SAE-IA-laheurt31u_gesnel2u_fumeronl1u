package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.jeux.Player;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import java.util.List;

public class MinMaxPlayer extends Player {

    public MinMaxPlayer(Game game, boolean playerOne) {
        super(game, playerOne);
        this.name = "MinMax";
    }

    @Override
    public Action getMove(GameState state) {
        if (this.player == PLAYER1) {
            return maxValue(state).getAction();
        } else {
            return minValue(state).getAction();
        }
    }

    private ActionValuePair maxValue(GameState state) {
        this.incStateCounter();
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMax = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        List<Action> actions = game.getActions(state);

        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action);
            ActionValuePair result = minValue(nextState);

            if (result.getValue() > vMax) {
                vMax = result.getValue();
                bestAction = action;
            }
        }

        return new ActionValuePair(bestAction, vMax);
    }

    private ActionValuePair minValue(GameState state) {
        this.incStateCounter();
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMin = Double.POSITIVE_INFINITY;
        Action bestAction = null;
        List<Action> actions = game.getActions(state);

        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action);
            ActionValuePair result = maxValue(nextState);

            if (result.getValue() < vMin) {
                vMin = result.getValue();
                bestAction = action;
            }
        }

        return new ActionValuePair(bestAction, vMin);
    }
}