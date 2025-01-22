package MNIST;

public class MainStatistiques {

    public static void main(String[] args) {
        Donnees ImageEntrainement = new Donnees("MNIST/train-images.idx3-ubyte");
        Etiquette LabelEntrainement = new Etiquette("MNIST/train-labels.idx1-ubyte");
        Imagette[] imagettes = ImageEntrainement.getImagettes();
        ImageEntrainement.chargerEtiquette(LabelEntrainement);

        Donnees ImageTest = new Donnees("MNIST/t10k-images.idx3-ubyte");
        Etiquette LabelTest = new Etiquette("MNIST/t10k-labels.idx1-ubyte");

        Imagette[] imagettesTest = ImageTest.getImagettes();
        ImageTest.chargerEtiquette(LabelTest);

        PlusProche pp = new PlusProche(ImageEntrainement.getImagettes(), LabelEntrainement.getEtiquettes());
        for(int i = 0; i < 50; i++) {
            int p = pp.predire(imagettesTest[i]);
            System.out.println("Première image test : " + p + " (attendu : " + imagettesTest[i].getEtiquette() + ")");
        }


        Statistiques stats = new Statistiques(pp, imagettesTest);
        System.out.println("Taux d'erreur : " + stats.getTauxErreur());

        kNN knn = new kNN(ImageEntrainement.getImagettes(), LabelEntrainement.getEtiquettes());
        for(int i = 0; i < 50; i++) {
            int p = knn.predire(imagettesTest[i]);
            System.out.println("Première image test : " + p + " (attendu : " + imagettesTest[i].getEtiquette() + ")");
        }

        Statistiques stats2 = new Statistiques(knn, imagettesTest);
        System.out.println("Taux d'erreur : " + stats2.getTauxErreur());
    }
}
