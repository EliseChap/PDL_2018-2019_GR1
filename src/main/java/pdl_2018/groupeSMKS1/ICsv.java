package src.main.java.pdl_2018.groupeSMKS1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface ICsv {

	public void initialisationSeparateurAutomatique();
	


	public	boolean verificationCheminDispo();


	public	Map<String, Boolean> getSeparateur();


	public	char getDelimit();


	public	String getCheminCsv();


	public	String getNomCsv();
	

	public	String nomCsvIncrementer();




	public String verificationSeparateurValide();

public 	void exporterCSV() throws IOException;


}