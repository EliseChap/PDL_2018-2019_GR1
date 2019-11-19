package pdl_2018.groupeSMKS1;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class TestFootNote {

	@Test
	public void testFootNote() throws IOException {
		String html = "wikimatrix-url[https://en.wikipedia.org/wiki/Comparison_of_World_War_I_tanks]-html";
		CommandLine command = new CommandLine(html);
		//System.out.println(command);
		String wiki = "wikimatrix-url[https://en.wikipedia.org/wiki/Comparison_of_World_War_I_tanks]-wikicode";
		CommandLine command2 = new CommandLine(wiki);
		
		String chemin = "C:\\Users\\celie\\git\\PDL_2018-2019_GR1\\output\\html\\Prototype-World War I Tanks that entered service after, but as designed in World War I-1.csv";
		String chemin2 = "C:\\Users\\celie\\git\\PDL_2018-2019_GR1\\output\\wikitext\\Prototype-World War I Tanks that entered service after, but as designed in World War I-1.csv";
		
		BufferedReader csvHtml = new BufferedReader(new FileReader(chemin));
		BufferedReader csvWikitext = new BufferedReader(new FileReader(chemin2));
		
		
		
		String lineHtml; 
		String lineWikitext;
		while((lineHtml = csvHtml.readLine()) != null 
				&& (lineWikitext = csvWikitext.readLine()) != null) {
			System.out.println(lineHtml);
			System.out.println(lineWikitext);
			// compares each line of the 2 csv (one by html extraction, one by wikicode extraction)
			// in html there is a foot note at the last line so both csv are different
			assertEquals(lineHtml, lineWikitext);
		}
		
	}

}
