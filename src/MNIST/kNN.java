package MNIST;

public class kNN extends AlgoClassification {

    public kNN(Imagette[] trainImages, int[] trainLabels) {
        super(trainImages, trainLabels);
    }

    @Override
    public int predire(Imagette image) {
        int k = 7;
        // faire une liste pour stocker les 10 plus proches voisins
        Imagette[] kPlusProches = new Imagette[k];
        // faire une liste pour stocker les distances des 10 plus proches voisins
        int[] distances = new int[k];
        // initialiser les listes
        for (int i = 0; i < k; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        // parcourir les images d'entrainement
        for (int i = 0; i < trainImages.length; i++) {
            // calculer la distance entre l'image d'entrainement et l'image de test
            int distance = 0;
            for (int j = 0; j < trainImages[i].getData().length; j++) {
                distance += Math.abs(trainImages[i].getData()[j] - image.getData()[j]);
            }
            // si la distance est plus petite que la plus grande distance des 10 plus proches voisins
            if (distance < distances[k - 1]) {
                // ajouter l'image d'entrainement et la distance aux listes
                distances[k - 1] = distance;
                kPlusProches[k - 1] = trainImages[i];
                // trier les listes
                for (int j = k - 1; j > 0; j--) {
                    if (distances[j] < distances[j - 1]) {
                        int tempDistance = distances[j];
                        distances[j] = distances[j - 1];
                        distances[j - 1] = tempDistance;
                        Imagette tempImagette = kPlusProches[j];
                        kPlusProches[j] = kPlusProches[j - 1];
                        kPlusProches[j - 1] = tempImagette;
                    }
                }
            }
        }
        // faire une liste pour stocker les étiquettes des 10 plus proches voisins
        int[] etiquettes = new int[k];
        // initialiser la liste
        for (int i = 0; i < k; i++) {
            etiquettes[i] = kPlusProches[i].getEtiquette();
        }
        // faire une liste pour stocker le nombre d'occurrences de chaque étiquette
        int[] occurrences = new int[10];
        // initialiser la liste
        for (int i = 0; i < 10; i++) {
            occurrences[i] = 0;
        }
        // compter le nombre d'occurrences de chaque étiquette
        for (int i = 0; i < k; i++) {
            occurrences[etiquettes[i]]++;
        }
        // trouver l'étiquette avec le plus grand nombre d'occurrences
        int maxOccurrences = 0;
        int maxIndex = -1;
        for (int i = 0; i < 10; i++) {
            if (occurrences[i] > maxOccurrences) {
                maxOccurrences = occurrences[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}