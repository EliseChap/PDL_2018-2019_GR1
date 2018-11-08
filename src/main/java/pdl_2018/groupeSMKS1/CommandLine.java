package pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import src.main.java.pdl_2018.groupeSMKS1.Url;
import src.main.java.pdl_2018.groupeSMKS1.Fichier;

public class CommandLine implements ICommandLine {
    char delimit;
    String cheminCSV;
    String nomCSV;
    String cheminEntree;
    Boolean extraHTML;
    Boolean extraWiki;
    String url;

    String ligneDeCommande;

    /**
     * Constructeur de la classe CommandLine
     *
     */
    public CommandLine(String commandLine){
        this.ligneDeCommande = commandLine;
        if(verifIntegriteCommandLine(this.ligneDeCommande)){
            if(this.url!=null && this.cheminEntree==null){
                Url monUrl = new Url(this.url, this.delimit, this.nomCSV, this.cheminCSV, this.extraWiki,this.extraHTML);
            }else if (this.url==null && this.cheminEntree!=null){
                //Fichier monFichier = new Fichier(this.cheminEntree, this.delimit, this.nomCSV, this.cheminCSV, this.extraWiki,this.extraHTML);
            }

        } else {
            //Demander à l'utilisateur de saisir à nouveau la ligne de commande, en prenant compte des messages d'erreurs affichés. Comment on gère ça ?
        }
    }

    /**
     * Vérification de l'intégrité de la ligne de commande
     * @override
     * @param commandLine : Ligne de commande saisie par l'utilisateur
     * @author KLE
     * @date 14 octobre 2018
     * La méthode prend en paramètre une ligne de commande String, et renvoi "true" si cette ligne de commande est conforme à la charte Wikimatrix, "false" sinon.
     */
    @Override
    public boolean verifIntegriteCommandLine(String commandLine) {

        boolean jetonIntegrite = true; //On initialise à vrai le jeton d'intégrité. Il passe à faux dès qu'un non respect de la charte "ligne de commande" est détecté.

        if(!verifUrlOrFichierChoice(commandLine)){
            jetonIntegrite = false;
        }
        if(!verifRepertoireSortie(commandLine)){
            jetonIntegrite = false;
        }
        if(!verifDelimiteur(commandLine)){
            jetonIntegrite = false;
        }
        if(!verifHtmlOrWikicodeChoice(commandLine)){
            jetonIntegrite = false;
        }

        return jetonIntegrite;
    }

    /**
     * Cette fonction prend en paramètre la ligne de commande et renvoie false si le choix html/wikicode n'est pas effectué ou effectué anormalement, true sinon.
     * @author KLH
     * @date 3 novembre 2018
     * @param commandLine : ligne de commande saisie par l'utilisateur
     * @return
     */
    @Override
    public boolean verifHtmlOrWikicodeChoice(String commandLine){
        int nbHTML = StringUtils.countMatches(commandLine, "-html");
        int nbWikicode = StringUtils.countMatches(commandLine, "-wikicode");
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
     * Cette fonction prend en paramètre la ligne de commande et renvoie false si aucune url ou aucun fichier d'entrée n'est indiqué, ou si l'indication n'est pas exploitable, et renvoie true sinon.
     * @author KLH
     * @date 3 novembre 2018
     * @param commandLine
     * @return
     */
    @Override
    public boolean verifUrlOrFichierChoice(String commandLine){
        int nbURL = StringUtils.countMatches(commandLine, "-url");
        int nbImport = StringUtils.countMatches(commandLine, "-import");
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
            jetonLocal = verifUrlOrCheminEntree(commandLine, nbURL, nbImport); //Lancement de l'analyse en profondeur
        }
        return jetonLocal;
    }

