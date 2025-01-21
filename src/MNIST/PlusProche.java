package MNIST;
import java.util.ArrayList;
import static java.lang.Math.abs;

public class PlusProche extends AlgoClassification
{

    public PlusProche(ArrayList<Imagette> imgs)
    {
        super(imgs);
    }

    @Override
    public int predireEtiquette(Imagette img)
    {
        int record = Integer.MAX_VALUE;
        int iRecord = -1;
        int diff = 0;
        int[][] imgCompPix;
        int[][] imgPix = img.getPixels();
        Imagette imgComp;
        for (int i = 0; i < imgs.size(); i++)
        {
            diff = 0;
            imgComp = imgs.get(i);
            imgCompPix = imgComp.getPixels();
            for (int j = 0; j < imgPix.length; j++)
            {
                for (int k = 0; k < imgPix[j].length; k++)
                {
                    diff += abs(imgPix[j][k] - imgCompPix[j][k]);
                }
            }
            if (diff < record)
            {
                record = diff;
                iRecord = i;
            }
        }
        System.out.println(imgs.get(iRecord).getLabel());
        if (iRecord != -1) return imgs.get(iRecord).getLabel();
        else return -1; // Aucune image trouvée pour comparer avec l'image fournie
    }
}
