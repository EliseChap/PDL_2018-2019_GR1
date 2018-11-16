package pdl_2018.groupeSMKS1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
 


class TestCommandLine {
//TODO test généralisé
    /**
     * Tests pour la méthode verifDelimiteur
     */
    @Test
    public void testVerifDelimiteur() {

        //Tests de la commande -import uniquement
        CommandLine myCommand = new CommandLine("-import[;]");
        Assertions.assertTrue(myCommand.verifDelimiteur(), "true");
        Assertions.assertEquals(myCommand.getDelimit(), ';');

        CommandLine myCommand2 = new CommandLine("-import[]");
        Assertions.assertTrue(myCommand2.verifDelimiteur(), "false");
        Assertions.assertEquals(myCommand.getDelimit(), null);

        CommandLine myCommand3 = new CommandLine("import[;]");
        Assertions.assertTrue(myCommand3.verifDelimiteur(), "false");
        Assertions.assertEquals(myCommand.getDelimit(), null);
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

        CommandLine myCommand2 = new CommandLine("-wiki");
        Assertions.assertTrue(myCommand2.verifHtmlOrWikicodeChoice());
        Assertions.assertTrue(myCommand2.getExtraWiki());
        Assertions.assertFalse(myCommand2.getExtraHTML());

        CommandLine myCommand3 = new CommandLine("-wiki -html");
        Assertions.assertTrue(myCommand3.verifHtmlOrWikicodeChoice());
        Assertions.assertTrue(myCommand3.getExtraWiki());
        Assertions.assertTrue(myCommand3.getExtraHTML());

        CommandLine myCommand4 = new CommandLine("");
        Assertions.assertFalse(myCommand4.verifHtmlOrWikicodeChoice());
        Assertions.assertFalse(myCommand4.getExtraWiki());
        Assertions.assertFalse(myCommand4.getExtraHTML());

        CommandLine myCommand5 = new CommandLine("html");
        Assertions.assertFalse(myCommand5.verifHtmlOrWikicodeChoice());
        Assertions.assertFalse(myCommand5.getExtraHTML());
        Assertions.assertFalse(myCommand5.getExtraWiki());

        CommandLine myCommand6 = new CommandLine("wiki");
        Assertions.assertFalse(myCommand6.verifHtmlOrWikicodeChoice());
        Assertions.assertFalse(myCommand6.getExtraWiki());
        Assertions.assertFalse(myCommand6.getExtraHTML());
    }

    /**
     * Test de la méthode verifCheminSortie
     */
    @Test
    public void testVerifCheminSortie(){

        CommandLine myCommand = new CommandLine("-save");
        Assertions.assertFalse(myCommand.verifRepertoireSortie());
        Assertions.assertTrue(myCommand.getCheminCSV()==null);

        CommandLine myCommand2 = new CommandLine("-save[]");
        Assertions.assertFalse(myCommand2.verifRepertoireSortie());
        Assertions.assertTrue(myCommand.getCheminCSV()==null);

        CommandLine myCommand3 = new CommandLine("-save[c:/users/admin]");
        Assertions.assertFalse(myCommand3.verifRepertoireSortie());
        Assertions.assertTrue(myCommand3.getCheminCSV()==null);

        CommandLine myCommand4 = new CommandLine("-save[c:/users/admin/]");
        Assertions.assertFalse(myCommand4.verifRepertoireSortie());
        Assertions.assertEquals(myCommand4.getCheminCSV(), "c:/users/admin");

    }

    /**
     * Test de la méthode verifNomSortie
     */
    @Test
    public void testVerifNomSortie(){

        CommandLine myCommand = new CommandLine("-name");
        Assertions.assertFalse(myCommand.verifNomSortie());
        Assertions.assertTrue(myCommand.getNomCSV()==null);

        CommandLine myCommand2 = new CommandLine("-name[]");
        Assertions.assertFalse(myCommand2.verifNomSortie());
        Assertions.assertTrue(myCommand.getNomCSV()==null);

        CommandLine myCommand3 = new CommandLine("-name[monBeauTableauWiki]");
        Assertions.assertTrue(myCommand3.verifRepertoireSortie());
        Assertions.assertEquals(myCommand3.getNomCSV(), "");

        CommandLine myCommand4 = new CommandLine("-name[monBeauTableauWiki.csv]");
        Assertions.assertFalse(myCommand4.verifRepertoireSortie());
        Assertions.assertEquals(myCommand4.getNomCSV(), "monBeauTableauWiki.csv");

    }

    /**
     *
     */
    @Test
    public void testVerifUrlOrFichierChoice(){
        CommandLine myCommand = new CommandLine("");
        Assertions.assertFalse(myCommand.verifUrlOrFichierChoice());
        Assertions.assertEquals(myCommand.getUrl(), "");
        Assertions.assertEquals(myCommand.getCheminEntree(), "");

        CommandLine myCommand2 = new CommandLine("-url[https://fr.wikipedia.org/rennes]"); // Test URL valide
        Assertions.assertTrue(myCommand2.verifUrlOrFichierChoice());
        Assertions.assertEquals(myCommand.getUrl(), "https://fr.wikipedia.org/rennes");
        Assertions.assertEquals(myCommand.getCheminEntree(), "");

        CommandLine myCommand3 = new CommandLine("-url[https://fr.wikipedia.org/rennes] -import[c:/users/admin/mesBellesUrl.txt]");
        Assertions.assertFalse(myCommand3.verifUrlOrFichierChoice());
        Assertions.assertEquals(myCommand.getUrl(), "");
        Assertions.assertEquals(myCommand.getCheminEntree(), "");

        CommandLine myCommand4 = new CommandLine("-import[c:/users/admin/mesBellesUrl.txt]");
        Assertions.assertTrue(myCommand4.verifUrlOrFichierChoice());
        Assertions.assertEquals(myCommand.getUrl(), "");
        Assertions.assertEquals(myCommand.getCheminEntree(), "c:/users/admin/mesBellesUrl.txt");

        CommandLine myCommand5 = new CommandLine("-url[https://es.wikipedia.org/rennes]"); //Test URL invalide
        Assertions.assertFalse(myCommand5.verifUrlOrFichierChoice());

        CommandLine myCommand6 = new CommandLine("-import[c:/users/admin/mesBellesUrl.docx]"); //Test répertoire d'entrée invalide
        Assertions.assertFalse(myCommand6.verifUrlOrFichierChoice());

        CommandLine myCommand7 = new CommandLine("-import[c:/users/admin/mesBellesUrl.txt]"); //Test répertoire d'entrée valide
        Assertions.assertTrue(myCommand7.verifUrlOrFichierChoice());
    }
}
