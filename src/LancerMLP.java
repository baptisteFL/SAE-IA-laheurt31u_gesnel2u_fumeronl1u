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
                {0, 0},
                {1, 1},
                {1, 0},
                {0, 0}
        };

        double[][] inputTest = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };

        // Étape 3 : Apprentissage
        double[] errMoy = new double[inputEntrainement.length];
        int i = 0;
        long startTimeApprentissage = System.currentTimeMillis();
        while (i < maxIterations)
        {
            double err = 0;
            for (int j = 0; j < inputEntrainement.length; j++)
            {
                double[] prediction = reseau.execute(inputEntrainement[j]);
                err += reseau.backPropagate(inputEntrainement[j], outputEntrainement[j]);
            }
            errMoy[i%inputEntrainement.length] = err / inputEntrainement.length;
            if (errMoy[i%inputEntrainement.length] < 0.01)
            {
                break;
            }
            i++;
        }
        long totalTimeApprentissage = System.currentTimeMillis() - startTimeApprentissage;
        double erreurMoyenneFinale = Arrays.stream(errMoy).reduce(0, Double::sum) / errMoy.length;

        // Étape 4 : Test
        int score = 0;
        double seuil = 0.1;
        long startTimeTest = System.currentTimeMillis();
        for (int j = 0; j < inputTest.length; j++)
        {
            double[] prediction = reseau.execute(inputTest[j]);
            System.out.println("Prédiction " + Arrays.toString(inputTest[j]) + " : " + Arrays.toString(prediction));
            for (int k = 0; k < outputEntrainement[j].length; k++)
            {
                if (Math.abs(prediction[k] - outputEntrainement[j][k]) < seuil)
                {
                    score++;
                }
            }
        }
        long totalTimeTest = System.currentTimeMillis() - startTimeTest;

        score = score / outputEntrainement.length;
        System.out.println("Score : " + score + "/" + inputTest.length);

        // Étape 5 : Sauvegarde
        FileWriter writer = new FileWriter("docs/resultXOR2Sorties.csv", true);
        String fonctionString = fonction.getClass().toString().contains("Sigmoid") ? "sigmoid" : "tanh";
        writer.write(couches.length + ";" + Arrays.toString(couches) + ";" + i + ";" + pasApprentissage + ";" + fonctionString + ";" + erreurMoyenneFinale + ";" + totalTimeApprentissage + ";" + score + "\n");
        writer.close();
    }
}
