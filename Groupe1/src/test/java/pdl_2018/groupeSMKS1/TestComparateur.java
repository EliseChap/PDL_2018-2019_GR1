package pdl_2018.groupeSMKS1;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pdl_2018.groupeSMKS1.Comparateur;


class TestComparateur {
	

	
/*
 * Test ratio contenu
 */
	@Test
	public void ratioContenuTest() {
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.ratioContenu(3),25.0);
	}
	
	/*
	 * Test nombre D'erreur dans le contenu
	 */
	
	@Test
	public void comparaisonTest() {
		
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.comparaison(),3);
		
	}
	
	/*
	 * test Comparaison nombre de ligne
	 */
	@Test
	public void differentNombreLigneWikiHtmlTest() {
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.differentNombreLigneWikiHtml(),false);
	}

	/*
	 * Test du retours du nombre de ligne
	 */
	@Test
	public void nombreLigneWikiTest() {
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.nombreLigneWiki(),4);
	}

	/*
	 * Test nbre de ligne html
	 */
	@Test
	public void nombreLigneHtmlTest() {
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.nombreLigneHtml(),1);
	}
	/*
	 * Test ratio nbre de ligne html
	 */
	@Test
	public void ratioLigneTest() {
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.ratioLigne(),25.0);
	}
	/*
	 * Test nbre de colonne wiki
	 */
	@Test
	public void nombreColonneWikiTest() {
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.nombreColonneWiki(),4);
	}
	
	/*
	 * Test nbre de colonne html
	 */
	@Test
	public void nombreColonneHtmlTest() {
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.nombreColonneHtml(),1);
	}
	
	/*
	 * Test nbre de colonne html
	 */
	@Test
	public void ratioColonneTest() {
		
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.ratioColonne(),100.0);
	}

	/*
	 * Test nbre de colonne html
	 */
	@Test
	public void differenceNumberlColumnTest() {
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.differenceNumberlColumn(),false);
	}
	
	/*
	 * Test ratio nbre de colonne html
	 */
	@Test
	public void ratioGeneraleTest() {
		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };
		Comparateur comparateur = new Comparateur(tab1, tab2);
		Assertions.assertEquals(comparateur.ratioGenerale(),35.0);

	}

}
