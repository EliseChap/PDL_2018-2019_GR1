package src.main.java.pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import org.apache.log4j.Logger;


public class Tableau implements ITableau{
	private static Logger logger = Logger.getLogger(Csv.class);
	char delimit;
	String cheminCsv;
	String nomCsv;
	ArrayList<String[]> tableau;
   public Tableau() {}
	
	
	
	public Tableau(char pDelimit, String pCheminCsv, String pNomCsv, ArrayList<String[]> pTableau) {
		delimit = pDelimit;
		cheminCsv = pCheminCsv;
		nomCsv =pNomCsv;
		tableau=pTableau;
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
	/**
	 * Check if the rows have the same number of columns
	 * @return True if the array has the same number of columns for all these lines 
	 */
	@Override
	public boolean verificationNumberIdenticalColumn() {
		int memoire = 0;

		for (String[] strArr : tableau) {
			if(memoire==0) {
				memoire=strArr.length;
			}
			else {
				if(strArr.length!=memoire) return false;
			}
		}
		return true;
	}
	
	/**
	 * Check the validity of the table and send it to convert it to CSV
	 * @return 
	 */
	
	@Override
	public boolean constructorCsv() {
		if(verificationNumberIdenticalColumn()) {
			Csv csv = new Csv(delimit, cheminCsv, nomCsv, tableau);
			return true;
		}
		else {
			logger.info("Le tableau n'est pas correcte en nombre de colonne");
			return false;
		}
	}
	
	public static void main(String[] args) {
		char delimit =';';
		String cheminCsv="";
		String nomCsv=null;
		
		ArrayList<String[]> list = new ArrayList<>();
		String[] arr1 = { "a", "b", "c" };
		String[] arr2 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		
		Tableau tableau = new Tableau(delimit, cheminCsv, nomCsv, list);
		logger.info(tableau.verificationNumberIdenticalColumn());
		tableau.constructorCsv();
		
		
		ArrayList<String[]> list2 = new ArrayList<String[]>();
		String[] arr3 = { "a", "b", "c", "e" };
		String[] arr4 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		Tableau tableau2 = new Tableau(delimit, cheminCsv, nomCsv, list2);
		logger.info(tableau2.verificationNumberIdenticalColumn());
		tableau2.constructorCsv();	

    }

}
