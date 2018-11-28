package pdl_2018.groupeSMKS1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import com.opencsv.CSVWriter;

public class Csv implements ICsv {

	private Logger logger = Logger.getLogger(Csv.class);
	private char delimit;
	private String cheminCsv;
	private String nomCsv;
	private String[][] tableau;
	private String nomTab;
	private static Map<String, Boolean> separateurAutomatique = new HashMap<>();

	public Csv(char pdelimit, String pcheminCsv, String pnomCsv, String[][] tableau2, String nomTab, boolean extraHtmlWiki) {

		// Verification si delimitation est null
		if (pdelimit == '\u0000') {
			delimit = ',';
		} else {
			delimit = pdelimit;
		}
		// Verification si nom est null
		this.nomTab = nomTab;
		nomCsv = pnomCsv;
		nomCsv = choixNomCsv();
		String[] split = nomCsv.split("\\.");
		nomCsv = split[0] + "-1." + split[1];

		// Verification si cheminCsv est null
		if (pcheminCsv == null) {
			cheminCsv = "";
		} else {
			cheminCsv = pcheminCsv;
		}
		
		
		if(extraHtmlWiki) {
			cheminCsv = cheminCsv+"/wikitext/";
		}
		else {
			cheminCsv = cheminCsv+"/html/";
		}
		
		tableau = tableau2;
		initialisationSeparateurAutomatique();
		exporterCSV();
		System.out.println("fin");
	}

	private String choixNomCsv() {
		if (nomCsv == null || nomCsv == "") {
			if (nomTab == null || nomTab == "") {
				nomCsv = "WikiMatrix.csv";
			} else {
				nomCsv = nomTab + ".csv";
			}

		} else {
			nomCsv = nomCsv;
		}
		return nomCsv;

	}

	@Override
	public Map<String, Boolean> getSeparateur() {
		return separateurAutomatique;
	}

	@Override
	public char getDelimit() {
		return delimit;
	}

	@Override
	public String getCheminCsv() {
		return cheminCsv;
	}

	@Override
	public String getNomCsv() {
		return nomCsv;
	}

	@Override
	public String[][] getTableau() {
		return tableau;
	}

	/**
	 * Initialisation du hashmap du separateur False signifie non present dans les
	 * donnees
	 * 
	 * @date 25/10/2018
	 */
	@Override
	public void initialisationSeparateurAutomatique() {
		separateurAutomatique.clear();
		separateurAutomatique.put(";", false);
		separateurAutomatique.put(":", false);
		separateurAutomatique.put("-", false);
		separateurAutomatique.put("|", false);
		separateurAutomatique.put(",", false);
		/*
		 * separateurAutomatique.put("&", false); separateurAutomatique.put(">", false);
		 * separateurAutomatique.put("<", false); separateurAutomatique.put("�",
		 * false); separateurAutomatique.put("*", false);
		 * separateurAutomatique.put("�", false);
		 * separateurAutomatique.put("\\", false); separateurAutomatique.put("/",
		 * false);
		 */
	}

	/**
	 * Verifie si le separateur de l'utilisateur n'est pas utilise sinon choisi un
	 * autre automatiquement
	 * 
	 * @return le separateur choisi
	 * @date 25/10/2018
	 * 
	 */

	@Override
	public String verificationSeparateurValide() {

		Boolean separateurUtilisateur = false;

		// Analyse les donnees afin de savoir quel est le separateur adapte

		String separateur = "" + delimit;

		for (String[] strArr : tableau) {
			for (String cellule : strArr) {
				if (cellule == null)
					cellule = "";
				if (cellule.contains(separateur)) {
					logger.info(
							"un choix de seperateur va �tre effectu� par d�faut, car celui d�finit pose des incoh�rences dans le csv");
					separateurUtilisateur = true;

				}
				Set cles = separateurAutomatique.keySet();
				Iterator it = cles.iterator();

				while (it.hasNext()) {
					Object cle = it.next();
					if (cellule.contains(cle.toString())) {
						separateurAutomatique.put(cle.toString(), true);
					}
				}

			}
		}
		return envoyeSeparateurValide(separateurUtilisateur, separateur);

	}

	/**
	 * Choix du separateur selon si le choix de l'utilisateur est dans les donnees
	 * si oui en choisir un autre
	 * 
	 * @param separateurUtilisateur,separateur
	 * @return le separateur choisi
	 * @date 13/11/2018
	 * 
	 */

	public String envoyeSeparateurValide(boolean separateurUtilisateur, String separateur) {

		if (separateurUtilisateur) {
			Set cles = separateurAutomatique.keySet();
			Iterator it = cles.iterator();
			while (it.hasNext()) {
				Object cle = it.next();
				if (!separateurAutomatique.get(cle)) {
					logger.info(cle.toString());
					return cle.toString();
				}
			}
		}
		return separateur;
	}

	/**
	 * Creation du CSV grace a un String en entree
	 * 
	 * @param Text
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @date 25/10/2018
	 */

	@Override
	public void exporterCSV() {

		String separateur = verificationSeparateurValide();
		char car = separateur.charAt(0);
		String lien;

		if (!verificationCheminDispo()) {
			lien = cheminCsv + nomCsv;
		} else {
			lien = nomCsvIncrementer();
		}
		File nomFichier = new File(lien);

		FileWriter outputfile;
		try {
			outputfile = new FileWriter(nomFichier);

			CSVWriter writer = new CSVWriter(outputfile, car, CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
			for (String[] strArr : tableau) {
				writer.writeNext(strArr);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

		/*
		 * finally {
		 * 
		 * try { if (outputfile != null) { outputfile.close(); } if (writer != null) {
		 * writer.close(); }
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * }
		 */
	}

	/**
	 * Verifie si le fichier existe si oui alors true
	 * 
	 * @return Boolean
	 * @date 25/10/2018
	 */

	@Override
	public boolean verificationCheminDispo() {

		File dir = new File(cheminCsv);

		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		File f = new File(cheminCsv + nomCsv);
		logger.info("le fichier existe deja, un nom incremente va etre creer");
		return f.isFile();
	}

	/**
	 * Trouve un nom de CSV non deja pris
	 * 
	 * @return String nomDuFichierAleatoire
	 * @date 25/10/2018
	 */

	@Override
	public String nomCsvIncrementer() {

		int incrementation = 1;
		File f = new File(cheminCsv + nomCsv);
		nomCsv =nomCsv.replaceAll("-1", "");
		String[] split = nomCsv.split("\\.");
		while (f.isFile()) {
			incrementation++;
			f = new File(cheminCsv + split[0] + "-" + incrementation + "." + split[1]);
		}
		return cheminCsv + split[0] + "-" + incrementation + "." + split[1];

	}
}
