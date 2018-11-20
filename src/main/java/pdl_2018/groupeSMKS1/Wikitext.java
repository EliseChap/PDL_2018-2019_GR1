package pdl_2018.groupeSMKS1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import de.fau.cs.osr.ptk.common.AstVisitor;

import org.sweble.wikitext.engine.ExpansionCallback;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngPage;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.nodes.WtNode;
import org.sweble.wikitext.parser.nodes.WtParagraph;
import org.sweble.wikitext.parser.nodes.WtTable;
import org.sweble.wikitext.parser.nodes.WtTableCaption;
import org.sweble.wikitext.parser.nodes.WtTableCell;
import org.sweble.wikitext.parser.nodes.*;
import org.sweble.wikitext.parser.nodes.WtTableRow;
import org.sweble.wikitext.parser.nodes.WtText;
import org.sweble.wikitext.parser.nodes.WtXmlAttribute;
import org.sweble.wikitext.parser.nodes.WtBody;
import org.sweble.wikitext.parser.nodes.WtHorizontalRule;
import org.sweble.wikitext.parser.nodes.WtXmlAttributes;
import org.wikipedia.Wiki;

public class Wikitext extends 
AstVisitor<WtNode> {
	private String domain;
	private String sousDomain;
	private char delimit;
	private String cheminCSV;
	private String nomCSV;
	private boolean extraHTML;
	private boolean extraWiki;
	private ArrayList<Tableau> lesTableaux;
	private Map<Integer, WtBody> lesWikitab;
	private int compteur = 0;

	
	
	
	//private static final Pattern ws = Pattern.compile("\\s+");

	//private final WikiConfig config;
	private StringBuilder sb;
	private StringBuilder line;

	private int extLinkNum;

	private List<Integer> toKeep = null;

	// to lazy to create an enum
	public static int INTERNAL_LINKS = 0;
	public static int BOLD = 1;
	public static int ITALICS = 2;
	public static int INTERNAL_LINKS_ARTICLES = 3;
	public static int CATEGORY_LINKS = 4;

	public static int CATEGORY_KEY = 14;

	/**
	 * Becomes true if we are no longer at the Beginning Of the whole Document.
	 */
	private boolean pastBod;

	private int needNewlines;

	private boolean needSpace;

	private LinkedList<Integer> sections;



	public void addToKeep(Integer element) {
		if (toKeep == null)
			toKeep = new ArrayList<Integer>();
		toKeep.add(element);
	}

	public void setToKeep(List<Integer> toKeep) {
		this.toKeep = toKeep;
	}

	public List<Integer> getToKeep() {
		return this.toKeep;
	}

	@Override
	protected WtNode before(WtNode node) {
		// This method is called by go() before visitation starts
		sb = new StringBuilder();
		line = new StringBuilder();
		extLinkNum = 1;
		pastBod = false;
		needNewlines = 0;
		needSpace = false;
		sections = new LinkedList<Integer>();
		return super.before(node);
	}

	@Override
	protected Object after(WtNode node, Object result) {
		finishLine();

		// This method is called by go() after visitation has finished
		// The return value will be passed to go() which passes it to the caller
		return sb.toString();
	}

	public void visit(WtNode n) {
		// Fallback for all nodes that are not explicitly handled below
		//write("<");
		//write(n.getNodeName());
		//write(" />");
	}

	public void visit(WtNodeList n) {
		iterate(n);
	}

	public void visit(WtUnorderedList e) {
		iterate(e);
	}

	public void visit(WtOrderedList e) {
		iterate(e);
	}

	public void visit(WtListItem item) {
		newline(1);
		iterate(item);
	}

	public void visit(EngPage p) {
		iterate(p);
	}

	public void visit(WtText text) {
		//write(text.getContent());
	}

	public void visit(WtWhitespace w) {
		//write(" ");
	}

	public void visit(WtBold b) {
		if ((toKeep != null) && (toKeep.contains(new Integer(BOLD)))) {
			//write("'''");
		}
		iterate(b);
		if ((toKeep != null) && (toKeep.contains(new Integer(BOLD)))) {
			//write("'''");
		}
	}

	public void visit(WtItalics i) {
		if ((toKeep != null) && (toKeep.contains(new Integer(ITALICS)))) {
			//write("''");
		}
		iterate(i);
		if ((toKeep != null) && (toKeep.contains(new Integer(ITALICS)))) {
			//write("''");
		}
	}

	public void visit(WtXmlCharRef cr) {
		//write(Character.toChars(cr.getCodePoint()));
	}

	public void visit(WtXmlEntityRef er) {
		String ch = er.getResolved();
		if (ch == null) {
			//write('&');
			//write(er.getName());
			//write(';');
		}
		else {
			//write(ch);
		}
	}

	public void visit(WtUrl wtUrl) {
		if (!wtUrl.getProtocol().isEmpty()) {
			//write(wtUrl.getProtocol());
			//write(':');
		}
		//write(wtUrl.getPath());
	}

	public void visit(WtExternalLink link) {
	}

	public void visit(WtInternalLink link) {
		if (!isCategory(link)) {
			if ((toKeep != null) && (toKeep.contains(new Integer(INTERNAL_LINKS_ARTICLES)))) {
				if (link.getPrefix() == " ") {
					// this is an article so we preserve the link - there is no prefix
					//write("[[");
					iterate(link.getTarget());
					if (link.hasTitle()) {
						//write("|");
						iterate(link.getTitle());
					}
					//write("]]");

					// note: what to do with the postfix?
				} else {
					// its not an article so we output only the anchor
					if (!link.hasTitle()) {
						iterate(link.getTarget());
					} else {
						iterate(link.getTitle());
					}
					//write(link.getPostfix());
					// note: what to do with the postfix?
				}

			} else if ((toKeep != null) && (toKeep.contains(new Integer(INTERNAL_LINKS)))) {
				//write("[[");
				//write(link.getPrefix());
				iterate(link.getTarget());
				if (link.hasTitle()) {
					//write("|");
					iterate(link.getTitle());
				}
				//write("]]");
				//write(link.getPostfix()); // ? what is a postfix?
			} else {
				//write(link.getPrefix()); // ?
				if (!link.hasTitle()) {
					//ignore categories
					iterate(link.getTarget());
				} else {
					iterate(link.getTitle());
				}
				//write(link.getPostfix()); // ? what is a postfix?
			}
		} else if ((toKeep != null) && (toKeep.contains(new Integer(CATEGORY_LINKS)))) {
			//write("[[");
			//write(link.getPrefix());
			iterate(link.getTarget());
			if (link.hasTitle()) {
				//write("|");
				iterate(link.getTitle());
			}
			//write("]]");
		} 
	}

	/**
	 *  Return true if the given link is a link to a category page
	 */
	private boolean isCategory(WtInternalLink link) {
		// Use config name!
		String categoryCanonical = "Category";
		String categoryNameSpace = "Category";
//		if (config.getNamespace(CATEGORY_KEY) != null) {
//			categoryNameSpace = config.getNamespace(14).getName();
//			categoryCanonical = config.getNamespace(14).getCanonical();
//		}
		return link.getTarget().getAsString().startsWith(categoryNameSpace) || 
			   link.getTarget().getAsString().startsWith(categoryCanonical);
	}

	public void visit(WtSection s) {
		finishLine();
		StringBuilder saveSb = sb;

		sb = new StringBuilder();

		iterate(s.getHeading());
		finishLine();
		String title = sb.toString().trim();

		sb = saveSb;

		if (s.getLevel() >= 1) {
			while (sections.size() > s.getLevel())
				sections.removeLast();
			while (sections.size() < s.getLevel())
				sections.add(1);

			StringBuilder sb2 = new StringBuilder();
			for (int i = 0; i < sections.size(); ++i) {
				if (i < 1)
					continue;

				sb2.append(sections.get(i));
				sb2.append('.');
			}

			if (sb2.length() > 0)
				sb2.append(' ');
			sb2.append(title);
			title = sb2.toString();
		}

		newline(2);
		//write(title);
		newline(1);
		//write(TextUtilities.strrep('-', title.length()));
		newline(2);

		iterate(s.getBody());

		while (sections.size() > s.getLevel())
			sections.removeLast();
		sections.add(sections.removeLast() + 1);
	}

	public void visit(WtParagraph p) {
		iterate(p);
		newline(2);
	}

	public void visit(WtHorizontalRule hr) {
		//newline(1);
		//write(TextUtilities.strrep('-', 80));
		newline(2);
	}

	public void visit(WtXmlElement e) {
//		System.out.println("processing: "+e.getName());
//		System.out.println(e.getBody().toString());
		if (e.getName().equalsIgnoreCase("br")) {
			newline(1);
		}
		else {
			iterate(e.getBody());
		}
	}

	// Stuff we want to hide

	public void visit(WtImageLink n) {
	}

	public void visit(WtIllegalCodePoint n) {
	}

	public void visit(WtXmlComment n) {
	}

	public void visit(WtTemplate n) {
	}

	public void visit(WtTemplateArgument n) {
	}

	public void visit(WtTemplateParameter n) {
	}

	public void visit(WtTagExtension n) {
	}

	public void visit(WtPageSwitch n) {
	}

	private void newline(int num) {
		if (pastBod) {
			if (num > needNewlines)
				needNewlines = num;
		}
	}

	private void wantSpace() {
		if (pastBod)
			needSpace = true;
	}

	private void finishLine() {
		sb.append(line.toString());
		line.setLength(0);
	}






	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
		lesWikitab = new HashMap<Integer, WtBody>();

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
			System.out.println(parse);
			parcourirNode(parse);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			comp++;
		}

		return "";
	}

	private void parcourirNode(WtNode fils) {
		
		for (Iterator<WtNode> l = fils.iterator(); l.hasNext();) {
			fils = l.next();
			String titre = "";

			if (fils.getNodeType() == WtTable.NT_TABLE) {

				WtTable table = (WtTable) fils;

				Object[] a = table.getBody().toArray();

				WtXmlAttributes e = table.getXmlAttributes();
				if (findClassWikitable(e)) {

				
//					titre = findCaption(caption);

					compteur++;
					lesWikitab.put(compteur, table.getBody());
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
		Wikitext t = new Wikitext("fr.wikipedia.org", "Équipe_de_France_de_football", ';', "chemin", " nomCSV", false,
				true);
		t.recuperationPage();
		t.traitementMap2();
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
