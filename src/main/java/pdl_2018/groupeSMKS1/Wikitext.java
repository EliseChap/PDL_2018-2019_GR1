package pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.fau.cs.osr.ptk.common.AstVisitor;

import org.sweble.wikitext.engine.ExpansionCallback;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngPage;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.nodes.*;
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
	private Map<Integer, String[][]> tab;

	private Map<String, WtBody> lesWikitab;
	private int compteur = 0;

	public Wikitext(String domain, String sousDomain, char delimit, String cheminCSV, String nomCSV, boolean extraHTML,
			boolean extraWiki) {
		this.domain = domain;
		this.sousDomain = sousDomain;
		this.delimit = delimit;
		this.nomCSV = nomCSV;
		this.cheminCSV = cheminCSV;
		this.extraHTML = extraHTML;
		this.extraWiki = extraWiki;
		lesTableaux = new ArrayList<Tableau>();
		lesWikitab = new HashMap<String, WtBody>();

	}

	// @Override
	public void recuperationPage() {
		try {
			Wiki wikisweble = new Wiki("fr.wikipedia.org");
			String contenu = wikisweble.getPageText(sousDomain);
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
			// testAffichage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void testAffichage() {

		for (Entry<String, WtBody> entry : lesWikitab.entrySet()) {
			String cle = entry.getKey();
			WtBody valeur = entry.getValue();
			System.out.println("******");
			System.out.println(cle);
			System.out.println("******");
			System.out.println(valeur);
		}
	}

	private boolean findClassWikitable(WtXmlAttributes e) {

		int compteur = 0;
		while (e.size() > compteur) {
			WtXmlAttribute attribut = (WtXmlAttribute) e.get(compteur);
			if (attribut.toString().contains("wikitable")) {
				return true;
			}
			compteur++;
		}

		return false;
	}

	private String findCaption(WtTableCaption c) {
		WtBody captionBody = c.getBody();
		int comp = 0;
		String titreCaption = "";
		while (captionBody.size() > comp) {
			if (captionBody.get(comp) instanceof WtText) {
				WtText titre = (WtText) captionBody.get(comp);
				titreCaption = titreCaption + titre.getContent();
			}
			if (captionBody.get(comp) instanceof WtTemplate) {
				WtTemplate titre = (WtTemplate) captionBody.get(comp);
				titreCaption = titreCaption + getTextWtTemplate(titre);
			}
			if (captionBody.get(comp) instanceof WtInternalLink) {
				WtInternalLink titre = (WtInternalLink) captionBody.get(comp);
				titreCaption = titreCaption + getTextWtInternalLink(titre);
			}

			comp++;
		}
		return titreCaption;
	}

	private String findHeader(WtTableHeader h) {
		WtBody headerBody = h.getBody();
		int comp = 0;
		String titreHeader = "";
		while (headerBody.size() > comp) {
			if (headerBody.get(comp) instanceof WtText) {
				WtText titre = (WtText) headerBody.get(comp);
				titreHeader = titreHeader + titre.getContent();
			}
			if (headerBody.get(comp) instanceof WtTemplate) {
				WtTemplate titre = (WtTemplate) headerBody.get(comp);
				titreHeader = titreHeader + getTextWtTemplate(titre);
			}
			comp++;
		}
		return titreHeader;
	}

	private String getTextWtTemplate(WtTemplate t) {
		Iterator<WtNode> l = t.iterator();
		String valeur = "";
		while (l.hasNext()) {
			WtNode node = l.next();
			if (node.getNodeType() == WtTemplate.NT_NAME) {
				int comp = 0;

				while (node.size() > comp) {
					if (node.get(comp) instanceof WtText) {
						WtText titre = (WtText) node.get(comp);
						valeur = titre.getContent();
					}
					comp++;
				}
			}
			getTextWtInternalLink(node);
		}
		return valeur;
	}

	private String getTextWtUnorderedList(WtNode i) {
		Iterator<WtNode> l = i.iterator();
		String valeur = "";
		while (l.hasNext()) {
			WtNode node = l.next();
			if (node.getNodeType() == WtUnorderedList.NT_LIST_ITEM) {
				int comp = 0;
				while (node.size() > comp) {
					if (node.get(comp) instanceof WtInternalLink) {
						WtInternalLink titre = (WtInternalLink) node.get(comp);
						valeur = valeur + getTextWtInternalLink(titre);
					}
					if (node.get(comp) instanceof WtText) {
						WtText titre = (WtText) node.get(comp);
						valeur = valeur + titre.getContent();
					}

					comp++;
				}

			}
			getTextWtUnorderedList(node);
		}
		return valeur;
	}

	private String getTextWtInternalLink(WtNode i) {
		Iterator<WtNode> l = i.iterator();
		String valeur = "";
		while (l.hasNext()) {
			WtNode node = l.next();
			if (node.getNodeType() == WtInternalLink.NT_LINK_TITLE) {
				int comp = 0;

				while (node.size() > comp) {
					if (node.get(comp) instanceof WtText) {
						WtText titre = (WtText) node.get(comp);
						valeur = titre.getContent();
					}
					comp++;
				}

			}
			getTextWtInternalLink(node);
		}
		return valeur;
	}

	private String findCellText(WtBody celluleBody) {
		int comp2 = 0;
		String text = "";
		while (celluleBody.size() > comp2) {
			String textPartiel = "";
			if (celluleBody.get(comp2) instanceof WtText) {
				WtText titre = (WtText) celluleBody.get(comp2);
				textPartiel = titre.getContent();
			}

			if (celluleBody.get(comp2) instanceof WtTemplate) {
				WtTemplate titre = (WtTemplate) celluleBody.get(comp2);
				textPartiel = getTextWtTemplate(titre);
			}
			if (celluleBody.get(comp2) instanceof WtInternalLink) {
				WtInternalLink titre = (WtInternalLink) celluleBody.get(comp2);
				textPartiel = getTextWtInternalLink(titre);
			}
			if (celluleBody.get(comp2) instanceof WtUnorderedList) {
				WtUnorderedList titre = (WtUnorderedList) celluleBody.get(comp2);
				textPartiel = getTextWtUnorderedList(titre);
			}

			text = text + textPartiel;
			comp2++;
		}
		return text;
	}

	private int findCol(WtTableRow r, List<String> rows) {
		WtBody row = r.getBody();
		int comp = 0;
		int nbCol = 0;
		while (row.size() > comp) {

			String text = "";
			if (row.get(comp) instanceof WtTableCell) {
				WtTableCell cellule = (WtTableCell) row.get(comp);
				WtBody celluleBody = cellule.getBody();
				rows.add(findCellText(celluleBody));
				nbCol++;
			} else if (row.get(comp) instanceof WtTableHeader) {
				WtTableHeader cellule = (WtTableHeader) row.get(comp);
				text = findHeader(cellule);
				rows.add(text);
				nbCol++;
			}
			// ne pas oublier de mettre les textes des liens aussi
			comp++;
		}
		return nbCol;
	}

	int comp = 0;

	private void parcourirNode(WtNode fils) {

		Iterator<WtNode> l = fils.iterator();
		while (l.hasNext()) {
			fils = l.next();
			if (fils.getNodeType() == WtTable.NT_TABLE) {

				WtTable table = (WtTable) fils;
				System.out.println(table);
				WtXmlAttributes e = table.getXmlAttributes();
				if (findClassWikitable(e)) {
					List<String> headerList = new ArrayList<String>();
					List<String> rowsList = new ArrayList<String>();
					String titre = "" + comp;
					int compteRows = 0;
					Iterator<WtNode> it = table.getBody().iterator();
					int nbCol = 0;
					while (it.hasNext()) {
						WtNode node = it.next();
						if (node.getNodeType() == WtTable.NT_TABLE_CAPTION) {

							WtTableCaption caption = (WtTableCaption) node;
							titre = findCaption(caption);
							System.out.println(titre);
						}
						if (node.getNodeType() == WtTable.NT_TABLE_HEADER) {
							WtTableHeader header = (WtTableHeader) node;
							headerList.add(findHeader(header));

						}
						if (node.getNodeType() == WtTable.NT_TABLE_ROW) {
							WtTableRow row = (WtTableRow) node;
							nbCol = findCol(row, rowsList);
							compteRows++;
						}
						if (node.getNodeType() == WtTable.NT_TABLE_CELL) {
							WtTableCell cell = (WtTableCell) node;
							rowsList.add(findCellText(cell.getBody()));
							compteRows++;
						}

					}
					boolean premiereLinge = false;
					if (headerList.size() != 0) {
						compteRows = compteRows + 1;
						nbCol = headerList.size();
						premiereLinge = true;
					}

					String[][] tab = new String[compteRows][nbCol];

					int colonnes = 0;
					int lig = 0;
					int compteur = 0;

					for (String item : headerList) {
						tab[lig][colonnes] = item;
						colonnes++;
					}

					for (String item : rowsList) {
						if (compteur == nbCol) {
							compteur = 0;
						}
						if (compteur == 0) {
							if (premiereLinge) {
								lig++;
							}
							premiereLinge = true;
							colonnes = 0;

						} else {
							colonnes++;
						}

						tab[lig][colonnes] = item;
						compteur++;
					}

					// lesWikitab.put(titre, tab);
					Tableau t = new Tableau();
					comp++;

				}
			}
			parcourirNode(fils);

		}

	}

	// @Override
	public void removeTableau() {
	}

	// @Override
	public String getNomTableau() {
		return "";
	}

	public String getDomain() {
		return domain;
	}

	public String getSousDomain() {
		return sousDomain;
	}

	// @Override
	public void addTableau(Tableau leTableau) {
		if (!lesTableaux.contains(leTableau)) {
			lesTableaux.add(leTableau);
		}
	}

	// @Override
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
		return this.nomCSV;
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

	public static void main(String[] args) {
		Wikitext t = new Wikitext("fr.wikipedia.org", "Équipe_de_France_de_football", ';', "chemin", " nomCSV", false,
				true);
		t.recuperationPage();
		// t.traitementMap2();
//		Set cles = t.lesWikitab.keySet();
//		Iterator<Integer> it = cles.iterator();
//		while (it.hasNext()) {
//			Integer cle = it.next();
//			WtBody ensemble = t.lesWikitab.get(cle);
//			// System.out.println(ensemble);
//			t.TraitementMap();
//		}

	}
}
