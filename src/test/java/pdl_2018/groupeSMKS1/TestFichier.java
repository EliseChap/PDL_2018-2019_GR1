package src.test.java.pdl_2018.groupeSMKS1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import src.main.java.pdl_2018.groupeSMKS1.Fichier;

class TestFichier {

    private static Fichier f;

    @BeforeAll
    public static void initialise() {

        f = new Fichier("c:/mesURLsWikipedia.txt", ' ', "", "", true, false);

    }

    /**
     * Tests pour la méthode traitementFichierEntree
     */
    @Test
    public void testTraitementFichierEntree() {

    }

    /**
     * Tests pour la méthode decoupageAndGenerationURLs
     */
    @Test
    public void testVerifDelimiteur() {

    }


}
