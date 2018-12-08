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
	private final String domain = "wikipedia.org"; // La mettre tout en haut en attribut final

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
				System.out.println("teste");
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
	
	
	public ArrayList<Extracteur> getExtracteur(){
		return lesExtracteurs;
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
	 * @return Un booleen qui indique si lextraction doit etre faite en HTML (true)
	 *         ou non (false)
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

	/**
	 * @return Une string avec le sous domaine de lurl
	 */

	public String GetSousDomain() {
		System.out.println("passers");
		String[] str = url.split("/wiki/");
		return str[1];
	}

	/**
	 * @return Une string avec le domaine de lurl
	 */

	public String GetDomain() {
		/*String host = myUrl.getHost();
		InternetDomainName name = InternetDomainName.from(host).topPrivateDomain();
		return name.toString();*/
		String[] str = url.split("/wiki/");
		return str[0].replaceAll("https://", "");

	}

	/**
	 * @return true si lurl est une adresse wikipedia, false sinon
	 */

	@Override
	public boolean isWikipediaURL() {
		System.out.println("1"+GetDomain());
		System.out.println("2"+domain);
		if (GetDomain().contains(domain)) {
			return true;
		}
		System.out.println("L'url : " + url + " rentree n'est pas une adresse wikipedia");
		return false;
		
	}

	/**
	 * 
	 * @return true si l'url est une image
	 */

	public boolean isAPicture() {
	
		boolean picture =  url.endsWith("jpg") || url.endsWith("JPG") || url.endsWith("svg") || url.endsWith("png")
				|| url.endsWith("gif") || url.endsWith("tif") || url.endsWith("bmp");
		if(picture) {System.out.println("L'url : " + url +" ne peut etre traitee, il s'agit d'une image");}
		return picture;
	}

	/**
	 * 
	 * @return true si l'url est une discussion, une conversation en observant si il
	 *         y a ':' non entour� de '-'
	 */

	public boolean isAnArticle() {
		CharSequence twopoint = ":";
	
		CharSequence twopointbis = "_:_";
		String sD = GetSousDomain();

		if (sD.contains(twopoint)) { // Si cest present, on verifie que cest si cest sous la forme "_:_" (dans un titre)
				if(!sD.contains(twopointbis))	{
					System.out.println("L'url : " + url + " traitee n'est pas un article wikipedia");
				}
			return sD.contains(twopointbis);
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
	//			System.out.println("html : "+html.getLesTableaux());
			}
		}

	}

	
	public static void main(String[] args) {
		String u = "https://ent.univ-rennes1.fr/f/welcome/normal/render.uP";
		//String utest = url.getPath(); //String[] str = utest.split("/wiki/");
		//System.out.println(utest); //System.out.println(str[1]);
	
		boolean testEndWith = u.endsWith("jpg") || u.endsWith("png") || u.endsWith("gif") || u.endsWith("tiff") || u.endsWith("bmp");
		System.out.println(testEndWith);
		CharSequence twoPoint = "_:_";
		boolean testTwoPoint = !u.contains(twoPoint);
		System.out.println(testTwoPoint);
		
		Url url = new Url("https://en.wikipedia.org/wiki/UEFA_Euro_2016", ';', "",
				"nomCSV.csv", true, false);	

		System.out.println(url.getExtracteur());
		}

}
