package src.main.java.pdl_2018.groupeSMKS1;

public interface IUrl {
	
	/**
	 * 
	 * @return True si l'URL est bien formé, false sinon
	 */
 public  boolean verifURL();
 
 /**
  * 
  * @return True si l'URL provient d'une page wikipedia, false sinon
  */
 public boolean isWikipediaURL();
 
 
 /**
  * 
  * @return Retour un object extracteur
  */
 public void ConstructeurExtracteur();
}


