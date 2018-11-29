package pdl_2018.groupeSMKS1;

public class App {

	public static void main(String[] args) {
	
		
		
	/*	char delimit = ';';
		String url ="https://fr.wikipedia.org/wiki/Stranger_Things";
		String cheminCSV = "C:/Users/sullivand/Music/Desktop/";
		boolean extraHTML = true;
		String nomCSV = "Html_Csv.csv";
		boolean extraWiki = false;
		/*Html html = new Html(url, delimit, cheminCSV, nomCSV, extraHTML, extraWiki);
		html.recuperationPage();*/
		
		CommandLine commandLine = new CommandLine("-delimit[;] -wikicode -save[C:/Users/Margaux/Music/Desktop/] -name[monBeauTableauWiki.csv] -url[https://fr.wikipedia.org/wiki/Stranger_Things]");
		System.out.println("app");
	}

}
