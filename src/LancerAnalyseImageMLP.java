import MNIST.Donnees;
import MNIST.Etiquette;
import MNIST.Imagette;
import ia.framework.common.ArgParse;
import ia.framework.recherche.MLP;
import ia.framework.recherche.TransferFunction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class LancerAnalyseImageMLP
{

    public static void main(String[] args) throws IOException
    {
        // fixer le message d'aide
        ArgParse.setUsage
                ("Utilisation :\n\n"
                        + "java LancerMLP [-c entier] "
                        + "[-p double] "
                        + "[-f string]"
                        + "[-h]\n\n"
                        + "-c : Nombre de couches. Par défaut 3\n"
                        + "-p : Pas d'apprentissage. Par défaut 0.1\n"
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

        TransferFunction fonction = ArgParse.getActivationFunctionFromCmd(args);

        MLP reseau = new MLP(couches, pasApprentissage, fonction);


        Donnees ImageEntrainement = new Donnees("MNIST/t10k-images.idx3-ubyte");
        Etiquette LabelEntrainement = new Etiquette("MNIST/t10k-labels.idx1-ubyte");
        ImageEntrainement.chargerEtiquette(LabelEntrainement);
        Imagette[] imagettes = ImageEntrainement.getImagettes();
        // Étape 2 : Préparer les données
        double[][] inputEntrainement = new double[imagettes.length][28 * 28];
        double[][] outputEntrainement = new double[imagettes.length][10];
        double[] tabSortie;
        int[] data;
        double[] finData = new double[28 * 28];
        for (int i = 0; i < imagettes.length; i++)
        {
            data = imagettes[i].getData();
            for (int j = 0; j < 28 * 28; j++)
            {
                finData[j] = (double) data[j] / 255;
            }
            int et = imagettes[i].getEtiquette();
            tabSortie = new double[10];
            tabSortie[et] = 1.;
            //System.out.println(et);
            //System.out.println(Arrays.toString(tabSortie));
            outputEntrainement[i] = tabSortie.clone();
            inputEntrainement[i] = finData.clone();
            System.out.println(Arrays.toString(inputEntrainement[0]));
            System.out.println(Arrays.toString(inputEntrainement[1]));
            System.out.println(Arrays.toString(outputEntrainement[0]));
            System.out.println(Arrays.toString(outputEntrainement[1]));
        }
        double[][] inputTest = inputEntrainement.clone();
        // Étape 3 : Apprentissage
        System.out.println("Apprentissage");
        double[] errMoy = new double[inputEntrainement.length];
        long startTimeApprentissage = System.currentTimeMillis();
        double err = 0;
        for (int j = 0; j < inputEntrainement.length; j++)
        {
            err += reseau.backPropagate(inputEntrainement[j], outputEntrainement[j]);
            errMoy[j] = err;
        }
        long totalTimeApprentissage = System.currentTimeMillis() - startTimeApprentissage;
        double erreurMoyenneFinale = Arrays.stream(errMoy).reduce(0, Double::sum) / inputEntrainement.length;

        // Étape 4 : Test
        int score = 0;
        double seuil = 0.1;
        long startTimeTest = System.currentTimeMillis();
        for (int j = 0; j < inputTest.length; j++)
        {
            double max = -1;
            int indiceMax = 0;
            double[] prediction = reseau.execute(inputTest[j]);
            for (int k = 0; k < prediction.length; k++)
            {
                if (max < prediction[k])
                {
                    max = prediction[k];
                    indiceMax = k;
                }
            }
            double realMax = -1;
            int indiceRealMax = 0;
            for (int k = 0; k < outputEntrainement[j].length; k++)
            {
                if (realMax < outputEntrainement[j][k])
                {
                    realMax = outputEntrainement[j][k];
                    indiceRealMax = k;
                }
            }
            System.out.println("Prédiction :" + indiceMax);
            System.out.println("Résultat attendu : " + indiceRealMax);
            if (indiceMax == indiceRealMax) score++;
        }
        long totalTimeTest = System.currentTimeMillis() - startTimeTest;
        System.out.println("temps d'exécution : " + totalTimeTest + " ms");
        System.out.println("Score : " + score + "/" + inputTest.length);

        // Étape 5 : Sauvegarde
        FileWriter writer = new FileWriter("docs/resultImageTestMLP.csv", true);
        String fonctionString = fonction.getClass().toString().contains("Sigmoid") ? "sigmoid" : "tanh";
        writer.write(couches.length + ";" + Arrays.toString(couches) + ";" + pasApprentissage + ";" + fonctionString + ";" + erreurMoyenneFinale + ";" + totalTimeApprentissage + ";" + score + "\n");
        writer.close();
    }
}
