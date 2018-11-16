package Historique;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.sweble.wikitext.parser.nodes.WtTable;
import org.sweble.wikitext.parser.nodes.WtTableCaption;
import org.sweble.wikitext.parser.parser.LinkTargetException; 
import org.wikipedia.Wiki;

import com.bitplan.mediawiki.japi.Mediawiki;

//import pdl_2018.groupeSMKS1.TextConverter;


/*public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World" );
    }
}*/



/**
 * Hello world! test commit de Margaux 
 *
 */
public class HistoriqueApp {
	Logger Logger = org.apache.log4j.Logger.getLogger(HistoriqueApp.class);

	public static void main(String[] args) throws Throwable {

		// Extraction();
		// ConvertirAdresseWikiCode("https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras");
		// exporterCSV("e");
		 testJsoup();
		//testLibrary();

	}

	/**
	 * ouverture du fichier adresse.txt lire toutes les lignes (url)
	 */
	public static void Extraction() {
		try {

			InputStream flux = new FileInputStream("adresse.txt");
			InputStreamReader lecture = new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			String ligne;
			while ((ligne = buff.readLine()) != null) {
				System.out.println(ligne);
				System.out.println(verificationUrl(ligne));
			}
			buff.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

			
		

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
	 * récupère le wikicode
	 * 
	 * @param text
	 */
	public static void ExtraireWikiCode(String text) {
		System.out.println("bien");
		// String to be scanned to find the pattern.
		String pattern = ".*<textarea(.*)</textarea>.*";
		// Create a Pattern object
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

	/*
	 * récupère le html
	 * 
	 * @param txtHtml
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

	/*
	 * Exporter vers CSV
	 * 
	 * @param Text
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

	public static void testJsoup() throws IOException {
		Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras").get();

		Element table = doc.select("table.wikitable").get(0);

		Elements rows = table.getElementsByTag("tr");
		for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
			Element row = rows.get(i);
			Elements cols = row.select("td");
			cols.select("span").remove();

			String d = cols.toString();
			// Pattern p = Pattern.compile(" <a href=.*\">");
			Pattern p = Pattern.compile("( {0,1}<a href=.*\">)");
			Matcher match = p.matcher(d);

			if (match.find()) {
				// System.out.println("sophie");
				String textComplet = match.group(0);
				// d=d.replace(textComplet, "");
				// System.out.println(textComplet);
			}
			// d=d.replace("<a href=\"/wiki/Canon_EOS_M100\" title=\"Canon EOS M100\">",
			// "");
			d = d.replace("<br>", " ");
			d = d.replace("</br>", " ");
			d = d.replace("<td>", "");
			d = d.replace("</td>", "");

			d = d.replace("<p>", "");
			d = d.replace("</p>", "");

			d = d.replace("</a>", "");

			System.out.println(d);

		}
	}

	// System.out.println(URLEncoder.encode(getFileContent("d:/1.jpg"),
	// "ISO-8859-1"));

	public static String getFileContent(String filePath) {
		try {
			File file = new File(filePath);
			byte bt[] = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(bt);
			fis.close();

			return new String(bt, "ISO-8859-1");
		} catch (FileNotFoundException e) {
			System.out.print("error 1" + e.getMessage());
		} catch (IOException e) {
			System.out.print("error 2" + e.getMessage());
		} catch (Exception e) {
			System.out.print("error 3" + e.getMessage());
		}

		return "";
	}

	public static void testLibrary() {
		// Mediawiki("https://fr.wikipedia.org/wiki/Discussion_utilisateur:185.76.0.147");

		try {

			/*
			 * Test avec la librairie bitplan.mediawiki
			 */

			// Mediawiki wiki = new Mediawiki("https://fr.wikipedia.org");
			// String contenu = wiki.getPageContent("Rennes");

			// WikitextParser test = new WikitextParser(null);
			// wiki.login("Dx002", "test");

			/*
			 * Test avec la wikipédia.org
			 * https://fr.wikipedia.org/wiki/Discussion_utilisateur:148.60.32.206?fbclid=IwAR3dxFvdL-UUMZXAS2c7KaRXoepZf8tbYR9memslXqQlwh2xgQxkz943y4c
			 */

			Wiki wikisweble = new Wiki("fr.wikipedia.org");

		//	https://fr.wikipedia.org/wiki/Saison_7_de_Game_of_Thrones#/media/File:Gaztelugatxe_4199995260.jpg

			//String contenu2 = wikisweble.getPageText("LostWinds");
			String contenu2 = wikisweble.getPageText("Équipe_de_France_masculine_de_football");
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

			/*
			 * test librairie sweble
			 */

			// System.out.println(convertWikiText("Aide:Insérer un tableau (wikicode,
			// avancé)", contenu2, 999999));

			WikiConfig config = DefaultConfigEnWp.generate();

			WtEngineImpl engine = new WtEngineImpl(config);
			PageTitle pageTitle = PageTitle.make(config, "title");
			PageId pageId = new PageId(pageTitle, -1);
			ExpansionCallback callback = null;
			EngProcessedPage parse = engine.parse(pageId, contenu2, callback);
		//	System.out.println(parse.toString());
		
			parcourirNode(parse);

			// Fonction qui recupere les tableaux, mettre en recursif pour que ca prenne tous les tableaux a tout niveaux. 
		/*for (Iterator<WtNode> i = parse.iterator(); i.hasNext();) {
			WtNode item = i.next();
				System.out.println(item);
				//System.out.println("-------");
				for (Iterator<WtNode> j = item.iterator(); j.hasNext();) {

					//System.out.println("********");
					WtNode item2 = j.next();
				//System.out.println(item2);

					for (Iterator<WtNode> k = item2.iterator(); k.hasNext();) {

					//System.out.println("////////////////////");
						WtNode item3 = k.next();
						//System.out.println(item3);		
						for (Iterator<WtNode> l = item3.iterator(); l.hasNext();) {
							WtNode item4 = l.next();
							//System.out.println("....................");
						//System.out.println(item4);	
							//System.out.println(item4.getNodeName());
							if (item4.getNodeName().toString().equals("WtTable")) {
								//Insersion dans une liste de tableau
								System.out.println(item4.toString());
							System.out.println(item4.get(0).get(0).get(1).get(0).toString().endsWith("WtText(\"wikitable\")"));
						
								System.out.println("TROUVERTOUVER");
							}
						}
					}
						
						
				}

			}*/
		

			

			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*private static void parcourir(EngProcessedPage parse) {
		// Fonction qui recupere les tableaux, mettre en recursif pour que ca prenne tous les tableaux a tout niveaux. 
	for (Iterator<WtNode> i = parse.iterator(); i.hasNext();) {
		WtNode item = i.next();
		if (item.getNodeName().toString().equals("WtTable")) {
			System.out.println("TROUVERTOUVER");
		}
		parcourirNode(item);
	}
		
	}*/
	private static void parcourirNode(WtNode fils) {
				for (Iterator<WtNode> l = fils.iterator(); l.hasNext();) {
					fils = l.next();
					
					
					if (fils.getNodeType() == WtTable.NT_TABLE) {
						 WtTable table = (WtTable) fils;
				
						 
						// System.out.println(table.getAttribute(name));
						//  table.
						  String wikitable = table.toString();
					//	  System.out.println(wikitable.toString());
						
//						String wikitable = fils.toString();
//						//System.out.println(wikitable.indexOf("wikitable"));
//						if(wikitable.indexOf("wikitable")!=-1){
//						//Insersion dans une liste de tableau
						System.out.println("TROUVERTOUVER");
						}
					parcourirNode(fils);
					}
					
					
					//					if (fils.getNodeName().toString().equals("WtTable")) {
//						System.out.println(fils.getNodeName().toString());
						//String wikitable = fils.toString();
//						//System.out.println(wikitable.indexOf("wikitable"));
//						if(wikitable.indexOf("wikitable")!=-1){
//						//Insersion dans une liste de tableau
//						System.out.println("TROUVERTOUVER");}}
		
					}

	public static String convertWikiText(String title, String wikiText, int maxLineLength)
			throws LinkTargetException, EngineException {
		// Set-up a simple wiki configuration
		WikiConfig config = DefaultConfigEnWp.generate();
		// Instantiate a compiler for wiki pages
		WtEngineImpl engine = new WtEngineImpl(config);
		// Retrieve a page
		PageTitle pageTitle = PageTitle.make(config, title);
		PageId pageId = new PageId(pageTitle, -1);
		// Compile the retrieved page
		EngProcessedPage cp = engine.postprocess(pageId, wikiText, null);
		TextConverter p = new TextConverter(config, maxLineLength);
		return (String) p.go(cp.getPage());
	}

	/**
	 * Vérification de l'intégrité de la ligne de commande
	 *
	 * @param commandLine
	 * @author KLE
	 * @date 14 octobre 2018
	 * La méthode prend en paramètre une ligne de commande String, et renvoi "true" si cette ligne de commande est conforme à la charte Wikimatrix, "false" sinon.
	 */
	public boolean verifIntegriteCommandLine(String commandLine) {

		boolean jetonIntegrite = true; //On initialise à vrai le jeton d'intégrité. Il passe à faux dès qu'un non respect de la charte "ligne de commande" est détecté.

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
			System.out.println("Vous n'avez pas choisi de source à partir de laquelle lancer l'extraction");
			jetonIntegrite = false;
		}
		if ((nbImport > 0) && (nbURL > 0)) {
			System.out.println("Vous ne pouvez choisir qu'un seul mode d'importation pour lancer l'extraction");
			jetonIntegrite = false;
		}

		if (nbImport == 1){ // On vérifie que le chemin de fichier spécifié est valide (on ne teste pas s'il est fonctionnel)
			Pattern pImport=Pattern.compile("-import\\[.*?\\]");
			Matcher mImport=pImport.matcher(commandLine);
			String contenuImport = mImport.group(1);
			String contenuImportExtension = contenuImport.substring(contenuImport.length() -4, contenuImport.length());
			if((contenuImportExtension!=".txt") || (contenuImport.length()<5)){
				System.out.print("Le chemin d'accès au fichier d'URLs à importer est invalide");
				jetonIntegrite=false;
			}
		} else if (nbURL == 1){ // On vérifie que l'URL spécifiée est valide, qu'il s'agit d'une url wiki (on ne teste pas si elle est fonctionnelle)
			Pattern pURL=Pattern.compile("-url\\[.*?\\]");
			Matcher mURL = pURL.matcher(commandLine);
			String contenuURL = mURL.group(1);
			if((contenuURL!="https://en.wikipedia.org/") || (contenuURL!="https://fr.wikipedia.org/") || (contenuURL!="http://en.wikipedia.org/") || (contenuURL!="http://fr.wikipedia.org/")){
				System.out.println("L'url saisie n'est pas prise en charge par Wikimatrix");
				jetonIntegrite=false;
			}
		}

		int nbSave = StringUtils.countMatches(commandLine, "-save");
		if (nbSave == 1){ // On vérifie que le chemin de fichier de sortie est valide (on ne teste pas s'il est fonctionnel)
			Pattern pSave=Pattern.compile("-save\\[.*?\\]");
			Matcher mSave=pSave.matcher(commandLine);
			String contenuSave = mSave.group(1);
			String contenuSaveExtension = contenuSave.substring(contenuSave.length() -4, contenuSave.length());
			if((contenuSaveExtension!=".csv") || (contenuSave.length()<5)){
				System.out.println("Le chemin du fichier de sortie spécifié est invalide");
				jetonIntegrite=false;
			}
		} else if (nbSave > 1){
			System.out.println("Il est impossible de spécifier plusieurs fichiers de sortie");
			jetonIntegrite = false;
		}

		int nbDelimit = StringUtils.countMatches(commandLine, "-delimit");
		List caracteresAutorises = new ArrayList(); // Cette liste permet de modifier facilement les délimiteurs autorisés par Wikimatrix sans changer le reste de la méthode.
		caracteresAutorises.add(';');
		caracteresAutorises.add(','); // A CHANGER, C'EST PAS BEAU
		if (nbDelimit == 1) { //On vérifie l'intégrité du délimiteur : est-il autorisé par Wikimatrix ?
			Pattern pDelimit=Pattern.compile("-delimit\\[.*?\\]");
			Matcher mDelimit = pDelimit.matcher(commandLine);
			char contenuDelimit = mDelimit.group(1).charAt(0);
			if(!caracteresAutorises.contains(contenuDelimit)){
				System.out.println("Le délimiteur saisi n'est pas pris en charge par Wikimatrix");
				jetonIntegrite = false;
			}
		} else if (nbDelimit>1){
			System.out.println("Vous ne pouvez choisir qu'un seul délimiteur");
			jetonIntegrite = false;
		}

		return jetonIntegrite;
	}

}
