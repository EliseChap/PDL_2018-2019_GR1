/*
 * Test de la classe Csv 
 */



package src.test.java.pdl_2018.groupeSMKS1;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import src.main.java.pdl_2018.groupeSMKS1.Csv;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.qos.logback.core.net.SyslogOutputStream;
import junit.framework.TestCase;

public class TestCsv extends TestCase {


	/**
	 * Test des param�tres en entr�e et cr�ation de l'objet CVS
	 */
	@Test
	public void testObjectCsvStandard() {
		
		
		Csv csv = new Csv(';',"C:/Users/sullivand/Music/Desktop/Nouveau dossier/","WikiMatrix",null);
		Assertions.assertEquals(csv.getDelimit(),';');
		Assertions.assertEquals(csv.getCheminCsv(),"C:/Users/sullivand/Music/Desktop/Nouveau dossier/");
		Assertions.assertEquals(csv.getNomCsv(),"WikiMatrix");
	}
	
	
	/**
	 * Test des param�tres en entr�e null et cr�ation de l'objet CVS
	 */
	@Test
	public void testObjectCsvNull() {
		Csv csv = new Csv('\u0000',null,null,null);
		Assertions.assertEquals(csv.getDelimit(),',');
		Assertions.assertEquals(csv.getCheminCsv(),"");
		Assertions.assertEquals(csv.getNomCsv(),"WikiMatrix.csv");
	}
	
	
	/**
	 * Test simple convertionTableauEnStringDelimiter
	 */
	@Test
	public void testConvertionTableauEnStringDelimiter() {
	
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] arr1 = { "a", "b", "c" };
		String[] arr2 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		Csv csv = new Csv(';',"","",list);
		/*String text = csv.convertionTableauEnStringDelimiter();
		
		String chaineValide = "a;b;c\n"+"1,0;2;3;4\n";
		
		System.out.println(text.equals(chaineValide));
		System.out.println(chaineValide);
		Assertions.assertEquals(chaineValide,text);*/
	}
	
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
		File fichier = new File("testExporterCSV.csv");
		fichier.delete();
		Csv csv = new Csv(';',"","testExporterCSV.csv",null);
		Assertions.assertFalse(csv.verificationCheminDispo());
		csv.exporterCSV("Ceci est un test de la classe Csv avec la methode ExporterCsv");
		Assertions.assertTrue(csv.verificationCheminDispo());
		BufferedReader buf;
		try {
			buf = new BufferedReader(new FileReader("testExporterCSV.csv"));
			String line = buf.readLine();

			Assertions.assertTrue(line.toString().equals("Ceci est un test de la classe Csv avec la methode ExporterCsv"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

		fichier.delete();
	}
	
	/**
	 * V�rifiation si le hashmap est initialis� en faux
	 */
	@Test
	public void testInitialisationSeparateurAutomatique() {
		
		boolean verification = false;
		
		Csv csv = new Csv(';',"","",null);
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
	
	/*
	 * Vérification de l'incrementation du fichier
	 */
	
	@Test
	public void testNomCsvIncrementer() {
		//initialisation
		File fichier = new File("testIncrementer.csv");
		fichier.delete();
		File fichier1 = new File("testIncrementer_2.csv");
		fichier1.delete();
		File fichier2 = new File("testIncrementer_3.csv");
		fichier2.delete();
		File fichier3 = new File("testIncrementer_4.csv");
		fichier3.delete();
		
		//création des fichiers
		
		Csv csv = new Csv(';',"","testIncrementer.csv",null);
		Assertions.assertEquals(csv.nomCsvIncrementer(),"testIncrementer_1.csv");
		csv.exporterCSV("testNomCsvIncrementer");
		Assertions.assertEquals(csv.nomCsvIncrementer(),"testIncrementer_2.csv");
		csv.exporterCSV("testNomCsvIncrementer");
		Assertions.assertEquals(csv.nomCsvIncrementer(),"testIncrementer_3.csv");
		csv.exporterCSV("testNomCsvIncrementer");
		Assertions.assertEquals(csv.nomCsvIncrementer(),"testIncrementer_4.csv");
		
		//supprimer les fichers
		fichier.delete();
		fichier1.delete();
		fichier2.delete();
		fichier3.delete();
	}
	
	/**
	 * Verifie si l'emplacement est pris
	 */
	
	@Test
	public void testVerificationCheminDispo() {
		File fichier = new File("testChemin.csv");
		fichier.delete();
		Csv csv = new Csv(';',"","testChemin.csv",null);
		Assertions.assertTrue(!csv.verificationCheminDispo());
		csv.exporterCSV("testChemin");
		Assertions.assertTrue(csv.verificationCheminDispo());
		fichier.delete();
		Assertions.assertTrue(!csv.verificationCheminDispo());
		
	}
	
	/*
	 * Verification du choix du délimiter
	 */
	
	@Test
	public void testVerificationSeparateurValide() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] arr1 = { "a;", "b", "c" };
		String[] arr2 = { "1,0:-", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		Csv csv = new Csv(';',"","",list);

		
		csv.verificationSeparateurValide();
		Map<String, Boolean> cle = csv.getSeparateur();
		String separateur = cle.toString();
		Assertions.assertTrue(separateur!=";");
		Assertions.assertTrue(separateur!=",");
		Assertions.assertTrue(separateur!=":");
		Assertions.assertTrue(separateur!="-");

	}
	
	
}