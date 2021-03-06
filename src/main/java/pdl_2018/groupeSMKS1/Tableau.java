package pdl_2018.groupeSMKS1;

import java.util.Arrays;


public class Tableau implements ITableau {
	private char delimit;
	private String cheminCsv;
	private String nomCsv;
	private String arr[][];
	private String nomTab;
	private boolean extraHtmlWiki;

	public Tableau() {}
	
	public Tableau(char pDelimit, String pCheminCsv, String pNomCsv, String pTableau[][], String nomTab, boolean extraHtmlWiki) {
		delimit = pDelimit;
		cheminCsv = pCheminCsv;
		nomCsv = pNomCsv;
		arr = pTableau;
		this.nomTab = nomTab;
		this.extraHtmlWiki=extraHtmlWiki;
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
	 * Browse the table to display it
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
	 * Returns the number of empty cells in the table
	 * @return countEmpty
	 */
	/*public int countEmpty() {
		int count = 0;
		for (String[] strArr : arr) {
			for (String cellule : strArr) {
				if (cellule == "") {
					count++;
				}
			}
		}
		return count;
	}*/
	
	public void constructeurCsv() {
		Csv csv = new Csv(delimit, cheminCsv, nomCsv, arr, nomTab,extraHtmlWiki);
	}
	
	/*public static void main(String[] args) {
		Html t = new Html("https://en.wikipedia.org/wiki/Infinity_on_High", ';', "chemin","nomCSV", true, false);
		t.recuperationPage();

	}*/
	

}
