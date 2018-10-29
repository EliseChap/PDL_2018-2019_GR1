import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;

import junit.framework.TestCase;
import test2.Csv;

public class TestCsv extends TestCase {

	
	public TestCsv() {
		
		Csv csv = new Csv(';',"","");
		
	}

/**
 * Test des paramètres en entrée et création de l'objet CVS
 */
	@Test
	public void testObjectCsvStandard() {
		Csv csv = new Csv(';',"C:/Users/sullivand/Music/Desktop/Nouveau dossier/","WikiMatrix");
		Assertions.assertEquals(csv.getDelimit(),';');
		Assertions.assertEquals(csv.getCheminCsv(),"C:/Users/sullivand/Music/Desktop/Nouveau dossier/");
		Assertions.assertEquals(csv.getNomCsv(),"WikiMatrix");
	}
	
	
	/**
	 * Test des paramètres en entrée null et création de l'objet CVS
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
	 * Verification de l'exportation d'un txt vers un cvs
	 */
	
	@Test
	public void testExporterCSV() {
		
		
		
	}
	
	/**
	 * Vérifiation si le hashmap est initialisé en faux
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
		
	}
	public void testVerificationCheminDispo() {
		
		Csv csv = new Csv(';',"","");
		csv.
		
	}
	@Test
	public void testVerificationSeparateurValide() {
		
	}
	
	
}
