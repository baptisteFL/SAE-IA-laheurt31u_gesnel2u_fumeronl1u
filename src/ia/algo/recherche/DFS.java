package ia.algo.recherche;

import java.util.Stack;
import java.util.ArrayList;

import ia.framework.common.Misc;
import ia.framework.common.State;
import ia.framework.common.Action;
import ia.framework.common.ArgParse;

import ia.framework.recherche.TreeSearch;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.SearchNode;

public class DFS extends TreeSearch {

    public DFS(SearchProblem prob, State intial_state) {
        super(prob, intial_state);
    }

    @Override
    public boolean solve() {
        // On commence à l'état initial
        SearchNode node = SearchNode.makeRootSearchNode(this.initial_state);
        State state = node.getState();

        if (ArgParse.DEBUG)
            System.out.print("[\n" + state);

        // On crée une pile pour les noeuds à explorer
        Stack<SearchNode> frontier = new Stack<>();
        frontier.push(node);

        while (!frontier.isEmpty()) {
            // On récupère le dernier noeud de la pile
            node = frontier.pop();
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

            // Ajouter les noeuds enfants à la pile
            for (Action a : actions) {
                SearchNode child = SearchNode.makeChildSearchNode(problem, node, a);
                frontier.push(child);
            }
        }

        if (ArgParse.DEBUG)
            System.out.println("]");

        return false;
    }
}