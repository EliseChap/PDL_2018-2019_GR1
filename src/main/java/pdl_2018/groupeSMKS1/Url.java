package pdl_2018.groupeSMKS1;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.IOException;
import com.google.common.net.InternetDomainName;

public class Url implements IUrl {
	private URL myUrl;
	private String url;
	private char delimit;
	private String cheminCsv;
	private String nomCsv;
	private boolean extraWiki;
	private boolean extraHtml;
	private ArrayList<Extracteur> lesExtracteurs;
	private final String domain = "wikipedia.org";

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
			
		
			verifURL();
			if(isWikipediaURL()) {
				System.out.println("L'URL correspond bien à une page Wikipédia");
			isAPicture();
			isAnArticle();
			ConstructeurExtracteur();
			}
			
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @return true if URL is valid, false otherwise
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
	 * @return treated URL
	 */

	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @return myUrl : the string URLin URL form
	 */

	public URL getmyUrl() {
		return myUrl;
	}
	
	
	public ArrayList<Extracteur> getExtracteur(){
		return lesExtracteurs;
	}

	/**
	 * 
	 * @para New Url
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
	 * @return the delimiter chosen by the user
	 */
	public char getDelimit() {
		return this.delimit;
	}

	/**
	 * 
	 * @return CSV file name chosen by the user
	 */
	public String getNomCSV() {
		return this.nomCsv;
	}

	/**
	 * 
	 * @return the backup path of the file chosen by the user
	 */
	public String getCheminCSV() {
		return this.cheminCsv;
	}

	/**
	 *
	 * @return A boolean that indicates whether the extraction should be done in HTML (true)
	 * or not (false)
	 */
	public boolean getExtraHTML() {
		return this.extraHtml;
	}

	/**
	 *
	 * @return A booleen that indicates whether the extraction should be done in
	 * wikicode (true) or not (false)
	 */
	public boolean getExtraWiki() {
		return this.extraWiki;
	}

	/**
	 * @returnA string with the sub domain of the URL
	 */

	public String GetSousDomain() {
		// System.out.println("passers");
		String[] str = url.split("/wiki/");
		return str[1];
	}

	/**
	 * @return String with the domain of URL
	 */

	public String GetDomain() {
		/*String host = myUrl.getHost();
		InternetDomainName name = InternetDomainName.from(host).topPrivateDomain();
		return name.toString();*/
		String[] str = url.split("/wiki/");
		return str[0].replaceAll("https://", "");

	}

	/**
	 * @return true if the url is a wikipedia address, false otherwise
	 */

	@Override
	public boolean isWikipediaURL() {
		System.out.println("Nom de domaine: "+GetDomain());
		//System.out.println("2"+domain);
		if (GetDomain().contains(domain)) {
			return true;
		}
		System.out.println("URL : " + url + " is not a wikipedia address");
		return false;
		
	}

	/**
	 * 
	 * @return true if URL is an image
	 */

	public boolean isAPicture() {
	
		boolean picture =  url.endsWith("jpg") || url.endsWith("JPG") || url.endsWith("svg") || url.endsWith("png")
				|| url.endsWith("gif") || url.endsWith("tif") || url.endsWith("bmp");
		if(picture) {System.out.println("The URL : " + url +" can not be treated, this is an image");}
		return picture;
	}

	/**
	 * 
	 * @return true si l'url est une discussion, une conversation en observant si il
	 *         y a ':' non entourï¿½ de '-'
	 */

	public boolean isAnArticle() {
		CharSequence twopoint = ":";
	
		CharSequence twopointbis = "_:_";
		String sD = GetSousDomain();

		if (sD.contains(twopoint)) { // Si cest present, on verifie que cest si cest sous la forme "_:_" (dans un titre)
				if(!sD.contains(twopointbis))	{
					System.out.println("The URL : " + url + " treated is not a wikipedia article");
				}
			return sD.contains(twopointbis);
		}
		return false;
	}

	/**
	 * 
	 * @return an wiki extractor and/or an html extractor
	 */
	@Override
	public void ConstructeurExtracteur() {

		if (verifURL() && isWikipediaURL()) {
		
			if (extraWiki) {
				//Wikitext wiki = new Wikitext(GetDomain(), GetSousDomain(), delimit, cheminCsv, nomCsv, extraHtml,
					//	extraWiki);
				String domaine = GetDomain();
				String sousDomaine = GetSousDomain();

				 Extracteur wiki;
				try {
					wiki = new Wikitext(GetDomain(),GetSousDomain(), delimit,
					 cheminCsv, nomCsv, extraHtml, extraWiki);
					lesExtracteurs.add(wiki);
					System.out.println("wiki : "+wiki.getLesTableaux());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (extraHtml) {
				Extracteur html = new Html(url, delimit, cheminCsv, nomCsv, extraHtml, extraWiki);
				lesExtracteurs.add(html);
				//System.out.println("html : "+html.getLesTableaux());
			}
		}

	}

	


}
