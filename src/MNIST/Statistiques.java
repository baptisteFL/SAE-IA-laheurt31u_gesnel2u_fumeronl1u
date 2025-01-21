package MNIST;
import java.util.ArrayList;

public class Statistiques
{
    AlgoClassification algo;

    public Statistiques(AlgoClassification a)
    {
        this.algo = a;
    }

    double statistiquer(ArrayList<Imagette> imgs)
    {
        int nbImg = imgs.size();
        int nbsuccess = 0;
        for (Imagette img : imgs)
        {
            if (algo.predireEtiquette(img) == img.getLabel()) nbsuccess++;
        }
        return (double) nbsuccess /nbImg;

    }
}
