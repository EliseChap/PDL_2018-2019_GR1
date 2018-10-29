package APPLICATION.src.main.java.pdl_2018.groupeSMKS1;

public class CommandLine implements ICommandLine{

    Char delimit;
    String cheminCSV;
    String nomCSV;
    String cheminEntree;
    Boolean extraHTML;
    Boolean extraWiki;
    String url;

    /**
     * Constructeur de la classe CommandLine
     * @param delimit : délimiteur pour le fichier CSV en sortie
     * @param cheminCSV :
     *
     */
    public CommandLine(){
        //Constructeur vide, constructeur par défaut. Les variables de la classe CommandLine sont définies au lancement de la méthode verifIntegriteCommandLine
    }

    /**
     * Vérification de l'intégrité de la ligne de commande
     *
     * @param commandLine
     * @author KLE
     * @date 14 octobre 2018
     * La méthode prend en paramètre une ligne de commande String, et renvoi "true" si cette ligne de commande est conforme à la charte Wikimatrix, "false" sinon.
     */
    public boolean verifIntegriteCommandLine(String commandLine) {

        boolean jetonIntegrite = true; //On initialise à vrai le jeton d'intégrité. Il passe à faux dès qu'un non respect de la charte "ligne de commande" est détecté.

        // Vérification du choix HTML / Wikicode
        int nbHTML = StringUtils.countMatches(commandLine, "-html");
        int nbWikicode = StringUtils.countMatches(commandLine, "-wikicode");
        if ((nbHTML > 1) || (nbWikicode > 1)) {
            System.out.println("La syntaxe de la commande est erronée : un même paramètre est saisi plusieurs fois");
            jetonIntegrite = false;
        } else if ((nbHTML < 1) && (nbWikicode < 1)) {
            System.out.println(
                    "Vous devez obligatoirement indiquer une méthode d'extraction, en ajoutant -html ou -wikicode");
            jetonIntegrite = false;
        }

        // Vérification de l'URL
        int nbURL = StringUtils.countMatches(commandLine, "-url");
        int nbImport = StringUtils.countMatches(commandLine, "-import");
        if (nbURL > 1) {
            System.out.println(
                    "Pour lancer l'extraction à partir de plusieurs URLs, veuillez utiliser la commande -import");
            jetonIntegrite = false;
        }
        if (nbImport > 1) {
            System.out.println("La commande import ne permet de manipuler qu'un seul fichier d'URLs à la fois");
            jetonIntegrite = false;
        }
        if ((nbImport < 1) && (nbURL < 1)) {
            System.out.println("Vous n'avez pas choisi de source à partir de laquelle lancer l'extraction");
            jetonIntegrite = false;
        }
        if ((nbImport > 0) && (nbURL > 0)) {
            System.out.println("Vous ne pouvez choisir qu'un seul mode d'importation pour lancer l'extraction");
            jetonIntegrite = false;
        }

        if (nbImport == 1){ // On vérifie que le chemin de fichier spécifié est valide (on ne teste pas s'il est fonctionnel)
            Pattern pImport=Pattern.compile("-import\\[.*?\\]");
            Matcher mImport=pImport.matcher(commandLine);
            String contenuImport = mImport.group(1);
            String contenuImportExtension = contenuImport.substring(contenuImport.length() -4, contenuImport.length());
            if((contenuImportExtension!=".txt") || (contenuImport.length()<5)){
                System.out.print("Le chemin d'accès au fichier d'URLs à importer est invalide");
                jetonIntegrite=false;
            }
        } else if (nbURL == 1){ // On vérifie que l'URL spécifiée est valide, qu'il s'agit d'une url wiki (on ne teste pas si elle est fonctionnelle)
            Pattern pURL=Pattern.compile("-url\\[.*?\\]");
            Matcher mURL = pURL.matcher(commandLine);
            String contenuURL = mURL.group(1);
            if((contenuURL!="https://en.wikipedia.org/") || (contenuURL!="https://fr.wikipedia.org/") || (contenuURL!="http://en.wikipedia.org/") || (contenuURL!="http://fr.wikipedia.org/")){
                System.out.println("L'url saisie n'est pas prise en charge par Wikimatrix");
                jetonIntegrite=false;
            }
        }

        int nbSave = StringUtils.countMatches(commandLine, "-save");
        if (nbSave == 1){ // On vérifie que le chemin de fichier de sortie est valide (on ne teste pas s'il est fonctionnel)
            Pattern pSave=Pattern.compile("-save\\[.*?\\]");
            Matcher mSave=pSave.matcher(commandLine);
            String contenuSave = mSave.group(1);
            String contenuSaveExtension = contenuSave.substring(contenuSave.length() -4, contenuSave.length());
            if((contenuSaveExtension!=".csv") || (contenuSave.length()<5)){
                System.out.println("Le chemin du fichier de sortie spécifié est invalide");
                jetonIntegrite=false;
            }
        } else if (nbSave > 1){
            System.out.println("Il est impossible de spécifier plusieurs fichiers de sortie");
            jetonIntegrite = false;
        }

        int nbDelimit = StringUtils.countMatches(commandLine, "-delimit");
        List caracteresAutorises = new ArrayList(); // Cette liste permet de modifier facilement les délimiteurs autorisés par Wikimatrix sans changer le reste de la méthode.
        caracteresAutorises.add(';');
        caracteresAutorises.add(','); // A CHANGER, C'EST PAS BEAU
        if (nbDelimit == 1) { //On vérifie l'intégrité du délimiteur : est-il autorisé par Wikimatrix ?
            Pattern pDelimit=Pattern.compile("-delimit\\[.*?\\]");
            Matcher mDelimit = pDelimit.matcher(commandLine);
            char contenuDelimit = mDelimit.group(1).charAt(0);
            if(!caracteresAutorises.contains(contenuDelimit)){
                System.out.println("Le délimiteur saisi n'est pas pris en charge par Wikimatrix");
                jetonIntegrite = false;
            }
        } else if (nbDelimit>1){
            System.out.println("Vous ne pouvez choisir qu'un seul délimiteur");
            jetonIntegrite = false;
        }

        return jetonIntegrite;
    }

}
