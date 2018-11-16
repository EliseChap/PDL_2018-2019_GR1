package pdl_2018.groupeSMKS1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;




import junit.framework.TestCase;


import java.util.ArrayList;

public class TestFichier {

    private static Fichier f;

    @BeforeAll
    public static void initialise() {

        f = new Fichier("c:/mesURLs.txt", ' ', "", "", true, false);

    }

    /**
     * Tests pour la méthode traitementFichierEntree
     * On teste avec differents fichiers en entrée
     */
    @Test
    public void testTraitementFichierEntree() {

        ArrayList<String> monTableauDeLignes = new ArrayList();
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/Rennes;https://fr.wikipedia.org/wiki/Haute-Bretagne");
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/Métro_de_Rennes");
        Assertions.assertEquals(f.traitementFichierEntree(), monTableauDeLignes); //Test avec fichier valide, 2 url sur premiere ligne et 1 url sur 2e ligne

        monTableauDeLignes = new ArrayList();
        f.setCheminFichierEntree("c:/mesURL.txt");
        Assertions.assertEquals(f.traitementFichierEntree(), monTableauDeLignes); //Test avec fichier invalide

        //ça marche mais y'a un problème d'encodage visiblement
    }

    /**
     * Tests pour la méthode decoupageAndGenerationURLs
     * On teste avec différentes lectures de ligne
     */
    @Test
    public void testDecoupageAndGenerationURLs() {
        f.setCheminFichierEntree("c:/mesURLs.txt");
        ArrayList<Url> mesURLs = new ArrayList(); //Test avec fichier dont les lignes ont bien été récupérées
        mesURLs.add(new Url("https://fr.wikipedia.org/wiki/Rennes",' ', "", "", true, false));
        mesURLs.add(new Url("https://fr.wikipedia.org/wiki/Haute-Bretagne",' ', "", "", true, false));
        mesURLs.add(new Url("https://fr.wikipedia.org/wiki/Métro_de_Rennes",' ', "", "", true, false));
        Assertions.assertEquals(f.getLesURLs(), mesURLs);

        f.setCheminFichierEntree("c:/mesURL.txt"); //Test avec un fichier invalide
        mesURLs = new ArrayList();
        Assertions.assertEquals(f.getLesURLs(), mesURLs);

    }


}
