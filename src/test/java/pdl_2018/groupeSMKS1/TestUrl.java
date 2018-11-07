package src.test.java.pdl_2018.groupeSMKS1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.sun.xml.bind.Util;

import junit.framework.TestCase;
import src.main.java.pdl_2018.groupeSMKS1.Url;

class TestUrl extends TestCase {
	@Test
	public void testURLstandard() {
		Url u = new Url("https://fr.wikipedia.org/wiki/Saison_7_de_Game_of_Thrones", ' ', "", "", true, false);

		Assertions.assertTrue(u.verifURL());
		Assertions.assertTrue(u.isWikipediaURL());
	}

	@Test
	public void testURLNonValide() {
		Url u = new Url("https://fr.wikipedia.", ' ', "", "", true, false);
		Assertions.assertFalse(u.verifURL());

	}

	@Test
	public void testisWikiURLFalse() {
		Url u = new Url("https://github.com/SulliDai/PDL_2018-2019_GR1", ' ', "", "", true, false);
		Assertions.assertFalse(u.isWikipediaURL());

	}
	
}
