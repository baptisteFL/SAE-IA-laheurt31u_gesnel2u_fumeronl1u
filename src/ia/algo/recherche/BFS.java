package ia.algo.recherche;

import java.util.LinkedList;
import java.util.ArrayList;

import ia.framework.common.Misc;
import ia.framework.common.State;
import ia.framework.common.Action;
import ia.framework.common.ArgParse;

import ia.framework.recherche.TreeSearch;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.SearchNode;

public class BFS extends TreeSearch {

    public BFS(SearchProblem prob, State intial_state) {
        super(prob, intial_state);
    }

    @Override
    public boolean solve() {
        // On commence à l'état initial
        SearchNode node = SearchNode.makeRootSearchNode(this.initial_state);
        State state = node.getState();

        if (ArgParse.DEBUG)
            System.out.print("[\n" + state);

        // On crée une file pour les noeuds à explorer
        LinkedList<SearchNode> frontier = new LinkedList<>();
        frontier.add(node);

        while (!frontier.isEmpty()) {
            // On récupère le premier noeud de la file
            node = frontier.poll();
            state = node.getState();

            if (ArgParse.DEBUG)
                System.out.print(" + " + state + "] -> [");

            // Vérifier si l'état actuel est l'état but
            if (problem.isGoalState(state)) {
                end_node = node;
                if (ArgParse.DEBUG)
                    System.out.println("]");
                return true;
            }

            // Les actions possibles depuis cet état
            ArrayList<Action> actions = problem.getActions(state);

            if (ArgParse.DEBUG) {
                System.out.print("Actions Possibles : {");
                System.out.println(Misc.collection2string(actions, ','));
            }

            // Ajouter les noeuds enfants à la file
            for (Action a : actions) {
                SearchNode child = SearchNode.makeChildSearchNode(problem, node, a);
                frontier.add(child);
            }
        }

        if (ArgParse.DEBUG)
            System.out.println("]");

        return false;
    }
}