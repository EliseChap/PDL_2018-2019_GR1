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
	private Map<String, String> ensembleDiff;
	private Map<String, String> fusion;

	/**
	 * Methode Challenge Imports the txt file Browse the run run file
	 * (csv analysis)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBenchExtractors() throws Exception {
		this.fusion = new LinkedHashMap<String, String>();
		this.stat = new LinkedHashMap<String, Double>();
		this.ensembleDiff = new LinkedHashMap<String, String>();
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
								//Html html=new Html(wurl);
			//	this.fusion.putAll(html.getNbreFusion());
			//	Url url2 = new Url(wurl, ';', url + ".csv", "output/",  true, true);

			} catch (Exception e) {
				listException.add(wurl);
			}
			nurl++;
		}

		br.close();
	//	assertEquals(nurl, 336);
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
	 * Search for files in the Common File Search folder and
	* Scans File search not present and adds to the list
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
		
		Map<String, String> croisement = new LinkedHashMap<String, String>();
		

		rapportCsvStat("rapportWikiMatrixComparaison.csv", this.stat);
		rapportCsvdiff("rapportWikiMatrixDiff.csv", this.ensembleDiff);
		rapportCsvdiff("rapportWikiMatrixFusion.csv", this.fusion);
	}

	private void rapportCsvdiff(String chemin, Map<String, String> ensembleDiff) {
	
		File fichier1 = new File(chemin);
		fichier1.delete();
		String csv = chemin;
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(csv));

			Set cles = ensembleDiff.keySet();
			Iterator it = cles.iterator();
			while (it.hasNext()) {
				Object cle = it.next();
				String valeur = ensembleDiff.get(cle);
				writer.writeNext(new String[] { cle.toString() + ";" + valeur });
			}

			System.out.println("CSV File written successfully All at a time");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add to the list all the files not found in the folder
	* opposes (wiki / html) list
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
	 * browse the csv and compare them and put them in the list of results
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
			Comparateur comp = new Comparateur(tabWiki, tabHtml,nom,nom);
			
			if(comp.getRatio()!=0){
						stat.put(nom+";"+nom, comp.getRatio());
			this.ensembleDiff.putAll(comp.getDiff());
			}
		else {
			String autreNom = nom.replaceAll("-[0-9]*.csv", "");
				AutreNom(autreNom,comp,cheminWiki,nom);
			}}
	
		
	}

	private void AutreNom(String autreNom, Comparateur comp, String cheminWiki, String nom) {
		int i = 1;
		String cheminHtml = "output/html/" + autreNom+"-"+i+".csv";
		File f = new File(cheminHtml);
		while(comp.getRatio()==0 && f.isFile()) {
			cheminHtml = "output/html/" + autreNom+"-"+i+".csv";
			String[] tabWiki = lectureCSV(cheminWiki);
			String[] tabHtml = lectureCSV(cheminHtml);
			comp = new Comparateur(tabWiki, tabHtml,autreNom+"-"+i+".csv",nom);
			i++;
			cheminHtml = "output/html/" + autreNom+"-"+i+".csv";
			f = new File(cheminHtml);
		}
		i--;
		stat.put(nom+";"+autreNom+"-"+i+".csv", comp.getRatio());
		this.ensembleDiff.putAll(comp.getDiff());
		
	}

	/**
	 * read the csv and get back his line board
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
	 * Intersection of two lists
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
	 *Collect file names together
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
	 *Write csv of a map
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
