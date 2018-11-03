package src.main.java.pdl_2018.groupeSMKS1;

public class Html extends Extracteur{

	@Override
	public void removeTableau() {}
	
	@Override
	public String getNomTableau(){
		return "";
		}
	@Override
	public void addTableau(){}
	@Override
	public Tableau constructeurTableau(char delimit, String cheminCSV,String NomCSV, boolean extraHTML, boolean extraWiki)
	{
		return new Tableau();
	}
	
}
