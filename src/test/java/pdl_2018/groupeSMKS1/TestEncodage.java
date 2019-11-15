package pdl_2018.groupeSMKS1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.bitplan.mediawiki.japi.api.Parse;

public class TestEncodage {

	@Test
	public void testEncodage() throws IOException {
	
		/*String saisie = "wikimatrix-url[https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Ido]-html-delimit[;]";
		CommandLine com = new CommandLine(saisie);
		System.out.println(com);  */
		Reader in = new FileReader("output/html/Alphabets-1.csv");
		char c= ';';
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(c).parse(in);
		String character= "ĉ";
		String a = "a";		
		int position=4;
		for (CSVRecord record : records) {
			System.out.println("Récupération 4ème caractères de la ligne: " + record.get(position));
			assertTrue(record.get(position).contains(character));
		}
		
	}
	
}
