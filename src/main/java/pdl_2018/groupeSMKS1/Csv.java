package pdl_2018.groupeSMKS1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVWriter;

public class Csv implements ICsv {

	private char delimit;
	private String cheminCsv;
	private String nomCsv;
	private String[][] tableau;
	private String nomTab;
	private static Map<String, Boolean> separateurAutomatique = new HashMap<>();

	public Csv(char pdelimit, String pcheminCsv, String pnomCsv, String[][] tableau2, String nomTab,
			boolean extraHtmlWiki) {

		//Verification if delimitation is null
		if (pdelimit == '\u0000') {
			delimit = ',';
		} else {
			delimit = pdelimit;
		}
		// Verification if name is null
		this.nomTab = nomTab;
		nomCsv = pnomCsv;
		nomCsv = choixNomCsv();
		nomCsv = nomCsv.replaceAll(".csv", "");
		nomCsv = nomCsv + "-1.csv";

		// Verification if cheminCSV is null
		if (pcheminCsv == null) {
			cheminCsv = "";
		} else {
			cheminCsv = pcheminCsv;
		}

		if (extraHtmlWiki) {
			cheminCsv = cheminCsv + "/wikitext/";
		} else {
			cheminCsv = cheminCsv + "/html/";
		}

		tableau = tableau2;
		initialisationSeparateurAutomatique();
		exporterCSV();
	}

	/**
	 * Choice of the name of the csv between the user and automatic
	 * if not informed take name table, if empty take WikiMatrix
	 * @return
	 */
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
	 * Initialization of the separator hashmap False means not present in the
	 * data
	 * 
	 * @date 25/10/2018
	 * @return Boolean meaning of presence
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
	 * Check if the user's separator is not used otherwise choose a
	 * other automatically
	 * 
	 * @return the chosen separator
	 * @date 25/10/2018
	 * 
	 */
	@Override
	public String verificationSeparateurValide() {

		Boolean separateurUtilisateur = false;

		// Analyze the data to find out which is the appropriate separator

		String separateur = "" + delimit;

		for (String[] strArr : tableau) {
			for (String cellule : strArr) {
				if (cellule == null)
					cellule = "";
				if (cellule.contains(separateur)) {
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
	 * Choice of the separator according to whether the choice of the user is in the data
	 * if yes choose another
	 * 
	 * @param separateurUtilisateur,separateur
	 * @return choosen separator
	 * @date 13/11/2018
	 * 
	 */
	@Override
	public String envoyeSeparateurValide(boolean separateurUtilisateur, String separateur) {

		if (separateurUtilisateur) {
			Set cles = separateurAutomatique.keySet();
			Iterator it = cles.iterator();
			while (it.hasNext()) {
				Object cle = it.next();
				if (!separateurAutomatique.get(cle)) {

					return cle.toString();
				}
			}
		}
		return separateur;
	}

	/**
	 * Creating the CSV with an input String
	 *
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
			e.printStackTrace();
		}
		;


	}

	/**
	 * Check if the file exists if yes then true
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

		return f.isFile();
	}

	/**
	 * Find a CSV name not already taken
	 * 
	 * @return String random file name
	 * @date 25/10/2018
	 */
	@Override
	public String nomCsvIncrementer() {

		int incrementation = 1;
		File f = new File(cheminCsv + nomCsv);
		nomCsv = nomCsv.replaceAll("-1.csv", "");
		while (f.isFile()) {
			incrementation++;
			f = new File(cheminCsv + nomCsv + "-" + incrementation + ".csv");
		}
		return cheminCsv + nomCsv + "-" + incrementation + ".csv";

	}
}
