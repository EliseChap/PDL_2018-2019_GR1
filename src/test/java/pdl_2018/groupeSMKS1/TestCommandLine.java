package pdl_2018.groupeSMKS1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TestCommandLine {

    /**
     * Tests pour la fonction verifDelimiteur
     */
    @Test
    public void testVerifDelimiteur() {

        //Tests de la commande -import uniquement
        CommandLine myCommand = new CommandLine("-import[;]");
        Assertions.assertTrue(myCommand.verifDelimiteur(), "true");
        Assertions.assertEquals(myCommand.delimit, ';');

        CommandLine myCommand2 = new CommandLine("-import[]");
        Assertions.assertTrue(myCommand2.verifDelimiteur(), "false");
        Assertions.assertEquals(myCommand.delimit, null);

        CommandLine myCommand3 = new CommandLine("import[;]");
        Assertions.assertTrue(myCommand3.verifDelimiteur(), "false");
        Assertions.assertEquals(myCommand.delimit, null);

        //ToDO Tests avec une ligne de commande entière
    }

    /**
     * Test pour la fonction verifHtmlOrWikicodeChoice
     */
    @Test
    public void testverifHtmlOrWikicodeChoice(){
        CommandLine myCommand = new CommandLine("-html");
        Assertions.assertTrue(myCommand.verifHtmlOrWikicodeChoice());
        Assertions.assertTrue(myCommand.extraHTML);
        Assertions.assertFalse(myCommand.extraWiki);

        CommandLine myCommand2 = new CommandLine("-wiki");
        Assertions.assertTrue(myCommand2.verifHtmlOrWikicodeChoice());
        Assertions.assertTrue(myCommand2.extraWiki);
        Assertions.assertFalse(myCommand2.extraHTML);

        CommandLine myCommand3 = new CommandLine("-wiki -html");
        Assertions.assertTrue(myCommand3.verifHtmlOrWikicodeChoice());
        Assertions.assertTrue(myCommand3.extraWiki);
        Assertions.assertTrue(myCommand3.extraHTML);

        CommandLine myCommand4 = new CommandLine("");
        Assertions.assertFalse(myCommand4.verifHtmlOrWikicodeChoice());
        Assertions.assertFalse(myCommand4.extraWiki);
        Assertions.assertFalse(myCommand4.extraHTML);

        CommandLine myCommand5 = new CommandLine("html");
        Assertions.assertFalse(myCommand5.verifHtmlOrWikicodeChoice());
        Assertions.assertFalse(myCommand5.extraHTML);
        Assertions.assertFalse(myCommand5.extraWiki);

        CommandLine myCommand6 = new CommandLine("wiki");
        Assertions.assertFalse(myCommand6.verifHtmlOrWikicodeChoice());
        Assertions.assertFalse(myCommand6.extraWiki);
        Assertions.assertFalse(myCommand6.extraHTML);
    }
}
