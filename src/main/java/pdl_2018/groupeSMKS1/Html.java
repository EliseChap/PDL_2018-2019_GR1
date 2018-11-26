
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.parser.*;

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
	
	public ArrayList<Tableau> getLesTableau(){
		return lesTableaux; 
	}

	@Override
	public void removeTableau() {
	}

	@Override
	public String getNomTableau() {
		return "";
	}

	@Override
	public void addTableau(Tableau leTableau) {
		if (!lesTableaux.contains(leTableau)) {
			lesTableaux.add(leTableau);
		}
	}

	@Override
	public Tableau constructeurTableau(char delimit, String cheminCSV, String NomCSV, boolean extraHTML,
			boolean extraWiki) {
		return new Tableau();
	}

	/**
	 * 
	 * @return le delimiteur choisit par l'utilisateur
	 */
	public char getDelimit() {
		return this.delimit;
	}

	/**
	 * 
	 * @return le chemin de sauvegarde du fichier choisit par l'utilisateur
	 */
	public String getCheminCSV() {
		return this.cheminCSV;
	}

	/**
	 * 
	 * @return le nom du fichier CSV choisit par l'utilisateur
	 */
	public String getNomCSV() {
		return this.getNomCSV();
	}

	/**
	 * 
	 * @return un booleen qui indique si l'extraction doit ï¿½etre faite en HTML
	 *         (true) ou non (false)
	 */
	public boolean getExtraHTML() {
		return this.extraHTML;
	}

	/**
	 * 
	 * @return Un booleen qui indique si l'extraction doit etre faite en
	 *         wikicode(true) ou non (false)
	 */
	public boolean getExtraWiki() {
		return this.extraWiki;
	}

	public void recuperationPage() {
		try {
			Document doc = Jsoup.connect(url).get();
			// System.out.println(doc);

			Elements table = doc.select(".wikitable");
			for (int i = 0; i < table.size(); i++) {
				String nom = "";

				Elements caption = table.get(i).getElementsByTag("caption");
				if (caption.isEmpty()) {
					nom = "table" + i;
				} else {
					nom = caption.text();
				}
				lesHtmltab.put(nom, table.get(i)); // cle : titre du tableau

			}
			// System.out.println(table);
			TraitementMap();

		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

	public void TraitementMap() {
		Set cles = lesHtmltab.keySet();
		Iterator<String> it = cles.iterator();
		int counter = 0;

		while (it.hasNext()) {
			String cle = it.next();
			System.out.println(cle);
			Element ensemble = lesHtmltab.get(cle);
			boolean tabcreated = false;

			Elements tr = ensemble.getElementsByTag("tr");
			Element first = tr.first();
			System.out.println("tr" + tr.size());

			String[][] tab = null;
			int i = 0;

			for (Element e : tr) {

				Elements th = e.getElementsByTag("th");

				int nbth = countNbCol(first);
				Elements td = e.getElementsByTag("td");

				if (!tabcreated) {
					int nbCol = 0;
					if (nbth > 0) {
						nbCol = nbth;
					} else {
						nbCol = td.size();
					}
					tab = new String[tr.size()][nbCol];
					System.out.println("th" + nbth);
					System.out.println("nbcol" + nbCol);
					System.out.println("td" + td.size());
					System.out.println("thsize" + th.size());
					System.out.println(nbCol);
					tabcreated = true;
				}
				int j = 0;
				tab = headerTableau(tab, 0, 0, first, th);
				tab = bodyTableau(tab, i, j, td);

				i++;
			}

			lectureTableau(tab);
			Tableau t = new Tableau(this.delimit, this.cheminCSV, this.nomCSV, tab, cle);
			lesTableaux.add(t);
		}
	}

	/**
	 * Analyse l'entÃªte de tableau
	 * 
	 * @param tab
	 * @param i
	 * @param j
	 * @param th
	 * @return tab[][]
	 */
	public String[][] headerTableau(String[][] tab, int i, int j, Element first, Elements th) {
		Elements td = first.getElementsByTag("td");
		int compteur = 0;
		while (j < tab[i].length) {
			Element value = first.child(compteur);

			String current = value.text();
			String colspan = value.attr("colspan");

			String rowspan = value.attr("rowspan");

			if (colspan != "") {
				int x = Integer.parseInt(colspan);
				tab = Fusion(tab, i, j, x, current, false);
				// System.out.println("i " + i + " j " + j + tab[i][j] + "FUSION1");
				j = j + x;
				System.out.println(j + "jjjjj");

			} else if (rowspan != "") {
				int y = Integer.parseInt(rowspan);
				if (y > tab.length) {
					// System.out.println(y+"rowspan");
					y = tab.length;
					// System.out.println(y+"rowspan2");

				}

				tab = Fusion(tab, i, j, y, current, true);
				// System.out.println("i " + i + " j " + j + tab[i][j] + "FUSION2");
				j++;

			} else {

				tab[i][j] = value.text();
				// System.out.println("i " + i + " j " + j + tab[i][j] + "");

				j++;

			}
			compteur++;

		}

		return tab;
	}

	public int countNbCol(Element first) {
		int count = 0;
		Elements th = first.getElementsByTag("th");

		Elements td = first.getElementsByTag("td");

		for (Element a : th) {
			String cellcol = a.attr("colspan");
			String cellcolrow = a.attr("rowspan");
			String current = a.text();
			if (a.attr("colspan") != "") {
				count = count + Integer.parseInt(cellcol);

			} else {
				count++;

			}
		}
		for (Element b : td) {
			count++;
		}
		return count;
	}

	/**
	 * Analyse du contenu du tableau
	 * 
	 * @param tab
	 * @param i
	 * @param j
	 * @param td
	 * @return tab[][]
	 */
	public String[][] bodyTableau(String[][] tab, int i, int j, Elements td) {

		for (Element g : td) {

			if (tab[i][j] != null) {

				i = deplacerTableau(tab, i, j, false);
				j = deplacerTableau(tab, i, j, true);

			}
			String current = g.text();

			String cell = g.attr("rowspan");
			// System.out.println("current" + current);
			if (cell != "") {
				int y = Integer.parseInt(cell);

				if (y > tab.length) {

					y = tab.length;

				}

				tab = Fusion(tab, i, j, y, current, true);

			}
			String cellcol = g.attr("colspan");

			if (g.attr("colspan") != "") {
				int x = Integer.parseInt(cellcol);
				// System.out.println("test " + x);
				tab = Fusion(tab, i, j, x, current, false);
				// System.out.println("i : " + i + " j : " + j + " " + tab[i][j] );
			}
			if (g.attr("bgcolor") != "") {
				tab[i][j] = g.attr("bgcolor");
			}

			if (g.hasText()) {
				tab[i][j] = g.text();
			}

			if (getUrlImage(g) != "") {
				tab[i][j] = tab[i][j] + " " + getUrlImage(g);
			}

			// System.out.println("i : test " + i + " j : " + j + " " + tab[i][j] );
			// System.out.println( tab[2][1] + "Tab( 2 ]1");
			if (j <= tab[i].length) {

				j++;

			} else {
				j = 0;
				i++;

			}

		}
		return tab;
	}

	/**
	 * Deplace le curseur si la cellule est dÃ©jÃ  pleine
	 * 
	 * @param tab
	 * @param i
	 * @param j
	 * @param vertical
	 * @return
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
	 * Afficher l'intÃ©gralitÃ© du tableau
	 * 
	 * @param tab
	 */
	public void lectureTableau(String[][] tab) {
		for (int a = 0; a < tab.length; a++) {
			for (int b = 0; b < tab[a].length; b++) {
				System.out.println("i : " + a + " j : " + b + " valeur : " + tab[a][b]);
				// System.out.println(tab[a][b]);
			}
		}
	}

	/**
	 * Remplir le tableau suivant les fusions horrizontale ou verticale
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
				System.out.println("i : " + i + " j : " + j + " " + tab[i][j] + "VERTICAL");
				i++;

			}

		} else {

			for (int b = 0; b < y; b++) {
				tab[i][j] = current;
				System.out.println("i : " + i + " j : " + j + " " + tab[i][j] + "HORIZONTAL");
				j++;
			}
		}
		return tab;
	}

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

	public static void main(String[] args) {
		//Html t = new Html("https://fr.wikipedia.org/wiki/Stranger_Things", ';',
		// "chemin", "nomCSV", true, false);
		// t.recuperationPage();
		//Html b = new Html("https://fr.wikipedia.org/wiki/Jeux_mondiaux_f%C3%A9minins_de_1934", ';', "chemin", "nomCSV",
		//true, false);
		Html b = new Html("https://fr.wikipedia.org/wiki/%C3%89quipe_de_France_de_football", ';',
		 "chemin", "nomCSV", true, false);
		// Html b = new Html("https://fr.wikipedia.org/wiki/Stranger_Things",
		// ';',"chemin", "nomCSV", true, false);
		// Html b = new
		// Html("https://fr.wikipedia.org/wiki/%C3%89lections_f%C3%A9d%C3%A9rales_suisses_de_1943",
		// ';',"chemin", "nomCSV", true, false);
		b.recuperationPage();

	}

}
