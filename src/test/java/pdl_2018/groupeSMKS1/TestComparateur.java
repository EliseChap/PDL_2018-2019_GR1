package pdl_2018.groupeSMKS1;

import org.junit.Test;

import static org.junit.Assert.*;


public class TestComparateur {
	

	
/*
 * Test the ratio of the content
 */
	@Test
	public void ratioContenuTest() {
		String tab1[] = { "a,b,c,d" ,  "1,0,2,3,4" ,  "a,b,c,d" ,"1,0,2,3,4"  };
		String tab2[] = { "a,b,c,d" } ;
		Comparateur comparateur = new Comparateur(tab1, tab2);
		assertEquals(comparateur.ratioContenu(3),25.0, 0.0);
	}
	
	/*
	 * Test the number of fails in the content
	 */	
	@Test
	public void comparaisonTest() {
		
		String tab1[] = { "a,b,c,d" ,  "1,0,2,3,4" ,  "a,b,c,d" ,"1,0,2,3,4"  };
		String tab2[] = { "a,b,c,d" } ;
		Comparateur comparateur = new Comparateur(tab1, tab2);
		assertEquals(comparateur.analyseComplet(),3);
		
	}
	
	/*
	 * test the difference of line between wiki and html 
	 */
	@Test
	public void differentNombreLigneWikiHtmlTest() {
		String tab1[] = { "a,b,c,d" ,  "1,0,2,3,4" ,  "a,b,c,d" ,"1,0,2,3,4"  };
		String tab2[] = { "a,b,c,d" } ;
		Comparateur comparateur = new Comparateur(tab1, tab2);
		assertEquals(comparateur.differentNombreLigneWikiHtml(),false);
	}

	/*
	 * Test the nomber of lines between wiki and html
	 */
	@Test
	public void nombreLigneWikiTest() {
		String tab1[] = { "a,b,c,d" ,  "1,0,2,3,4" ,  "a,b,c,d" ,"1,0,2,3,4"  };
		String tab2[] = { "a,b,c,d" } ;
		Comparateur comparateur = new Comparateur(tab1, tab2);
		assertEquals(comparateur.nombreLigneWiki(),4);
	}

	/*
	 * Test the nimber of line in html
	 */
	@Test
	public void nombreLigneHtmlTest() {
		String tab1[] = { "a,b,c,d" ,  "1,0,2,3,4" ,  "a,b,c,d" ,"1,0,2,3,4"  };
		String tab2[] = { "a,b,c,d" } ;
		Comparateur comparateur = new Comparateur(tab1, tab2);
		assertEquals(comparateur.nombreLigneHtml(),1);
	}
	/*
	 * Test the number of ligne html ratio 
	 */
	@Test
	public void ratioLigneTest() {
		String tab1[] = { "a,b,c,d" ,  "1,0,2,3,4" ,  "a,b,c,d" ,"1,0,2,3,4"  };
		String tab2[] = { "a,b,c,d" } ;
		Comparateur comparateur = new Comparateur(tab1, tab2);
		assertEquals(comparateur.ratioLigne(),25.0, 0.0);
	}
}

