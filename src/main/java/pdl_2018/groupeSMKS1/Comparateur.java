package pdl_2018.groupeSMKS1;


import java.util.Arrays;


public class Comparateur implements IComparateur {
	private String[] wiki;
	private String[] html;
	private Double ratio;

	public Comparateur(String[] wiki, String[] html) {
		this.wiki = wiki;
		this.html = html;
		analyse();
	}

	/*
	 * TRAITEMENT LIGNE
	 */
	
	/**
	 * Afficher ratio
	 * @return
	 */
	public Double getRatio() {
		return ratio;
	}

	/**
	 * Nombre de ligne Wikicode
	 * 
	 * @return nombre de ligne
	 * @date 17/11/2018
	 */
	public int nombreLigneWiki() {
		return wiki.length;
	}

	/**
	 * Nombre de ligne Html
	 * 
	 * @return nombre de ligne
	 * @date 17/11/2018
	 */
	public int nombreLigneHtml() {
		return html.length;
	}

	/**
	 * Comparaison du nombre de lignes
	 * 
	 * @return Vrai si le nombre de ligne est identique
	 * @date 17/11/2018
	 */

	public boolean differentNombreLigneWikiHtml() {
		int nbreLigneWiki = wiki.length;
		int nbreLigneHtml = html.length;
		return nbreLigneWiki == nbreLigneHtml;
	}

	/**
	 * Ratio entre les nombre de ligne
	 * 
	 * @return pourcentage
	 * @date 17/11/2018
	 */
	public double ratioLigne() {
		double V1 = wiki.length;
		double V2 = html.length;
		double V3 = 100 - (Math.abs(V2 - V1) / V1 * 100);
		return V3;
	}

	/*
	 * TRAITEMENT CONTENU
	 */

	/**
	 * Verification de la methode de comparaison partiel ou complet
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
	 * Methode qui parcourts l'ensemble des deux tableaux pour pouvoir trouver les
	 * differences
	 * 
	 * @return Nombre de difference de contenu
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
			}
			if (i >= nbLigneHtml) {
				nombreDifference++;
				/*System.out.println("ligne " + i + " differente wiki" + " : " + wiki[i]);
				System.out.println("ligne " + i + " differente html" + " : ");*/
			}

			if (i < nbLigneWiki && i < nbLigneHtml) {
				if(wiki[i]!=null) {
				if (!wiki[i].equals(html[i])) {
					nombreDifference++;
					/*System.out.println("diffrérence entre les deux fichiers :");
					System.out.println("ligne " + i + " differente wiki" + " : " + wiki[i]);
					System.out.println("ligne " + i + " differente html" + " : " + html[i]);*/
				}
			}}
			i++;
		}

		return nombreDifference;
	}

	/**
	 * Realisation du pourcentage de difference de contenu entre les deux tableaux
	 * 
	 * @param erreurContenu
	 * @return pourcentage de difference entre les deux type de tableau
	 * @date 17/11/2018
	 */
	public double ratioContenu(int erreurContenu) {
		double V1 = wiki.length;
		double V2 = erreurContenu;
		double V3 = 100 - (V2 / V1 * 100);
		return V3;
	}

	/**
	 * Ratio generale avec tous les crit�res
	 * 
	 * @return pourcentage
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
	 * private void analyseAlgoDiff() { // TODO Auto-generated method stub
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
	 * TRAITEMENT CELLULE
	 */

	/**
	 * Calculateur du nombre de cellule wiki
	 * 
	 * @return nombreCellule
	 */

	/*
	 * public int nombreCelluleWiki() { int ligne = nombreLigneWiki(); int colonne =
	 * nombreColonneWiki(); return ligne * colonne; }
	 */

	/**
	 * Calculateur du nombre de cellule wiki
	 * 
	 * @return nombreCellule
	 */

	/*
	 * public int nombreCelluleHtml() { int ligne = nombreLigneHtml(); int colonne =
	 * nombreColonneHtml(); return ligne * colonne; }
	 */
}
