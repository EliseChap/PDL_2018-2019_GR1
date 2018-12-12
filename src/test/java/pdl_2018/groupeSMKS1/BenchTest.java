package pdl_2018.groupeSMKS1;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import com.opencsv.CSVWriter;

public class BenchTest {

	private List<String> listException;
	private Map<String, Double> stat;

	/**
	 * Methode Challenge Importe le fichier txt Parcours le fichier lance run
	 * (analyse des csv)
	 * 
	 * @throws Exception
	 */
	//@Test
	public void testBenchExtractors() throws Exception {
		this.listException = new ArrayList();
		this.stat = new LinkedHashMap<String, Double>();

		List<String> listException = new ArrayList<>();

		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
		String outputDirHtml = "output" + File.separator + "html" + File.separator;
		assertTrue(new File(outputDirHtml).isDirectory());
		String outputDirWikitext = "output" + File.separator + "wikitext" + File.separator;
		assertTrue(new File(outputDirWikitext).isDirectory());

		File file = new File("inputdata" + File.separator + "wikiurls.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String url;
		int nurl = 0;
		while ((url = br.readLine()) != null) {
			String wurl = BASE_WIKIPEDIA_URL + url;
			System.out.println("Wikipedia url: " + wurl);
			String csvFileName = mkCSVFileName(url, 1);
			System.out.println("CSV file name: " + url);

			try {
				Url url2 = new Url(wurl, ';', url + ".csv", "output/",  true, true);
			} catch (Exception e) {
				listException.add(wurl + ";" + e);
			}
			nurl++;
		}

		br.close();
		assertEquals(nurl, 336);
		run();
	}

	/*
	 * Methode ouvrir un fichier txt avec des urls http://...
	 */
	// @Test
	public void testFichierUrl() throws Exception {
		this.stat = new LinkedHashMap<String, Double>();
		this.listException = new ArrayList();
		try {
		Fichier monFichier = new Fichier("src/test/java/Fichiers_entree/mesUrl.txt", ';', "WikiMatrix.csv", "output/", true,
				true);
		} catch (Exception e) {
		
		}
		run();
	}

	/**
	 * Recherche des fichiers dans le dossier Recherche des fichiers commun et les
	 * analyses Recherche des fichiers non presente et ajoute dans la liste
	 */
	private void run() {

		List<String> htmlList = parcourirRepertoire("output/html");
		List<String> wikiList = parcourirRepertoire("output/wikitext");

		List<String> listCommun = intersection(htmlList, wikiList);
		List<String> htmlListNonTrouve = new ArrayList();
		htmlListNonTrouve.addAll(htmlList);
		htmlListNonTrouve.removeAll(listCommun);
		List<String> wikiListNonTrouve = new ArrayList();
		wikiListNonTrouve.addAll(wikiList);
		wikiListNonTrouve.removeAll(listCommun);

		parcourirCsv(listCommun);
		parcourirNonTrouve(wikiListNonTrouve, "Html");
		parcourirNonTrouve(htmlListNonTrouve, "Wiki");
		rapportCsv("rapportWikiMatrixErreur.csv", this.listException);
		rapportCsvStat("rapportWikiMatrixComparaison.csv", this.stat);

	}

	/**
	 * Ajoute a la liste tous les fichiers non trouve dans le dossier
	 * oppose(wiki/html)
	 * 
	 * @param NonTrouve
	 * @param type
	 */
	private void parcourirNonTrouve(List<String> NonTrouve, String type) {
		Iterator<String> it = NonTrouve.iterator();
		while (it.hasNext()) {
			String nom = it.next();
			stat.put(nom + ";-1;non trouve dans " + type, (double) -1);
		}
	}

	/**
	 * parcourir les csv et les comparer puis les mettres dans la liste de resultats
	 * 
	 * @param listCommum
	 */
	private void parcourirCsv(List<String> listCommum) {

		Iterator<String> it = listCommum.iterator();
		while (it.hasNext()) {
			String nom = it.next();
			String cheminWiki = "output/wikitext/" + nom;
			String cheminHtml = "output/html/" + nom;

			String[] tabWiki = lectureCSV(cheminWiki);
			String[] tabHtml = lectureCSV(cheminHtml);

			Comparateur comp = new Comparateur(tabWiki, tabHtml);
			stat.put(nom, comp.getRatio());
		}
	}

	/**
	 * lecture du csv et recupere son tableau de ligne
	 * 
	 * @param cheminWiki
	 * @return
	 */
	private String[] lectureCSV(String cheminWiki) {

		try {
			ArrayList<String> list = new ArrayList();
			File f = new File(cheminWiki);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			try {
				String line = br.readLine();

				while (line != null) {
					line = br.readLine();
					list.add(line);
				}

				br.close();
				fr.close();

				String[] array = list.toArray(new String[list.size()]);
				return array;
			} catch (IOException exception) {
				System.out.println("Erreur lors de la lecture : " + exception.getMessage());
			}
		} catch (FileNotFoundException exception) {
			System.out.println("Le fichier n a pas ete trouve");
		}
		return null;
	}

	/**
	 * Intersection de deux listes
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public List<String> intersection(List<String> list1, List<String> list2) {
		List<String> list = new ArrayList<String>();

		for (String t : list1) {
			if (list2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

	/**
	 * Recuperer ensemble des nom de fichiers
	 * 
	 * @param chemin
	 * @return
	 */
	private List<String> parcourirRepertoire(String chemin) {

		String[] listefichiers;
		File rep = new File(chemin);
		int i;
		File repertoire;
		listefichiers = rep.list();
		List<String> list = Arrays.asList(listefichiers);
		return list;
	}

	/**
	 * Ecrire les Csv
	 * 
	 * @param chemin
	 * @param list
	 */

	public void rapportCsv(String chemin, List<String> list) {
		File fichier1 = new File(chemin);
		fichier1.delete();
		String csv = chemin;
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(csv));
			for (String i : list) {
				writer.writeNext(new String[] { i.toString() });
			}
			System.out.println("CSV File written successfully All at a time");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Ecrire csv d'une map 
	 * @param chemin
	 * @param stat
	 */
	public void rapportCsvStat(String chemin, Map<String, Double> stat) {
		File fichier1 = new File(chemin);
		fichier1.delete();
		String csv = chemin;
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(csv));

			Set cles = stat.keySet();
			Iterator it = cles.iterator();
			while (it.hasNext()) {
				Object cle = it.next();
				Double valeur = stat.get(cle);
				writer.writeNext(new String[] { cle.toString() + ";" + valeur });
			}

			System.out.println("CSV File written successfully All at a time");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String mkCSVFileName(String url, int n) {
		return url.trim() + "-" + n + ".csv";
	}

}
