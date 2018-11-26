package pdl_2018.groupeSMKS1;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;



public class TestUrl {

	private static Url u;

	@BeforeClass
	public static void initialise() {
		u = new Url("https://www.facebook.com/", ' ', "", "", true, false);
		
	}
 
	@Test
	public void testURLstandard() {

		u.setUrl("https://fr.wikipedia.org/wiki/Saison_7_de_Game_of_Thrones");
		assertTrue(u.verifURL());
		assertTrue(u.isWikipediaURL());

		u.setUrl("https://ent.univ-rennes1.fr/f/welcome/normal/render.uP");
		assertTrue(u.verifURL());
		assertFalse(u.isWikipediaURL());

		u.setUrl("https://www.wikimanche.fr/Accueil");
		assertTrue(u.verifURL());
		assertFalse(u.isWikipediaURL());
	}

	@Test
	public void testURLNonValide() {
		u.setUrl("https://fr.wikipedia.");
		assertFalse(u.verifURL());

		u.setUrl("test");
		assertFalse(u.verifURL());

		u.setUrl("https://fr.wikipedia.org/wiki/Page");
		assertTrue(u.verifURL());

		// u.setUrl("http://etudiant.istic.univ-rennes1.fr/current/L1L2/for");
		// assertFalse(u.verifURL());

	}

	@Test
	public void testIsWikiURLFalse() {

		u.setUrl("https://github.com/SulliDai/PDL_2018-2019_GR1");
		assertFalse(u.isWikipediaURL());

		u.setUrl("https://www.wikimanche.fr/Accueil");
		assertFalse(u.isWikipediaURL());

	}

	@Test
	public void testIsAPicture() {
		u.setUrl("https://fr.wikipedia.org/wiki/Page");
		assertFalse(u.isAPicture());

		u.setUrl("https://fr.wikipedia.org/wiki/Game_of_Thrones#/media/File:George_R.R._Martin_at_Archipelacon.jpg");
		assertTrue(u.isAPicture());

		u.setUrl(
				"https://fr.wikipedia.org/wiki/Game_of_Thrones#/media/File:Correspondance_Chap%C3%AEtres_livres_-_S%C3%A9rie_Game_of_Thrones4.png");
		assertTrue(u.isAPicture());

		u.setUrl("https://upload.wikimedia.org/wikipedia/commons/e/e4/Fountain_of_Mercury_-_Alcazar_of_Seville.JPG");
		assertTrue(u.isAPicture());

		u.setUrl("https://fr.wikipedia.org/wiki/Game_of_Thrones#/media/File:Moins16.svg");
		assertTrue(u.isAPicture());

		u.setUrl(
				"https://fr.wikipedia.org/wiki/Coupe_du_monde_de_football#/media/File:Jogo_no_Est%C3%A1dio_do_Maracan%C3%A3,_antes_da_Copa_do_Mundo_de_1950.tif");
		assertTrue(u.isAPicture());

		// A Ajouter : test pour gif bmp
	}

	@Test
	public void testIsDiscussion() {
		u.setUrl("https://fr.wikipedia.org/wiki/Discussion_utilisateur:148.60.32.206");
		assertFalse(u.isAnArticle());

		u.setUrl("https://fr.wikipedia.org/wiki/On_est_encore_l%C3%A0_:_Bercy_2008");
		assertTrue(u.isAnArticle());

	}

	@Test
	public void testIsContributions() {
		u.setUrl("https://fr.wikipedia.org/wiki/Sp%C3%A9cial:Contributions/86.253.41.153");
		assertFalse(u.isAnArticle());
	}

	@Test
	public void testIsRealURL() {
	}

}
