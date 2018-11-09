package pdl_2018.groupeSMKS1;

public interface ICommandLine {

    boolean verifIntegriteCommandLine();

    boolean verifHtmlOrWikicodeChoice();

    boolean verifUrlOrFichierChoice();

    boolean verifUrlOrCheminEntree(int nbURL, int nbImport);

    boolean verifRepertoireSortie();

    boolean verifNomSortie();

    boolean verifDelimiteur();

}
