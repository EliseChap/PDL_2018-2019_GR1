package src.main.java.pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra.system_add_column_family_args;

public class Tableau implements ITableau{
	
	static char delimit;
	static String cheminCsv;
	static String nomCsv;
	ArrayList<String[]> tableau;
   
	
	
	
	public Tableau(char delimit, String cheminCsv, String nomCsv, ArrayList<String[]> tableau) {
		this.delimit = delimit;
		this.cheminCsv = cheminCsv;
		this.nomCsv =nomCsv;
		this.tableau=tableau;
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
			System.out.println("Le tableau n'est pas correcte en nombre de colonne");
			return false;
		}
	}
	
	public static void main(String[] args) {
		char delimit =';';
		String cheminCsv="";
		String nomCsv=null;
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] arr1 = { "a", "b", "c" };
		String[] arr2 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		
		Tableau tableau = new Tableau(delimit, cheminCsv, nomCsv, list);
		System.out.println(tableau.verificationNumberIdenticalColumn());
		tableau.constructorCsv();
		
		
		ArrayList<String[]> list2 = new ArrayList<String[]>();
		String[] arr3 = { "a", "b", "c", "e" };
		String[] arr4 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		Tableau tableau2 = new Tableau(delimit, cheminCsv, nomCsv, list2);
		System.out.println(tableau2.verificationNumberIdenticalColumn());
		tableau2.constructorCsv();
		
		

				


    }

}
