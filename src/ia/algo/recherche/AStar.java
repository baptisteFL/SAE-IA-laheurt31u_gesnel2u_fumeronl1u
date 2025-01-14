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
import java.util.PriorityQueue;

public class AStar extends TreeSearch
{
    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public AStar(SearchProblem p, State s)
    {
        super(p, s);
    }

    @Override
    public boolean solve()
    {
        // On commence à l'état initial
        SearchNode node = SearchNode.makeRootSearchNode(this.initial_state);
        State state = node.getState();

        if (ArgParse.DEBUG)
            System.out.print("[\n" + state);

        // On crée une file pour les noeuds à explorer
        PriorityQueue<SearchNode> frontier = new PriorityQueue<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                return (int) (o1.getCost() + o1.getHeuristic() - o2.getCost() - o2.getHeuristic());
            }
        });
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
                if (ArgParse.DEBUG) {
                    System.out.println("Ajout de " + child.getState() + " à la frontière");
                }
                frontier.add(child);
            }
        }

        return false;
    }
}
