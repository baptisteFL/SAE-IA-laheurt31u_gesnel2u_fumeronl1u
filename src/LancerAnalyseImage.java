import MNIST.Donnees;
import MNIST.Etiquette;
import MNIST.Imagette;
import ia.framework.common.ArgParse;
import ia.framework.recherche.MLP;
import ia.framework.recherche.TransferFunction;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class LancerAnalyseImage
{

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


        Donnees ImageEntrainement = new Donnees("MNIST/train-images.idx3-ubyte");
        Etiquette LabelEntrainement = new Etiquette("MNIST/train-labels.idx1-ubyte");
        ImageEntrainement.chargerEtiquette(LabelEntrainement);
        Imagette[] imagettes = ImageEntrainement.getImagettes();
        // Étape 2 : Préparer les données
        double[][] inputEntrainement = new double[imagettes.length][28*28];
        double[][] outputEntrainement = new double[imagettes.length][1];
        double[] tabSortie;
        int[] data;
        double[] finData = new double[28*28];
        for (int i = 0; i < imagettes.length; i++)
        {
            System.out.println(imagettes[i].getEtiquette());
            data = imagettes[i].getData();
            for (int j = 0; j < 28*28; j++)
            {
                finData[j]= (double) data[j] /255;
            }
            int et = imagettes[i].getEtiquette();
            tabSortie = new double[10];
            for (int j = 0; j<10;j++)
            {
                tabSortie[j] = 0.;
                if (j == et) tabSortie[j] = 1.;
            }
            System.out.println(Arrays.toString(tabSortie));
            //System.out.println(et);
            //System.out.println(Arrays.toString(tabSortie));
            outputEntrainement[i] = tabSortie;
            inputEntrainement[i] = finData.clone();
        }
        double[][] inputTest = inputEntrainement.clone();
        // Étape 3 : Apprentissage
        System.out.println("j'apprends");
        double[] errMoy = new double[inputEntrainement.length];
        int i = 0;
        long startTimeApprentissage = System.currentTimeMillis();
        while (i < maxIterations)
        {
            System.out.println("apprend" + i);
            double err = 0;
            for (int j = 0; j < inputEntrainement.length; j++)
            {
                double[] prediction = reseau.execute(inputEntrainement[j]);
                err += reseau.backPropagate(inputEntrainement[j], outputEntrainement[j]);
            }
            errMoy[i%inputEntrainement.length] = err / inputEntrainement.length;
            System.out.println(errMoy[i%inputEntrainement.length]);
            if (errMoy[i%inputEntrainement.length] < 0.01)
            {
                break;
            }
            i++;
        }
        long totalTimeApprentissage = System.currentTimeMillis() - startTimeApprentissage;
        double erreurMoyenneFinale = Arrays.stream(errMoy).reduce(0, Double::sum) / errMoy.length;

        System.out.println("j'essaie");

        // Étape 4 : Test
        int score = 0;
        double seuil = 0.1;
        long startTimeTest = System.currentTimeMillis();
        for (int j = 0; j < inputTest.length; j++)
        {
            double[] prediction = reseau.execute(inputTest[j]);
            System.out.println("Prédiction " + j + " : " + Arrays.toString(prediction));
            System.out.println("Résultat attendu : " + Arrays.toString(outputEntrainement[j]));
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
    }
}
