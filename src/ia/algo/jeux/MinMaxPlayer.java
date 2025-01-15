package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.common.State;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;

import java.util.ArrayList;

public class MinMaxPlayer extends Player {
    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeux
     * @param player_one si joueur 1
     */
    public MinMaxPlayer(Game g, boolean player_one) {
        super(g, player_one);
        name = "MinMax";
    }

    @Override
    public Action getMove(GameState state) {
        if (state.getPlayerToMove() == PLAYER1) {
            return maxValue(state).getAction();
        } else {
            return minValue(state).getAction();
        }
    }

    private ActionValuePair maxValue(GameState state) {
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMax = Double.NEGATIVE_INFINITY;
        Action cMax = null;

        ArrayList<Action> actions = game.getActions((State) state);
        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action);
            ActionValuePair result = minValue(nextState);
            if (result.getValue() > vMax) {
                vMax = result.getValue();
                cMax = action;
            }
        }

        return new ActionValuePair(cMax, vMax);
    }

    private ActionValuePair minValue(GameState state) {
        if (game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vMin = Double.POSITIVE_INFINITY;
        Action cMin = null;

        ArrayList<Action> actions = game.getActions((State) state);
        for (Action action : actions) {
            GameState nextState = (GameState) game.doAction(state, action);
            ActionValuePair result = maxValue(nextState);
            if (result.getValue() < vMin) {
                vMin = result.getValue();
                cMin = action;
            }
        }

        return new ActionValuePair(cMin, vMin);
    }
}