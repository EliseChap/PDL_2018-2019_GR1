package src.main.java.pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.sweble.wikitext.engine.ExpansionCallback;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.nodes.WtNode;
import org.wikipedia.Wiki;

public class Wikitext extends Extracteur {
	private String domain;
	private String sousDomain;
	private char delimit;
	private String cheminCSV;
	private String nomCSV;
	private boolean extraHTML;
	private boolean extraWiki;
	private ArrayList<Tableau> lesTableaux;
	private Map<Integer,String> lesWikitab;

	public Wikitext(String domain,String sousDomain,char delimit, String cheminCSV, String nomCSV, boolean extraHTML, boolean extraWiki) {
		this.domain=domain;
		this.sousDomain=sousDomain;
		this.delimit = delimit;
		this.nomCSV = nomCSV;
		this.cheminCSV = cheminCSV;
		this.extraHTML = extraHTML;
		this.extraWiki = extraWiki;
		lesTableaux = new ArrayList<Tableau>();
		lesWikitab = new HashMap<Integer, String>();
		
	}

	@Override
	public void recuperationPage() {
		try {
		Wiki wikisweble = new Wiki("fr.wikipedia.org");
		String contenu = wikisweble.getPageText("Discussion:Deux-points");
	
		wikiconfig(contenu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void wikiconfig(String contenu) {
		try {
		WikiConfig config = DefaultConfigEnWp.generate();

		WtEngineImpl engine = new WtEngineImpl(config);
		PageTitle pageTitle = PageTitle.make(config, "title");
		PageId pageId = new PageId(pageTitle, -1);
		ExpansionCallback callback = null;
		EngProcessedPage parse = engine.parse(pageId, contenu, callback);
		parcourirNode(parse);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
		
	private static void parcourirNode(WtNode fils) {
		for (Iterator<WtNode> l = fils.iterator(); l.hasNext();) {
			fils = l.next();
			if (fils.getNodeName().toString().equals("WtTable")) {
				System.out.println(fils.getNodeName().toString());
				String wikitable = fils.toString();
				//System.out.println(wikitable.indexOf("wikitable"));
				if(wikitable.indexOf("wikitable")!=-1){
				//Insersion dans une liste de tableau
				System.out.println("TROUVERTOUVER");}}
			parcourirNode(fils);
		}
}
	
	
	@Override
	public void removeTableau() {
	}

	@Override
	public String getNomTableau() {
		return "";
	}
	

	public String getDomain() {
		return domain;
	}
	

	public String getSousDomain() {
		return sousDomain;
	}
	
	
	

	@Override
	public void addTableau(Tableau leTableau) {
		if (!lesTableaux.contains(leTableau)){
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
	 * @return le d�limiteur choisit par l'utilisateur
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
	 * @return un boolean qui indique si l'extraction doit �tre faire en HTML (true)
	 *         ou non (false)
	 */
	public boolean getExtraHTML() {
		return this.extraHTML;
	}

	/**
	 * 
	 * @return Un boolean qui indique si l'extraction doit �tre faire en
	 *         wikicode(true) ou non (false)
	 */
	public boolean getExtraWiki() {
		return this.extraWiki;
	}
}
