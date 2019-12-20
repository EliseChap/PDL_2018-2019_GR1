package GroundTruth;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pdl_2018.groupeSMKS1.CommandLine;

public class GroundTruth2 {

	public List<String[]> readFromCsvFile(String separator, String fileName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			List<String[]> list = new ArrayList<>();
			String line = "";
			while ((line = reader.readLine()) != null) {
				String[] array = line.split(separator);
				list.add(array);
			}
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Test
	public void groundTruth() {

		String saisie = "wikimatrix-url[https://en.wikipedia.org/wiki/Comparison_between_Ido_and_Interlingua]-html-name[compidointerlingua.csv]";
		CommandLine com = new CommandLine(saisie);
		System.out.println(com);
		List<String[]> csv1 = readFromCsvFile(":", "output/html/compidointerlingua-2.csv");
		List<String[]> csv2 = readFromCsvFile(":", "inputdata/comp.csv");
		for (int i = 0; i < csv1.size(); i++) {

			String[] result1 = csv1.get(i);
			String[] result2 = csv2.get(i);

			assertTrue(Arrays.toString(result1).equals(Arrays.toString(result2)));

		}

	}
}