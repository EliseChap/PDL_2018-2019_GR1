package pdl_2018.groupeSMKS1;





import java.util.ArrayList;
import java.io.*;

public class Fichier {

    private ArrayList<Url> lesURLs; //Le tableau qui contient toutes les URLs issues du fichier d'entrée
    private String cheminFichierEntree;

    /**
     * @author KLH
     * @date 10 novembre 2018
     * @param cheminEntree : Chemin où trouver le fichier contenant les URLs wikipedia
     * @param delimit
     * @param nomCsv
     * @param cheminCsv
     * @param extraWiki
     * @param extraHtml
     */
    public Fichier(String cheminEntree, char delimit, String nomCsv, String cheminCsv, boolean extraWiki, boolean extraHtml){ //On fait ça ou directement commandLine ? A voir par rapport aux tests unitaires qui se compliquent.

        this.lesURLs = new ArrayList();

        this.cheminFichierEntree = cheminEntree;

        ArrayList<String> lignesADecouper = traitementFichierEntree(); //Permet de traiter un fichier d'entrée dont les URLs sont séparées par un retour chariot.

        decoupageAndGenerationURLs(lignesADecouper, delimit, nomCsv, cheminCsv, extraWiki, extraHtml); //Permet de traiter un fichier d'entrée dont les URLs sont séparées par ";" ou ","

        //On effectue ce double traitement pour rendre Wikimatrix plus souple concernant les fichiers .txt qu'il peut accépter en entrée.

    }

    /**
     * La méthode récupère le fichier, et retourne un ArrayList contenant toutes les lignes du fichier
     * @date 10 novembre 2018
     * @return
     */
    public ArrayList<String> traitementFichierEntree(){

        ArrayList<String> lignesDuFichier = new ArrayList();

        try{
            File fichier = new File (this.cheminFichierEntree);
            FileReader fichierReader = new FileReader (fichier);
            BufferedReader bufferReader = new BufferedReader (fichierReader);
            try{
                String ligne = bufferReader.readLine();
                while (ligne != null)
                {
                    lignesDuFichier.add(ligne);
                    ligne = bufferReader.readLine();
                }
                bufferReader.close();
                fichierReader.close();
            }catch (Exception exception){
                System.out.println ("Erreur lors de la lecture du fichier d'entrée : " + exception.getMessage());
            }
        }catch(FileNotFoundException file_exception){
            System.out.println ("Le fichier d'entrée n'a pas été trouvé par Wikimatrix");
        }
        return lignesDuFichier;
    }


    /**
     * Cette méthode prend en paramètre l'ArrayList contenant toutes les lignes du fichier d'entrée, découpe ces lignes via les délimiteurs ";" et ",", puis instancie dans un ArrayList chaque URL issue du découpage.
     * @date 11 novembre 2018
     * @param lignesADecouper : ArrayList contenant les lignes du fichier d'entrée
     * @param delimit
     * @param nomCsv
     * @param cheminCsv
     * @param extraWiki
     * @param extraHtml
     */
    public void decoupageAndGenerationURLs(ArrayList<String> lignesADecouper, char delimit, String nomCsv, String cheminCsv, boolean extraWiki, boolean extraHtml){

        for(int i=0; i<lignesADecouper.size(); i++){
            String[] delimitation = lignesADecouper.get(i).split(";|,");
            for(int j=0; j<delimitation.length; j++){
                this.lesURLs.add(new Url(delimitation[j], delimit, nomCsv, cheminCsv, extraWiki, extraHtml));
            }
        }
    }

    public ArrayList<Url> getLesURLs() {
        return lesURLs;
    }

    public void setLesURLs(ArrayList<Url> lesURLs) {
        this.lesURLs = lesURLs;
    }

    public String getCheminFichierEntree() {
        return cheminFichierEntree;
    }

    public void setCheminFichierEntree(String cheminFichierEntree) {
        this.cheminFichierEntree = cheminFichierEntree;
    }

    /**
     * Méthode main pour tester à la main la classe Fichier
     * @param args
     * @date 15 novembre 2018
     */
    public static void main(String[] args){
        //Fichier monFichier = new Fichier("c:/mesURLs.txt", ';', "moncsv", "monchemincsv", true, false);

        //System.out.println(monFichier.getLesURLs().get(2).getmyUrl());
    }
}
