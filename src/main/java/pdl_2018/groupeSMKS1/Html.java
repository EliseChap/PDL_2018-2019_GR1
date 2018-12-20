
package pdl_2018.groupeSMKS1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Html extends Extracteur {

	private String url;
	private char delimit;
	private String cheminCSV;
	private String nomCSV;
	private boolean extraHTML;
	private boolean extraWiki;
	private ArrayList<Tableau> lesTableaux;
	private Map<String, Element> lesHtmltab;

	public Html(String url, char delimit, String cheminCSV, String nomCSV, boolean extraHTML, boolean extraWiki) {
		this.url = url;
		this.delimit = delimit;
		this.cheminCSV = cheminCSV;
		this.extraHTML = extraHTML;
		this.extraWiki = extraWiki;
		this.nomCSV = nomCSV;
		lesTableaux = new ArrayList<Tableau>();
		lesHtmltab = new HashMap<String, Element>();
		recuperationPage();
	}

	/**
	 * 
	 * @return  LesTableaux list
	 */
	@Override
	public ArrayList<Tableau> getLesTableaux() {
		return lesTableaux;
	}

	/**
	 * 
	 * Delete the table
	 */
	@Override
	public void removeTableau(Tableau leTableau) {
		if (lesTableaux.contains(leTableau)) {
			lesTableaux.remove(leTableau);
		}
	}

	/**
	 * 
	 * @return The name of the table
	 */
	@Override
	public String getNomTableau(Tableau leTableau) {
		return leTableau.getnomTab();
	}

	@Override
	/**
	 * Add the Table to the list lesTableaux if he is not already present
	 * 
	 * @param leTableau
	 */
	public void addTableau(Tableau leTableau) {
		if (!lesTableaux.contains(leTableau)) {
			lesTableaux.add(leTableau);
		}
	}

	/**
	 * 
	 * @param tab      A string array with the values of a table
	 *                 extracted from wikipedia
	 * @param nomTab   The name of the wikipedia table
	 * @param wikiHtml true to indicate that the data has been extracted with
	 *                 wikicode
	 * @return A Table object
	 */
	@Override
	public Tableau constructeurTableau(String[][] tab, String cle, boolean wikiHtml) {
		return new Tableau(delimit, cheminCSV, nomCSV, tab, cle, false);
	}

	/**
	 * 
	 * @return The delimiter chosen by the user
	 */
	public char getDelimit() {
		return this.delimit;
	}

	/**
	 * 
	 * @return The backup path of the file chosen by the user
	 */
	public String getCheminCSV() {
		return this.cheminCSV;
	}

	/**
	 * 
	 * @return The name of the CSV file chosen by the user
	 */
	public String getNomCSV() {
		return this.getNomCSV();
	}

	/**
	 * 
	 * @return A booleen that indicates whether the extraction is done in HTML (true) or
	 *         not (false)
	 */
	public boolean getExtraHTML() {
		return this.extraHTML;
	}

	/**
	 * 
	 * @return A booleen that indicates if the extraction must be done in
	 *         wikicode(true) or not (false)
	 */
	public boolean getExtraWiki() {
		return this.extraWiki;
	}

	/**
	 * Get the page, and each table on this page is put in a map with
	 * for key his name or by default table + his table number
	 * 
	 */
	@Override
	public void recuperationPage() {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements table = doc.select(".wikitable");

			for (int i = 0; i < table.size(); i++) {
				String nom = "";

				Elements caption = table.get(i).getElementsByTag("caption");
				if (caption.isEmpty() || !caption.hasText()) {
					nom = "table" + i;
				} else {
					nom = caption.text();

				}
				if (lesHtmltab.containsKey(nom)) {
					nom = nom + i;
				}
				lesHtmltab.put(nom, table.get(i)); // key : title of the table

			}
			TraitementMap();

		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

	/**
	 * For each table of the Map, we do the necessary treatments
	 * 
	 */

	public void TraitementMap() {
		Set cles = lesHtmltab.keySet();
		Iterator<String> it = cles.iterator();

		while (it.hasNext()) {
			String cle = it.next();
			Element ensemble = lesHtmltab.get(cle);
			boolean tabcreated = false;

			Elements tr = ensemble.getElementsByTag("tr");
			int taille = getMax(tr);

			Element first = tr.first();

			while (!first.hasText()) {

				tr.remove(0);
				first = tr.first();
			}

			String[][] tab = null;
			int i = 0;

			for (Element e : tr) {

				Elements thdetr = e.getElementsByTag("th");

				Elements td = e.getElementsByTag("td");

				if (!tabcreated) {
					tab = new String[tr.size()][taille];
					tabcreated = true;
				}
				int j = 0;
				tab = bodyTableau(tab, i, 0, thdetr);

				tab = bodyTableau(tab, i, j, td);

				i++;
			}

			tab = TraitementColonnesVides(tab);
			//lectureTableau(tab);
			lesTableaux.add(constructeurTableau(tab, cle, false));
		}
	}

	
	/**
	 * Analysis of table content
	 * 
	 * @param tab, a dimensional array of Strings
	 * @param i
	 * @param j
	 * @param td
	 * @return tab[][]
	 */
	public String[][] bodyTableau(String[][] tab, int i, int j, Elements td) {

		if (td.size() > tab[0].length) { // cas de cellules hors du tableau

			td.remove(td.size() - 1);

		}

		for (Element g : td) {

			if (tab[i][j] != null) {

				i = deplacerTableau(tab, i, j, false);
				j = deplacerTableau(tab, i, j, true);

			}
			String current = g.text();
			
			String cell = g.attr("rowspan");

			if (cell != "") {
				int y = Integer.parseInt(cell);

				if (y + i > tab.length) {

					y = tab.length - i ;

				}
				
				tab = Fusion(tab, i, j, y, current, true);

			}
			
			String cellcol = g.attr("colspan");

			if (g.attr("colspan") != "") {
	
				int x = Integer.parseInt(cellcol);

				if (x + j > tab[0].length) {
					x = tab[0].length - j;
				}
				tab = Fusion(tab, i, j, x, current, false);

			}
		

			if (g.attr("bgcolor") != "") {
				tab[i][j] = g.attr("bgcolor");
			}

			tab[i][j] = g.text() + "";

			if (getUrlImage(g) != "") {
				tab[i][j] = tab[i][j] + " " + getUrlImage(g);
			}

			if (j < tab[i].length - 1) {
				j++;

			} else {
				j = 0;
				i++;

			}

		}
		return tab;
	}

	/**
	 * Treatment of the table if there are empty columns in the table
	 * 
	 * @param tab
	 * @return String[][] tab, without empty columns
	 */
	public String[][] TraitementColonnesVides(String[][] tab) {
		boolean vide = true;
		int i = 0;
		String test = "test";
		for (int j = 0; j < tab[i].length; j++) {
			while (i < tab.length) {

				if (!((tab[i][j] + test).equalsIgnoreCase(test))) {
					vide = false;
				}
				i++;
			}
			i = 0;

			if (vide) {
				tab = SuppressionColVides(tab, j);
			}
			vide = true;
		}
		return tab;
	}

	/**
	 * Deleting empty columns
	 * 
	 * @param tab
	 * @param j
	 * @return String[][] tab, without empty columns
	 */

	public String[][] SuppressionColVides(String[][] tab, int j) {
		String[][] tab1 = new String[tab.length][j];
		if (j == tab[0].length - 1) {
			for (int a = 0; a < tab1.length; a++) {
				for (int b = 0; b < tab1[a].length; b++) {
					tab1[a][b] = tab[a][b];
				}
			}
		} else {
			for (int a = 0; a < tab1.length; a++) {
				for (int b = 0; b < tab1[a].length; b++) {
					if (b != j) {
						tab1[a][b] = tab[a][b];

					} else {
						tab1[a][b] = tab[a][b + 1];
					}
				}
			}
		}
		return tab1;
	}

	/**
	 * Move the cursor if the cell is already full
	 * 
	 * @param tab
	 * @param i
	 * @param j
	 * @param vertical
	 * @return int, i if vertical and j otherwise
	 */

	public int deplacerTableau(String[][] tab, int i, int j, boolean vertical) {

		while (tab[i][j] != null) {
		
			if (j < tab[i].length - 1) {
				
				j++;
			} else if (i < tab.length - 1) {
				j = 0;
				i++;

			}

		}
		if (vertical) {
			return j;
		} else {
			return i;
		}
	}

	/**
	 * Display the entire table
	 * 
	 * @param tab
	 */
	public void lectureTableau(String[][] tab) {
		for (int a = 0; a < tab.length; a++) {
			for (int b = 0; b < tab[a].length; b++) {
				//System.out.println("i : " + a + " j : " + b + " valeur : " + tab[a][b]);
			}
		}
	}

	/**
	 * Fill in the table following the horizontal or vertical mergers
	 * 
	 * @param tab
	 * @param i
	 * @param j
	 * @param y
	 * @param current
	 * @param vertical
	 * @return Tab[][]
	 */
	public String[][] Fusion(String[][] tab, int i, int j, int y, String current, boolean vertical) {
		if (vertical) {

			for (int b = 0; b < y; b++) {
				tab[i][j] = current;
				
				if (i < tab.length - 1) {
					i++;
				}

			}

		} else {
			//System.out.println("j" + j + "y" + y);
			
			for (int b = 0; b < y; b++) {
				tab[i][j] = current;
				if (j < tab[i].length - 1) {
					j++;
				}
				

			}
		}
		return tab;
	}

	/**
	 * Retrieve the URL of an image contained in a cell
	 * 
	 * @param Element g
	 * @return String of the URL
	 */

	public String getUrlImage(Element g) {

		Elements image = g.getElementsByTag("a");

		for (Element im : image) {
			if (im.attr("class").contains("image")) {
				im.attr("href");
				return im.attr("href");
			}

		}
		return "";
	}
	
	/**
	 * Retrieves the maximum number of columns in the table
	 * 
	 * @param Elements tr
	 * @return int who is the maximum
	 */


	public int getMax(Elements tr) {
		int max = 0;

		for (Element e : tr) {
			int counttd = 0;
			int countth = 0;
			Elements th = e.getElementsByTag("th");
			Elements td = e.getElementsByTag("td");
			for (Element a : th) {
				String cellcol = a.attr("colspan");
				if (a.attr("colspan") != "") {
					countth = countth + Integer.parseInt(cellcol) - 1;

				}
			}

			for (Element b : td) {
				String cellcol = b.attr("colspan");

				if (b.attr("colspan") != "") {
					counttd = counttd + Integer.parseInt(cellcol) - 1;

				}

			}
			if (max < th.size() + td.size() + counttd + countth) {
				max = th.size() + td.size() + countth + counttd;
			}
		}
		return max;
	}



}
