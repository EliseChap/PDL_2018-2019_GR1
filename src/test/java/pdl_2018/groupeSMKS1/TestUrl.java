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
	}

	@Test
	public void testURLNonValide() {
		u.setUrl("https://fr.wikipedia.");
		Assertions.assertFalse(u.verifURL());

	}

	@Test
	public void testisWikiURLFalse() {
		u.setUrl("https://github.com/SulliDai/PDL_2018-2019_GR1");
		Assertions.assertFalse(u.isWikipediaURL());

	}
	
}