    /**
     * Cette fonction vérifie que le chemin du fichier d'entrée ou l'url saisie par l'utilisateur est exploitable et renvoie vrai dans ce cas, et false sinon.
     * @author KLH
     * @date 3 novembre 2018
     * @param commandLine : ligne de commande saisie par l'utilisateur
     * @param nbURL : nombre de commandes "url" dans la ligne de commande
     * @param nbImport : nombre de commandes "import" dans la ligne de commande
     * @return
     */
    @Override
    public boolean verifUrlOrCheminEntree(String commandLine, int nbURL, int nbImport){
        if (nbImport == 1){ // On vérifie que le chemin de fichier spécifié est valide (on ne teste pas s'il est fonctionnel)
            Pattern pImport=Pattern.compile("-import\\[.*?\\]");
            Matcher mImport=pImport.matcher(commandLine);
            String contenuImport = mImport.group(1);
            String contenuImportExtension = contenuImport.substring(contenuImport.length() -4, contenuImport.length());
            if((contenuImportExtension!=".txt") || (contenuImport.length()<5)){
                System.out.print("Le chemin d'accès au fichier d'URLs à importer est invalide");
               return false;
            }
            setCheminEntree(contenuImport);
        } else if (nbURL == 1){ // On vérifie que l'URL spécifiée est valide, qu'il s'agit d'une url wiki (on ne teste pas si elle est fonctionnelle)
            Pattern pURL=Pattern.compile("-url\\[.*?\\]");
            Matcher mURL = pURL.matcher(commandLine);
            String contenuURL = mURL.group(1);
            if((contenuURL!="https://en.wikipedia.org/") || (contenuURL!="https://fr.wikipedia.org/") || (contenuURL!="http://en.wikipedia.org/") || (contenuURL!="http://fr.wikipedia.org/")){
                System.out.println("L'url saisie n'est pas prise en charge par Wikimatrix");
                return false;
            }
            setUrl(contenuURL);
        }
        return true;
    }

    /**
     * Cette fonction prend en paramètre la ligne de commande, et vérifie que le chemin de sortie, si spécifié, est valide puis renvoie vrai dans ce cas, false sinon.
     * @author KLH
     * @date 4 novembre 2018
     * @param commandLine : ligne de commande saisie par l'utilisateur
     * @return
     */
    @Override
    public boolean verifRepertoireSortie(String commandLine){
        int nbSave = StringUtils.countMatches(commandLine, "-save");
        String contenuSave = null;
        if (nbSave == 1){ // On vérifie que le chemin de fichier de sortie est valide (on ne teste pas s'il est fonctionnel)
            Pattern pSave=Pattern.compile("-save\\[.*?\\]");
            Matcher mSave=pSave.matcher(commandLine);
            contenuSave = mSave.group(1);

            if((contenuSave.charAt(contenuSave.length())!='/') || (contenuSave.length()<3)){ //Le répertoire termine obligatoirement par un slash et fait au moins 3 caractères.
                //TODO Eventuellement vérifier le système d'exploitation, pour analyser présence de "X:/" etc... selon l'OS
                System.out.println("Le chemin du fichier de sortie spécifié est invalide");
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
     * Cette fonction prend en paramètre la ligne de commande, et renvoie vrai si le délimiteur choisi par l'utilisateur est autorisé par l'application, et renvoie false sinon.
     * @author KLE
     * @date 4 novembre 2018
     * @param commandLine : ligne de commande saisie par l'utilisateur
     * @return
     */
    @Override
    public boolean verifDelimiteur(String commandLine){
        int nbDelimit = StringUtils.countMatches(commandLine, "-delimit");
        char contenuDelimit = '\0';
        List caracteresAutorises = new ArrayList(); // Cette liste permet de modifier facilement les délimiteurs autorisés par Wikimatrix sans changer le reste de la méthode.
        caracteresAutorises.add(';');
        caracteresAutorises.add(','); // A CHANGER, C'EST PAS BEAU
        if (nbDelimit == 1) { //On vérifie l'intégrité du délimiteur : est-il autorisé par Wikimatrix ?
            Pattern pDelimit=Pattern.compile("-delimit\\[.*?\\]");
            Matcher mDelimit = pDelimit.matcher(commandLine);
            contenuDelimit = mDelimit.group(1).charAt(0);
            if(!caracteresAutorises.contains(contenuDelimit)){
                System.out.println("Le délimiteur saisi n'est pas pris en charge par Wikimatrix");
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


}
