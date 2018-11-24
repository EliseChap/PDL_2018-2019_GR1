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

public class Wikitext extends AstVisitor<WtNode> {
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

			// EngProcessedPage cp = engine.postprocess(pageId, contenu, null);

			EngProcessedPage parse = engine.parse(pageId, contenu, callback);

			// this.go(cp);

			// System.out.println(parse);
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

		while (captionBody.size() > comp) {
			if (captionBody.get(comp) instanceof WtText) {
				WtText titre = (WtText) captionBody.get(comp);
				return titre.getContent();
			}
			// ne pas oublier de mettre les textes des liens aussi
			comp++;
		}
		return "";
	}

	private String findHeader(WtTableHeader h) {
		WtBody headerBody = h.getBody();
		int comp = 0;

		while (headerBody.size() > comp) {
			if (headerBody.get(comp) instanceof WtText) {
				WtText titre = (WtText) headerBody.get(comp);
				return titre.getContent();
			}
			// ne pas oublier de mettre les textes des liens aussi
			comp++;
		}
		return "";
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

	private String getTextWtInternalLink(WtNode i) {
		Iterator<WtNode> l = i.iterator();
		String valeur = "";
		while (l.hasNext()) {
			WtNode node = l.next();
			if (node.getNodeType() == WtInternalLink.NT_PAGE_NAME) {
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

	private void findCol(WtTableRow r, List<String> rows) {
		WtBody row = r.getBody();
		int comp = 0;

		while (row.size() > comp) {
			if (row.get(comp) instanceof WtTableCell) {
				WtTableCell cellule = (WtTableCell) row.get(comp);
				WtBody celluleBody = cellule.getBody();
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
					text = text + textPartiel;
					comp2++;
				}
				rows.add(text);
			}
			// ne pas oublier de mettre les textes des liens aussi
			comp++;
		}
	}

	int comp = 0;

	private void parcourirNode(WtNode fils) {

		Iterator<WtNode> l = fils.iterator();
		while (l.hasNext())

		// for (Iterator<WtNode> l = fils.iterator(); l.hasNext();)
		{
			fils = l.next();
			String titre = "" + comp;
			List<String> headerList = new ArrayList<String>();
			List<String> rowsList = new ArrayList<String>();
			if (fils.getNodeType() == WtTable.NT_TABLE) {

				WtTable table = (WtTable) fils;
				WtXmlAttributes e = table.getXmlAttributes();
				if (findClassWikitable(e)) {

					Iterator<WtNode> it = table.getBody().iterator();

					while (it.hasNext()) {
						WtNode node = it.next();
						if (node.getNodeType() == WtTable.NT_TABLE_CAPTION) {

							WtTableCaption caption = (WtTableCaption) node;
							titre = findCaption(caption);
						}
						if (node.getNodeType() == WtTable.NT_TABLE_HEADER) {
							// remplir list
							WtTableHeader header = (WtTableHeader) node;
							headerList.add(findHeader(header));
						}
						if (node.getNodeType() == WtTable.NT_TABLE_ROW) {
							// remplir list
							WtTableRow row = (WtTableRow) node;
							findCol(row, rowsList);
						}
						// creer tableau avec liste
					}

					String[][] tab = new String[rowsList.size()][headerList.size()];

					System.out.println("*******");

					headerList.forEach(item -> System.out.println(item));
					rowsList.forEach(item -> System.out.println(item));

					System.out.println("*******");
					// System.out.println(table.getBody());
					lesWikitab.put(titre, table.getBody());
					comp++;
					// lesWikitab.put(titre, table.getBody());
				}
			}
			parcourirNode(fils);

		}

	}

	public void rechercheColRow(WtNode fils, String[][] tab) {

		for (Iterator<WtNode> l = fils.iterator(); l.hasNext();) {
			WtNode node = l.next();
			if (node.getNodeType() == WtTableRow.NT_TABLE_ROW) {
				// System.out.println("R");

			}
			if (node.getNodeType() == WtTableRow.NT_TABLE_HEADER) {
				// System.out.println("H");
			}

			if (node.getNodeType() == WtTableRow.NT_TABLE_CELL) {
				// System.out.println("C");
			}
			if (node.getNodeType() == WtText.NT_TEXT) {

				WtText text = (WtText) node;

				// System.out.println(text.getContent());
			}

			rechercheColRow(node, tab);
		}
	}

	public void traitementMap2() {

		Set cles = lesWikitab.keySet();
		Iterator<Integer> it = cles.iterator();
		while (it.hasNext()) {
			Integer cle = it.next();
			WtBody ensemble = lesWikitab.get(cle);
			// System.out.println(ensemble);
			String[][] tab = new String[100][100];
			rechercheColRow(ensemble, tab);
		}
		// System.out.println(lesWikitab.size());
	}

	/**
	 * 
	 * @return
	 */
	public void TraitementMap() {

		Set cles = lesWikitab.keySet();
		Iterator<Integer> it = cles.iterator();
		while (it.hasNext()) {
			Integer cle = it.next();
			WtBody ensemble = lesWikitab.get(cle);
			int counter = 0;

			while (ensemble.size() > counter) {
				if (ensemble.get(counter).getNodeType() == WtTableRow.NT_TABLE_ROW) {

					WtTableRow row = (WtTableRow) ensemble.get(counter);
					int counterrow = 0;
					while (row.size() > counterrow) {
						System.out.println(row.get(counterrow));
						System.out.println(row.get(counterrow));
						// System.out.println(WtTableCell.NT_TABLE_CELL);

						if (row.get(counterrow).getNodeType() == WtTableCell.NT_TABLE_CELL) {
							WtTableCell cell = (WtTableCell) row.get(counterrow);
							// System.out.println(cell.toString());
						}
						counterrow++;
					}

				}
				// System.out.println(ensemble.get(counter).toString());
				counter++;

			}

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
		Wikitext t = new Wikitext("fr.wikipedia.org", "Josef_Newgarden", ';', "chemin", " nomCSV", false, true);
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
