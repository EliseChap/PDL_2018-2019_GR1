package src.main.java.pdl_2018.groupeSMKS1;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.IOException;
import com.google.common.net.InternetDomainName;

import src.main.java.pdl_2018.groupeSMKS1.IUrl;

public class Url implements IUrl {
	private URL myUrl;
	private String url;
	private char delimit;
	private String cheminCsv;
	private String nomCsv;
	private boolean extraWiki;
	private boolean extraHtml;
	private ArrayList<Extracteur> lesExtracteurs;

	public Url(String url, char delimit, String nomCsv, String cheminCsv, boolean extraWiki, boolean extraHtml) {
		this.url = url;
		this.delimit = delimit;
		this.nomCsv = nomCsv;
		this.extraHtml = extraHtml;
		this.extraWiki = extraWiki;
		this.cheminCsv = cheminCsv;
		lesExtracteurs = new ArrayList<Extracteur>();

		try {
			myUrl = new URL(url);
		} catch (MalformedURLException e) {
			// return false;
		}
	}

	/**
	 * 
	 * @return true si l'URL est valide, false sinon
	 * 
	 */

	@Override
	public boolean verifURL() {

		try {
			URLConnection c = myUrl.openConnection();
			c.connect();
		} catch (IOException e) {
			return false;
		}
		
		
		return true;
	}
	

	/**
	 * 
	 * @return l'Url traitee
	 */

	public String getUrl() {
		return url;
	}

	
	
	/**
	 * 
	 * @return myUrl : la string url sous forme URL
	 */

	public URL getmyUrl() {
		return myUrl;
	}

	/**
	 * 
	 * @para Nouvelle Url
	 * 
	 */
	
	
	

	public void setUrl(String newUrl) {
		url = newUrl;
		try {
			myUrl = new URL(url);
		} catch (MalformedURLException e) {
			// return false;
		}
	} 

	/**
	 * 
	 * @return le delimiteur choisit par lutilisateur
	 */
	public char getDelimit() {
		return this.delimit;
	}

	/**
	 * 
	 * @return le nom du fichier CSV choisit par lutilisateur
	 */
	public String getNomCSV() {
		return this.nomCsv;
	}

	/**
	 * 
	 * @return le chemin de sauvegarde du fichier choisit par lutilisateur
	 */
	public String getCheminCSV() {
		return this.cheminCsv;
	}

	/**
	 * 
	 * @return Un booleen qui indique si lextraction doit etre faite en HTML
	 *         (true) ou non (false)
	 */
	public boolean getExtraHTML() {
		return this.extraHtml;
	}

	/**
	 * 
	 * @return Un booleen qui indique si l'extraction doit etre faite en
	 *         wikicode(true) ou non (false)
	 */
	public boolean getExtraWiki() {
		return this.extraWiki;
	}

	// TEST
	public static boolean isWikiURL(String url) {
		String domain = "wikipedia.org";
		URL u = null;

		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}
		String host = u.getHost();
		InternetDomainName name = InternetDomainName.from(host).topPrivateDomain();
		// System.out.println(name); // A supp apres
		// System.out.println(name.toString());// A supp apres
		if (name.toString().equals(domain)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return Une string avec le sous domaine de lurl
	 */

	public String GetSousDomain() {
		String path = myUrl.getPath();
		String[] str = url.split("/wiki/");
		return str[1];
	}
	
	/**
	 * @return Une string avec le domaine de lurl
	 */

	public String GetDomain() {
		String host = myUrl.getHost();
		InternetDomainName name = InternetDomainName.from(host).topPrivateDomain();
		return name.toString();
	}

	/**
	 * @return true si lurl est une adresse wikipedia, false sinon 
	 */

	@Override
	public boolean isWikipediaURL() {
		String domain = "wikipedia.org";
		
		if (GetDomain().equals(domain)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return true si l'url est une image 
	 */
	
	
	public boolean isAPicture() {
		
		
		return url.endsWith("jpg") ||url.endsWith("JPG") ||url.endsWith("svg") || url.endsWith("png") ||  url.endsWith("gif") ||  url.endsWith("tif") ||  url.endsWith("bmp");
	}

	/**
	 * 
	 * @return true si l'url est une discussion, une conversation en observant si il y a ':' non entouré de '-'
	 */
	
	
	public boolean isATwoPoint() {
		CharSequence twopoint = ":";
		CharSequence twopointbis = "_:_";
		if(url.contains(twopoint)) { // Si cest present, on verifie que cest si cest sous la forme "_:_" (dans un titre par exemple)
			return !url.contains(twopointbis);
		}
		
		return false;
	}

	
	/**
	 * 
	 * @return un Extracteur wiki et/ou un Extracteur html
	 */
	@Override
	public void ConstructeurExtracteur() {
		if (verifURL() && isWikipediaURL()) {
			if (extraWiki) {
				Extracteur wiki = new Wikitext(GetDomain(),GetSousDomain(), delimit, cheminCsv, nomCsv, extraHtml, extraWiki);
				lesExtracteurs.add(wiki);
			}
			if (extraHtml) {
				Extracteur html = new Html(url, delimit, cheminCsv, nomCsv, extraHtml, extraWiki);
				lesExtracteurs.add(html);
			}
		}
		

	}

	public static void main(String[] args) {
		String u = "https://ent.univ-rennes1.fr/f/welcome/normal/render.uP";
		boolean test = isWikiURL(u);
		System.out.println(test);
		URL url = null;
		try {
			url = new URL(u);
		} catch (MalformedURLException e) {

		}
        
		//String utest = url.getPath();
		//String[] str = utest.split("/wiki/");
		//System.out.println(utest);
		//System.out.println(str[1]);
		//boolean test2 = u.endsWith("jpg")||  u.endsWith("png") ||  u.endsWith("gif") ||  u.endsWith("tiff") ||  u.endsWith("bmp") ;
		//System.out.println(test2);
		CharSequence twopoint = "_:_";
		boolean test3 = !u.contains(twopoint);
		System.out.println(test3);
		
		
		
		
		
	}

}
