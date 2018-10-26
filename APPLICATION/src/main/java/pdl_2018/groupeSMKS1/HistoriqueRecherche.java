package pdl_2018.groupeSMKS1;

import org.apache.log4j.Logger;
import org.apache.commons.lang.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sweble.wikitext.engine.EngineException;
import org.sweble.wikitext.engine.ExpansionCallback;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.nodes.WtNode;
import org.sweble.wikitext.parser.parser.LinkTargetException;
import org.wikipedia.Wiki;

import com.bitplan.mediawiki.japi.Mediawiki;

public class HistoriqueRecherche {
	Logger Logger = org.apache.log4j.Logger.getLogger(HistoriqueRecherche.class);

	public static void main(String[] args) throws Throwable {
	
		//Extraction();
		// ConvertirAdresseWikiCode("https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras");
		// exporterCSV("e");
		// testJsoup();
		//testLibrary();
		exporterCSV("fff");

	}

	/**
	 * ouverture du fichier adresse.txt lire toutes les lignes (url)
	 * 
	 * @date 25 octobre 2018
	 */
	
	public static void Extraction() {
		try {
			InputStream flux = new FileInputStream("adresse.txt");
			InputStreamReader lecture = new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			String ligne;
			while ((ligne = buff.readLine()) != null) {
				System.out.println(ligne);
				//System.out.println(verificationUrl(ligne));
			}
			buff.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
			
	/**
	 * récupère le html et le mets en string avec séparateur
	 * 
	 * @param txtHtml
	 * 
	 * @date 25 octobre 2018
	 */
	
	public static void BaliseTableau(String txtHtml) throws FileNotFoundException {
		Pattern p = Pattern.compile(".*<table class=\"wikitable.*<tbody><tr><th>(.*)</table>.*");
		Matcher match = p.matcher(txtHtml);
		if (match.matches()) {
			String textComplet = match.group(1);
			textComplet = textComplet.replace("</th><th>", ";");
			textComplet = textComplet.replace("<p>", " ");
			textComplet = textComplet.replace("</p>", " ");
			textComplet = textComplet.replace("</th></tr><tr><td>", "\n");
			textComplet = textComplet.replace("<a href=\"/wiki/Canon_EOS-1Ds\" title=\"Canon EOS-1Ds\">", "");
			System.out.println(textComplet);
			exporterCSV(match.group(1));
		}
	}

	/**
	 * Exporter vers CSV 
	 * @param Text
	 * 
	 * @date 25 octobre 2018
	 */

	public static void exporterCSV(String Text) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("test_enregistrer_csv.csv"));
		StringBuilder sb = new StringBuilder();
		// System.out.println(Text);
		sb.append(Text);
		pw.write(Text);
		pw.close();
		System.out.println("done!");

	}
	
	/**
	 * Test Jsoup capture balisage
	 * 
	 * @date 25 octobre 2018
	 */

	public static void testJsoup() throws IOException {
		Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras").get();
		Element table = doc.select("table.wikitable").get(0);
		Elements rows = table.getElementsByTag("tr");
		for (int i = 1; i < rows.size(); i++) { 
			Element row = rows.get(i);
			Elements cols = row.select("td");
			cols.select("span").remove();
			String d = cols.toString();
			System.out.println(d);
		}
	}


	/**
	 * Test des librairies
	 * 
	 *
	 * @date 25 octobre 2018
	 */
	
