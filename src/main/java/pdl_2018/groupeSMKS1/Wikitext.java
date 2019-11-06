package pdl_2018.groupeSMKS1;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import org.sweble.wikitext.engine.*;
import org.sweble.wikitext.engine.config.WikiConfig;
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
	private String colspan = "colspan";
	private String rowspan = "rowspan";
	private Map<String, WtBody> lesWikitab;
	private Wiki wikisweble;
	private int comp = 0;
	private List<String[][]> rowspanList = new ArrayList<String[][]>();
	private List<String[][]> rowspanHeaderList = new ArrayList<String[][]>();

	public Wikitext(String domain, String sousDomain, char delimit, String cheminCSV, String nomCSV, boolean extraHTML,
			boolean extraWiki) throws Exception {
		this.domain = domain;
		this.sousDomain = sousDomain;
		this.delimit = delimit;
		this.nomCSV = nomCSV;
		this.cheminCSV = cheminCSV;
		this.extraHTML = extraHTML;
		this.extraWiki = extraWiki;
		lesTableaux = new ArrayList<Tableau>();
		lesWikitab = new HashMap<String, WtBody>();
		recuperationPage();

	}

	public void recuperationPage() {
		try {
			wikisweble = new Wiki(domain);
			String[] tableau = { sousDomain };
			boolean[] tab = wikisweble.exists(tableau);
			if (tab[0]) {
				String contenu = wikisweble.getPageText(sousDomain);
				wikiconfig(contenu);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void wikiconfig(String contenu) throws Exception {

		WikiConfig config = DefaultConfigEnWp.generate();

		WtEngineImpl engine = new WtEngineImpl(config);
		PageTitle pageTitle = PageTitle.make(config, "title");
		PageId pageId = new PageId(pageTitle, -1);
		ExpansionCallback callback = null;

		EngProcessedPage parse = engine.parse(pageId, contenu, callback);

		parcourirNode(parse);
	

	}


/**
 * Find if the node contains "wikiclass"
 * @param e an WtXmlAttributes of a node
 * @return true if the Xmlattribut contains "wikiclass" else false
 */
	private boolean findClassWikitable(WtXmlAttributes e) {

		int compteur = 0;
		while (e.size() > compteur) {
			if (e.get(compteur) instanceof WtXmlAttribute) {
				WtXmlAttribute attribut = (WtXmlAttribute) e.get(compteur);
				if (attribut.toString().contains("wikitable")) {
					return true;
				}
			}
			compteur++;
		}

		return false;
	}

	/**
	 * return the text of the node in the param
	 * @param t a node of the tree
	 * @return the text of the node
	 */
	private String getTextWtValue(WtValue t) {
		Iterator<WtNode> l = t.iterator();
		String valeur = "1";
		while (l.hasNext()) {
			WtNode node = l.next();
			if (node instanceof WtText) {
				WtText titre = (WtText) node;
				valeur = titre.getContent();
			}
		}
		return valeur;
	}
/**
 * 
 * @param e
 * @param textFind
 * @return
 */
	private int findColspanRowSpan(WtXmlAttributes e, String textFind) {
		int nbSpan = 1;
		int compteur = 0;
		while (e.size() > compteur) {
			if (e.get(compteur) instanceof WtXmlAttribute) {
				WtXmlAttribute attribut = (WtXmlAttribute) e.get(compteur);
				if (attribut.toString().contains(textFind)) {
					WtValue valeur = attribut.getValue();
					return Integer.parseInt(getTextWtValue(valeur));
				}
			}
			compteur++;
		}

		return nbSpan;
	}
/**
 * Find the table caption text and return it
 * @param c a table caption node
 * @return the table caption
 */
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
	
/**
 * Find the table header text and return it
 * @param h A tableHeader node
 * @return the table header text
 */
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

			if (headerBody.get(comp) instanceof WtInternalLink) {
				WtInternalLink titre = (WtInternalLink) headerBody.get(comp);
				titreHeader = titreHeader + getTextWtInternalLink(titre);
			}
			if (headerBody.get(comp) instanceof WtUnorderedList) {
				WtUnorderedList titre = (WtUnorderedList) headerBody.get(comp);
				titreHeader = getTextWtUnorderedList(titre);
			}
			comp++;
		}
		return titreHeader;
	}
/**
 * Find the template node text and return it
 * @param t a template node
 * @return the template node text
 */
	private String getTextWtTemplate(WtTemplate t) {
		Iterator<WtNode> l = t.iterator();
		String valeur = "";
		String valeur2 = "";
		while (l.hasNext()) {
			WtNode node = l.next();
			if (node.getNodeType() == WtTemplate.NT_TEMPLATE_ARGUMENTS) {
				int comp = 0;

				while (node.size() > comp) {
					int comp2 = 0;
					WtNode node2 = node.get(comp);
					while (node2.size() > comp2) {
						if (node2.get(comp2) instanceof WtValue) {
							WtValue titre = (WtValue) node2.get(comp2);
							valeur = getTextWtValue(titre);
						}
						comp2++;
					}
					comp++;
				}
			}
			if (node.getNodeType() == WtTemplate.NT_NAME) {
				int comp = 0;
				while (node.size() > comp) {
					if (node.get(comp) instanceof WtText) {
						WtText titre = (WtText) node.get(comp);
						valeur2 = titre.getContent();
					}
					comp++;
				}
			}
			getTextWtInternalLink(node);
		}

		if (valeur != "") {
			return valeur;
		} else {
			return valeur2;
		}
	}

	/**
	 * Find the Unordered list and return the text
	 * @param i a node
	 * @return return the unorderedList text
	 */
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

	/**
	 * return the text of the internal link node
	 * @param i A node of the tree
	 * @return Internal link text
	 */
	private String getTextWtInternalLink(WtNode i) {
		Iterator<WtNode> l = i.iterator();
		String valeurTitle = "";
		String valeur = "";
		while (l.hasNext()) {
			WtNode node = l.next();
			if (node.getNodeType() == WtInternalLink.NT_LINK_TITLE) {
				int comp = 0;

				while (node.size() > comp) {
					if (node.get(comp) instanceof WtText) {
						WtText titre = (WtText) node.get(comp);
						valeurTitle = titre.getContent();
					}
					comp++;
				}

			}
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

		if (valeurTitle != "") {
			return valeurTitle;
		} else {
			return valeur;
		}

	}
/**
 * Find the cell text in the body and return if
 * @param celluleBody a Body node
 * @return text of the cell
 */
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
/**
 * Add the table cells text in the rows list
 * @param r A TableRow node
 * @param rows The list of any rows
 * @param nbRow 
 * @return the number of columns in the tableRow
 */
	private int findCol(WtTableRow r, List<String> rows, int nbRow) {
		WtBody row = r.getBody();
		int comp = 0;
		int nbCol = 0;
		while (row.size() > comp) {

			if (row.get(comp) instanceof WtTableCell) {
				WtTableCell cellule = (WtTableCell) row.get(comp);
				WtBody celluleBody = cellule.getBody();

				int i = findColspanRowSpan(cellule.getXmlAttributes(), colspan);
				int k = findColspanRowSpan(cellule.getXmlAttributes(), rowspan);
				String textCell = findCellText(celluleBody);
				addRowSpan(nbRow, nbCol, k, textCell, false);
				for (int j = 1; j <= i; j++) {
					rows.add(textCell);
					nbCol++;

				}
			} else if (row.get(comp) instanceof WtTableHeader) {
				WtTableHeader cellule = (WtTableHeader) row.get(comp);
				int i = findColspanRowSpan(cellule.getXmlAttributes(), colspan);
				int k = findColspanRowSpan(cellule.getXmlAttributes(), rowspan);
				String text = findHeader(cellule);
				addRowSpan(nbRow, nbCol, k, text, false);
				for (int j = 1; j <= i; j++) {
					rows.add(text);
					nbCol++;

				}
			}
			comp++;
		}
		return nbCol;
	}



	/**
	 * Add the rowspan text in the lists
	 * @param compteRows 
	 * @param nbCol Columns of the row
	 * @param k number of occurence 
	 * @param text text of the rowSpan
	 * @param header true is the rowspan is a header, else false
	 */
	private void addRowSpan(int compteRows, int nbCol, int k, String text, boolean header) {
		if (k > 1) {
			String[][] tabRow = new String[1][4];
			tabRow[0][0] = String.valueOf(compteRows);
			tabRow[0][1] = String.valueOf(nbCol);
			tabRow[0][2] = String.valueOf(k - 1);
			tabRow[0][3] = text;
			if (header) {
				rowspanHeaderList.add(tabRow);
			} else {
				rowspanList.add(tabRow);
			}

		}
	}

	/**
	 *landmark the line break
	 * @param b a body node
	 * @return return true if this is it /n
	 */
	private boolean sautDeLigne(WtBody b) {
		int comp = 0;

		while (b.size() > comp) {
			if (b.get(comp) instanceof WtText) {
				WtText titre = (WtText) b.get(comp);
				return titre.getContent() == "/n";
			}
			comp++;
		}
		return false;
	}
/**
 * browse all node in the table and create a tableau object
 * @param fils a node
 */
	private void parcourirNode(WtNode fils) {

		Iterator<WtNode> l = fils.iterator();
		while (l.hasNext()) {
			fils = l.next();
			if (fils.getNodeType() == WtTable.NT_TABLE) {
				rowspanList.clear();
				rowspanHeaderList.clear();
				WtTable table = (WtTable) fils;
				WtXmlAttributes e = table.getXmlAttributes();
				if (findClassWikitable(e)) {
					List<String> headerList = new ArrayList<String>();
					List<String> rowsList = new ArrayList<String>();
					String titre = "withoutTitle" + comp;
					int compteRows = 0;
					Iterator<WtNode> it = table.getBody().iterator();
					int nbCol = 0;
					int nbColTemp = 0;
					while (it.hasNext()) {
						WtNode node = it.next();

						if (node.getNodeType() == WtTable.NT_TABLE_CAPTION) {

							WtTableCaption caption = (WtTableCaption) node;
							titre = findCaption(caption);
					
						}
						if (node.getNodeType() == WtTable.NT_TABLE_HEADER) {

							WtTableHeader header = (WtTableHeader) node;

							int i = findColspanRowSpan(header.getXmlAttributes(), colspan);
							int k = findColspanRowSpan(header.getXmlAttributes(), rowspan);
							String textHeader = findHeader(header);
							addRowSpan(compteRows, nbCol, k, textHeader, true);
							for (int j = 1; j <= i; j++) {
								headerList.add(textHeader);
								nbCol++;
							}
						}
						if (node.getNodeType() == WtTable.NT_TABLE_ROW) {
							WtTableRow row = (WtTableRow) node;
							if (!row.getBody().isEmpty() && !sautDeLigne(row.getBody())) {
								nbCol = findCol(row, rowsList, compteRows);
								compteRows++;
							}
							
						}
						if (node.getNodeType() == WtTable.NT_TABLE_CELL) {
							WtTableCell cell = (WtTableCell) node;
							int i = findColspanRowSpan(cell.getXmlAttributes(), colspan);
							int k = findColspanRowSpan(cell.getXmlAttributes(), rowspan);
							String textCell = findCellText(cell.getBody());
							addRowSpan(compteRows, nbCol, k, textCell, false);
							for (int j = 1; j <= i; j++) {
								rowsList.add(textCell);
								nbCol++;
							}

						}
						if (nbCol > nbColTemp) {
							nbColTemp = nbCol;
						}
					}
		
					if (headerList.size() != 0) {
						compteRows = compteRows + 1;
						nbCol = headerList.size();
					}
					nbCol = nbColTemp;
					String[][] tab = new String[compteRows][nbCol];

					int colonnes = 0;
					int lig = 0;

					for (String item : headerList) {
						tab[lig][colonnes] = item;
						int index = 0;
						boolean find = false;
			
						while (rowspanHeaderList.size() > index) {
							String[][] tableau = rowspanHeaderList.get(index);
							if (Integer.parseInt(tableau[0][0]) == lig && Integer.parseInt(tableau[0][1]) == colonnes) {
								find = true;
								int Newligne = lig + 1;
								for (int i = 0; i < Integer.parseInt(tableau[0][2]); i++) {
									tab[Newligne][colonnes] = checkString(tableau[0][3]);
									Newligne += 1;
								}
							}
							index++;
						}
						colonnes++;
					}
					colonnes = -1;
					if (headerList.size() != 0) {
						lig = 1;
					}

					for (String item : rowsList) {
						int colDeacalle = 1;
						int LigDeacalle = 1;
						if (colonnes == nbCol - 1) {
							colonnes = 0;
							lig++;
						} else {
							colonnes++;
						}

						if (tab[lig][colonnes] != null) {
							while (tab[lig][colonnes] != null && colonnes < nbCol - 1) {
								colonnes++;
								colDeacalle++;
							}
							if (colonnes+10 >= nbCol - 1) {
								lig++;
								LigDeacalle = 1;
								colonnes = 1;
							}
						while (tab[lig][colonnes] != null && colonnes < nbCol - 1) {
								colonnes++;
								colDeacalle++;
							}
						}

						tab[lig][colonnes] = checkString(item);
						int index = 0;
						boolean find = false;
						while (rowspanList.size() > index && !find) {
							String[][] tableau = rowspanList.get(index);
							int ligneTab;

							if (headerList.size() != 0) {
								ligneTab = Integer.parseInt(tableau[0][0]) + 1;
							} else {
								ligneTab = Integer.parseInt(tableau[0][0]);
							}

							int col = Integer.parseInt(tableau[0][1]) + colDeacalle;
							if (LigDeacalle != 0) {

								col = colDeacalle;

							}

							if (ligneTab + LigDeacalle == lig && col == colonnes) {
								find = true;
								int Newligne = lig + 1;
								for (int i = 0; i < Integer.parseInt(tableau[0][2]); i++) {
									tab[Newligne][colonnes] = checkString(tableau[0][3]);
									Newligne += 1;
								}
							}
							index++;
						}
					}

					addTableau(constructeurTableau(tab, titre, true));
					comp++;

				}
			} else if (fils.getNodeType() == WtRedirect.NT_REDIRECT) {
				String contenu;
				try {

					String valeur = "";
					int comp = 0;
					while (fils.size() > comp) {
						if (fils.get(comp) instanceof WtPageName) {
							WtPageName titre = (WtPageName) fils.get(comp);
							valeur = titre.getAsString();
						}
						comp++;
					}

					if (valeur != "") {
						sousDomain = valeur;
						contenu = wikisweble.getPageText(sousDomain);
						wikiconfig(contenu);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				parcourirNode(fils);
			}

		}

	}
/**
 * Remove the white space 
 * @param valeur a string 
 * @return the new valeur of string
 */
	private String checkString(String valeur) {
		if (!" ".equals(valeur)) {
			return valeur.trim();
		} else {
			return valeur;
		}
	}

	@Override
	public void removeTableau(Tableau leTableau) {
		if (lesTableaux.contains(leTableau)) {
			lesTableaux.remove(leTableau);
		}

	}

	/**
	 * 
	 * @return Le domaine du lien URL pour lequel on cherche des tableaux
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * 
	 * @return le sous domaine du lien url pour lequel on cherche des tableaux
	 */
	public String getSousDomain() {
		return sousDomain;
	}
/**
 * Add a tableau object in the lesTableaux list
 */
	public void addTableau(Tableau leTableau) {
		if (!lesTableaux.contains(leTableau)) {
			lesTableaux.add(leTableau);
		}
	}

	/**
	 * 
	 * @param tab
	 *            Un tableau de string comportant les valeurs d'un tableau extrait
	 *            de wikipédio
	 * @param nomTab
	 *            Le nom du tableau wikipédia
	 * @param wikiHtml
	 *            true pour indiquer que les données on était extraites avec
	 *            wikicode
	 * @return Un objet tableau
	 */
	public Tableau constructeurTableau(String[][] tab, String nomTab, boolean wikiHtml) {
		return new Tableau(delimit, cheminCSV, nomCSV, tab, nomTab, wikiHtml);
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
	 * @return un boolean qui indique si l'extraction doit �tre faire en HTML
	 *         (true) ou non (false)
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

	@Override
	public ArrayList<Tableau> getLesTableaux() {
		return lesTableaux;
	}

	/**
	 * 
	 * @return le nom du Tableau
	 */
	@Override
	public String getNomTableau(Tableau leTableau) {
		return leTableau.getnomTab();
	}


}
