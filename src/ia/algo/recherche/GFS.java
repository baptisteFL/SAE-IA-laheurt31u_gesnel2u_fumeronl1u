package ia.algo.recherche;

import ia.framework.common.Action;
import ia.framework.common.ArgParse;
import ia.framework.common.Misc;
import ia.framework.common.State;
import ia.framework.recherche.SearchNode;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class GFS extends TreeSearch {
    public GFS(final SearchProblem prob, final State initial_state) {
        super(prob, initial_state);
    }

    @Override
    public boolean solve() {
        SearchNode node = SearchNode.makeRootSearchNode(this.initial_state);
        State state = node.getState();
        if (ArgParse.DEBUG) {
            System.out.print("[\n" + state);
        }
        final PriorityQueue<SearchNode> frontier = new PriorityQueue<>(new Comparator<SearchNode>() {
            @Override
            public int compare(final SearchNode o1, final SearchNode o2) {
                return (int) (o1.getHeuristic() - o2.getHeuristic());
            }
        });
        frontier.add(node);

        // Create a set to keep track of visited states
        HashSet<State> visited = new HashSet<>();
        visited.add(state);

        while (!frontier.isEmpty()) {
            node = frontier.poll();
            state = node.getState();
            if (ArgParse.DEBUG) {
                System.out.print(" + " + state + "] -> [");
            }
            if (this.problem.isGoalState(state)) {
                this.end_node = node;
                if (ArgParse.DEBUG) {
                    System.out.println("]");
                }
                return true;
            }
            final ArrayList<Action> actions = this.problem.getActions(state);
            if (ArgParse.DEBUG) {
                System.out.print("Actions Possibles : {");
                System.out.println(Misc.collection2string(actions, ','));
            }
            for (final Action a : actions) {
                final SearchNode child = SearchNode.makeChildSearchNode(this.problem, node, a);
                State childState = child.getState();
                if (!visited.contains(childState)) {
                    frontier.add(child);
                    visited.add(childState);
                    if (ArgParse.DEBUG) {
                        System.out.println("Ajout de " + childState + " à la frontière");
                    }
                }
            }
        }
        return false;
    }
}