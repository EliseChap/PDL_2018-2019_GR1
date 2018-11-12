package src.main.java.pdl_2018.groupeSMKS1;

import org.apache.log4j.Logger;

public class Tableau implements ITableau {
	private static Logger logger = Logger.getLogger(Csv.class);
	char delimit;
	String cheminCsv;
	String nomCsv;
	String tableau[][];

	public Tableau() {
	}

	public Tableau(char pDelimit, String pCheminCsv, String pNomCsv, String pTableau[][]) {
		delimit = pDelimit;
		cheminCsv = pCheminCsv;
		nomCsv = pNomCsv;
		tableau = pTableau;
	}

	@Override
	public char getDelimit() {
		return delimit;
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
	public String getTableau() {
		return tableau.toString();
	}

	public static void main(String[] args) {
		char delimit = ';';
		String cheminCsv = "";
		String nomCsv = null;

		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Tableau tableau = new Tableau(delimit, cheminCsv, nomCsv, tab);

	}

}
