package APPLICATION.src.main.java.pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.Map;

public interface ICsv {

	void initialisationSeparateurAutomatique();
	

	//public String verificationSeparateurValide(ArrayList<String[]> tableau);

	String convertionTableauEnStringDelimiter(ArrayList<String[]> tableau);

	void exporterCSV(String Text);

	boolean verificationCheminDispo();


	Map<String, Boolean> getSeparateur();


	char getDelimit();


	String getCheminCsv();


	String getNomCsv();


	String verificationSeparateurValide(ArrayList<String[]> tableau);


	String nomCsvIncrementer();

	//String nomCsvIncrementer();

//	String verificationSeparateurValide(ArrayList<String[]> tableau);

}