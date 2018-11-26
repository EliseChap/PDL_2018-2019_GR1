package pdl_2018.groupeSMKS1;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;



@Ignore
public class TestFichier {

    private static Fichier f;

    @BeforeClass
    public static void initialise() {

        f = new Fichier("c:/urlWithAllDelimiteurs.txt", ' ', "", "", true, false);

    }

    /**
     * Tests pour la méthode traitementFichierEntree
     * On teste avec differents fichiers en entrée
     */
    @Test
    public void testTraitementFichierEntree() {

        ArrayList<String> monTableauDeLignes = new ArrayList();
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/Rennes,https://fr.wikipedia.org/wiki/Soleil;https://fr.wikipedia.org/wiki/Neoval");
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/VAL_208");
        assertEquals(f.traitementFichierEntree(), monTableauDeLignes); //Test avec fichier valide, tous types de délimiteurs

        monTableauDeLignes = new ArrayList();
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/Rennes;https://fr.wikipedia.org/wiki/Soleil;https://fr.wikipedia.org/wiki/Neoval;https://fr.wikipedia.org/wiki/VAL_208");
        f.setCheminFichierEntree("c:/urlWithDelimitPointVirgule.txt");
        assertEquals(f.traitementFichierEntree(), monTableauDeLignes); //Test avec fichier valide, uniquement point virgule

        monTableauDeLignes = new ArrayList();
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/Rennes,https://fr.wikipedia.org/wiki/Soleil,https://fr.wikipedia.org/wiki/Neoval,https://fr.wikipedia.org/wiki/VAL_208");
        f.setCheminFichierEntree("c:/urlWithDelimitVirgule.txt");
        assertEquals(f.traitementFichierEntree(), monTableauDeLignes); //Test avec fichier valide, uniquement virgule

        monTableauDeLignes = new ArrayList();
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/Rennes");
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/Soleil");
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/Neoval");
        monTableauDeLignes.add("https://fr.wikipedia.org/wiki/VAL_208");
        f.setCheminFichierEntree("c:/urlWithDelimitRetourLigne.txt");
        assertEquals(f.traitementFichierEntree(), monTableauDeLignes); //Test avec fichier valide, retours à la ligne uniquement

        monTableauDeLignes = new ArrayList();
        f.setCheminFichierEntree("c:/urlVide.txt");
        assertTrue(f.traitementFichierEntree().size()==0); //Test avec fichier valide, mais vide

        monTableauDeLignes = new ArrayList();
        f.setCheminFichierEntree("c:/urlVide.docx");
        assertTrue(f.traitementFichierEntree().size()==0); //Test avec fichier invalide
    }

    /**
     * Tests pour la méthode decoupageAndGenerationURLs
     * On teste avec différentes lectures de ligne
     */
    @Test
    public void testDecoupageAndGenerationURLs() {
        f = new Fichier("c:/urlWithAllDelimiteurs.txt", ' ', "", "", true, false);

        ArrayList<Url> mesURLs = new ArrayList(); //Test avec fichier dont les lignes ont bien été récupérées
        mesURLs.add(new Url("https://fr.wikipedia.org/wiki/Rennes",' ', "", "", true, false));
        mesURLs.add(new Url("https://fr.wikipedia.org/wiki/Soleil",' ', "", "", true, false));
        mesURLs.add(new Url("https://fr.wikipedia.org/wiki/Neoval",' ', "", "", true, false));
        mesURLs.add(new Url("https://fr.wikipedia.org/wiki/VAL_208",' ', "", "", true, false));
        for(int i=0; i<mesURLs.size(); i++){
            assertEquals(f.getLesURLs().get(i).getUrl(), mesURLs.get(i).getUrl());
        }

        f = new Fichier("c:/urlWithAllDelimiteurs.docx", ' ', "", "", true, false);

        //f.setCheminFichierEntree("c:/mesURL.docx"); //Test avec un fichier invalide
        assertTrue(f.getLesURLs().size()==0);

    }


}
