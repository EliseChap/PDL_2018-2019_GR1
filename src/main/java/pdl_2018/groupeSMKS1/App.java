package pdl_2018.groupeSMKS1;

import com.sun.xml.bind.v2.TODO;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		
		afficherMessageBienvenue();
		
		CommandLine com = demanderCommande();
		
		while(!com.getJeton()) {
			com = demanderCommande();
		}
		
	}

	public static void afficherMessageBienvenue(){
		System.out.println("Welcome to Wikimatrix, the Wikipedia tables extractor made in Rennes!");
		System.out.println("To start extracting data, write a command below and type \"Enter\".");
		System.out.println("For a list of the required or optional commands available, type directly \"Enter\".");
	}

	public static CommandLine demanderCommande(){
		
		Scanner saisieClavier = new Scanner(System.in);
		System.out.println("YOUR COMMAND >>> ");
		String ldc = saisieClavier.nextLine();

		if(ldc==null || ldc==""){
			afficherCommandesDisponibles();
		}else{
			return new CommandLine(ldc);
		}
		return null;
	}

	public static void afficherCommandesDisponibles(){
		System.out.println("(mandatory) -html and/or -wikicode : allows you to specify your preference for HTML-based or Wikicode-based extraction");
		System.out.println("-import[...] : allows you to specify an input file in .txt format, to extract from multiple URLs simultaneously");
		System.out.println("-save[...] : allows you to specify an output directory for generated CSV files");
		System.out.println("-name[...] : allows you to specify a name for generated CSV files");
		System.out.println("-delimit[...] : allows you to specify a delimiter \n");

		demanderCommande(); //After displaying the available options, restart the request to enter a command.
	}
}


