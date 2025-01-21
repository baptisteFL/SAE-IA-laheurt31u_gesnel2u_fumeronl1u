package MNIST;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Imagette
{
    private int[][] pixels;
    private int label;

    public Imagette(int[][] pixels, int l)
    {
        this.pixels = pixels;
        this.label = l;
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.label);
    }

    public int[][] getPixels()
    {
        return pixels;
    }

    public int getLabel()
    {
        return label;
    }

    public void createImage(String name) throws IOException
    {
        BufferedImage image = new BufferedImage(pixels[0].length, pixels.length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < pixels.length; i++)
        {
            for (int j = 0; j < pixels[i].length; j++)
            {
                image.setRGB(i, j, pixels[i][j] << 16 | pixels[i][j] << 8 | pixels[i][j]);
                ImageIO.write(image, "png", new File("img/"+name+".png"));
            }
        }
    }
}
