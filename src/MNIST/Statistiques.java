package MNIST;

public class Statistiques {

    AlgoClassification algo;
    Imagette[] testImages;

    public Statistiques(AlgoClassification algo, Imagette[] testImages) {
        this.algo = algo;
        this.testImages = testImages;
    }

    public double getTauxErreur() {
        int erreur = 0;
        for(Imagette image : testImages) {
            if(algo.predire(image) != image.getEtiquette()) {
                erreur++;
            }
        }
        return (double)erreur / testImages.length;
    }
}
