package src.main.java.pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.Map;

public interface ICsv {

	void initialisationSeparateurAutomatique();
	


	boolean verificationCheminDispo();


	Map<String, Boolean> getSeparateur();


	char getDelimit();


	String getCheminCsv();


	String getNomCsv();
	

	String nomCsvIncrementer();




	String verificationSeparateurValide();

	void exporterCSV();


}