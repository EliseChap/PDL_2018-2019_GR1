package pdl_2018.groupeSMKS1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
 

@Disabled 
public class TestCommandLine {
//TODO test généralisé
    /**
     * Tests pour la méthode verifDelimiteur
     */
    @Test
    public void testVerifDelimiteur() {

        //Tests de la commande -delimit uniquement
        CommandLine myCommand = new CommandLine("-delimit[;]");
        Assertions.assertTrue(myCommand.verifDelimiteur(), "true");
        Assertions.assertEquals(myCommand.getDelimit(), ';');

        myCommand.setLigneDeCommande("-delimit[]");
        Assertions.assertTrue(myCommand.verifDelimiteur(), "false");
        Assertions.assertEquals(myCommand.getDelimit(), '\0');

        myCommand.setLigneDeCommande("");
        Assertions.assertTrue(myCommand.verifDelimiteur(), "true");
        Assertions.assertEquals(myCommand.getDelimit(), '\0');

        myCommand.setLigneDeCommande("delimit[unMotQuelconque]");
        Assertions.assertTrue(myCommand.verifDelimiteur(), "false");
        Assertions.assertEquals(myCommand.getDelimit(), '\0');
    }

    /**
     * Test pour la méthode verifHtmlOrWikicodeChoice
     */
    @Test
    public void testVerifHtmlOrWikicodeChoice(){

        CommandLine myCommand = new CommandLine("-html");
        Assertions.assertTrue(myCommand.verifHtmlOrWikicodeChoice());
        Assertions.assertTrue(myCommand.getExtraHTML());
        Assertions.assertFalse(myCommand.getExtraWiki());

        myCommand.setLigneDeCommande("-wiki");
        Assertions.assertTrue(myCommand.verifHtmlOrWikicodeChoice());
        Assertions.assertTrue(myCommand.getExtraWiki());
        Assertions.assertFalse(myCommand.getExtraHTML());

        myCommand.setLigneDeCommande("-wiki -html");
        Assertions.assertTrue(myCommand.verifHtmlOrWikicodeChoice());
        Assertions.assertTrue(myCommand.getExtraWiki());
        Assertions.assertTrue(myCommand.getExtraHTML());

        myCommand.setLigneDeCommande("");
        Assertions.assertFalse(myCommand.verifHtmlOrWikicodeChoice());
        Assertions.assertFalse(myCommand.getExtraWiki());
        Assertions.assertFalse(myCommand.getExtraHTML());

        myCommand.setLigneDeCommande("html");
        Assertions.assertFalse(myCommand.verifHtmlOrWikicodeChoice());
        Assertions.assertFalse(myCommand.getExtraHTML());
        Assertions.assertFalse(myCommand.getExtraWiki());

        myCommand.setLigneDeCommande("wiki");
        Assertions.assertFalse(myCommand.verifHtmlOrWikicodeChoice());
        Assertions.assertFalse(myCommand.getExtraWiki());
        Assertions.assertFalse(myCommand.getExtraHTML());
    }

    /**
     * Test de la méthode verifCheminSortie
     */
    @Test
    public void testVerifCheminSortie(){

        CommandLine myCommand = new CommandLine("-save");
        Assertions.assertFalse(myCommand.verifRepertoireSortie());
        Assertions.assertTrue(myCommand.getCheminCSV()=="");

        myCommand.setLigneDeCommande("-save[]");
        Assertions.assertFalse(myCommand.verifRepertoireSortie());
        Assertions.assertTrue(myCommand.getCheminCSV()=="");

        myCommand.setLigneDeCommande("-save[c:/users/admin]");
        Assertions.assertTrue(myCommand.verifRepertoireSortie());
        Assertions.assertEquals(myCommand.getCheminCSV(), "c:/users/admin");

    }

    /**
     * Test de la méthode verifNomSortie
     */
    @Test
    public void testVerifNomSortie(){

        CommandLine myCommand = new CommandLine("-name");
        Assertions.assertFalse(myCommand.verifNomSortie());
        Assertions.assertTrue(myCommand.getNomCSV()=="");

        myCommand.setLigneDeCommande("-name[]");
        Assertions.assertFalse(myCommand.verifNomSortie());
        Assertions.assertTrue(myCommand.getNomCSV()=="");

        myCommand.setLigneDeCommande("-name[monBeauTableauWiki.csv]");
        Assertions.assertFalse(myCommand.verifRepertoireSortie());
        Assertions.assertEquals(myCommand.getNomCSV(), "monBeauTableauWiki.csv");

    }

    /**
     *
     */
    @Test
    public void testVerifUrlOrFichierChoice(){
        CommandLine myCommand = new CommandLine("-url[]");
        Assertions.assertFalse(myCommand.verifUrlOrFichierChoice());
        Assertions.assertEquals(myCommand.getUrl(), "");
        Assertions.assertEquals(myCommand.getCheminEntree(), "");

        myCommand.setLigneDeCommande("-url[https://fr.wikipedia.org/rennes]"); // Test URL valide
        Assertions.assertTrue(myCommand.verifUrlOrFichierChoice());
        Assertions.assertEquals(myCommand.getUrl(), "https://fr.wikipedia.org/rennes");
        Assertions.assertEquals(myCommand.getCheminEntree(), "");

        myCommand.setLigneDeCommande("-url[https://fr.wikipedia.org/rennes] -import[c:/users/admin/mesBellesUrl.txt]");
        Assertions.assertFalse(myCommand.verifUrlOrFichierChoice());
        Assertions.assertEquals(myCommand.getUrl(), "");
        Assertions.assertEquals(myCommand.getCheminEntree(), "");

        myCommand.setLigneDeCommande("-import[c:/users/admin/mesBellesUrl.txt]");
        Assertions.assertTrue(myCommand.verifUrlOrFichierChoice());
        Assertions.assertEquals(myCommand.getUrl(), "");
        Assertions.assertEquals(myCommand.getCheminEntree(), "c:/users/admin/mesBellesUrl.txt");

    }
}
