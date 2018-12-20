package pdl_2018.groupeSMKS1;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

@Ignore
public class TestCommandLine {
//TODO general test 
    /**
     * Tests fort the method verifDelimiteur
     */
    @Test
    public void testVerifDelimiteur() {

        //Tests de la commande -delimit uniquement
        CommandLine myCommand = new CommandLine("-delimit[;]");
        assertTrue(myCommand.verifDelimiteur());
        assertEquals(myCommand.getDelimit(), ';');

        myCommand.setLigneDeCommande("-delimit[]");
        assertFalse(myCommand.verifDelimiteur());
        assertEquals(myCommand.getDelimit(), '\0');

        myCommand.setLigneDeCommande("");
        assertTrue(myCommand.verifDelimiteur());
        assertEquals(myCommand.getDelimit(), '\0');

        myCommand.setLigneDeCommande("delimit[unMotQuelconque]");
        assertFalse(myCommand.verifDelimiteur());
        assertEquals(myCommand.getDelimit(), '\0');
    }

    /**
     * Test for the method verifHtmlOrWikicodeChoice
     */
    @Test
    public void testVerifHtmlOrWikicodeChoice(){

        CommandLine myCommand = new CommandLine("-html");
        assertTrue(myCommand.verifHtmlOrWikicodeChoice());
        assertTrue(myCommand.getExtraHTML());
        assertFalse(myCommand.getExtraWiki());

        myCommand.setLigneDeCommande("-wiki");
        assertTrue(myCommand.verifHtmlOrWikicodeChoice());
        assertTrue(myCommand.getExtraWiki());
        assertFalse(myCommand.getExtraHTML());

        myCommand.setLigneDeCommande("-wiki -html");
        assertTrue(myCommand.verifHtmlOrWikicodeChoice());
        assertTrue(myCommand.getExtraWiki());
        assertTrue(myCommand.getExtraHTML());

        myCommand.setLigneDeCommande("");
        assertFalse(myCommand.verifHtmlOrWikicodeChoice());
        assertFalse(myCommand.getExtraWiki());
        assertFalse(myCommand.getExtraHTML());

        myCommand.setLigneDeCommande("html");
        assertFalse(myCommand.verifHtmlOrWikicodeChoice());
        assertFalse(myCommand.getExtraHTML());
        assertFalse(myCommand.getExtraWiki());

        myCommand.setLigneDeCommande("wiki");
        assertFalse(myCommand.verifHtmlOrWikicodeChoice());
        assertFalse(myCommand.getExtraWiki());
        assertFalse(myCommand.getExtraHTML());
    }

    /**
     * Test for the method verifCheminSortie
     */
    @Test
    public void testVerifCheminSortie(){

        CommandLine myCommand = new CommandLine("-save");
        assertFalse(myCommand.verifRepertoireSortie());
        assertTrue(myCommand.getCheminCSV()=="");

        myCommand.setLigneDeCommande("-save[]");
        assertFalse(myCommand.verifRepertoireSortie());
        assertTrue(myCommand.getCheminCSV()=="");

        myCommand.setLigneDeCommande("-save[c:/users/admin]");
        assertTrue(myCommand.verifRepertoireSortie());
        assertEquals(myCommand.getCheminCSV(), "c:/users/admin");

    }

    /**
     * Test for the method verifNomSortie
     */
    @Test
    public void testVerifNomSortie(){

        CommandLine myCommand = new CommandLine("-name");
        assertFalse(myCommand.verifNomSortie());
        assertTrue(myCommand.getNomCSV()=="");

        myCommand.setLigneDeCommande("-name[]");
        assertFalse(myCommand.verifNomSortie());
        assertTrue(myCommand.getNomCSV()=="");

        myCommand.setLigneDeCommande("-name[monBeauTableauWiki.csv]");
        assertFalse(myCommand.verifRepertoireSortie());
        assertEquals(myCommand.getNomCSV(), "monBeauTableauWiki.csv");

    }

    /**
     *This Junit test check the verifUrlOrFichierChoice method
     */
    @Test
    public void testVerifUrlOrFichierChoice(){
        CommandLine myCommand = new CommandLine("-url[]");
        assertFalse(myCommand.verifUrlOrFichierChoice());
        assertEquals(myCommand.getUrl(), "");
        assertEquals(myCommand.getCheminEntree(), "");

        myCommand.setLigneDeCommande("-url[https://fr.wikipedia.org/rennes]"); // Test URL valide
        assertTrue(myCommand.verifUrlOrFichierChoice());
        assertEquals(myCommand.getUrl(), "https://fr.wikipedia.org/rennes");
        assertEquals(myCommand.getCheminEntree(), "");

        myCommand.setLigneDeCommande("-url[https://fr.wikipedia.org/rennes] -import[c:/users/admin/mesBellesUrl.txt]");
        assertFalse(myCommand.verifUrlOrFichierChoice());
        assertEquals(myCommand.getUrl(), "");
        assertEquals(myCommand.getCheminEntree(), "");

        myCommand.setLigneDeCommande("-import[c:/users/admin/mesBellesUrl.txt]");
        assertTrue(myCommand.verifUrlOrFichierChoice());
        assertEquals(myCommand.getUrl(), "");
        assertEquals(myCommand.getCheminEntree(), "c:/users/admin/mesBellesUrl.txt");

    }
}
