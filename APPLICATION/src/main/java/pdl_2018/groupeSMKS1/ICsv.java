package APPLICATION.src.main.java.pdl_2018.groupeSMKS1;

import java.util.ArrayList;

public interface ICsv {

	void initialisationSeparateurAutomatique();
	

	//public String verificationSeparateurValide(ArrayList<String[]> tableau);

	String convertionTableauEnStringDelimiter(ArrayList<String[]> tableau);

	void exporterCSV(String Text);

	boolean verificationCheminDispo();

	//String nomCsvIncrementer();

//	String verificationSeparateurValide(ArrayList<String[]> tableau);

}