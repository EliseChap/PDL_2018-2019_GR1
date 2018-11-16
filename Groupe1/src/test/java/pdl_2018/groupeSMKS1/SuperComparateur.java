package pdl_2018.groupeSMKS1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import pdl_2018.groupeSMKS1.Fichier;

public class SuperComparateur {
	private Collection<Array> Stat;
	private String cheminEntree;
	char delimit;
	String nomCsv;
	String cheminCsv;

	public void SuperComparateur(String cheminEntree, char delimit, String nomCsv, String cheminCsv) {
		this.cheminEntree = cheminEntree;
		this.delimit = delimit;
		this.nomCsv = nomCsv;
		this.cheminCsv = cheminCsv;
		Stat = null;
	}

	public void analyseStat() {
		/*Fichier fichier = new Fichier(cheminEntree, delimit, nomCsv, cheminCsv);
		fichier(fichier);*/
		
		
		

	}

	private void fichier(Fichier fichier) {

		ArrayList lesURLs = new ArrayList();

		ArrayList<String> lignesADecouper = fichier.traitementFichierEntree();

		fichier.decoupageAndGenerationURLs(lignesADecouper, delimit, nomCsv, cheminCsv, true, true);

	}

}
