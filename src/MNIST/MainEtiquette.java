package MNIST;

public class MainEtiquette {

    public static void main(String[] args) {
        Etiquette etiquette = new Etiquette("images/train-labels.idx1-ubyte");

        System.out.println(etiquette.getEtiquette(0));
        System.out.println(etiquette.getEtiquette(etiquette.getEtiquettes().length - 1));
    }
}
