package pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;



public class CommandLine implements ICommandLine {
    private char delimit;
    private String cheminCSV;
    private String nomCSV;
    private String cheminEntree;
    private Boolean extraHTML;
    private Boolean extraWiki;
    private String url;

    private String ligneDeCommande;

    /**
     * Constructeur de la classe CommandLine
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

        } else {
            //Demander à l'utilisateur de saisir à nouveau la ligne de commande, en prenant compte des messages d'erreurs affichés. Comment on gère ça ?
        }
        System.out.println("CommandLine");
    }

    /**
     * Vérification de l'intégrité de la ligne de commande
     * @override
     * @author KLE
     * @date 14 octobre 2018
     * La méthode renvoie "true" si cette ligne de commande est conforme à la charte Wikimatrix, "false" sinon.
     */
    @Override
    public boolean verifIntegriteCommandLine() {

        boolean jetonIntegrite = true; //On initialise à vrai le jeton d'intégrité. Il passe à faux dès qu'un non respect de la charte "ligne de commande" est détecté.

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
     * Cette fonction renvoie false si le choix html/wikicode n'est pas effectué ou effectué anormalement, true sinon.
     * @author KLH
     * @date 3 novembre 2018
     * @return
     */
    @Override
    public boolean verifHtmlOrWikicodeChoice(){
        int nbHTML = StringUtils.countMatches(this.ligneDeCommande, "-html");
        int nbWikicode = StringUtils.countMatches(this.ligneDeCommande, "-wikicode");
        if ((nbHTML > 1) || (nbWikicode > 1)) {
            System.out.println("La syntaxe de la commande est erronée : un même paramètre est saisi plusieurs fois");
            return false;
        } else if ((nbHTML < 1) && (nbWikicode < 1)) {
            System.out.println(
                    "Vous devez obligatoirement indiquer une méthode d'extraction, en ajoutant -html ou -wikicode");
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
     * Cette fonction renvoie false si aucune url ou aucun fichier d'entrée n'est indiqué, ou si l'indication n'est pas exploitable, et renvoie true sinon.
     * @author KLH
     * @date 3 novembre 2018
     * @return
     */
    @Override
    public boolean verifUrlOrFichierChoice(){
        int nbURL = StringUtils.countMatches(this.ligneDeCommande, "-url");
        int nbImport = StringUtils.countMatches(this.ligneDeCommande, "-import");
        boolean jetonLocal = true;
        if (nbURL > 1) {
            System.out.println(
                    "Pour lancer l'extraction à partir de plusieurs URLs, veuillez utiliser la commande -import");
            jetonLocal = false;
        }
        if (nbImport > 1) {
            System.out.println("La commande import ne permet de manipuler qu'un seul fichier d'URLs à la fois");
            jetonLocal = false;
        }
        if ((nbImport < 1) && (nbURL < 1)) {
            System.out.println("Vous n'avez pas choisi de source à partir de laquelle lancer l'extraction");
            jetonLocal = false;
        }
        if ((nbImport > 0) && (nbURL > 0)) {
            System.out.println("Vous ne pouvez choisir qu'un seul mode d'importation pour lancer l'extraction");
            jetonLocal = false;
        }

        if(jetonLocal) {
            jetonLocal = verifUrlOrCheminEntree(nbURL, nbImport); //Lancement de l'analyse en profondeur
        }
        return jetonLocal;
    }

    /**
     * Cette fonction vérifie que le chemin du fichier d'entrée ou l'url est renseigné et renvoie vrai dans ce cas, et false sinon.
     * @author KLH
     * @date 3 novembre 2018
     * @param nbURL : nombre de commandes "url" dans la ligne de commande
     * @param nbImport : nombre de commandes "import" dans la ligne de commande
     * @return
     */
    @Override
    public boolean verifUrlOrCheminEntree(int nbURL, int nbImport){
        if (nbImport == 1){ // On vérifie que le chemin de fichier spécifié n'est pas vide (on ne teste pas s'il est fonctionnel)
            Pattern pImport=Pattern.compile("-import\\[.*?\\]");
            Matcher mImport=pImport.matcher(this.ligneDeCommande);
            String contenuImport = "";
            while (mImport.find()) {
                contenuImport = mImport.group();
                contenuImport = contenuImport.substring(8,contenuImport.length()-1);
            }
            if(contenuImport=="" || contenuImport==null){
                System.out.print("Le chemin d'accès au fichier d'URLs à importer n'est pas renseigné");
               return false;
            }
            setCheminEntree(contenuImport);
        } else if (nbURL == 1){ // On vérifie que l'URL spécifiée est valide, qu'il s'agit d'une url wiki (on ne teste pas si elle est fonctionnelle)
            Pattern pURL=Pattern.compile("-url\\[.*?\\]");
            Matcher mURL = pURL.matcher(this.ligneDeCommande);
            String contenuURL = "";
            while (mURL.find()) {
                contenuURL = mURL.group();
                contenuURL = contenuURL.substring(5, contenuURL.length()-1);
            }
            if(contenuURL=="" || contenuURL==null){
                System.out.println("L'url n'est pas renseignée");
                return false;
            }
            setUrl(contenuURL);
        }

/* Code qui vérifie que l'url saisie est une url wikipédia. VOIR AVEC LE PROF SI ON GARDE ON PAS
    } else if (nbURL == 1){ // On vérifie que l'URL spécifiée est valide, qu'il s'agit d'une url wiki (on ne teste pas si elle est fonctionnelle)
        Pattern pURL=Pattern.compile("-url\\[.*?\\]");
        Matcher mURL = pURL.matcher(this.ligneDeCommande);
        String contenuURL = mURL.group(1);
        if((contenuURL.substring(0, 24)!="https://en.wikipedia.org/") || (contenuURL.substring(0, 24)!="https://fr.wikipedia.org/") || (contenuURL.substring(0, 23)!="http://en.wikipedia.org/") || (contenuURL.substring(0, 23)!="http://fr.wikipedia.org/")){
            System.out.println("L'url saisie n'est pas prise en charge par Wikimatrix");
            return false;
        }
        setUrl(contenuURL);
    }
    */

        return true;
    }

    /**
     * Cette fonction vérifie que le chemin de sortie est renseigné puis renvoie vrai dans ce cas, false sinon.
     * @author KLH
     * @date 4 novembre 2018
     * @return
     */
    @Override
    public boolean verifRepertoireSortie(){
        int nbSave = StringUtils.countMatches(this.ligneDeCommande, "-save");
        String contenuSave = "";
        if (nbSave == 1){ // On vérifie que le chemin de fichier de sortie est valide (on ne teste pas s'il est fonctionnel)
                  	
        	Pattern pSave=Pattern.compile("-save\\[.*?\\]");
            Matcher mSave=pSave.matcher(this.ligneDeCommande);
            
            while (mSave.find()) {
            	 contenuSave = mSave.group();
            	 contenuSave = contenuSave.substring(6, contenuSave.length()-1);
            }
            
            if(contenuSave=="" || contenuSave==null){
                System.out.println("Le chemin du fichier de sortie n'est pas renseigné");
                return false;
            }
        } else if (nbSave > 1){
            System.out.println("Il est impossible de spécifier plusieurs répertoires de sortie");
            return false;
        }
        setCheminCSV(contenuSave);
        return true;
    }

    /**
     * Cette fonction vérifie que le nom de sortie n'est pas vide puis renvoie vrai dans ce cas, false sinon.
     * @author KLH
     * @date 4 novembre 2018
     * @return
     */
    @Override
    public boolean verifNomSortie(){
        int nbSave = StringUtils.countMatches(this.ligneDeCommande, "-name");
        String contenuName = "";
        if (nbSave == 1){ // On vérifie que le nom de fichier de sortie est valide (on ne teste pas s'il est fonctionnel)
            Pattern pName=Pattern.compile("-name\\[.*?\\]");
            Matcher mName=pName.matcher(this.ligneDeCommande);
            while (mName.find()) {
                contenuName = mName.group();
                contenuName = contenuName.substring(6, contenuName.length()-1);
            }
            if(contenuName=="" || contenuName==null){
                System.out.println("Le nom du fichier de sortie n'est pas renseigné");
                return false;
            }
            /*
                if((contenuName.substring(contenuName.length()-5)!=".csv") || (contenuName.length()<5)){ //Le fichier termine obligatoirement par .csv et fait au moins 5 caractères (le nom minimum 1 + le .csv de taille 4).
                System.out.println("Le chemin du fichier de sortie spécifié est invalide");
                return false;
                }
             */
        } else if (nbSave > 1){
            System.out.println("Il est impossible de spécifier plusieurs fichiers de sortie");
            return false;
        }
        setNomCSV(contenuName);
        return true;
    }
    /**
     * Cette fonction renvoie vrai si le délimiteur choisi par l'utilisateur est autorisé par l'application, et renvoie false sinon.
     * @author KLE
     * @date 4 novembre 2018
     * @return
     */
    @Override
    public boolean verifDelimiteur(){
        int nbDelimit = StringUtils.countMatches(this.ligneDeCommande, "-delimit");
        char contenuDelimit = '\0';
        List caracteresAutorises = new ArrayList(); // Cette liste permet de modifier facilement les délimiteurs autorisés par Wikimatrix sans changer le reste de la méthode.
        caracteresAutorises.add(';');
        caracteresAutorises.add(','); // A CHANGER, C'EST PAS BEAU
        if (nbDelimit == 1) { //On vérifie l'intégrité du délimiteur : est-il autorisé par Wikimatrix ?
            Pattern pDelimit=Pattern.compile("-delimit\\[.*?\\]");
            Matcher mDelimit = pDelimit.matcher(this.ligneDeCommande);
            while (mDelimit.find()) {
                String contenuDelimitTemp = mDelimit.group();
                contenuDelimit = contenuDelimitTemp.substring(9, contenuDelimitTemp.length()-1).charAt(0);
            }
            if(!caracteresAutorises.contains(contenuDelimit)){
                System.out.println("Le délimiteur saisi n'est pas pris en charge par Wikimatrix");
                return false;
            }
            if(contenuDelimit=='\0'){
                System.out.println("Le délimiteur n'a pas été renseigné");
                return false;
            }
        } else if (nbDelimit>1){
            System.out.println("Vous ne pouvez choisir qu'un seul délimiteur");
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

    /**
     * Méthode main pour tester à la main la classe CommandLine
     * @param args
     * @date 15 novembre 2018
     */

    public static void main(String[] args){

        CommandLine myCommand = new CommandLine("-html -wikicode -import[c:/urlWithAllDelimiteurs.txt] -delimit[,]");
    }

}
