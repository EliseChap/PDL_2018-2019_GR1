
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
	
	/**
	 * 
	 * @return la liste LesTableaux
	 */
	@Override
	public ArrayList<Tableau> getLesTableaux() {
		return lesTableaux;
	}

	@Override
	public void removeTableau() {

	}
	/**
	 * 
	 * @return le nom du Tableau
	 */
	@Override
	public String getNomTableau() {
		return "";
	}

	@Override
	/**
	 * Ajoute le Tableau a la liste lesTableaux si il n'est pas déjà présents
	 * @param leTableau
	 */
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
	 * @return un booleen qui indique si l'extraction doit etre faite en HTML (true)
	 *         ou non (false)
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
	
	/**
	 * Récupère la page et chaque tableau de cette page est mis dans une map avec pour cle son nom
	 * ou par defaut table + son numero de table 
	 * 
	 */

	public void recuperationPage() {
		try {
			Document doc = Jsoup.connect(url).get();

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
			TraitementMap();

		} catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}

	/**
	 * Pour chaque tableau de la Map, on fait les traitements nécessaires
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
				 int taille =getMax(tr);
				 System.out.println(taille + "taille");
				
			Element first = tr.first();
			//Element second = tr.get(1);

			Elements th = first.getElementsByTag("th");
			//Elements th2 = second.getElementsByTag("th");

			while (!first.hasText()) {

				tr.remove(0);
				first = tr.first();
			}
			//int nbth = countNbCol(first);
			//if (th2.size() > th.size()) {
				//nbth = countNbCol(second);
			//}

			String[][] tab = null;
			int i = 0;

			for (Element e : tr) {

				Elements thdetr = e.getElementsByTag("th");

				Elements td = e.getElementsByTag("td");

				if (!tabcreated) {
					/*int nbCol = 0;
					if (nbth > 0) {
						nbCol = nbth;
					} else {
						nbCol = td.size();
					}*/
					tab = new String[tr.size()][taille];
					tabcreated = true;
				}
				int j = 0;
				tab = bodyTableau(tab, i, 0, thdetr);

				tab = bodyTableau(tab, i, j, td);

				i++;
			}

			tab = TraitementColonnesVides(tab);
			lectureTableau(tab);
			Tableau t = new Tableau(this.delimit, this.cheminCSV, this.nomCSV, tab, cle, false);
			lesTableaux.add(t);
		}
	}

	
	
	//a degager
	/**
	 * Compte le nombre de colonnes du tableau
	 * 
	 * @param first
	 * @return int, le nombre de colonnes du tableau
	 */

	public int countNbCol(Element first) {
		int count = 0;
		Elements th = first.getElementsByTag("th");

		Elements td = first.getElementsByTag("td");

		for (Element a : th) {
			String cellcol = a.attr("colspan");
			String current = a.text();
			if (a.attr("colspan") != "") {
				count = count + Integer.parseInt(cellcol);

			} else {
				count++;

			}
		}
		for (Element b : td) {
			String cellcol = b.attr("colspan");
			String current = b.text();

			if (b.attr("colspan") != "") {
				count = count + Integer.parseInt(cellcol);

			} else {
				count++;

			}
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

		if (td.size() > tab[0].length) { // cas de cellules hors du tableau

			td.remove(td.size() - 1);

		}

		for (Element g : td) {
			// System.out.println(g);
			if (tab[i][j] != null) {

				i = deplacerTableau(tab, i, j, false);

				j = deplacerTableau(tab, i, j, true);

			}
			String current = g.text();

			String cell = g.attr("rowspan");
			// System.out.println("current" + current);
			if (cell != "") {
				int y = Integer.parseInt(cell);

				if (y >= tab.length) {

					y = tab.length - 1;

				}

				tab = Fusion(tab, i, j, y, current, true);

			}
			String cellcol = g.attr("colspan");

			if (g.attr("colspan") != "") {
				int x = Integer.parseInt(cellcol);
				if (x >= tab[0].length) {

					x = tab[0].length - 1;

				}

				if (x + j >= tab[0].length) {
					x = tab[0].length - 1 - j;
				}
				tab = Fusion(tab, i, j, x, current, false);
				System.out.println("i : test " + i + " j : " + j + " " + tab[i][j]);
			}
			if (g.attr("bgcolor") != "") {
				tab[i][j] = g.attr("bgcolor");
			}

			tab[i][j] = g.text() + "";

			if (getUrlImage(g) != "") {
				tab[i][j] = tab[i][j] + " " + getUrlImage(g);
			}

			System.out.println("i : test " + i + " j : " + j + " " + tab[i][j]);

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
	 * Traitement du tableau si il existe des colonnes vides dans le tableau
	 * 
	 * @param tab
	 * @return String[][] tab, sans les colonnes vides
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
	 * Suppression des colonnes vides
	 * 
	 * @param tab
	 * @param j
	 * @return String[][] tab, sans les colonnes vides
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
	 * Deplace le curseur si la cellule est deja  pleine
	 * 
	 * @param tab
	 * @param i
	 * @param j
	 * @param vertical
	 * @return int, i si vertical j sinon
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
	 * Afficher l'integralite du tableau
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
	 * Remplir le tableau suivant les fusions horizontales ou verticales
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
				if (i < tab.length - 1) {
					i++;
				}
			}

		} else {

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
	 * Recuperer l'url dune image contenu dans une cellule
	 * 
	 * @param Element g
	 * @return String de l'url
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
	public int getMax(Elements tr) {
		int max = 0;
		for (Element e : tr) {
			Elements th = e.getElementsByTag("th");
			Elements td = e.getElementsByTag("td");
				if(max<th.size()+td.size()) {
					max = th.size()+td.size();
				}

		}
		return max;
	}

	public static void main(String[] args) {
		Html b = new Html("https://en.wikipedia.org/wiki/Comparison_of_cognitive_architectures", ';', "", "nomCSV.csv",
				true, false);

		b.recuperationPage();

	}

}
