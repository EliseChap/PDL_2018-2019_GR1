package pdl_2018.groupeSMKS1;





import java.util.ArrayList;
import java.io.*;

public class Fichier {

    private ArrayList<Url> lesURLs; // The array that contains all the URLs from the input file
    private String cheminFichierEntree;

    /**
     * @author KLH
     * @date 10 novembre 2018
     * @param cheminEntree : Path to find the file containing wikipedia URLs
     * @param delimit
     * @param nomCsv
     * @param cheminCsv
     * @param extraWiki
     * @param extraHtml
     */
    public Fichier(String cheminEntree, char delimit, String nomCsv, String cheminCsv, boolean extraWiki, boolean extraHtml){ //On fait ça ou directement commandLine ? A voir par rapport aux tests unitaires qui se compliquent.

        this.lesURLs = new ArrayList();

        this.cheminFichierEntree = cheminEntree;

        ArrayList<String> lignesADecouper = traitementFichierEntree(); // Process an input file whose URLs are separated by a carriage return.

        decoupageAndGenerationURLs(lignesADecouper, delimit, nomCsv, cheminCsv, extraWiki, extraHtml); // Used to process an input file whose URLs are separated by ";" or ","

        // We do this double processing to make Wikimatrix more flexible concerning the .txt files that can be input

    }

    /**
     * The method retrieves the file, and returns an ArrayList containing all the lines of the file
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
                System.out.println ("Error to read the input file: " + exception.getMessage());
            }
        }catch(FileNotFoundException file_exception){
            System.out.println ("The input file was not found by Wikimatrix");
        }
        return lignesDuFichier;
    }


    /**
     * This method takes as parameter the ArrayList containing all the lines of the input file, cutting these lines via the delimiters ";" and "," and then instantiates in each ArrayList every URL that comes from the clipping.
     * @date 11 novembre 2018
     * @param lignesADecouper : ArrayList containing the lines of the input file
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
     * Main method to manually test the Fichier class
     * @param args
     * @date 15 novembre 2018
     */
    public static void main(String[] args){

        Fichier monFichier = new Fichier("c:/urlProf.txt", ';', "moncsv", "monchemincsv", true, false);

        for(int k=0; k<monFichier.getLesURLs().size(); k++){
            System.out.println(monFichier.getLesURLs().get(k).getmyUrl());
        }

    }
}
