package src.main.java.pdl_2018.groupeSMKS1;

public interface ITableau {

	char getDelimit();

	String getCheminCsv();

	String getNomCsv();

	String getTableau();

	boolean verificationNumberIdenticalColumn();

	void constructorCsv();


}
