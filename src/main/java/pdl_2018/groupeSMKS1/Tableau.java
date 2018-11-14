package src.main.java.pdl_2018.groupeSMKS1;

import java.util.Arrays;

import org.apache.log4j.Logger;

public class Tableau implements ITableau {
	//private static Logger logger = Logger.getLogger(Csv.class);
	char delimit;
	String cheminCsv;
	String nomCsv;
	String arr[][];

	public Tableau() {
	}

	public Tableau(char pDelimit, String pCheminCsv, String pNomCsv, String pTableau[][]) {
		delimit = pDelimit;
		cheminCsv = pCheminCsv;
		nomCsv = pNomCsv;
		arr = pTableau;
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
		return Arrays.toString(arr);
	}


}
