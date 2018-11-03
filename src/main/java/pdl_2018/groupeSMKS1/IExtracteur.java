package src.main.java.pdl_2018.groupeSMKS1;

public interface IExtracteur {

/**
 * 
 * @param delimit d�limiteur choisit par l'utilisateur
 * @param cheminCSV Chemin de sauvegarde du CSV final choisit par l'utilisateur
 * @param NomCSV Nom duy fichier CSV choisit par l'utilisateur
 * @param extraHTML Boolean � true si l'extraction doit �tre fait en HTML, false sinon
 * @param extraWiki Boolean � true si l'extraction doit �tre fait AVEC LE WIKICODE, false sinon
 * @return un objet tableau
 */
	public Tableau constructeurTableau(char delimit, String cheminCSV,String NomCSV, boolean extraHTML, boolean extraWiki);
	
	/**
	 * 
	 * @return Retourne le nom du tableau
	 */
	public String getNomTableau();
	
	/**
	 * Supprime le tableau
	 */
	public void removeTableau();
	
	/**
	 * Ajout un tableau
	 */
	public void addTableau();
	
	
}
