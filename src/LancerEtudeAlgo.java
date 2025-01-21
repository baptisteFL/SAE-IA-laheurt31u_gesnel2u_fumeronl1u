import ia.problemes.Dummy;
import ia.framework.recherche.TreeSearch;
import ia.algo.recherche.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LancerEtudeAlgo {
    public static void main(String[] args) {
        int[] sizes = {102, 105}; // tailles du graphe
        int[] branches = {3, 6}; // facteurs de branchement
        long seed = 12345; // graine aléatoire

        List<String[]> results = new ArrayList<>();
        results.add(new String[]{"Algo", "Taille du graphe", "Facteurs de branchements", "Temps d'éxécution (ms)", "États visités"});

        for (int size : sizes) {
            for (int branch : branches) {
                Dummy problem = new Dummy(size, branch, seed);
                runAlgorithm(new BFS(problem, Dummy.initialState()), "BFS", size, branch, results);
                runAlgorithm(new DFS(problem, Dummy.initialState()), "DFS", size, branch, results);
                runAlgorithm(new UCS(problem, Dummy.initialState()), "UCS", size, branch, results);
                runAlgorithm(new AStar(problem, Dummy.initialState()), "AStar", size, branch, results);
                runAlgorithm(new GFS(problem, Dummy.initialState()), "GFS", size, branch, results);
                runAlgorithm(new RandomSearch(problem, Dummy.initialState()), "RandomSearch", size, branch, results);
                runAlgorithm(new RandomTreeSearch(problem, Dummy.initialState()), "RandomTreeSearch", size, branch, results);
            }
        }

        writeCSV("results.csv", results);
    }

    private static void runAlgorithm(TreeSearch algorithm, String name, int size, int branch, List<String[]> results) {
        long startTime = System.currentTimeMillis();
        boolean solved = algorithm.solve();
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        int nodesExplored = algorithm.getStateVisitedCount();

        results.add(new String[]{name, String.valueOf(size), String.valueOf(branch), String.valueOf(timeTaken), String.valueOf(nodesExplored)});
    }

    private static void writeCSV(String fileName, List<String[]> data) {
        try (FileWriter csvWriter = new FileWriter(fileName)) {
            for (String[] rowData : data) {
                csvWriter.append(String.join(",", rowData));
                csvWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}