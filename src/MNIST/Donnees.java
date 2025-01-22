package MNIST;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Donnees {

    final boolean DEBUG = true;

    int[][] imagettes;

    Imagette[] imagettes2;

    public Donnees(String path) {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(path));

            int type = dis.readInt();
            if(type != 2051) {
                throw new RuntimeException("??");
            }

            int nbImages = dis.readInt();
            if(DEBUG) {
                nbImages = 1000;
            }
            int nbLignes = dis.readInt();
            int nbColonnes = dis.readInt();

            imagettes = new int[nbImages][nbLignes * nbColonnes];

            for(int i = 0; i < 100; i++) {
                for(int j = 0; j < nbLignes * nbColonnes; j++) {
                    imagettes[i][j] = dis.readUnsignedByte();
                }
            }

            imagettes2 = new Imagette[nbImages];
            for(int i = 0; i < nbImages; i++) {
                // Le I dans imagette devra être changé (étiquette, nombre représentant l'image)
                imagettes2[i] = new Imagette(i, imagettes[i]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sauverImage(int[] image, String path) {
        BufferedImage img = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        for(int i = 0; i < 28; i++) {
            for(int j = 0; j < 28; j++) {
                int pixel = image[i * 28 + j];
                img.setRGB(j, i, pixel | (pixel << 8) | (pixel << 16));
            }
        }

        try {
            ImageIO.write(img, "png", new File("MNIST/" + path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Imagette getImagette(int index) {
        return imagettes2[index];
    }

    public Imagette[] getImagettes() {
        return imagettes2;
    }

    public void chargerEtiquette(Etiquette etiquettes) {
        for(int i = 0; i < imagettes2.length; i++) {
            imagettes2[i].setEtiquette(etiquettes.getEtiquette(i));
        }
    }
}