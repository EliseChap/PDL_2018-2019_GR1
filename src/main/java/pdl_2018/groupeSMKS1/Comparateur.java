package pdl_2018.groupeSMKS1;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;


public class Comparateur implements IComparateur {
	private String[] wiki;
	private String[] html;
	private Double ratio;
	private String htmlNom;
	private Map<String, String> diff;
	private String wikiNom;

	public Comparateur(String[] wiki, String[] html, String htmlNom, String wikiNom) {
		this.diff = new LinkedHashMap<String, String>();
		this.wiki = wiki;
		this.html = html;
		this.htmlNom=htmlNom;
		this.wikiNom=wikiNom;
		analyse();
	}

	public Map<String, String> getDiff() {
		return diff;
	}
	/*
	 * LINE TREATMENT
	 */
	
	/**
	 * Show ratio
	 * @return
	 */
	public Double getRatio() {
		return ratio;
	}

	/**
	 * number of Wikicode lines
	 * 
	 * @return number of lines
	 * @date 17/11/2018
	 */
	public int nombreLigneWiki() {
		return wiki.length;
	}

	/**
	 * Number of HTML lines
	 * 
	 * @return Number of lines
	 * @date 17/11/2018
	 */
	public int nombreLigneHtml() {
		return html.length;
	}

	/**
	 * Comparison of the number of lines
	 * 
	 * @return True if the number of lines is the same
	 * @date 17/11/2018
	 */

	public boolean differentNombreLigneWikiHtml() {
		int nbreLigneWiki = wiki.length;
		int nbreLigneHtml = html.length;
		return nbreLigneWiki == nbreLigneHtml;
	}

	/**
	 * Ratio between the number of lines
	 * 
	 * @return percentage
	 * @date 17/11/2018
	 */
	public double ratioLigne() {
		double V1 = wiki.length;
		double V2 = html.length;
		double V3 = 100 - (Math.abs(V2 - V1) / V1 * 100);
		return V3;
	}

	/*
	 * CONTENT TREATMENT
	 */

	/**
	 * Verification of partial or complete comparison method
	 * @return
	 */
	public double analyse() {
		double ratioC;
			ratioC = ratioContenu(analyseComplet());
			if(ratioC<0 || ratioC>100) {
				this.ratio=(double) 0;
			}else {
		this.ratio=ratioC;}
		return ratioC;
	}

	/**
	 * Method that path all the two tables to be able to find differences
	 *
	 * 
	 * @return Number of content differences
	 * @date 17/11/2018
	 */
	
	public int analyseComplet() {

		int nombreDifference = 0;

		int nbLigneWiki = wiki.length;
		int nbLigneHtml = html.length;

		int i = 0;
		while (i < nbLigneWiki || i < nbLigneHtml) {
			if (i >= nbLigneWiki) {
				nombreDifference++;
				/*System.out.println("ligne " + i + " differente html" + " : " + html[i]);
				System.out.println("ligne " + i + " differente wiki" + " : ");*/
				this.diff.put(wikiNom+";" +htmlNom+";"+ i + ";wiki" + "�" ,"");
				this.diff.put(wikiNom+";" +htmlNom+";"+ i + ";html" + "�" ,html[i]);
		
			}
			if (i >= nbLigneHtml) {
				nombreDifference++;
				/*System.out.println("ligne " + i + " differente wiki" + " : " + wiki[i]);
				System.out.println("ligne " + i + " differente html" + " : ");*/
				this.diff.put(wikiNom+";" +htmlNom+";"+ i + ";wiki" + "�" ,wiki[i]);
				this.diff.put(wikiNom+";" +htmlNom+";"+ i + ";html" + "�" ,"");
			}

			if (i < nbLigneWiki && i < nbLigneHtml) {
				String strhtml;
				if(wiki[i]!=null) {
					String strWiki = wiki[i].toLowerCase().replaceAll(" ", "").replaceAll("\\[[0-9]*\\]", "").replaceAll("unk", "unknown").replace("dunno", "?"); // majscule, [indice] et " "
					if(html[i]!=null) {
					strhtml = html[i].toLowerCase().replaceAll(" ", "").replaceAll("\\[[0-9]*\\]", "");}
					else {
						strhtml="";
					}
				if (!strWiki.equals(strhtml)) {
					nombreDifference++;
					this.diff.put(wikiNom+";" +htmlNom+";"+ i + ";wiki" + "�" ,wiki[i]);
					this.diff.put(wikiNom+";" +htmlNom+";"+ i + ";html" + "�" ,html[i]);
				/*	System.out.println("difference entre les deux fichiers :");
					System.out.println("ligne " + i + " differente wiki" + " : " + wiki[i]);
					System.out.println("ligne " + i + " differente html" + " : " + html[i]);*/
				}
			}}
			i++;
		}

		return nombreDifference;
	}

