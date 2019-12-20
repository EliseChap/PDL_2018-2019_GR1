package GroundTruth;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import pdl_2018.groupeSMKS1.CommandLine;

public class GroundTruth1 {

	@Test
	public void GroundTruth() throws IOException {

		// String saisie =
		// "wikimatrix-url[https://en.wikipedia.org/w/index.php?title=Comparison_of_video_container_formats&oldid=929735291]-html-delimit[;]";
		// CommandLine com = new CommandLine(saisie);

		Reader in = new FileReader("output/html/table0-1.csv");
		char c = ';';
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(c).parse(in);
		int i = 0;
		for (CSVRecord record : records) {
			System.out.println("Num de la ligne:   " + record.getRecordNumber());
			if (record.getRecordNumber() == 1)
				assertEquals(record.get(i).length(), 181);
			if (record.getRecordNumber() == 2)
				assertEquals(record.get(i).length(), 73);
			if (record.getRecordNumber() == 3)
				assertEquals(record.get(i).length(), 72);
			if (record.getRecordNumber() == 4)
				assertEquals(record.get(i).length(), 114);
			if (record.getRecordNumber() == 5)
				assertEquals(record.get(i).length(), 133);
			if (record.getRecordNumber() == 6)
				assertEquals(record.get(i).length(), 83);
			if (record.getRecordNumber() == 7)
				assertEquals(record.get(i).length(), 78);
			if (record.getRecordNumber() == 8)
				assertEquals(record.get(i).length(), 75);
			if (record.getRecordNumber() == 9)
				assertEquals(record.get(i).length(), 76);
			if (record.getRecordNumber() == 10)
				assertEquals(record.get(i).length(), 160);
			if (record.getRecordNumber() == 11)
				assertEquals(record.get(i).length(), 102);
			if (record.getRecordNumber() == 12)
				assertEquals(record.get(i).length(), 80);
			if (record.getRecordNumber() == 13)
				assertEquals(record.get(i).length(), 181);
			if (record.getRecordNumber() == 14)
				assertEquals(record.get(i).length(), 83);
			if (record.getRecordNumber() == 15)
				assertEquals(record.get(i).length(), 114);
			if (record.getRecordNumber() == 16)
				assertEquals(record.get(i).length(), 104);
			if (record.getRecordNumber() == 17)
				assertEquals(record.get(i).length(), 126);
			if (record.getRecordNumber() == 18)
				assertEquals(record.get(i).length(), 160);
			if (record.getRecordNumber() == 19)
				assertEquals(record.get(i).length(), 160);
			if (record.getRecordNumber() == 20)
				assertEquals(record.get(i).length(), 160);
			if (record.getRecordNumber() == 21)
				assertEquals(record.get(i).length(), 160);
			if (record.getRecordNumber() == 10)
				assertEquals(record.get(i).length(), 160);
			if (record.getRecordNumber() == 22)
				assertEquals(record.get(i).length(), 160);

		}

	}
}
