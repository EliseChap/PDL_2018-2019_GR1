package src.main.java.pdl_2018.groupeSMKS1;

public interface IUrl {
	
	/**
	 * 
	 * @param URL est l'adresse URL d'une page web
	 * @return True si l'URL est bien form�, false sinon
	 */
 public  boolean verifURL(String URL);
 
 /**
  * 
  * @param URL est l'adresse URL d'une page web
  * @return True si l'URL provient d'une page wikipedia, false sinon
  */
 public boolean isWikipediaURL(String URL);
 
 
 /**
  * 
  * @param delimit le d�limiteur choisit par l'utilisateur
  * @param cheminCSV le chemin ou le fichier sera sauvegard�
  * @param NomCSV Le nom du fichier CSV de sortie
  * @param extraHTML Un boolean � true si l'extraction doit �tre faite en HTML, false sinon
  * @param extraWiki Un boolean � true si l'extraction doit etre faite avec le 	Wikicode, false sinon
  * @return Retour un object extracteur
  */
 public Extracteur ConstructeurExtracteur(char delimit, String cheminCSV,String NomCSV, boolean extraHTML, boolean extraWiki);
}


