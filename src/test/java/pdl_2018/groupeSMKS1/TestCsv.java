/*
 * Test de la classe Csv 
 */

package pdl_2018.groupeSMKS1;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;



public class TestCsv extends TestCase {

	/**
	 * Test des parametres en entree et creation de l'objet CVS
	 */
	@Test
	public void testObjectCsvStandard() {
		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "C:/Users/sullivand/Music/Desktop/Nouveau dossier/", "WikiMatrix.csv", tab);
		Assertions.assertEquals(csv.getDelimit(), ';');
		Assertions.assertEquals(csv.getCheminCsv(), "C:/Users/sullivand/Music/Desktop/Nouveau dossier/");
		Assertions.assertEquals(csv.getNomCsv(), "WikiMatrix.csv");
	}

	/**
	 * Test des parametres en entree null et creation de l'objet CVS
	 */
	@Test
	public void testObjectCsvNull() {
		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };
		Csv csv = new Csv('\u0000', null, null, tab);
		Assertions.assertEquals(csv.getDelimit(), ',');
		Assertions.assertEquals(csv.getCheminCsv(), "");
		Assertions.assertEquals(csv.getNomCsv(), "WikiMatrix.csv");
	}

	/**
	 * Verification de l'exportation d'un csv
	 */

	@Test
	public void testExporterCSV2() {
		File fichier = new File("testExporterCSV2.csv");
		fichier.delete();

		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "", "testExporterCSV2.csv", tab);

		csv.exporterCSV();

		FileInputStream csvFile = null;
		try {
			csvFile = new FileInputStream("testExporterCSV2.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStreamReader inputreader = new InputStreamReader(csvFile);
		BufferedReader br = new BufferedReader(inputreader);

		String line;
		try {
			String strArray1 = String.join(";", tab[0]);
			String strArray2 = String.join(";", tab[1]);
			String array[] = { strArray1, strArray2 };
			int i = 0;
			while ((line = br.readLine()) != null) {
				System.out.println(array[i].equals(line));
				i++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testExporterCSV() {
		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };
		File fichier = new File("testExporterCSV.csv");
		fichier.delete();
		Csv csv = new Csv(';', "", "testExporterCSV.csv", tab);
		

		csv.exporterCSV();

		Assertions.assertTrue(csv.verificationCheminDispo());
	
		fichier.delete();
	}

	/**
	 * Verifiation si le hashmap est initialise en faux
	 */
	@Test
	public void testInitialisationSeparateurAutomatique() {

		boolean verification = false;
		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "", "", tab);
		Map<String, Boolean> separateurAutomatique = csv.getSeparateur();
		Set cles = separateurAutomatique.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext() && !verification) {
			Object cle = it.next();
			if (separateurAutomatique.get(cle) == true) {
				verification = true;
			}
		}
		Assertions.assertEquals(verification, true);

	}

	/*
	 * Verification de l'incrementation du fichier
	 */

	@Test
	public void testNomCsvIncrementer() {
		// initialisation
		File fichier = new File("testIncrementer.csv");
		fichier.delete();
		File fichier1 = new File("testIncrementer_2.csv");
		fichier1.delete();
		File fichier2 = new File("testIncrementer_3.csv");
		fichier2.delete();
		File fichier3 = new File("testIncrementer_4.csv");
		fichier3.delete();

		// creation des fichiers

		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "", "testIncrementer.csv", tab);
		Assertions.assertEquals(csv.nomCsvIncrementer(), "testIncrementer_2.csv");

		csv.exporterCSV();

		Assertions.assertEquals(csv.nomCsvIncrementer(), "testIncrementer_3.csv");

		csv.exporterCSV();

		Assertions.assertEquals(csv.nomCsvIncrementer(), "testIncrementer_4.csv");

		csv.exporterCSV();

		Assertions.assertEquals(csv.nomCsvIncrementer(), "testIncrementer_5.csv");

		// supprimer les fichers
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
		
		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "", "testChemin.csv", tab);

		Assertions.assertTrue(csv.verificationCheminDispo());
		fichier.delete();
		Assertions.assertTrue(!csv.verificationCheminDispo());

	}

	/*
	 * Verification du choix du delimiter
	 */

	@Test
	public void testVerificationSeparateurValide() {

		String tab[][] = { { "a;", "b", "c", "d" }, { "1,0:-", "2", "3", "4" } };
		Csv csv = new Csv(';', "", "", tab);

		csv.verificationSeparateurValide();
		Map<String, Boolean> cle = csv.getSeparateur();
		String separateur = cle.toString();
		Assertions.assertTrue(separateur != ";");
		Assertions.assertTrue(separateur != ",");
		Assertions.assertTrue(separateur != ":");
		Assertions.assertTrue(separateur != "-");

	}

}