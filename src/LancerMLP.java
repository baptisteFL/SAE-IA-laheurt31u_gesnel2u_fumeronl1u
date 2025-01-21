import ia.framework.common.ArgParse;
import ia.framework.recherche.MLP;
import ia.framework.recherche.SigmoidFunction;
import ia.framework.recherche.TransferFunction;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.min;

public class LancerMLP {

    public static void main(String[] args) {
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
                {0.1, 0.2, 0.3},
                {0.4, 0.5, 0.6}
        };
        double[][] outputEntrainement = {
                {0, 1},
                {1, 0}
        };

        double[][] inputTest = {
                {0.1, 0.2, 0.3},
                {0.7, 0.8, 0.9}
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
        for (int i = 0; i < inputTest.length; i++)
        {
            double[] prediction = reseau.execute(inputTest[i]);
            System.out.println("Prédiction " + Arrays.toString(inputTest[i]) + " : " + Arrays.toString(prediction));
            if (min(Math.abs(prediction[0] - outputEntrainement[i][0]), Math.abs(prediction[1] - outputEntrainement[i][1])) < seuil)
            {
                score++;
            }
        }

        System.out.println("Score : " + score + "/" + inputTest.length);
    }
}
