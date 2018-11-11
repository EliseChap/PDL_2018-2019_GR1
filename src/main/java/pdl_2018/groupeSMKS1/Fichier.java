package src.main.java.pdl_2018.groupeSMKS1;




import src.main.java.pdl_2018.groupeSMKS1.Url;

import java.util.ArrayList;
import java.io.*;

public class Fichier {

    private ArrayList<src.main.java.pdl_2018.groupeSMKS1.Url> lesURLs; //Le tableau qui contient toutes les URLs issues du fichier d'entrée

    /**
     * @author KLH
     * @date 10 novembre 2018
     * @param commandLine : Objet ligne de commande
     * @param commandLine
     */
    public Fichier(src.main.java.pdl_2018.groupeSMKS1.CommandLine commandLine){

        this.lesURLs = new ArrayList();

        String cheminFichierEntree = commandLine.getCheminEntree();

        ArrayList<String> lignesADecouper = traitementFichierEntree(cheminFichierEntree); //Permet de traiter un fichier d'entrée dont les URLs sont séparées par un retour chariot.

        decoupageAndGenerationURLs(lignesADecouper, commandLine); //Permet de traiter un fichier d'entrée dont les URLs sont séparées par ";" ou ","

        //On effectue ce double traitement pour rendre Wikimatrix plus souple concernant les fichiers .txt qu'il peut accépter en entrée.

    }

    /**
     * La méthode prend en paramètre le chamin d'accès au fichier d'entrée contenant les URLs, récupère le fichier, et retourne un ArrayList contenant toutes les lignes du fichier
     * @date 10 novembre 2018
     * @param cheminFichierEntree : chemin d'accès au fichier d'entrée contenant les URLs, issue de l'objet ligne de commande.
     * @return
     */
    public ArrayList<String> traitementFichierEntree(String cheminFichierEntree){

        ArrayList<String> lignesDuFichier = new ArrayList();

        try{
            File fichier = new File (cheminFichierEntree);
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
            }catch (IOException exception){
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
     * @param commandLine : Objet ligne de commande
     */
    public void decoupageAndGenerationURLs(ArrayList<String> lignesADecouper, src.main.java.pdl_2018.groupeSMKS1.CommandLine commandLine){

        for(int i=0; i<lignesADecouper.size(); i++){
            String[] delimitation = lignesADecouper.get(i).split(";|,");
            for(int j=0; j<delimitation.length; j++){
                this.lesURLs.add(new Url(delimitation[j], commandLine.getDelimit(), commandLine.getNomCSV(), commandLine.getCheminCSV(), commandLine.getExtraWiki(), commandLine.getExtraHTML()));
                //FAUDRA CHANGER CA, SI POSSIBLE
            }
        }
    }

    public ArrayList<Url> getLesURLs() {
        return lesURLs;
    }

    public void setLesURLs(ArrayList<Url> lesURLs) {
        this.lesURLs = lesURLs;
    }
}
