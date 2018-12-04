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
			String contenu = wikisweble.getPageText(sousDomain);
			wikiconfig(contenu);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// System.out.println(e.getMessage());
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
		// testAffichage();

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

	int comp = 0;
	List<String[][]> rowspanList = new ArrayList<String[][]>();
	List<String[][]> rowspanHeaderList = new ArrayList<String[][]>();

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

	private void parcourirNode(WtNode fils) {

		Iterator<WtNode> l = fils.iterator();
		while (l.hasNext()) {
			fils = l.next();
			if (fils.getNodeType() == WtTable.NT_TABLE) {
				rowspanList.clear();
				rowspanHeaderList.clear();
				WtTable table = (WtTable) fils;
				System.out.println(table);
				WtXmlAttributes e = table.getXmlAttributes();
				if (findClassWikitable(e)) {
					List<String> headerList = new ArrayList<String>();
					List<String> rowsList = new ArrayList<String>();
					String titre = "withoutTitle" + comp;
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

							// ce n'est pas un ajour de rows mais ce sont des cellule, qui font formé une
							// rows entre elles

						}

					}
					// boolean premiereLinge = false;
					if (headerList.size() != 0) {
						compteRows = compteRows + 1;
						nbCol = headerList.size();
						// premiereLinge = true;
					}

					String[][] tab = new String[compteRows][nbCol];

					int colonnes = 0;
					int lig = 0;
					// int compteur = 0;

					for (String item : headerList) {
						tab[lig][colonnes] = item;
						int index = 0;
						boolean find = false;
						// && !find
						while (rowspanHeaderList.size() > index) {
							String[][] tableau = rowspanHeaderList.get(index);
							if (Integer.parseInt(tableau[0][0]) == lig && Integer.parseInt(tableau[0][1]) == colonnes) {
								find = true;
								int Newligne = lig + 1;
								for (int i = 0; i < Integer.parseInt(tableau[0][2]); i++) {
									tab[Newligne][colonnes] = tableau[0][3].trim();
									Newligne += 1;
								}
							}
							index++;
						}
						colonnes++;
					}
					colonnes = 0;
					if (headerList.size() != 0) {
						lig = 1;
						colonnes = -1;
					}

					for (String item : rowsList) {
						if (colonnes == nbCol - 1) {
							colonnes = 0;
							lig++;
						} else {
							colonnes++;
						}

						if (tab[lig][colonnes] != null) {
							while (tab[lig][colonnes] != null && colonnes < nbCol -1) {
								colonnes++;
								
							}
							if (colonnes >= nbCol - 1) {
									lig++;
								colonnes = 0;
						}
							while (tab[lig][colonnes] != null && colonnes < nbCol -1) {
							colonnes++;
								
							}
						}
						tab[lig][colonnes] = item.trim();
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
							if (ligneTab == lig && Integer.parseInt(tableau[0][1]) == colonnes) {
								find = true;
								int Newligne = lig + 1;
								for (int i = 0; i < Integer.parseInt(tableau[0][2]); i++) {
									tab[Newligne][colonnes] = tableau[0][3].trim();
									Newligne += 1;
								}
							}
							index++;
						}

//						if (compteur == nbCol) {
//							compteur = 0;
//						}
//						if (compteur == 0) {
//							if (premiereLinge) {
//								lig++;
//							}
//							premiereLinge = true;
//							colonnes = 0;
//							while (tab[lig][colonnes] != null && compteur < nbCol - 1) {
//								colonnes++;
//								compteur++;
//							}
//							if (compteur > nbCol - 1) {
//								compteur = 0;
//								lig++;
//								colonnes = 0;
//							}
//							while (tab[lig][colonnes] != null && compteur < nbCol - 1) {
//								colonnes++;
//								compteur++;
//							}
//
//						} else {
//
//							colonnes++;
//							while (tab[lig][colonnes] != null && compteur < nbCol - 1) {
//								colonnes++;
//								compteur++;
//							}
//							if (compteur > nbCol - 1) {
//								compteur = 0;
//								lig++;
//								colonnes = 0;
//							}
//							while (tab[lig][colonnes] != null && compteur < nbCol - 1) {
//								colonnes++;
//								compteur++;
//							}
//						}
//
//						tab[lig][colonnes] = item.trim();
//						int index = 0;
//						boolean find = false;
//						while (rowspanList.size() > index && !find) {
//							String[][] tableau = rowspanList.get(index);
//							int ligneTab;
//
//							if (headerList.size() != 0) {
//								ligneTab = Integer.parseInt(tableau[0][0]) + 1;
//							} else {
//								ligneTab = Integer.parseInt(tableau[0][0]);
//							}
//							if (ligneTab == lig && Integer.parseInt(tableau[0][1]) == colonnes) {
//								find = true;
//								int Newligne = lig + 1;
//								for (int i = 0; i < Integer.parseInt(tableau[0][2]) ; i++) {
//									tab[Newligne][colonnes] = tableau[0][3].trim();
//									Newligne += 1;
//								}
//							}
//							index++;
//						}
//
//						compteur++;
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

	public void addTableau(Tableau leTableau) {
		if (!lesTableaux.contains(leTableau)) {
			lesTableaux.add(leTableau);
		}
	}

	/**
	 * 
	 * @param tab      Un tableau de string comportant les valeurs d'un tableau
	 *                 extrait de wikipédio
	 * @param nomTab   Le nom du tableau wikipédia
	 * @param wikiHtml true pour indiquer que les données on était extraites avec
	 *                 wikicode
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

	public static void main(String[] args) {

		try {
			Wikitext t = new Wikitext("en.wikipedia.org", "Comparison_between_Esperanto_and_Novial", ';', "chemin",
					" nomCSV.csv", false, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
