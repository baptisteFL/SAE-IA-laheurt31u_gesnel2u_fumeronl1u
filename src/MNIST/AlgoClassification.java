package MNIST;

public abstract class AlgoClassification {

    Imagette[] trainImages;
    int[] trainLabels;

    public AlgoClassification(Imagette[] trainImages, int[] trainLabels) {
        this.trainImages = trainImages;
        this.trainLabels = trainLabels;
    }

    public abstract int predire(Imagette image);


}
