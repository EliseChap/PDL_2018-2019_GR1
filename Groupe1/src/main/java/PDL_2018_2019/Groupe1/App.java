package PDL_2018_2019.Groupe1;
import org.apache.log4j.Logger; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.parser.parser.LinkTargetException;

import com.bitplan.mediawiki.japi.Mediawiki;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

/**
 * Hello world! d
 *
 */
/*public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello Worl" );
    }
}*/



/**
 * Hello world! test commit
 *
 */
public class App 
{
	 Logger Logger = org.apache.log4j.Logger.getLogger (App.class);
    public static void main( String[] args ) throws Throwable
    {
       //Extraction();
    	//ConvertirAdresseWikiCode("https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras");
    	//exporterCSV("e");
    	//testJsoup();
    	testLibrary();

    }
    
    
    /**
     * ouverture du fichier adresse.txt
     * lire toutes les lignes (url)
     */
    public static void Extraction() {
    	try{
    		InputStream flux=new FileInputStream("adresse.txt"); 
    		InputStreamReader lecture=new InputStreamReader(flux);
    		BufferedReader buff=new BufferedReader(lecture);
    		String ligne;
    		while ((ligne=buff.readLine())!=null){
    			System.out.println(ligne);
    			System.out.println(verificationUrl(ligne));
    		}
    		buff.close(); 
    		}		
    		catch (Exception e){
    		System.out.println(e.toString());
    		}
    }
    
    /* Convertir Url Entrée en URL WikiCode
     * 
     * 1) Récupération du titre de l'article
     * 2) Concaténation sous la forme edit 
     * 
     */    
    
    public static void ConvertirAdresseWikiCode(String url) {
        String pattern = ".*wiki/(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(url);
        if (m.find( )) {
        String d = "https://fr.wikipedia.org/w/index.php?title="; 
        String d1 = "&action=edit";
        String d2 = d+m.group(1)+d1;
        System.out.println(d2);
        getTextFile(d2); 
        }else {
           System.out.println("NO MATCH");
        }
    }

    /*
     * Param : Url
     * Précondiction = True if adresse Wiki faux sinon
     *
     */
    public static boolean verificationUrl(String url) throws IOException, BadLocationException {
    	String URLValide=".wikipedia.org";
    	if (url.contains(URLValide)) {
    		getTextFile(url);
    	}
    	return url.contains(URLValide);
    }
     
    /*
     * récupère le wikicode
     */
    public static void ExtraireWikiCode(String text) {
    	System.out.println("bien");
    	   // String to be scanned to find the pattern.
           String pattern = ".*<textarea(.*)</textarea>.*";
           // Create a Pattern object
           Pattern r = Pattern.compile(pattern);
           // Now create matcher object.
           Matcher m = r.matcher(text);
          // System.out.println("Found value: " + m.group(1) );
         if (m.find( )) {
              System.out.println("Found value: " + m.group(1) );}
         else {
        	 System.out.println("nomatch");
         }
    } 
    
    /*
     * lecture du site internet affiche de lire le html
     */
    public static String getTextFile(String _url) {
        BufferedReader reader = null;
        try {
            URL url = new URL(_url);
            URLConnection urlConnection = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            
                //sb.append("\n");
            }

           BaliseTableau(sb.toString());
           // ExtraireWikiCode(sb.toString());
            

            return sb.toString();
            
        } catch (IOException ex) {
          
            return "";
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {

            }
        }
    }
    /*
     * récupère le html
     */
    public static void BaliseTableau(String txtHtml) throws FileNotFoundException {
    	Pattern p = Pattern.compile(".*<table class=\"wikitable.*<tbody><tr><th>(.*)</table>.*");
    	Matcher match = p.matcher(txtHtml);
    	if(match.matches()) {
           String textComplet=match.group(1);
           textComplet=textComplet.replace("</th><th>",";");
           textComplet=textComplet.replace("<p>"," ");
           textComplet=textComplet.replace("</p>"," ");
           textComplet=textComplet.replace("</th></tr><tr><td>","\n");
           textComplet=textComplet.replace("<a href=\"/wiki/Canon_EOS-1Ds\" title=\"Canon EOS-1Ds\">","");
           
           System.out.println(textComplet);
        exporterCSV(match.group(1));}
    }
    
    /*
     * Exporter vers CSV
     */
    
    public static void exporterCSV(String Text) throws FileNotFoundException {
    	
    	PrintWriter pw = new PrintWriter(new File("test_enregistrer_csv.csv"));
          StringBuilder sb = new StringBuilder();
          //System.out.println(Text);
          sb.append(Text);


          pw.write(Text);
          pw.close();
          
          System.out.println("done!");
    	
    }

    
    public static void testJsoup() throws IOException {
    	Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/Comparison_of_Canon_EOS_digital_cameras").get();
    	    	
    	Element table =  doc.select("table.wikitable").get(0);
    	
    	Elements rows = table.getElementsByTag("tr");
    	for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
    	    Element row = rows.get(i);
    	    Elements cols = row.select("td");
    	    cols.select("span").remove();

    	    String d = cols.toString();
        	//Pattern p = Pattern.compile(" <a href=.*\">");
        	Pattern p = Pattern.compile("( {0,1}<a href=.*\">)");
        	Matcher match = p.matcher(d);
      
        	if(match.find()) {
        	//	System.out.println("sophie");
               String textComplet=match.group(0);
             //d=d.replace(textComplet, "");
            //  System.out.println(textComplet);
        	}
    	    //d=d.replace("<a href=\"/wiki/Canon_EOS_M100\" title=\"Canon EOS M100\">", "");
        	d=d.replace("<br>", " ");
        	d=d.replace("</br>", " ");
    	    d=d.replace("<td>", "");
    	    d=d.replace("</td>", "");
    	    
    	    d=d.replace("<p>", "");
    	    d=d.replace("</p>", "");
    	        	    
    	   d=d.replace("</a>", "");
    	    
    	    System.out.println(d);
    	  
    	}
    }
    
 // System.out.println(URLEncoder.encode(getFileContent("d:/1.jpg"), "ISO-8859-1"));

    public static String getFileContent(String filePath) {
        try {
            File file = new File(filePath);
            byte bt[] = new byte[(int)file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(bt);
            fis.close();

            return new String(bt, "ISO-8859-1");
        }
        catch(FileNotFoundException e){
            System.out.print("error 1" + e.getMessage());
        }
        catch(IOException e){
            System.out.print("error 2" + e.getMessage());
        }
        catch(Exception e){
            System.out.print("error 3" + e.getMessage());
        }

        return "";
}
    
    
    public static void testLibrary()  {
    	//Mediawiki("https://fr.wikipedia.org/wiki/Discussion_utilisateur:185.76.0.147");
    	
		try {
		Mediawiki wiki = new Mediawiki("https://fr.wikipedia.org/wiki/Rennes");
			//wiki.login("Dx002", "test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	

  
    }
} 
    

