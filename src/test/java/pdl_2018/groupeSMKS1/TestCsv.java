/*
O * Test de la classe Csv 
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

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;




public class TestCsv {

	/**
	 * Test des parametres en entree et creation de l'objet CVS
	 */
	@Test
	public void testObjectCsvStandard() {
		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "src/test/java/Fichiers_Sortie", "WikiMatrix.csv", tab,"nomTab",false);
		assertEquals(csv.getDelimit(), ';');
		assertEquals(csv.getCheminCsv(), "src/test/java/Fichiers_Sortie/html/");
		assertEquals(csv.getNomCsv(), "WikiMatrix-1.csv");
		File fichier1 = new File("src/test/java/Fichiers_Sortie/html/WikiMatrix-1.csv");
		fichier1.delete();
		
	}

	/**
	 * Test des parametres en entree null et creation de l'objet CVS
	 */
	@Test
	public void testObjectCsvNull() {
		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };
		Csv csv = new Csv('\u0000', "src/test/java/Fichiers_Sortie", null, tab,"nomTab",false);
		assertEquals(csv.getDelimit(), ',');
		assertEquals(csv.getCheminCsv(), "src/test/java/Fichiers_Sortie/html/");
		assertEquals(csv.getNomCsv(), "nomTab-1.csv");
		File fichier1 = new File("src/test/java/Fichiers_Sortie/html/nomTab-1.csv");
		fichier1.delete();
	}

	/**
	 * Verification de l'exportation d'un csv
	 */

	@Test
	public void testExporterCSV2() {


		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "src/test/java/Fichiers_Sortie/", "testExporterCSV2.csv", tab, "nomTab", false);

		csv.exporterCSV();

		FileInputStream csvFile = null;
		try {
			csvFile = new FileInputStream("testExporterCSV2.csv");		
			InputStreamReader inputreader = new InputStreamReader(csvFile);
			BufferedReader br = new BufferedReader(inputreader);
			String line;

				String strArray1 = String.join(";", tab[0]);
				String strArray2 = String.join(";", tab[1]);
				String array[] = { strArray1, strArray2 };
				int i = 0;
				while ((line = br.readLine()) != null) {
					System.out.println(array[i].equals(line));
					i++;
				}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		File fichier1 = new File("src/test/java/Fichiers_Sortie/html/testExporterCSV2-1.csv");
		fichier1.delete();



	}

	/**
	 * Verifiation si le hashmap est initialise en faux
	 */
	@Test
	public void testInitialisationSeparateurAutomatique() {

		boolean verification = false;
		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "src/test/java/Fichiers_Sortie/", "", tab, "nomTab", false);
		Map<String, Boolean> separateurAutomatique = csv.getSeparateur();
		Set cles = separateurAutomatique.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext() && !verification) {
			Object cle = it.next();
			if (separateurAutomatique.get(cle) == true) {
				verification = true;
			}
		}
		assertEquals(verification, true);
		
		File fichier1 = new File("src/test/java/Fichiers_Sortie/html/nomTab-1.csv");
		fichier1.delete();

	}

	/*
	 * Verification de l'incrementation du fichier
	 */

	@Test
	public void testNomCsvIncrementer() {
		// initialisation
		File fichier = new File("src/test/java/Fichiers_Sortie/html/testIncrementer-1.csv");
		fichier.delete();
		File fichier1 = new File("src/test/java/Fichiers_Sortie/html/testIncrementer-2.csv");
		fichier1.delete();
		File fichier2 = new File("src/test/java/Fichiers_Sortie/html/testIncrementer-3.csv");
		fichier2.delete();
		File fichier3 = new File("src/test/java/Fichiers_Sortie/html/testIncrementer-4.csv");
		fichier3.delete();
		File fichier4 = new File("src/test/java/Fichiers_Sortie/html/testIncrementer-5.csv");
		fichier4.delete();

		// creation des fichiers

		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "src/test/java/Fichiers_Sortie", "testIncrementer.csv", tab, "nomTab", false);
		assertEquals(csv.nomCsvIncrementer(), "src/test/java/Fichiers_Sortie/html/testIncrementer-2.csv");

		csv.exporterCSV();

		assertEquals(csv.nomCsvIncrementer(), "src/test/java/Fichiers_Sortie/html/testIncrementer-3.csv");

		csv.exporterCSV();

		assertEquals(csv.nomCsvIncrementer(), "src/test/java/Fichiers_Sortie/html/testIncrementer-4.csv");

		csv.exporterCSV();

		assertEquals(csv.nomCsvIncrementer(), "src/test/java/Fichiers_Sortie/html/testIncrementer-5.csv");

		// supprimer les fichers
		fichier.delete();
		fichier1.delete();
		fichier2.delete();
		fichier3.delete();
		fichier4.delete();
	}

	/**
	 * Verifie si l'emplacement est pris
	 */

	@Test
	public void testVerificationCheminDispo() {
		File fichier = new File("src/test/java/Fichiers_Sortie/html/testChemin-1.csv");
		fichier.delete();
		
		String tab[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" } };

		Csv csv = new Csv(';', "src/test/java/Fichiers_Sortie/", "testChemin.csv", tab, "nomTab",false);

		assertTrue(csv.verificationCheminDispo());
		fichier.delete();
		assertTrue(!csv.verificationCheminDispo());
		
		File fichier1 = new File("src/test/java/Fichiers_Sortie/html/testChemin-1.csv");
		fichier1.delete();

	}

	/*
	 * Verification du choix du delimiter
	 */

	@Test
	public void testVerificationSeparateurValide() {

		String tab[][] = { { "a;", "b", "c", "d" }, { "1,0:-", "2", "3", "4" } };
		Csv csv = new Csv(';', "src/test/java/Fichiers_Sortie/", "", tab, "nomTab",false);

		csv.verificationSeparateurValide();
		Map<String, Boolean> cle = csv.getSeparateur();
		String separateur = cle.toString();
		assertTrue(separateur != ";");
		assertTrue(separateur != ",");
		assertTrue(separateur != ":");
		assertTrue(separateur != "-");
		File fichier1 = new File("src/test/java/Fichiers_Sortie/html/nomTab-1.csv");
		fichier1.delete();

	}
	
	
	

}