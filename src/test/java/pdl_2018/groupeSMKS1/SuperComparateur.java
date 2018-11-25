package pdl_2018.groupeSMKS1;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.Disabled;

import pdl_2018.groupeSMKS1.Fichier;

@Disabled
public class SuperComparateur {

	private static LinkedHashMap<String,Double> hashMapStat;
	

	public SuperComparateur(Fichier monFichier) {
		this.hashMapStat = new LinkedHashMap();
		parcourirFichier(monFichier);
		parcourirResultat();
		
	}
	
	
	
	public static void main(String[] args) {
		
		Fichier monFichier = new Fichier("C:/Users/sullivand/Music/Desktop/mesBellesUrl.txt", ';', "moncsv.csv", "C:/Users/sullivand/Music/Desktop/", false, true);
		
		SuperComparateur superComparateur = new SuperComparateur(monFichier);
		
		
	}

	private static void parcourirFichier(Fichier monFichier) {
		
		
		ArrayList<Url> c = monFichier.getLesURLs();
	 
		Iterator<Url> it = c.iterator();
		 
		while (it.hasNext()) {
		       Url s = it.next();
		       URL url = s.getmyUrl();
		       parcourirUrl(s, url);
		}
	}

	private static void parcourirUrl(Url s, URL url) {
		
	
		ArrayList<Extracteur> c = s.getExtracteur();
		 
		Iterator<Extracteur> it = c.iterator();
		 System.out.println(it);
		while (it.hasNext()) {
			
		       Extracteur e = it.next();
		       ArrayList<Tableau> html = e.getLesTableaux();
		       ArrayList<Tableau> wiki = e.getLesTableaux(); //voir sophie
		       parcourirLesTableaux(html, wiki, url);
		}
		
	}

	private static void parcourirLesTableaux(ArrayList<Tableau> html, ArrayList<Tableau> wiki, URL url) {
		
		int i = 0;
		Iterator<Tableau> itHtml = html.iterator();
		Iterator<Tableau> itWiki = wiki.iterator();
		while (itHtml.hasNext() && itWiki.hasNext()) {
			i++;
		       Tableau h = itHtml.next();
		       Tableau w = itWiki.next();
		       String[][] arrH = h.getTableau();
		       String[][] arrW = w.getTableau();
		       Comparateur comparateur = new Comparateur(arrH, arrW);
			
				hashMapStat.put(url+"/"+i,comparateur.analyse());
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


