package src.main.java.pdl_2018.groupeSMKS1;

public class Wikitext extends Extracteur {
	private String domain;
	private String sousDomain;
	private char delimit;
	private String cheminCSV;
	private String nomCSV;
	private boolean extraHTML;
	private boolean extraWiki;

	public Wikitext(String domain,String sousDomain,char delimit, String cheminCSV, String nomCSV, boolean extraHTML, boolean extraWiki) {
		this.domain=domain;
		this.sousDomain=sousDomain;
		this.delimit = delimit;
		this.nomCSV = nomCSV;
		this.cheminCSV = cheminCSV;
		this.extraHTML = extraHTML;
		this.extraWiki = extraWiki;
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
	public void addTableau() {
	}

	@Override
	public Tableau constructeurTableau(char delimit, String cheminCSV, String NomCSV, boolean extraHTML,
			boolean extraWiki) {
		return new Tableau();
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
	 * @return un boolean qui indique si l'extraction doit étre faire en HTML (true)
	 *         ou non (false)
	 */
	public boolean getExtraHTML() {
		return this.extraHTML;
	}

	/**
	 * 
	 * @return Un boolean qui indique si l'extraction doit étre faire en
	 *         wikicode(true) ou non (false)
	 */
	public boolean getExtraWiki() {
		return this.extraWiki;
	}
}
