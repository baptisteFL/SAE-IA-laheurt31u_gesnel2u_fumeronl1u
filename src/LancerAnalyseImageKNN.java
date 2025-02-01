import MNIST.Donnees;
import MNIST.Etiquette;
import MNIST.Imagette;
import MNIST.KNN;
import ia.framework.common.ArgParse;
import ia.framework.recherche.MLP;
import ia.framework.recherche.TransferFunction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class LancerAnalyseImageKNN
{

    public static void main(String[] args) throws IOException
    {
        // fixer le message d'aide
        ArgParse.setUsage
                ("Utilisation :\n\n"
                        + "java LancerMLP [-k entier] "
                        + "[-h]\n\n"
                        + "-k : Nombre de voisins. Par défaut 3\n"
                );

        // Étape 1 : Configuration
        int k = ArgParse.getKFromCmd(args);

        Donnees ImageEntrainement = new Donnees("MNIST/fashion/train-images.idx3-ubyte");
        Etiquette LabelEntrainement = new Etiquette("MNIST/fashion/train-labels.idx1-ubyte");

        Donnees ImageTest = new Donnees("MNIST/fashion/t10k-images.idx3-ubyte");
        Etiquette LabelTest = new Etiquette("MNIST/fashion/t10k-labels.idx1-ubyte");
        ImageEntrainement.chargerEtiquette(LabelEntrainement);
        ImageTest.chargerEtiquette(LabelTest);
        Imagette[] imagettesTrain = ImageEntrainement.getImagettes();
        int[] labelsTrain = LabelEntrainement.getEtiquettes();
        Imagette[] imagettesTest = ImageTest.getImagettes();
        int[] labelsTest = LabelTest.getEtiquettes();


        // Étape 2 : Créer le réseau
        KNN reseau = new KNN(imagettesTrain, labelsTrain, k);
        int score = 0;

        // Étape 3 : Lancer le réseau
        long start = System.currentTimeMillis();
        for (int i = 0; i < imagettesTest.length; i++)
        {
            int prediction = reseau.predire(imagettesTest[i]);
            if (prediction == labelsTest[i])
            {
                score++;
            }
        }
        long total = System.currentTimeMillis() - start;

        // Étape 4 : Sauvegarde des résultats
        FileWriter fw = new FileWriter("docs/resultImageFashionKNN.csv", true);
        fw.write(k+";"+score+";"+imagettesTest.length+";"+total+"\n");
        fw.close();
    }
}
