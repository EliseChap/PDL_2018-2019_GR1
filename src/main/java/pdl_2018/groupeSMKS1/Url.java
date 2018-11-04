package src.main.java.pdl_2018.groupeSMKS1;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import com.google.common.net.InternetDomainName;

import src.main.java.pdl_2018.groupeSMKS1.IUrl;

public class Url implements IUrl {

	private String url;
	private char delimit;
	private String cheminCsv;
	private String nomCsv;
	private boolean extraWiki;
	private boolean extraHtml;

	public Url(String url, char delimit, String nomCsv, String cheminCsv, boolean extraWiki, boolean extraHtml) {
		this.url = url;
		this.delimit = delimit;
		this.nomCsv = nomCsv;
		this.extraHtml = extraHtml;
		this.extraWiki = extraWiki;
		this.cheminCsv = cheminCsv;
	}

	/**
	 * 
	 * @param url
	 * @return true si l'URL est valide, false sinon
	 * 
	 */

	@Override
	public boolean verifURL(String url) {
		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}
		try {
			URLConnection c = u.openConnection();
			c.connect();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return l'Url traitée
	 */

	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @para Nouvelle Url
	 * 
	 */

	public void setUrl(String newUrl) {
		url = newUrl;
	}

	/**
	 * 
	 * @return le délimiteur choisit par l'utilisateur
	 */
	public char getDelimit() {
		return this.delimit;
	}

	/**
	 * 
	 * @return le nom du fichier CSV choisit par l'utilisateur
	 */
	public String getNomCSV() {
		return this.nomCsv;
	}

	/**
	 * 
	 * @return le chemin de sauvegarde du fichier choisit par l'utilisateur
	 */
	public String getCheminCSV() {
		return this.cheminCsv;
	}

	/**
	 * 
	 * @return Un booléen qui indique si l'extraction doit être faite en HTML
	 *         (true) ou non (false)
	 */
	public boolean getExtraHTML() {
		return this.extraHtml;
	}

	/**
	 * 
	 * @return Un booléen qui indique si l'extraction doit être faite en
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
	 * @param
	 */

	@Override
	public boolean isWikipediaURL(String url) {
		String domain = "wikipedia.org";
		URL u = null;
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}
		String host = u.getHost();
		InternetDomainName name = InternetDomainName.from(host).topPrivateDomain();
		if (name.toString().equals(domain)) {
			return true;
		}
		return false;
	}

	@Override
	public Extracteur ConstructeurExtracteur(char delimit, String cheminCSV, String nomCSV, boolean extraHTML,
			boolean extraWiki) {
		String url = getUrl();
		if (verifURL(url) && isWikipediaURL(url)) {
			if (extraWiki) {
				Extracteur wiki = new Wikitext(delimit, cheminCSV, nomCSV, extraHTML, extraWiki);
				return wiki;
			}
			if (extraHtml) {
				Extracteur html = new Html(delimit, cheminCSV, nomCSV, extraHTML, extraWiki);
				return html;
			}
		}
		return null;

	}

	public static void main(String[] args) {
		String u = "https://fr.wikipedia.org/wiki/France";
		boolean test = isWikiURL(u);
		System.out.println(test);
	}

}
