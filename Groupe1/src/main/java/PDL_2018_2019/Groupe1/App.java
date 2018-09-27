package PDL_2018_2019.Groupe1;

/**
 * Hello world!
 *
 */
/*public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello Worl" );
    }
}*/

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       // Extraction();
    	ConvertirAdresseWikiCode("https://fr.wikipedia.org/wiki/Ordre_du_Temple");
    	
    }
    
    public static void ConvertirAdresseWikiCode(String url) {
        // String to be scanned to find the pattern.
        String pattern = ".*wiki/(.*)";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        // Now create matcher object.
        Matcher m = r.matcher(url);
        if (m.find( )) {
           //System.out.println("Found value: " + m.group(0) );
           //System.out.println("Found value: " + m.group(1) );
           //System.out.println("Found value: " + m.group(2) );
           //System.out.println("Found value: " + m.group(3) );
        String d = "https://fr.wikipedia.org/w/index.php?title="; 
        String d1 = "&action=edit";
        String d2 = d+m.group(1)+d1;
        System.out.println(d2);
        getTextFile(d2); 
        }else {
           System.out.println("NO MATCH");
        }
    }
   
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
    /*
     * Précondiction = True if adresse Wiki faux sinon
     *
     */
    public static boolean verificationUrl(String url) throws IOException, BadLocationException {
    	String URLValide="https://fr.wikipedia.org";
    	if (url.contains(URLValide)) {
    		getTextFile(URLValide);
    	}
    	return url.contains(URLValide);
    }
    
    public static void BaliseTableau(String txtHtml) {
    	Pattern p = Pattern.compile(".*<tr(.*)</tr>.*");
    	Matcher match = p.matcher(txtHtml);
    	if(match.matches())
            System.out.println(match.group(1));

    }
       
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
           System.out.println(sb.toString());
           // BaliseTableau(sb.toString());
            ExtraireWikiCode(sb.toString());
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

} 
    