	/**
	 * Realization of the content difference percentage between the two tables
	 * 
	 * @param erreurContenu
	 * @return Difference percentage between the two types of tables
	 * @date 17/11/2018
	 */
	public double ratioContenu(int erreurContenu) {
		double V1 = wiki.length;
		double V2 = erreurContenu;
		double V3 = 100 - (V2 / V1 * 100);
		return V3;
	}
	
	
	   public static void main(String[] args)
	    {
	        String s = ";WinAce;yes;yes;yes;yes;yes;yes;yes;no;yes;unk";
	        s = s.replaceAll("unk", "Unknown");
	        s=s.toLowerCase();
	        System.out.println(s);
	    }

	/**
	 * General ratio with all criteria
	 * 
	 * @return percentage
	 * @date 17/11/2018
	 */

	/*
	 * public double ratioGenerale() { System.out.println("ratiocol : " +
	 * ratioColonne()); System.out.println("ratiolig : " + ratioLigne());
	 * System.out.println("ratiocon : " + ratioContenu(analyseComplet()));
	 * 
	 * return ratioColonne() * 0.2 + ratioLigne() * 0.2 +
	 * ratioContenu(analyseComplet()) * 0.4; }
	 * 
	 * private void analyseAlgoDiff() {
	 * 
	 * }
	 * 
	 * 
	 * /** Return le nombre de donn�e a analys�e
	 * 
	 * @param taillePopulation
	 * 
	 * @return tailleEchantillon
	 */
	/*
	 * public int tailleEchantillon(int taille) { double z = Math.pow(1.96, 2); //
	 * 95% double marge = Math.pow(0.05, 2); double p = 0.5; double numerateur = (z
	 * * p * (1 - p)) / marge; double denominateur = 1 + ((z * p * (1 - p)) / (marge
	 * * taille)); return (int) (numerateur / denominateur); }
	 * 
	 * /** Analyse partielle du contenu cellule au hasard
	 * 
	 * @param i
	 */
	/*
	 * public int analysePartielle(int echantillon) { int nombreDifference = 0; for
	 * (int i = 0; i < echantillon; i++) { int lig = aleatoire(html.length-1); int
	 * col = aleatoire(html[0].length-1);
	 * 
	 * String strHtml = html[lig][col]; String strWiki = wiki[lig][col]; if
	 * (!strWiki.equals(strHtml)) { nombreDifference++; System.out.println("ligne :"
	 * + lig +" colonne :" +col+ " differente wiki" + " : " + strWiki);
	 * System.out.println("ligne :" + lig +" colonne :" +col+ " differente wiki" +
	 * " : " + strHtml); } } return nombreDifference; }
	 * 
	 * /*public int aleatoire(int echantillon) { return (int) (Math.random() *
	 * echantillon); }
	 */

	/*
	 * CELL TREATMENT
	 */

	/**
	 * Calculator of the number of wiki cells
	 * 
	 * @return cellNumbers
	 */

	/*
	 * public int nombreCelluleWiki() { int ligne = nombreLigneWiki(); int colonne =
	 * nombreColonneWiki(); return ligne * colonne; }
	 */

	/**
	 * Calculator of the number of wiki cells
	 * 
	 * @return cellNumber
	 */

	/*
	 * public int nombreCelluleHtml() { int ligne = nombreLigneHtml(); int colonne =
	 * nombreColonneHtml(); return ligne * colonne; }
	 */
}
