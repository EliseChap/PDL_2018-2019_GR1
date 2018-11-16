package pdl_2018.groupeSMKS1;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class TestUrl {

	private static Url u;

	@BeforeAll
	public static void initialise() {
		u = new Url("https://fr.wikipedia.org/wiki/Saison_7_de_Game_of_Thrones", ' ', "", "", true, false);
		
	}
 
	@Test
	public void testURLstandard() {

		u.setUrl("https://fr.wikipedia.org/wiki/Saison_7_de_Game_of_Thrones");
		Assertions.assertTrue(u.verifURL());
		Assertions.assertTrue(u.isWikipediaURL());

		u.setUrl("https://ent.univ-rennes1.fr/f/welcome/normal/render.uP");
		Assertions.assertTrue(u.verifURL());
		Assertions.assertFalse(u.isWikipediaURL());

		u.setUrl("https://www.wikimanche.fr/Accueil");
		Assertions.assertTrue(u.verifURL());
		Assertions.assertFalse(u.isWikipediaURL());
	}

	@Test
	public void testURLNonValide() {
		u.setUrl("https://fr.wikipedia.");
		Assertions.assertFalse(u.verifURL());

		u.setUrl("test");
		Assertions.assertFalse(u.verifURL());

		u.setUrl("https://fr.wikipedia.org/wiki/Page");
		Assertions.assertTrue(u.verifURL());

		// u.setUrl("http://etudiant.istic.univ-rennes1.fr/current/L1L2/for");
		// Assertions.assertFalse(u.verifURL());

	}

	@Test
	public void testIsWikiURLFalse() {

		u.setUrl("https://github.com/SulliDai/PDL_2018-2019_GR1");
		Assertions.assertFalse(u.isWikipediaURL());

		u.setUrl("https://www.wikimanche.fr/Accueil");
		Assertions.assertFalse(u.isWikipediaURL());

	}

	@Test
	public void testIsAPicture() {
		u.setUrl("https://fr.wikipedia.org/wiki/Page");
		Assertions.assertFalse(u.isAPicture());

		u.setUrl("https://fr.wikipedia.org/wiki/Game_of_Thrones#/media/File:George_R.R._Martin_at_Archipelacon.jpg");
		Assertions.assertTrue(u.isAPicture());

		u.setUrl(
				"https://fr.wikipedia.org/wiki/Game_of_Thrones#/media/File:Correspondance_Chap%C3%AEtres_livres_-_S%C3%A9rie_Game_of_Thrones4.png");
		Assertions.assertTrue(u.isAPicture());

		u.setUrl("https://upload.wikimedia.org/wikipedia/commons/e/e4/Fountain_of_Mercury_-_Alcazar_of_Seville.JPG");
		Assertions.assertTrue(u.isAPicture());

		u.setUrl("https://fr.wikipedia.org/wiki/Game_of_Thrones#/media/File:Moins16.svg");
		Assertions.assertTrue(u.isAPicture());

		u.setUrl(
				"https://fr.wikipedia.org/wiki/Coupe_du_monde_de_football#/media/File:Jogo_no_Est%C3%A1dio_do_Maracan%C3%A3,_antes_da_Copa_do_Mundo_de_1950.tif");
		Assertions.assertTrue(u.isAPicture());

		// A Ajouter : test pour gif bmp
	}

	@Test
	public void testIsDiscussion() {
		u.setUrl("https://fr.wikipedia.org/wiki/Discussion_utilisateur:148.60.32.206");
		Assertions.assertTrue(u.isATwoPoint());

		u.setUrl("https://fr.wikipedia.org/wiki/On_est_encore_l%C3%A0_:_Bercy_2008");
		Assertions.assertFalse(u.isATwoPoint());

	}

	@Test
	public void testIsContributions() {
		u.setUrl("https://fr.wikipedia.org/wiki/Sp%C3%A9cial:Contributions/86.253.41.153");
		Assertions.assertTrue(u.isATwoPoint());
	}

	@Test
	public void testIsRealURL() {
	}

}
