package pdl_2018.groupeSMKS1;

public interface ICsv {

	void initialisationSeparateurAutomatique();

	String verificationSeparateurValide(ArrayList<String[]> tableau);

	String convertionTableauEnStringDelimiter(ArrayList<String[]> tableau);

	void exporterCSV(String Text);

	boolean verificationCheminDispo();

	String nomCsvIncrémenter();

}