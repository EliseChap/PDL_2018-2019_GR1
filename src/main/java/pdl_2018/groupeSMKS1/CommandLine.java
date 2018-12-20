package pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;



public class CommandLine implements ICommandLine {
    private char delimit;
    private String cheminCSV;
    private String nomCSV;
	private String cheminEntree;
	private Boolean extraHTML;
    private Boolean extraWiki;
    private String url;
private boolean jetonIntegrite = true;
    private String ligneDeCommande;

    /**
     * Constructor of CommandLine class
     *
     */
    public CommandLine(String commandLine){

        this.delimit='\0';
        this.url="";
        this.nomCSV="";
        this.cheminEntree="";
        this.cheminCSV="";

        this.ligneDeCommande = commandLine;

        if(verifIntegriteCommandLine()){
            if(this.url!="" && this.cheminEntree==""){
                Url monUrl = new Url(this.url, this.delimit, this.nomCSV, this.cheminCSV, this.extraWiki,this.extraHTML);
            }else if (this.url=="" && this.cheminEntree!=""){
                Fichier monFichier = new Fichier(this.cheminEntree, this.delimit, this.nomCSV, this.cheminCSV, this.extraWiki,this.extraHTML);
            }

        }
    }

    public CommandLine() {
		
	}

	/**
     * Verifying the integrity of the command line
     * @override
     * @date 14 octobre 2018
     * The method returns "true" if this command line conforms to the Wikimatrix policy, "false" otherwise.
     */
    @Override
    public boolean verifIntegriteCommandLine() {

        jetonIntegrite = true; // The integrity token is initialized. It passes if a non respect of the charter "command line" is detected.
        if(this.ligneDeCommande.length()>10) {
        if(!this.ligneDeCommande.substring(0, 10).equals("wikimatrix")){
            jetonIntegrite = false;
            System.out.println("The command entered is not a Wikimatrix command");
        }
        }else {
            jetonIntegrite = false;
        System.out.println("The command entered is not a Wikimatrix command");
        }

        if(!verifUrlOrFichierChoice()){
            jetonIntegrite = false;
        }
        if(!verifRepertoireSortie()){
            jetonIntegrite = false;
        }
        if(!verifNomSortie()){
            jetonIntegrite = false;
        }
        if(!verifDelimiteur()){
            jetonIntegrite = false;
        }
        if(!verifHtmlOrWikicodeChoice()){
            jetonIntegrite = false;
        }

        return jetonIntegrite;
    }

    /**
     * This method returns false if the html / wikicode choice is not made or made abnormally, true otherwise.
     * @date 3 novembre 2018
     * @return
     */
    @Override
    public boolean verifHtmlOrWikicodeChoice(){
        int nbHTML = StringUtils.countMatches(this.ligneDeCommande, "-html");
        int nbWikicode = StringUtils.countMatches(this.ligneDeCommande, "-wikicode");
        if ((nbHTML > 1) || (nbWikicode > 1)) {
            System.out.println("The syntax of the command is wrong: the same parameter is entered several times");
            return false;
        } else if ((nbHTML < 1) && (nbWikicode < 1)) {
            System.out.println("You must specify an extraction method, adding -html or -wikicode");
            return false;
        }else{
            if(nbHTML==1 && nbWikicode==1){
                setExtraHTML(true);
                setExtraWiki(true);
            } else if (nbHTML==1 && nbWikicode!=1){
                setExtraHTML(true);
                setExtraWiki(false);
            } else if (nbHTML!=1 && nbWikicode==1){
                setExtraHTML(false);
                setExtraWiki(true);
            }
            return true;
        }
    }

    /**
     * This function returns false if no url or input file is specified, or if the indication is not exploitable, and returns true otherwise.
     * @date 3 novembre 2018
     * @return
     */
    @Override
    public boolean verifUrlOrFichierChoice(){
        int nbURL = StringUtils.countMatches(this.ligneDeCommande, "-url");
        int nbImport = StringUtils.countMatches(this.ligneDeCommande, "-import");
        boolean jetonLocal = true;
        if (nbURL > 1) {
            System.out.println("To start extraction from multiple URLs, please use the -import command");
            jetonLocal = false;
        }
        if (nbImport > 1) {
            System.out.println("The import command allows you to manipulate only one URL file at a time");
            jetonLocal = false;
        }
        if ((nbImport < 1) && (nbURL < 1)) {
            System.out.println("You did not choose a source from which to start the extraction");
            jetonLocal = false;
        }
        if ((nbImport > 0) && (nbURL > 0)) {
            System.out.println("You can only choose one import mode to start the extraction");
            jetonLocal = false;
        }

        if(jetonLocal) {
            jetonLocal = verifUrlOrCheminEntree(nbURL, nbImport); //Launch of the in-depth analysis
        }
        return jetonLocal;
    }

