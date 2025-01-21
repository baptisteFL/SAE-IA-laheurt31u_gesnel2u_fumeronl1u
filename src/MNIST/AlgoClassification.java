package MNIST;
import java.util.ArrayList;

public abstract class AlgoClassification
{
    ArrayList<Imagette> imgs;

    public AlgoClassification(ArrayList<Imagette> i)
    {
        imgs = i;
    }

    public abstract int predireEtiquette(Imagette img);
}
