package src.test.java.pdl_2018.groupeSMKS1;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import src.main.java.pdl_2018.groupeSMKS1.Csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import junit.framework.TestCase;

public class TestCsv extends TestCase {


	/**
	 * Test des param�tres en entr�e et cr�ation de l'objet CVS
	 */
	@Test
	public void testObjectCsvStandard() {
		Csv csv = new Csv(';',"C:/Users/sullivand/Music/Desktop/Nouveau dossier/","WikiMatrix");
		Assertions.assertEquals(csv.getDelimit(),';');
		Assertions.assertEquals(csv.getCheminCsv(),"C:/Users/sullivand/Music/Desktop/Nouveau dossier/");
		Assertions.assertEquals(csv.getNomCsv(),"WikiMatrix");
	}
	
	
	/**
	 * Test des param�tres en entr�e null et cr�ation de l'objet CVS
	 */
	@Test
	public void testObjectCsvNull() {
		Csv csv = new Csv('\u0000',null,null);
		Assertions.assertEquals(csv.getDelimit(),',');
		Assertions.assertEquals(csv.getCheminCsv(),"");
		Assertions.assertEquals(csv.getNomCsv(),"WikiMatrix.csv");
	}
	
	
	/**
	 * Test simple convertionTableauEnStringDelimiter
	 */
	/*public void testConvertionTableauEnStringDelimiter() {
		Csv csv = new Csv(';',"","");
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] arr1 = { "a", "b", "c" };
		String[] arr2 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		String text = csv.convertionTableauEnStringDelimiter(list);
		
		String chaineValide = "a;b;c\r\n" + 
				"1,0;2;3;4\n";
		
		System.out.println(text.equals(chaineValide));
		System.out.println(chaineValide);
		Assertions.assertEquals(chaineValide,text);
	}*/
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * Ajouter d'autres Test .....
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	/**
	 * Verification de l'exportation d'un txt vers un csv
	 */
	
	@Test
	public void testExporterCSV() {
		
		
		
	}
	
	/**
	 * V�rifiation si le hashmap est initialis� en faux
	 */
	@Test
	public void testInitialisationSeparateurAutomatique() {
		
		boolean verification = false;
		
		Csv csv = new Csv(';',"","");
		Map<String, Boolean> separateurAutomatique = csv.getSeparateur();
		Set cles = separateurAutomatique.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext() && !verification){
		   Object cle = it.next();
		   if(separateurAutomatique.get(cle)==true) {
			   verification = true;
			}
		}
		Assertions.assertEquals(verification,false);
				
	}
	
	@Test
	public void testNomCsvIncrementer() {
		Csv csv = new Csv(';',"","");
	}
	
	/**
	 * Verifie si l'emplacement est pris
	 */
	
	/*@Test
	public void testVerificationCheminDispo() {
		
		Csv csv = new Csv(';',"","WikiMatrix");
		csv.exporterCSV("test");
		Assertions.assertTrue(csv.verificationCheminDispo());
		
	}*/
	
	
	@Test
	public void testVerificationSeparateurValide() {
		
	}
	
	
}