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

public class AStar extends TreeSearch {
    public AStar(SearchProblem p, State s) {
        super(p, s);
    }

    @Override
    public boolean solve() {
        SearchNode node = SearchNode.makeRootSearchNode(this.initial_state);
        State state = node.getState();

        if (ArgParse.DEBUG)
            System.out.print("[\n" + state);

        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return (int) (o1.getCost() + o1.getHeuristic() - o2.getCost() - o2.getHeuristic());
            }
        });
        frontier.add(node);

        // Create a set to keep track of visited states
        HashSet<State> visited = new HashSet<>();
        visited.add(state);

        while (!frontier.isEmpty()) {
            node = frontier.poll();
            state = node.getState();

            if (ArgParse.DEBUG)
                System.out.print(" + " + state + "] -> [");

            if (problem.isGoalState(state)) {
                end_node = node;
                if (ArgParse.DEBUG)
                    System.out.println("]");
                return true;
            }

            ArrayList<Action> actions = problem.getActions(state);

            if (ArgParse.DEBUG) {
                System.out.print("Actions Possibles : {");
                System.out.println(Misc.collection2string(actions, ','));
            }

            for (Action a : actions) {
                SearchNode child = SearchNode.makeChildSearchNode(problem, node, a);
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