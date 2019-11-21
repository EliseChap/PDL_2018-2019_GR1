package pdl_2018.groupeSMKS1;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class NumberofColumnNumberofRow {
	
	@Test
	public void comparisonNumberofRows() throws IOException{
		
		String saisie = "wikimatrix-url[https://en.wikipedia.org/wiki/Comparison_of_World_War_I_tanks]-wikicode-name[fichiertestwikicode.csv]";
		CommandLine com = new CommandLine(saisie);
		System.out.println(com);   
		Reader in = new FileReader("output/wikitext/fichiertestwikicode-1.csv");
		
		 int count = 0;
    	 String str = "";
    	 
    	 FileInputStream fis = new FileInputStream("output/wikitext/fichiertestwikicode-1.csv");
    	 LineNumberReader l = new LineNumberReader(       
    	        new BufferedReader(new InputStreamReader(fis)));
    	               while ((str=l.readLine())!=null)
    	              {
    	                 count = l.getLineNumber();
    	              }
 

	        
    	         	String saisie2 = "wikimatrix-url[https://en.wikipedia.org/wiki/Comparison_of_World_War_I_tanks]-html-name[fichiertesthtml.csv]";
    	    		CommandLine com2 = new CommandLine(saisie2);
    	    		Reader in2 = new FileReader("output/html/fichiertesthtml-1.csv");
    	    		

    	     		
    	    		 int count2 = 0;
    	        	 String str2 = "";
    	        	 
    	        	 FileInputStream fis2 = new FileInputStream("output/html/fichiertesthtml-1.csv");
    	        	 LineNumberReader l2 = new LineNumberReader(       
    	        	        new BufferedReader(new InputStreamReader(fis2)));
    	        	               while ((str2=l2.readLine())!=null)
    	        	              {
    	        	                 count2 = l2.getLineNumber();
    	        	              }
	
			
					assertTrue(count==count2); 
					
	}
	
	@Test
	public void comparisonNumberofColumns() throws IOException{
		
		String saisie = "wikimatrix-url[https://en.wikipedia.org/wiki/Comparison_of_World_War_I_tanks]-wikicode-name[fichiertestwikicode.csv]";
		CommandLine com = new CommandLine(saisie);
		
		Reader in = new FileReader("output/wikitext/fichiertestwikicode-1.csv");
		
      	 int nb = 0;

		 int count = 0;
		 String str = "";
   	 
   	 FileInputStream fis = new FileInputStream("output/wikitext/fichiertestwikicode-1.csv");
   	 LineNumberReader l = new LineNumberReader(
   	        new BufferedReader(new InputStreamReader(fis)));
   	               while ((str=l.readLine())!=null)
   	              {
   	            	   
   	            	 char separateur = ':'; 
   	            	 String s = ":"; 
   	            	 for (int i=0; i < str.length(); i++)
   	            	 {
   	            	 if (str.charAt(i) == separateur)
   	            	 nb++;
   	            	 }

   	              }

   	String saisie2 = "wikimatrix-url[https://en.wikipedia.org/wiki/Comparison_of_World_War_I_tanks]-html-name[fichiertesthtml.csv]";
	CommandLine com2 = new CommandLine(saisie2);
	Reader in2 = new FileReader("output/html/fichiertesthtml-1.csv");
	

	int nb2 = 0; 
		
	 int count2 = 0;
	 String str2 = "";
	 
	 FileInputStream fis2 = new FileInputStream("output/html/fichiertesthtml-1.csv");
	 LineNumberReader l2 = new LineNumberReader(
	   	        new BufferedReader(new InputStreamReader(fis2)));	    
	 	while ((str2=l2.readLine())!=null)
	              {
	            	   
	   	            	 char separateur = ':'; 
	   	            	 String s = ":"; 
	   	            	 for (int i=0; i < str2.length(); i++)
	   	            	 {
	   	            	 if (str2.charAt(i) == separateur)
	   	            	 nb2++;
	   	            	 }

	   	              }
	 	
	 	assertTrue(nb==nb2); 

	  					
}

	
	
	
	
}
	