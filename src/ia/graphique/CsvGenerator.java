package ia.graphique;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CsvGenerator {

    /**
     * Génère un fichier CSV contenant les résultats des tests.
     *
     * @param fileName Nom du fichier CSV à créer.
     * @param results  Liste des résultats, chaque résultat est une map contenant les colonnes et leurs valeurs.
     * @param columns  Liste des colonnes à inclure dans le CSV.
     * @throws IOException En cas d'erreur d'écriture du fichier.
     */
    public static void generateCsv(String fileName, List<Map<String, String>> results, List<String> columns) throws IOException {
        try  {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < columns.size(); i++) {
                writer.append(columns.get(i));
                if (i < columns.size() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            // Écrire les données
            for (Map<String, String> result : results) {
                for (int i = 0; i < columns.size(); i++) {
                    String column = columns.get(i);
                    writer.append(result.get(column));
                    if (i < columns.size() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }

            System.out.println("Fichier généré " + fileName);
        } catch (IOException e) {
            System.out.println("Erreur lors de la génération du fichier CSV");
            throw e;
        }
    }
}