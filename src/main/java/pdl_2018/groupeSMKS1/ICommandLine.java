package pdl_2018.groupeSMKS1;

public interface ICommandLine {

    boolean verifIntegriteCommandLine(String commandLine);

    boolean verifHtmlOrWikicodeChoice(String commandLine);

    boolean verifUrlOrFichierChoice(String commandLine);

    boolean verifUrlOrCheminEntree(String commandLine, int nbURL, int nbImport);

    boolean verifRepertoireSortie(String commandLine);

    boolean verifDelimiteur(String commandLine);

}
