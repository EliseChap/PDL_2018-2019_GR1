package pdl_2018.groupeSMKS1;

public class App {

	public static void main(String[] args) {
	
		
		char delimit = ';';
		String url ="https://fr.wikipedia.org/wiki/Stranger_Things";
		String cheminCSV = "";
		boolean extraHTML = true;
		String nomCSV = "Html_Csv";
		boolean extraWiki = false;
		Html html = new Html(url, delimit, cheminCSV, nomCSV, extraHTML, extraWiki);
		html.recuperationPage();
	}

}
