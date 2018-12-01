package pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.junit.Ignore;

@Ignore
public class SuperComparateur {

	private static LinkedHashMap<String,Double> hashMapStat;
	

	public SuperComparateur(Fichier monFichier) {
		this.hashMapStat = new LinkedHashMap();
		parcourirFichier(monFichier);
		parcourirResultat();
		
	}
	
	
	
	public static void main(String[] args) {
		
		Fichier monFichier = new Fichier("C:/Users/sullivand/Music/Desktop/mesBellesUrl.txt", ';', "", "output", true, true);
		SuperComparateur superComparateur = new SuperComparateur(monFichier);
		
	}

	private static void parcourirFichier(Fichier monFichier) {
		
		
		ArrayList<Url> c = monFichier.getLesURLs();
	 
		Iterator<Url> it = c.iterator();
		String url = new String();
		Url s = new Url(null, ';', null, null, false, false);
		while (it.hasNext()) {
		       s = it.next();
		       url = s.getUrl();
		       parcourirUrl(s, url);
		}
	}

	private static void parcourirUrl(Url url, String strUrl) {
		

		
		ArrayList<Extracteur> c = url.getExtracteur();
		 
		Iterator<Extracteur> it = c.iterator();
		
		

		while (it.hasNext()) {
			
		       Extracteur extra = it.next();
		       ArrayList<Tableau> html = extra.getLesTableaux();

		   if(it.hasNext()) {   
			   
			   extra = it.next();
		       ArrayList<Tableau> wiki = extra.getLesTableaux();
		       parcourirLesTableaux(html, wiki, strUrl);}
		}
		 
	}

	private static void parcourirLesTableaux(ArrayList<Tableau> html, ArrayList<Tableau> wiki, String strUrl) {
		
		int i = 0;
		System.out.println("margaux");
		System.out.println(html);
		System.out.println("sophie");
		System.out.println(wiki);
		
		Iterator<Tableau> itHtml = html.iterator();
		Iterator<Tableau> itWiki = wiki.iterator();
		while (itHtml.hasNext() && itWiki.hasNext()) {
			i++;
		       Tableau h = itHtml.next();
		       Tableau w = itWiki.next();
		       String[][] arrH = h.getTableau();
		       String[][] arrW = w.getTableau();
		       Comparateur comparateur = new Comparateur(arrH, arrW);
			//lectureTableau(arrH);
				hashMapStat.put(strUrl+" Tableau : "+i,comparateur.analyse());
			}
			
		}
	
	
	/**
	 * Afficher l'integralite du tableau
	 * 
	 * @param tab
	 */
	public static void lectureTableau(String[][] tab) {
		for (int a = 0; a < tab.length; a++) {
			for (int b = 0; b < tab[a].length; b++) {
				System.out.println("test i : " + a + " j : " + b + " valeur : " + tab[a][b]);
			}
		}
	}
		
		public static void parcourirResultat() {
			
			 Iterator itRes = hashMapStat.keySet().iterator();
			 System.out.println("Les resultats des comparaisons sont :");
			 while (itRes.hasNext())
			 {
				    String clef = (String)itRes.next();
				    Double valeur = hashMapStat.get(clef);
				    
				    System.out.println("clef : " + clef + " Resultats : " + valeur);
				}
		}
		
	}


