package MNIST;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Etiquette {

    int[] etiquettes;

    public Etiquette(String path) {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(path));

            int type = dis.readInt();
            if(type != 2049) {
                throw new RuntimeException("??");
            }

            int nbElements = dis.readInt();
            etiquettes = new int[nbElements];

            for(int i = 0; i < nbElements; i++) {
                etiquettes[i] = dis.readUnsignedByte();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getEtiquette(int index) {
        return etiquettes[index];
    }

    public int[] getEtiquettes() {
        return etiquettes;
    }
}