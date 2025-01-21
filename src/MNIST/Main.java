package MNIST;
import java.io.*;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        int[] etiquettes = LabelCrawler.getEtiquettes("train-labels.idx1-ubyte");
        DataInputStream reader = new DataInputStream(new FileInputStream("MNIST/train-images.idx3-ubyte"));
        int magic = reader.readInt();
        int nbImages = reader.readInt();
        int rows = reader.readInt();
        int cols = reader.readInt();
        int[][] pixels;
        ArrayList<Imagette> images = new ArrayList<Imagette>();
        for (int i = 0; i < 500; i++)
        {
            pixels = new int[cols][rows];
            for (int j = 0; j < rows; j++)
            {
                for (int k = 0; k < cols; k++)
                {
                    pixels[k][j] = reader.readUnsignedByte();
                }
            }
            images.add(new Imagette(pixels, etiquettes[i]));
        }
        int compteur = 0;
        /*for (Imagette img : images)
        {
            System.out.println(img);
            img.createImage(String.valueOf(compteur));
            compteur++;
        }*/

        int[] etiquettesDevin = LabelCrawler.getEtiquettes("t10k-labels.idx1-ubyte");

        AlgoClassification algo = new PlusProche(images);
        Statistiques stats = new Statistiques(algo);

        reader = new DataInputStream(new FileInputStream("MNIST/t10k-images.idx3-ubyte"));
        int magicD = reader.readInt();
        int nbImagesD = reader.readInt();
        int rowsD = reader.readInt();
        int colsD = reader.readInt();
        int[][] pixelsD;
        ArrayList<Imagette> imagesD = new ArrayList<Imagette>();
        for (int i = 0; i < 500; i++)
        {
            pixelsD = new int[cols][rows];
            for (int j = 0; j < rows; j++)
            {
                for (int k = 0; k < cols; k++)
                {
                    pixelsD[k][j] = reader.readUnsignedByte();
                }
            }
            imagesD.add(new Imagette(pixelsD, etiquettesDevin[i]));
        }
        System.out.println("la précision est "+stats.statistiquer(imagesD));


    }
}