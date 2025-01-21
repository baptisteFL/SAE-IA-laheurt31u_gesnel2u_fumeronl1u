import ia.framework.common.ArgParse;
import ia.framework.recherche.MLP;
import ia.framework.recherche.TransferFunction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class LancerMLP {

    public static void main(String[] args) throws IOException
    {
        // fixer le message d'aide
        ArgParse.setUsage
                ("Utilisation :\n\n"
                        + "java LancerMLP [-c entier] "
                        + "[-p double] "
                        + "[-i entier] "
                        + "[-f string]"
                        + "[-h]\n\n"
                        + "-c : Nombre de couches. Par défaut 3\n"
                        + "-p : Pas d'apprentissage. Par défaut 0.1\n"
                        + "-i : Nombre maximum d'itérations. Par défaut 10000\n"
                        + "-f : Fonction d'activation {sigmoid, tanh}. Par défaut sigmoid\n"
                );

        // Étape 1 : Configuration
        int[] couches = new int[ArgParse.getLayersFromCmd(args)];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < couches.length; i++)
        {
            System.out.println("Entrez le nombre de neurones pour la couche " + (i + 1) + " : ");
            couches[i] = sc.nextInt();
        }

        double pasApprentissage = ArgParse.getLearningRateFromCmd(args);

        int maxIterations = ArgParse.getIterationsFromCmd(args);

        TransferFunction fonction = ArgParse.getActivationFunctionFromCmd(args);

        MLP reseau = new MLP(couches, pasApprentissage, fonction);

        // Étape 2 : Préparer les données
        double[][] inputEntrainement = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        double[][] outputEntrainement = {
                {0},
                {1},
                {1},
                {0}
        };

        double[][] inputTest = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        // Étape 3 : Apprentissage
        for (int i = 0; i < maxIterations; i++)
        {
            double errMoy = reseau.backPropagate(inputEntrainement[i % inputEntrainement.length], outputEntrainement[i % outputEntrainement.length]);
            System.out.println("Erreur à l'itération " + i + " : " + errMoy);
        }

        // Étape 4 : Test
        int score = 0;
        double seuil = 0.1;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < inputTest.length; i++)
        {
            double[] prediction = reseau.execute(inputTest[i]);
            System.out.println("Prédiction " + Arrays.toString(inputTest[i]) + " : " + Arrays.toString(prediction));
            for (int j = 0; j < outputEntrainement[i].length; j++)
            {
                if (Math.abs(prediction[j] - outputEntrainement[i][j]) < seuil)
                {
                    score++;
                }
            }
        }
        long totalTime = System.currentTimeMillis() - startTime;

        System.out.println("Score : " + score + "/" + inputTest.length);

        // Étape 5 : Sauvegarde
//        FileWriter writer = new FileWriter("docs/resultXOR.csv", true);
//        String fonctionString = fonction.getClass().toString().contains("Sigmoid") ? "sigmoid" : "tanh";
//        writer.write(couches.length + ";" + Arrays.toString(couches) + ";" + maxIterations + ";" + pasApprentissage + ";" + fonctionString + ";" + score + ";" + totalTime+"\n");
//        writer.close();
    }
}
