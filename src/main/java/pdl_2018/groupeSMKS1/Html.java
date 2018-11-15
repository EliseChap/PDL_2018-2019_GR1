package src.main.java.pdl_2018.groupeSMKS1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sweble.wikitext.parser.nodes.WtBody;


public class Html extends Extracteur {
	
	private String url;
	private char delimit;
	private String cheminCSV;
	private String nomCSV;
	private boolean extraHTML;
	private boolean extraWiki;
	private ArrayList<Tableau> lesTableaux;
	private Map<String, Element> lesHtmltab;
	

	public Html(String url,char delimit, String cheminCSV, String nomCSV, boolean extraHTML, boolean extraWiki) {
		this.url = url;
		this.delimit = delimit;
		this.cheminCSV = cheminCSV;
		this.extraHTML = extraHTML;
		this.extraWiki = extraWiki;
		lesTableaux = new ArrayList<Tableau>();
		lesHtmltab = new HashMap<String, Element>();
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
	 * @return un booleen qui indique si l'extraction doit ïetre faite en HTML (true)
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
	
	public void recuperationPage() {
		try {
			Document doc = Jsoup.connect(url).get();
			//System.out.println(doc);
			
			Elements table = doc.select(".wikitable");
			for(int i =0; i<table.size();i++) {
				lesHtmltab.put(table.get(i).getElementsByTag("caption").text(), table.get(i)); // cle : titre du tableau
				
			}
			//System.out.println(table);
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
			Element ensemble = lesHtmltab.get(cle);
			
			
			Elements tr = ensemble.getElementsByTag("tr");
			
			for(Element e : tr) {
			String th = e.getElementsByTag("th").text();
			String td = e.getElementsByTag("td").text();
			System.out.println(td);}
			
		
			
			
			
			//System.out.println(ensemble);
			
		}
		
	}
		
	
	
	public static void main(String[] args) {
		Html t = new Html("https://fr.wikipedia.org/wiki/%C3%89quipe_de_France_masculine_de_football", ';', "chemin",
				"nomCSV", true, false);
		t.recuperationPage(); 
		
	}
	
	
}
