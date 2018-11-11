package src.main.java.pdl_2018.groupeSMKS1;




import src.main.java.pdl_2018.groupeSMKS1.Url;

import java.util.ArrayList;
import java.io.*;

public class Fichier {

    private ArrayList<src.main.java.pdl_2018.groupeSMKS1.Url> lesURLs; //Le tableau qui contient toutes les URLs issues du fichier d'entrée

    public Fichier(src.main.java.pdl_2018.groupeSMKS1.CommandLine commandLine){

        this.lesURLs = new ArrayList();

        String cheminFichierEntree = commandLine.getCheminEntree();

        ArrayList<String> lignesADecouper = traitementFichierEntree(cheminFichierEntree);

        decoupageAndGenerationURLs(lignesADecouper, commandLine);

    }

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
