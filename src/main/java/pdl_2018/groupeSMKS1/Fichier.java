package pdl_2018.groupeSMKS1;


import java.util.ArrayList;
import java.io.*;

public class Fichier {

    private ArrayList<src.main.java.pdl_2018.groupeSMKS1.Url> lesURLs; //Le tableau qui contient toutes les URLs issues du fichier d'entrée

    public Fichier(src.main.java.pdl_2018.groupeSMKS1.CommandLine commandLine){

        this.lesURLs = new ArrayList();

        String cheminFichierEntree = commandLine.getCheminEntree();

        ArrayList<String> lignesADecouper = traitementFichierEntree(cheminFichierEntree);

    }

    private ArrayList<String> traitementFichierEntree(String cheminFichierEntree){

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

}
