import ia.framework.common.ArgParse;
import ia.framework.common.State;
import ia.framework.recherche.SearchProblem;
import ia.framework.recherche.TreeSearch;
import ia.problemes.Dummy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class LancerEtudeAlgo {

    public static void main(String[] args) throws IOException {
        // Paramètres de l'étude
        int[] taillesGraphe = {10,100,1000, 2000,3000,4000,5000,6000}; // Très simple à très difficile
        int[] facteursBranchement = {2,4,6,8}; // Facteurs de branchement

        // Fichier CSV pour enregistrer les résultats
        String fileName = "docs/resultEtudeAlgo.csv";
        FileWriter writer = new FileWriter(fileName, true);
        writer.write("Algorithme;TailleGraphe;FacteurBranchement;TempsExecution;SolutionTrouvee\n");

        // Algorithmes à comparer
        String[] algos = {"astar", "bfs", "dfs", "gfs", "ucs"};

        for (int taille : taillesGraphe) {
            for (int facteur : facteursBranchement) {
                // Créer une instance de Dummy
                Dummy problem = new Dummy(taille, facteur, 1234);

                for (String algoName : algos) {
                    // Créer l'état initial et l'algorithme
                    State initialState = Dummy.initialState();
                    TreeSearch algo = ArgParse.makeAlgo(algoName, problem, initialState);

                    // Mesurer le temps d'exécution
                    long startTime = System.currentTimeMillis();
                    boolean solved = algo.solve();
                    long estimatedTime = System.currentTimeMillis() - startTime;

                    double cost = solved ? algo.getEndNode().getCost() : -1;

                    // Enregistrer les résultats
                    writer.write(algoName + ";" + taille + ";" + facteur + ";" + estimatedTime + ";" + solved + ";" + cost +"\n");
                }
            }
        }

        writer.close();
        System.out.println("Étude terminée, résultats enregistrés dans " + fileName);
    }
}