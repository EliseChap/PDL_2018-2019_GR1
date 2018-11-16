package pdl_2018.groupeSMKS1;
 
public interface ICommandLine {

  public  boolean verifIntegriteCommandLine();

  public  boolean verifHtmlOrWikicodeChoice();

  public  boolean verifUrlOrFichierChoice();

  public   boolean verifUrlOrCheminEntree(int nbURL, int nbImport);

  public  boolean verifRepertoireSortie();

  public  boolean verifNomSortie();

  public  boolean verifDelimiteur();

}
