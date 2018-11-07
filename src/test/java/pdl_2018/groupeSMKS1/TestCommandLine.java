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
        Assertions.assertTrue(myCommand.verifDelimiteur(myCommand.ligneDeCommande), "true");
        Assertions.assertEquals(myCommand.delimit, ';');

        CommandLine myCommand2 = new CommandLine("-import[]");
        Assertions.assertTrue(myCommand2.verifDelimiteur(myCommand.ligneDeCommande), "false");
        Assertions.assertEquals(myCommand.delimit, null);

        CommandLine myCommand3 = new CommandLine("import[;]");
        Assertions.assertTrue(myCommand3.verifDelimiteur(myCommand.ligneDeCommande), "false");
        Assertions.assertEquals(myCommand.delimit, null);

        //ToDO Tests avec une ligne de commande entière
    }
}
