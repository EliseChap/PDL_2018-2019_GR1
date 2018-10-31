package src.main.java.pdl_2018.groupeSMKS1;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import src.main.java.pdl_2018.groupeSMKS1.ICsv;
import src.main.java.pdl_2018.groupeSMKS1.Url;

public class Csv implements ICsv{
	
	static char delimit;
	static String cheminCsv;
	static String nomCsv;
	
    static Map<String, Boolean> separateurAutomatique = new HashMap<String, Boolean>();
	
	
	
	public Csv(char delimit, String cheminCsv, String nomCsv) {
		
		Url url = new Url();
		
		
		//V�rification si d�limitation est null
		if(delimit=='\u0000') {
			this.delimit = ',';
		}
		else {
			this.delimit = delimit;
		}
		
		//V�rification si nom est null
		if(nomCsv==null || nomCsv=="") {
			this.nomCsv = "WikiMatrix.csv";
		}
		else {
			this.nomCsv = nomCsv;
		}
		
		//V�rification si cheminCsv est null
		if(cheminCsv==null) {
			this.cheminCsv = "";
		}
		else {
			this.cheminCsv= cheminCsv;
		}
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
	 * Initialisation du hashmap du separateur
	 * False signifie non present dans les donn�es
	 * @date 25/10/2018
	 */
	@Override
	public void initialisationSeparateurAutomatique() {
		separateurAutomatique.clear();
		separateurAutomatique.put(";", false);
		//separateurAutomatique.put("&", false);
		//separateurAutomatique.put(">", false);
		//separateurAutomatique.put("<", false);
		separateurAutomatique.put(":", false);
		//separateurAutomatique.put("�", false);
		//separateurAutomatique.put("*", false);
		//separateurAutomatique.put("�", false);
		//separateurAutomatique.put("\\", false);
		//separateurAutomatique.put("/", false);
		separateurAutomatique.put("-", false);
		separateurAutomatique.put("|", false);
		separateurAutomatique.put(",", false);
	}
	

	/**
	 * V�rifie si le s�parateur de l'utilisateur n'est pas utilis� sinon choisi un autre automatiquement
	 * @param tableau
	 * @return le separateur choisi
	 * @date 25/10/2018
	 */
	
	@Override
	public String verificationSeparateurValide(ArrayList<String[]> tableau) {
		
		Boolean separateurUtilisateur = false;
		
		//Analyse les donn�es afin de savoir quel est le s�parateur adapt�
		
		String separateur = ""+ delimit;
		for (String[] strArr : tableau) {
			for(String cellule :strArr) {
				if(cellule.contains(separateur)) {
					System.out.println("un choix de seperateur va �tre effectu� par d�faut, car celui d�finit pose des incoh�rences dans le csv");
					separateurUtilisateur=true;
				
				}

	
				Set cles = separateurAutomatique.keySet();
				Iterator it = cles.iterator();
				while (it.hasNext()){
				   Object cle = it.next();
				   if(cellule.contains(cle.toString())) {
					   separateurAutomatique.put(cle.toString(), true);
					}
				}
				
			}
		}

		//Return le separateur choisi
		
		if(separateurUtilisateur) {
			Set cles = separateurAutomatique.keySet();
			Iterator it = cles.iterator();
			while (it.hasNext()){
				Object cle = it.next();
				if(separateurAutomatique.get(cle)==false) {
					System.out.println(cle.toString());
					return cle.toString();
				}
			}
		}
		return separateur;
		
	}	
	
	/**
	 * Convertie un ArrayList avec des tableaux en String avec s�parateur
	 * @param tableau
	 * @return stringDelimiter
	 * @date 25/10/2018
	 */
	@Override
	public String convertionTableauEnStringDelimiter(ArrayList<String[]> tableau) {
		
		String stringDelimiter = "";
		
		 String separateur = verificationSeparateurValide(tableau);
		for (String[] strArr : tableau) {
			System.out.println(Arrays.toString(strArr));
			 String nouvelleLigne = String.join(separateur, strArr);
			 stringDelimiter = stringDelimiter + nouvelleLigne + "\n";
		}
	
		return stringDelimiter;
	}
	
	/**
	 * Cr�ation du CSV gr�ce a un String en entr�e
	 * @param Text
	 * @throws FileNotFoundException
	 * @date 25/10/2018
	 */
	
	@Override
	public void exporterCSV(String Text) {
		PrintWriter pw;
		try {
			String lien;
			
			if (!verificationCheminDispo()) {
				lien = cheminCsv+nomCsv;
						}
			else {
				lien = nomCsvIncrementer();
			}
				
			File nomFichier = new File(lien);
			try {
				nomFichier.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			pw = new PrintWriter(new File(lien));
			StringBuilder sb = new StringBuilder();
			sb.append(Text);
			pw.write(Text);
			pw.close();

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * V�rifie si le fichier existe si oui alors true
	 * @return Boolean
	 * @date 25/10/2018
	 */
	
	@Override
	public boolean verificationCheminDispo() {
		File f = new File(cheminCsv+nomCsv);
		System.out.println("le fichier existe d�j�, un nom increment� va �tre cr��");
		return f.isFile();
	}

	/**
	 * Trouve un nom de CSV non d�j� pris
	 * @return String nomDuFichierAl�atoire
	 * @date 25/10/2018
	 */
	
	@Override
	public String nomCsvIncrementer() {
		
		int incrementation = 1;
		File f = new File(cheminCsv+nomCsv);
		String[] split = nomCsv.split("\\.");
		System.out.println(split[0]);
		while(f.isFile()) {
			incrementation++;
			f= new File(cheminCsv+split[0]+"_"+incrementation+"."+split[1]);			
		}
		return cheminCsv+split[0]+"_"+incrementation+"."+split[1];
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// List of String arrays
		//char delimit ='\u0000';
		char delimit =';';
		String cheminCsv="";
		//String cheminCsv="../";
		String nomCsv=null;
		String cheminEntree=null;
		
		Csv csv = new Csv(delimit, cheminCsv,nomCsv);
		
		System.out.println(csv.verificationCheminDispo());
		
		ArrayList<String[]> list = new ArrayList<String[]>();
				String[] arr1 = { "a", "b", "c" };
				String[] arr2 = { "1,0", "2", "3", "4" };
				list.add(arr1);
				list.add(arr2);
				
				String text = csv.convertionTableauEnStringDelimiter(list);
				csv.exporterCSV(text);


    }

	public void menter() {
		// TODO Auto-generated method stub
		
	}     
 }
