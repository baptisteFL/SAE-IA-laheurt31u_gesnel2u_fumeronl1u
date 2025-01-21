package MNIST;

public class MainImage {

    public static void main(String[] args) {
        Donnees imagette = new Donnees("MNIST/train-images.idx3-ubyte");

        int[] premiereImagette = imagette.getImagette(0).getData();
        imagette.sauverImage(premiereImagette, "premiereImagette.png");
        int[] derniereImagette = imagette.getImagette(imagette.getImagettes().length - 1).getData();
        imagette.sauverImage(derniereImagette, "derniereImagette.png");

        Imagette[] imagettes = imagette.getImagettes();
        for(int i = 0; i < imagettes.length; i++) {
            int[] data = imagettes[i].getData();
            imagette.sauverImage(data, "imagette" + i + ".png");
        }
    }
}
