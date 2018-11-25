package pdl_2018.groupeSMKS1;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class Tableau implements ITableau {
	//private static Logger logger = Logger.getLogger(Csv.class);
	private char delimit;
	private String cheminCsv;
	private String nomCsv;
	private String arr[][];
	private String nomTab;


	public Tableau(char pDelimit, String pCheminCsv, String pNomCsv, String pTableau[][], String nomTab) {
		delimit = pDelimit;
		cheminCsv = pCheminCsv;
		nomCsv = pNomCsv;
		arr = pTableau;
		this.nomTab = nomTab;
		getTableauToString();
		constructeurCsv();
		
	}
	


	@Override
	public char getDelimit() {
		return delimit;
	}
	
	public String getnomTab() {
		return nomTab;
	}

	@Override
	public String getCheminCsv() {
		return cheminCsv;
	}

	@Override
	public String getNomCsv() {
		return nomCsv;
	}

	@Override
	public String[][] getTableau() {
		return arr;
	}
	/**
	 * Parcourir le tableau afin de l'afficher
	 * @return String
	 */
	public String getTableauToString() {
		String tableauTostring = "";
		for (String[] strArr : arr) {
			tableauTostring+=Arrays.toString(strArr);
		}
		return tableauTostring;
	}
	
	/**
	 * Retourne le nombre de vide dans le tableau
	 * @return countEmpty
	 */
	public int countEmpty() {
		int count = 0;
		for (String[] strArr : arr) {
			for (String cellule : strArr) {
				if (cellule == "") {
					count++;
				}
			}
		}
		return count;
	}
	
	public void constructeurCsv() {
		Csv csv = new Csv(delimit, cheminCsv, nomCsv, arr);
	}
	
	public static void main(String[] args) {
		Html t = new Html("https://en.wikipedia.org/wiki/Infinity_on_High", ';', "chemin","nomCSV", true, false);
		t.recuperationPage();

	}
	

}
