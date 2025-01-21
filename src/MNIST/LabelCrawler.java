package MNIST;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
public class LabelCrawler
{
    public static int[] getEtiquettes(String fichier) throws IOException
    {
        DataInputStream reader = new DataInputStream(new FileInputStream("data/"+fichier));
        int magic = reader.readInt();
        int nbLabel = reader.readInt();
        int[] etiquettes = new int[nbLabel];
        for (int i = 0; i < nbLabel; i++)
        {
            int label = reader.readByte();
            etiquettes[i] = label;
        }
        return etiquettes;

    }
}
