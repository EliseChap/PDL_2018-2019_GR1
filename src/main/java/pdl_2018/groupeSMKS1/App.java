package pdl_2018.groupeSMKS1;

import com.sun.xml.bind.v2.TODO;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		afficherMessageBienvenue();//On ne le lance qu'une fois, même si il y a un problème et que l'user doit re-saisir une commande
		demanderCommande(); //On demande la saisie d'une commande pour la 1ere fois.
	}

	public static void afficherMessageBienvenue(){
		System.out.println("Bienvenue sur Wikimatrix, l'extracteur de tableaux Wikipédia made in Rennes !");
		System.out.println("Pour commencer à extraire de la donnée, saisissez une commande ci-dessous puis tapez \"entrée\".");
		System.out.println("Pour obtenir la liste des commandes obligatoires ou optionnelles disponibles, tapez directement \"entrée\".");
	}

	public static void demanderCommande(){
		// TODO: Comment on gère la relance de cette méthode en cas de plantage ? Voir jeudi 6 déc.

		Scanner saisieClavier = new Scanner(System.in);
		System.out.println("VOTRE COMMANDE >>> ");
		String ldc = saisieClavier.nextLine();

		if(ldc==null || ldc==""){
			afficherCommandesDisponibles();
		}else{
			new CommandLine(ldc);
		}

	}

	public static void afficherCommandesDisponibles(){
		System.out.println("(obligatoire) -html et/ou -wikicode : permet d'indiquer votre préférence pour une extraction à base d'HTML ou de Wikicode");
		System.out.println("-import[...] : permet de préciser un fichier d'entrée au format .txt, pour extraire à partir de plusieurs URLs simultanément");
		System.out.println("-save[...] : permet de préciser un répertoire de sortie pour les fichiers CSV générés.");
		System.out.println("-name[...] : permet de préciser un nom pour les fichiers CSV générés.");
		System.out.println("-delimit[...] : permet de spécifier un délimiteur \n");

		demanderCommande(); //Après affichage des options disponibles, on relance la demande de saisie d'une commande.
	}
}

	/*	char delimit = ';';
		String url ="https://fr.wikipedia.org/wiki/Stranger_Things";
		String cheminCSV = "C:/Users/sullivand/Music/Desktop/";
		boolean extraHTML = true;
		String nomCSV = "Html_Csv.csv";
		boolean extraWiki = false;
		/*Html html = new Html(url, delimit, cheminCSV, nomCSV, extraHTML, extraWiki);
		html.recuperationPage();

		CommandLine commandLine = new CommandLine("-delimit[;] -wikicode -save[C:/Users/Margaux/Music/Desktop/] -name[monBeauTableauWiki.csv] -url[https://fr.wikipedia.org/wiki/Stranger_Things]");
		*/
