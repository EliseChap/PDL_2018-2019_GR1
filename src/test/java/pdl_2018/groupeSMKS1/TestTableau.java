package pdl_2018.groupeSMKS1;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestTableau {
	
	
	
	/**
	 * Test parametre tableau
	 */
	@Test
	public void testObjectTableau() {
		
		String tab[][] = { { "0", "2a", "4h", "6", "fds8" }, { "sQ1", "3", "GRE5", "7", "D9" } };

		Tableau csv = new Tableau(';', "", "WikiMatrix.csv", tab,"tableau",false);
		assertEquals(csv.getDelimit(), ';');
		assertEquals(csv.getCheminCsv(), "");
		assertEquals(csv.getNomCsv(), "WikiMatrix.csv");
		System.out.println(csv.getTableauToString());
		assertEquals(csv.getTableauToString(),"[0, 2a, 4h, 6, fds8][sQ1, 3, GRE5, 7, D9]");

	}

}
