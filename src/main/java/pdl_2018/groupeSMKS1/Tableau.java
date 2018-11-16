package src.main.java.pdl_2018.groupeSMKS1;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class Tableau implements ITableau {
	//private static Logger logger = Logger.getLogger(Csv.class);
	private char delimit;
	private String cheminCsv;
	private String nomCsv;
	private String arr[][];
	private String nomTab;

	public Tableau() {
	}

	public Tableau(char pDelimit, String pCheminCsv, String pNomCsv, String pTableau[][], String nomTab) {
		delimit = pDelimit;
		cheminCsv = pCheminCsv;
		nomCsv = pNomCsv;
		arr = pTableau;
		this.nomTab = nomTab;
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
	public String getTableau() {
		return Arrays.toString(arr);
	}


}
