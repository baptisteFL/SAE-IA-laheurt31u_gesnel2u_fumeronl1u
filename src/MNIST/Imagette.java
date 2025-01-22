package MNIST;

public class Imagette {

    int[] data;
    int etiquette;

    public Imagette(int etiquette, int [] data) {
        this.etiquette = etiquette;
        this.data = data;
    }

    public int getEtiquette() {
        return etiquette;
    }

    public int[] getData() {
        return data;
    }

    public void setEtiquette(int etiquette) {
        this.etiquette = etiquette;
    }
}
