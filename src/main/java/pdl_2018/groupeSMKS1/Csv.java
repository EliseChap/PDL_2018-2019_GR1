package src.main.java.pdl_2018.groupeSMKS1;

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
	private String tableau[][];
	private static Map<String, Boolean> separateurAutomatique = new HashMap<>();

	public Csv(char pdelimit, String pcheminCsv, String pnomCsv, String[][] tableau2) {

		// V�rification si delimitation est null
		if (pdelimit == '\u0000') {
			delimit = ',';
		} else {
			delimit = pdelimit;
		}

		// Verification si nom est null
		if (pnomCsv == null || pnomCsv == "") {
			nomCsv = "WikiMatrix.csv";
		} else {
			nomCsv = pnomCsv;
		}

		// Verification si cheminCsv est null
		if (pcheminCsv == null) {
			cheminCsv = "";
		} else {
			cheminCsv = pcheminCsv;
		}
		tableau = tableau2;
		initialisationSeparateurAutomatique();

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
		 * separateurAutomatique.put("<", false); separateurAutomatique.put("�", false);
		 * separateurAutomatique.put("*", false); separateurAutomatique.put("�", false);
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

		// Analyse les donn�es afin de savoir quel est le s�parateur adapt�

		String separateur = "" + delimit;
		for (String[] strArr : tableau) {
			for (String cellule : strArr) {
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
	 * Choix du separateur selon si le choix de l'utilisateur est dans les données
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
				if (separateurAutomatique.get(cle)) {
					logger.info(cle.toString());
					return cle.toString();
				}
			}
		}
		return separateur;
	}

	/**
	 * Cr�ation du CSV grace a un String en entree
	 * 
	 * @param Text
	 * @throws IOException 
	 * @throws FileNotFoundException
	 * @date 25/10/2018
	 */

	@Override
	public void exporterCSV() throws IOException {

		String separateur = verificationSeparateurValide();
		char car = separateur.charAt(0);
		String lien;

		if (!verificationCheminDispo()) {
			lien = cheminCsv + nomCsv;
		} else {
			lien = nomCsvIncrementer();
		}
		File nomFichier = new File(lien);

		FileWriter outputfile = null;
		CSVWriter writer = null;

		try {
			outputfile = new FileWriter(nomFichier);
			writer = new CSVWriter(outputfile, car, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);
			for (String[] strArr : tableau) {
				writer.writeNext(strArr);
			}
		
		} finally {


				if (outputfile != null) {
					outputfile.close();
				}
				if (writer != null) {
					writer.close();
				}

		
			}		
	}

	/**
	 * Verifie si le fichier existe si oui alors true
	 * 
	 * @return Boolean
	 * @date 25/10/2018
	 */

	@Override
	public boolean verificationCheminDispo() {
		File f = new File(cheminCsv + nomCsv);
		logger.info("le fichier existe d�j�, un nom increment� va �tre cr��");
		return f.isFile();
	}

	/**
	 * Trouve un nom de CSV non deja pris
	 * 
	 * @return String nomDuFichierAl�atoire
	 * @date 25/10/2018
	 */

	@Override
	public String nomCsvIncrementer() {

		int incrementation = 1;
		File f = new File(cheminCsv + nomCsv);
		String[] split = nomCsv.split("\\.");
		logger.info(split[0]);
		while (f.isFile()) {
			incrementation++;
			f = new File(cheminCsv + split[0] + "_" + incrementation + "." + split[1]);
		}
		return cheminCsv + split[0] + "_" + incrementation + "." + split[1];

	}

}
