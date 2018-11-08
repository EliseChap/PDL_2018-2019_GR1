package src.test.java.pdl_2018.groupeSMKS1;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.sun.xml.bind.Util;

import junit.framework.TestCase;
import src.main.java.pdl_2018.groupeSMKS1.Url;

class TestUrl extends TestCase {
	
	private Url u;
	
	@Before 
	public void initialise() {
		u = new Url("https://fr.wikipedia.org/wiki/Saison_7_de_Game_of_Thrones", ' ', "", "", true, false);
	}
	@Test
	public void testURLstandard() {
		Assertions.assertTrue(u.verifURL());
		Assertions.assertTrue(u.isWikipediaURL());
	}// Faire dautre set et asserions dans le focntion

	@Test
	public void testURLNonValide() {
		u.setUrl("https://fr.wikipedia.");
		Assertions.assertFalse(u.verifURL());

	} // Faire dautre set et asserions dans le focntion 

	@Test
	public void testIsWikiURLFalse() {
		u.setUrl("https://github.com/SulliDai/PDL_2018-2019_GR1");
		Assertions.assertFalse(u.isWikipediaURL());

	}// Faire dautre set et asserions dans le focntion
	
	@Test 
	public void testIsPNG() {}
	
	@Test 
	public void testIsJPEG() {}
	
	@Test 
	public void testIsDiscussion() {}
	
	@Test 
	public void testIsContributions() {}
	
	@Test 
	public void testIsRealURL() {}
	
	
	
}
