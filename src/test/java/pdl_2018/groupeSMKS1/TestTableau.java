package pdl_2018.groupeSMKS1;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;


public class TestTableau {
	
	
	
	/**
	 * Table Parameter Test
	 */
	@Test
	public void testObjectTableau() {
		
		String tab[][] = { { "0", "2a", "4h", "6", "fds8" }, { "sQ1", "3", "GRE5", "7", "D9" } };

		Tableau csv = new Tableau(';', "src/test/java/Fichiers_Sortie", "WikiMatrix.csv", tab,"tableau",false);
		assertEquals(csv.getDelimit(), ';');
		assertEquals(csv.getCheminCsv(), "src/test/java/Fichiers_Sortie");
		System.out.println(csv.getNomCsv());
		assertEquals(csv.getNomCsv(), "WikiMatrix.csv");
		System.out.println(csv.getTableauToString());
		assertEquals(csv.getTableauToString(),"[0, 2a, 4h, 6, fds8][sQ1, 3, GRE5, 7, D9]");

		File fichier1 = new File("src/test/java/Fichiers_Sortie/html/WikiMatrix-1.csv");
		fichier1.delete();
	}

}