	public static void testLibrary() {

		try {

			/*
			 * Test avec la wikipédia.org
			 */

			Wiki wikisweble = new Wiki("fr.wikipedia.org");

			//String contenu2 = wikisweble.getPageText("LostWinds");
			String contenu2 = wikisweble.getPageText("Championnats pan-pacifiques 1989");

			/*
			 * test librairie sweble
			 */

			WikiConfig config = DefaultConfigEnWp.generate();

			WtEngineImpl engine = new WtEngineImpl(config);
			PageTitle pageTitle = PageTitle.make(config, "title");
			PageId pageId = new PageId(pageTitle, -1);
			ExpansionCallback callback = null;
			EngProcessedPage parse = engine.parse(pageId, contenu2, callback);
			System.out.println(parse.toString());
			parcourirNode(parse);
			
			/*
			 * Test avec la librairie bitplan.mediawiki
			 */

			// Mediawiki wiki = new Mediawiki("https://fr.wikipedia.org");
			// String contenu = wiki.getPageContent("Rennes");

			// WikitextParser test = new WikitextParser(null);
			
			/*
			 * Test avec la librairie sweble pour matcher wikitable
			 */

			/*
			 * String regex = "\n"; String replacement = ""; contenu =
			 * contenu.replaceAll(regex, replacement); //System.out.println(contenu);
			 * //Pattern p = Pattern.compile("{| class="wikitable"(.*)|}"); Pattern p =
			 * Pattern.compile("wikitab(.*)|}"); Matcher match = p.matcher(contenu);
			 * 
			 * while (match.find()) { System.out.println("groupe = " + match.group()) ; }
			 * Parse parse = wiki.getParse(contenu); System.out.println(parse.getText());
			 */
			
			// System.out.println(convertWikiText("Aide:Insérer un tableau (wikicode,
			// avancé)", contenu2, 999999));
			
			// Mediawiki("https://fr.wikipedia.org/wiki/Discussion_utilisateur:185.76.0.147");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Parcourir le wikiCode pour trouver le WtTable et Wikitable
	 * 
	 * @param fils
	 * @date 25 octobre 2018
	 */
	private static void parcourirNode(WtNode fils) {
				for (Iterator<WtNode> l = fils.iterator(); l.hasNext();) {
					fils = l.next();
					if (fils.getNodeName().toString().equals("WtTable")) {
						System.out.println(fils.getNodeName().toString());
						String wikitable = fils.toString();
						if(wikitable.indexOf("wikitable")!=-1){
						System.out.println("wikitable trouvé");}}
					parcourirNode(fils);
				}
	}
	

	/**
	 * Vérification de l'intégrité de la ligne de commande
	 * 
	 * @param commandLine
	 * @date 14 octobre 2018
	 */
	public boolean verifIntegriteCommandLine(String commandLine) {

		boolean jetonIntegrite = true;

		// Vérification du choix HTML / Wikicode
		int nbHTML = StringUtils.countMatches(commandLine, "-html");
		int nbWikicode = StringUtils.countMatches(commandLine, "-wikicode");
		if ((nbHTML > 1) || (nbWikicode > 1)) {
			System.out.println("La syntaxe de la commande est erronée : un même paramètre est saisi plusieurs fois");
			jetonIntegrite = false;
		} else if ((nbHTML < 1) && (nbWikicode < 1)) {
			System.out.println(
					"Vous devez obligatoirement indiquer une méthode d'extraction, en ajoutant -html ou -wikicode");
			jetonIntegrite = false;
		}

		// Vérification de l'URL
		int nbURL = StringUtils.countMatches(commandLine, "-url");
		int nbImport = StringUtils.countMatches(commandLine, "-import");
		if (nbURL > 1) {
			System.out.println(
					"Pour lancer l'extraction à partir de plusieurs URLs, veuillez utiliser la commande -import");
			jetonIntegrite = false;
		}
		if (nbImport > 1) {
			System.out.println("La commande import ne permet de manipuler qu'un seul fichier d'URLs à la fois");
			jetonIntegrite = false;
		}
		if ((nbImport < 1) && (nbURL < 1)) {
			System.out.println("Vous n'avez pas choisi de sources à partir desquelles lancer l'extraction");
			jetonIntegrite = false;
		}
		if ((nbImport > 0) && (nbURL > 0)) {
			System.out.println("-url ou -import, il faut choisir !");
			jetonIntegrite = false;
		}
		if (nbURL == 1 && ((!commandLine.contains("https://en.wikipedia.org/"))
				|| (!commandLine.contains("https://fr.wikipedia.org/")))) {
			System.out.print("L'url saisie n'est pas prise en charge par Wikimatrix");
			jetonIntegrite = false;
		}
		return jetonIntegrite;
	}

	
	
	// #################################  Fonction obsolète ###########################
	
	/*
	 * 	 
	 * @param url
	 * @date 14 octobre 2018
	 *  
	 */
	
	public static void ConvertirAdresseWikiCode(String url) {
		String pattern = ".*wiki/(.*)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(url);
		if (m.find()) {
			String d = "https://fr.wikipedia.org/w/index.php?title=";
			String d1 = "&action=edit";
			String d2 = d + m.group(1) + d1;
			System.out.println(d2);
			getTextFile(d2);
		} else {
			System.out.println("NO MATCH");
		}
	}
	
	/*
	 * @Param : url Précondition = True if adresse Wiki false sinon
	 *
	 */
	public static boolean verificationUrl(String url) throws IOException, BadLocationException {
		String URLValide = ".wikipedia.org";
		if (url.contains(URLValide)) {
			getTextFile(url);
		}
		return url.contains(URLValide);
	}

	/*
	 * lecture du site internet affiche de lire le html
	 * 
	 * @param _url
	 */
	public static String getTextFile(String _url) {
		BufferedReader reader = null;
		try {
			URL url = new URL(_url);
			URLConnection urlConnection = url.openConnection();
			reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);

				// sb.append("\n");
			}

			BaliseTableau(sb.toString());
			// ExtraireWikiCode(sb.toString());

			return sb.toString();

		} catch (IOException ex) {

			return "";
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {

			}
		}
	}
	
	/**
	 * récupère le wikicode
	 * 
	 * @param text
	 * @date 25 octobre 2018
	 */
	
	public static void ExtraireWikiCode(String text) {
		System.out.println("bien");
		// String to be scanned to find the pattern.
		String pattern = ".*<textarea(.*)</textarea>.*";
		Pattern r = Pattern.compile(pattern);
		// Now create matcher object.
		Matcher m = r.matcher(text);
		// System.out.println("Found value: " + m.group(1) );
		if (m.find()) {
			System.out.println("Found value: " + m.group(1));
		} else {
			System.out.println("nomatch");
		}
	}
}