    /**
     * This function checks that the path of the input file or url is filled in and returns true in this case, and false otherwise.
     * @date 3 novembre 2018
     * @param nbURL : number of "url" commands in the command line
     * @param nbImport : number of "import" commands in the command line
     * @return
     */
    @Override
    public boolean verifUrlOrCheminEntree(int nbURL, int nbImport){
        if (nbImport == 1){ // We check that the specified file path is not empty (we do not test if it is functional)
            Pattern pImport=Pattern.compile("-import\\[.*?\\]");
            Matcher mImport=pImport.matcher(this.ligneDeCommande);
            String contenuImport = "";
            while (mImport.find()) {
                contenuImport = mImport.group();
                contenuImport = contenuImport.substring(8,contenuImport.length()-1);
            }
            if(contenuImport=="" || contenuImport==null){
                System.out.print("The path to the URLs file to import is not filled");
               return false;
            }
            setCheminEntree(contenuImport);
        } else if (nbURL == 1){ // We verify that the specified URL is valid, that it is a wiki URL (we do not test if it is functional)
            Pattern pURL=Pattern.compile("-url\\[.*?\\]");
            Matcher mURL = pURL.matcher(this.ligneDeCommande);
            String contenuURL = "";
            while (mURL.find()) {
                contenuURL = mURL.group();
                contenuURL = contenuURL.substring(5, contenuURL.length()-1);
            }
            if(contenuURL=="" || contenuURL==null){
                System.out.println("The url is not filled");
                return false;
            }
            setUrl(contenuURL);
        }

        return true;
    }

    /**
     * This function checks that the output path is filled and returns true in this case, false otherwise.
     * @date 4 novembre 2018
     * @return
     */
    @Override
    public boolean verifRepertoireSortie(){
        int nbSave = StringUtils.countMatches(this.ligneDeCommande, "-save");
        String contenuSave = "";
        if (nbSave == 1){ // We check that the output file path is valid (we do not test if it is functional)
                  	
        	Pattern pSave=Pattern.compile("-save\\[.*?\\]");
            Matcher mSave=pSave.matcher(this.ligneDeCommande);
            
            while (mSave.find()) {
            	 contenuSave = mSave.group();
            	 contenuSave = contenuSave.substring(6, contenuSave.length()-1);
            }
            
            if(contenuSave=="" || contenuSave==null){
                System.out.println("The output file path is not filled");
                return false;
            }
        } else if (nbSave > 1){
            System.out.println("You can not specify multiple output directories");
            return false;
        }
        setCheminCSV(contenuSave);
        return true;
    }

    /**
     * This function checks that the output name is not empty, then returns true in this case, false otherwise.
     * @date 4 novembre 2018
     * @return
     */
    @Override
    public boolean verifNomSortie(){
        int nbSave = StringUtils.countMatches(this.ligneDeCommande, "-name");
        String contenuName = "";
        if (nbSave == 1){ // We check that the output file name is valid (we do not test if it is functional)
            Pattern pName=Pattern.compile("-name\\[.*?\\]");
            Matcher mName=pName.matcher(this.ligneDeCommande);
            while (mName.find()) {
                contenuName = mName.group();
                contenuName = contenuName.substring(6, contenuName.length()-1);
            }
            if(contenuName=="" || contenuName==null){
                System.out.println("The output file name is not filled");
                return false;
            }
        } else if (nbSave > 1){
            System.out.println("You can not specify multiple output files");
            return false;
        }
        setNomCSV(contenuName);
        return true;
    }
    /**
     * This function returns true if the delimiter chosen by the user is allowed by the application, and returns false otherwise.
     * @date 4 novembre 2018
     * @return
     */
    @Override
    public boolean verifDelimiteur(){
        int nbDelimit = StringUtils.countMatches(this.ligneDeCommande, "-delimit");
        char contenuDelimit = '\0';
        List caracteresAutorises = new ArrayList(); // This list allows you to easily edit the delimiters allowed by Wikimatrix without changing the rest of the method.
        caracteresAutorises.add(';');
        caracteresAutorises.add(',');
        if (nbDelimit == 1) { //We check the integrity of the delimiter: is it authorized by Wikimatrix?
            Pattern pDelimit=Pattern.compile("-delimit\\[.*?\\]");
            Matcher mDelimit = pDelimit.matcher(this.ligneDeCommande);
            while (mDelimit.find()) {
                String contenuDelimitTemp = mDelimit.group();
                contenuDelimit = contenuDelimitTemp.substring(9, contenuDelimitTemp.length()-1).charAt(0);
            }
            if(!caracteresAutorises.contains(contenuDelimit)){
                System.out.println("The entered delimiter is not supported by Wikimatrix");
                return false;
            }
            if(contenuDelimit=='\0'){
                System.out.println("Delimiter has not been filled");
                return false;
            }
        } else if (nbDelimit>1){
            System.out.println("You can only choose one delimiter");
            return false;
        }
        setDelimit(contenuDelimit);
        return true;
    }

    public char getDelimit() {
        return delimit;
    }

    public void setDelimit(char delimit) {
        this.delimit = delimit;
    }

    public String getCheminCSV() {
        return cheminCSV;
    }

    public void setCheminCSV(String cheminCSV) {
        this.cheminCSV = cheminCSV;
    }

    public String getNomCSV() {
        return nomCSV;
    }

    public void setNomCSV(String nomCSV) {
        this.nomCSV = nomCSV;
    }

    public String getCheminEntree() {
        return cheminEntree;
    }

    public void setCheminEntree(String cheminEntree) {
        this.cheminEntree = cheminEntree;
    }

    public Boolean getExtraHTML() {
        return extraHTML;
    }

    public void setExtraHTML(Boolean extraHTML) {
        this.extraHTML = extraHTML;
    }

    public Boolean getExtraWiki() {
        return extraWiki;
    }

    public void setExtraWiki(Boolean extraWiki) {
        this.extraWiki = extraWiki;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLigneDeCommande() {
        return ligneDeCommande;
    }

    public void setLigneDeCommande(String ligneDeCommande) {
        this.ligneDeCommande = ligneDeCommande;
    }
    
    public boolean getJeton() {
    	return jetonIntegrite;
    }


}
