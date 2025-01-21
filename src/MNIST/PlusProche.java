package MNIST;

public class PlusProche extends AlgoClassification {

    public PlusProche(Imagette[] trainImages, int[] trainLabels) {
        super(trainImages, trainLabels);
    }

    @Override
    public int predire(Imagette image) {
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;
        for(int i = 0; i < trainImages.length; i++) {
            int distance = 0;
            for(int j = 0; j < trainImages[i].getData().length; j++) {
                distance += Math.abs(trainImages[i].getData()[j] - image.getData()[j]);
            }
            if(distance < minDistance) {
                minDistance = distance;
                minIndex = i;
            }
        }
        return trainLabels[minIndex];
    }
}
