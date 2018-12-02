package pdl_2018.groupeSMKS1;

import java.util.ArrayList;

public interface IExtracteur {

/**
 * 
 * @param tab tableau de string contenant les donnees extraites d'un tableau wikipedia
 * @param nomTab nom du tableau tab
 * @param wikiHtml True si les données on était extraites avec wikicode, false sinon
 * @return un objet tableau
 */
	public Tableau constructeurTableau( String[][] tab, String nomTab, boolean wikiHtml);
	
	/**
	 * 
	 * @return Retourne le nom du tableau
	 */
	public String getNomTableau();
	
	/**
	 * Supprime le tableau
	 */
	public void removeTableau(Tableau leTableau);
	
	/**
	 * @param Un object de type tableau
	 * Ajout un tableau
	 */
	public void addTableau(Tableau leTableau);
		
	/**
	 * 	Connection à la page donné en paramètre
	 */
	public void recuperationPage();
	
	/**
	 * 
	 * @return une liste d'objet tableau construis pour chaque tableau extrait du wiki ou html
	 */
	public ArrayList<Tableau> getLesTableaux();
}
