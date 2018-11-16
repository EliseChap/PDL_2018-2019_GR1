package pdl_2018.groupeSMKS1;

import java.util.Arrays;

public class Comparateur implements IComparateur {
	private String[][] wiki;
	private String[][] html;

	public Comparateur(String[][] wiki, String[][] html) {
		this.wiki = wiki;
		this.html = html;
	}

	public int comparaison() {
		
		int nombreDifference = 0;
		
		int nbLigneWiki = wiki.length;
		int nbLigneHtml = html.length;
		boolean identique = true;
		String[] tabWiki;
		String[] tabHtml;

		int i = 0;
		while (i < nbLigneWiki || i < nbLigneHtml) {
			if (i >= nbLigneWiki) {
				nombreDifference++;
				tabHtml = html[i];
				identique = false;
				System.out.println("ligne " + i + " differente html" + " : " + Arrays.asList(tabHtml).toString());
				System.out.println("ligne " + i + " differente wiki" + " : ");
			}
			if (i >= nbLigneHtml) {
				nombreDifference++;
				tabWiki = wiki[i];
				identique = false;
				System.out.println("ligne " + i + " differente wiki" + " : " + Arrays.asList(tabWiki).toString());
				System.out.println("ligne " + i + " differente html" + " : ");
			}

			if (i < nbLigneWiki && i < nbLigneHtml) {
				tabHtml = html[i];
				tabWiki = wiki[i];
				if (!Arrays.equals(tabWiki, tabHtml)) {
					nombreDifference++;
					identique = false;
					System.out.println("diffrérence entre les deux fichiers :");
					System.out.println("ligne " + i + " differente wiki" + " : " + Arrays.asList(tabWiki).toString());
					System.out.println("ligne " + i + " differente html" + " : " + Arrays.asList(tabHtml).toString());
				}
			}
			i++;
		}
		
		return nombreDifference;
	}
	
	public double ratioContenu(int erreurContenu) {
		double V1 = wiki.length;
		System.out.println("v1"+V1);
		double V2 = erreurContenu;
		System.out.println("erreur"+erreurContenu);
		double V3 = 100 - (V2/ V1 * 100);
		System.out.println(((Math.abs(V2 - V1)) / V1) * 100);
		return V3;
	}

	public boolean differentNombreLigneWikiHtml() {
		int nbreLigneWiki = wiki.length;
		int nbreLigneHtml = html.length;
		System.out.println("nombre de ligne Wiki : " + nbreLigneWiki);
		System.out.println("nombre de ligne Html : " + nbreLigneHtml);
		return nbreLigneWiki == nbreLigneHtml;
	}

	public int nombreLigneWiki() {
		return wiki.length;
	}

	public int nombreLigneHtml() {
		return html.length;
	}

	public double ratioLigne() {
		double V1 = wiki.length;
		double V2 = html.length;
		double V3 = 100 - (Math.abs(V2 - V1) / V1 * 100);
		return V3;
	}

	public int nombreColonneWiki() {
		return wiki.length;
	}

	public int nombreColonneHtml() {
		return html.length;
	}

	public double ratioColonne() {
		double V1 = wiki[0].length;
		double V2 = html[0].length;
		double V3 = 100 - (Math.abs(V2 - V1) / V1 * 100);
		return V3;
	}

	public int numberIdenticalColumn(String[][] tab) {
		int memoire = 0;

		for (String[] strArr : tab) {
			if (memoire == 0) {
				memoire = strArr.length;
			} else {
				if (strArr.length != memoire)
					return -1;
			}
		}
		return memoire;
	}

	public boolean differenceNumberlColumn() {
		int nbreWiki = numberIdenticalColumn(wiki);
		int nbreHtml = numberIdenticalColumn(html);
		if (nbreWiki == -1 || nbreHtml == -1 || nbreWiki != nbreHtml) {
			return true;
		}
		return false;
	}
	
	public double ratioGenerale() {
		System.out.println("ratiocol : "+ratioColonne());
		System.out.println("ratiolig : "+ratioLigne());
		System.out.println("ratiocon : "+ratioContenu(comparaison()));
		
		
		return ratioColonne()*0.2+ratioLigne()*0.2+ratioContenu(comparaison())*0.4;
	}

	public static void main(String[] args) {

		String tab1[][] = { { "a", "b", "c", "d" }, { "1,0", "2", "3", "4" }, { "a", "b", "c", "d" },
				{ "1,0", "2", "3", "4" } };
		String tab2[][] = { { "a", "b", "c", "d" } };

		Comparateur comparateur = new Comparateur(tab1, tab2);

		System.out.println("ratioGene : "+comparateur.ratioGenerale());
		/*System.out.println(comparateur.ratioColonne());

		// System.out.println("compare ligne " +
		// comparateur.differentNombreLigneWikiHtml());
		// System.out.println("compare colonne " +
		// comparateur.differenceNumberlColumn());*/
		// System.out.println(comparateur.comparaison());
		// System.out.println(list);
		// System.out.println(list.equals(list3));

	}
}
