package MNIST;

public class MainFashion {

    public static void main(String[] args) {
        Donnees ImageEntrainement = new Donnees("fashion/train-images-idx3-ubyte");
        Etiquette LabelEntrainement = new Etiquette("fashion/train-labels-idx1-ubyte");
        Imagette[] imagettes = ImageEntrainement.getImagettes();
        ImageEntrainement.chargerEtiquette(LabelEntrainement);

        Donnees ImageTest = new Donnees("fashion/t10k-images-idx3-ubyte");
        Etiquette LabelTest = new Etiquette("fashion/t10k-labels-idx1-ubyte");

        Imagette[] imagettesTest = ImageTest.getImagettes();
        ImageTest.chargerEtiquette(LabelTest);

        PlusProche pp = new PlusProche(ImageEntrainement.getImagettes(), LabelEntrainement.getEtiquettes());
        for(int i = 0; i < 50; i++) {
            int p = pp.predire(imagettesTest[i]);
            ImageTest.sauverImage(imagettesTest[i].getData(), "premiereImagetteTest.png");
            System.out.println("Première image test : " + p + " (attendu : " + imagettesTest[i].getEtiquette() + ")");
        }


        Statistiques stats = new Statistiques(pp, imagettesTest);
        System.out.println("Taux d'erreur : " + stats.getTauxErreur());

        KNN knn = new KNN(ImageEntrainement.getImagettes(), LabelEntrainement.getEtiquettes(), 3);
        for(int i = 0; i < 50; i++) {
            int p = knn.predire(imagettesTest[i]);
            ImageTest.sauverImage(imagettesTest[i].getData(), "premiereImagetteTest.png");
            System.out.println("Première image test : " + p + " (attendu : " + imagettesTest[i].getEtiquette() + ")");
        }

        Statistiques stats2 = new Statistiques(knn, imagettesTest);
        System.out.println("Taux d'erreur : " + stats2.getTauxErreur());
    }
}
