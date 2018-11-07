package src.main.java.pdl_2018.groupeSMKS1;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



import src.main.java.pdl_2018.groupeSMKS1.ICsv;

public class Csv implements ICsv{
	
	static char delimit;
	static String cheminCsv;
	static String nomCsv;
	
    static Map<String, Boolean> separateurAutomatique = new HashMap<String, Boolean>();
	
    ArrayList<String[]> tableau;
	
	public Csv(char delimit, String cheminCsv, String nomCsv, ArrayList<String[]> tableau) {
		

		//V�rification si delimitation est null
		if(delimit=='\u0000') {
			this.delimit = ',';
		}
		else {
			this.delimit = delimit;
		}
		
		//Verification si nom est null
		if(nomCsv==null || nomCsv=="") {
			this.nomCsv = "WikiMatrix.csv";
		}
		else {
			this.nomCsv = nomCsv;
		}
		
		//Verification si cheminCsv est null
		if(cheminCsv==null) {
			this.cheminCsv = "";
		}
		else {
			this.cheminCsv= cheminCsv;
		}
		this.tableau=tableau;
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
	 * False signifie non present dans les donnees
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
	 * Verifie si le separateur de l'utilisateur n'est pas utilise sinon choisi un autre automatiquement
	 * @param tableau
	 * @return le separateur choisi
	 * @date 25/10/2018
	 * 
	 */
	
	@Override
	public String verificationSeparateurValide() {
		
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
	 * Convertie un ArrayList avec des tableaux en String avec separateur
	 * @param tableau
	 * @return stringDelimiter
	 * @date 25/10/2018
	 */
	@Override
	public String convertionTableauEnStringDelimiter() {
		
		String stringDelimiter = "";
		
		 String separateur = verificationSeparateurValide();
		for (String[] strArr : tableau) {
			System.out.println(Arrays.toString(strArr));
			 String nouvelleLigne = String.join(separateur, strArr);
			 stringDelimiter = stringDelimiter + nouvelleLigne + "\n";
		}
	
		return stringDelimiter;
	}
	
	/**
	 * Cr�ation du CSV grace a un String en entree
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
	 * Verifie si le fichier existe si oui alors true
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
	 * Trouve un nom de CSV non deja pris
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
	/*	File fichier = new File("testExporterCSV.csv");
		fichier.delete();
		Csv csv = new Csv(';',"","testExporterCSV.csv");
		//Assertions.assertFalse(csv.verificationCheminDispo());
		csv.exporterCSV("Ceci est un test de la classe Csv avec la methode ExporterCsv");
		//Assertions.assertTrue(csv.verificationCheminDispo());
		BufferedReader buf;
		try {
			buf = new BufferedReader(new FileReader("testExporterCSV.csv"));
			String line = buf.readLine();
			System.out.println(line);
			System.out.println(line.toString().equals("Ceci est un test de la classe Csv avec la methode ExporterCsv"));
			//Assertions.assertEquals(line, "Ceci est un test de la classe Csv avec la methode ExporterCsv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		

		fichier.delete();*/
		
		
		/*separateurAutomatique.put(";", false);
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
		separateurAutomatique.put(",", false);*/

char delimit =';';
		String cheminCsv="";
		//String cheminCsv="../";
		String nomCsv=null;
		String cheminEntree=null;
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] arr1 = { "a", "b", "c" };
		String[] arr2 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		Csv csv = new Csv(delimit, cheminCsv,nomCsv, list);
		
		
		System.out.println(csv.verificationCheminDispo());
		

				
				/*String text = csv.convertionTableauEnStringDelimiter();
				csv.exporterCSV(text);*/

    }
   
 }
