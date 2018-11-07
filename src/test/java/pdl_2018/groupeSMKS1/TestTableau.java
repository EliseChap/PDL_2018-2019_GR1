package src.test.java.pdl_2018.groupeSMKS1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import src.main.java.pdl_2018.groupeSMKS1.Tableau;

class TestTableau {
	
	
	/*
	 * Test verificationNumberIdenticalColumn
	 * Checking the number of columns is identical
	 * 
	 */
	
	@Test
	public void testverificationNumberIdenticalColumn() {
		char delimit =';';
		String cheminCsv="";
		String nomCsv=null;
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] arr1 = { "a", "b", "c" };
		String[] arr2 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		
		Tableau tableau = new Tableau(delimit, cheminCsv, nomCsv, list);
		Assertions.assertFalse(tableau.verificationNumberIdenticalColumn());
		
		
		ArrayList<String[]> list2 = new ArrayList<String[]>();
		String[] arr3 = { "a", "b", "c", "e" };
		String[] arr4 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		Tableau tableau2 = new Tableau(delimit, cheminCsv, nomCsv, list2);
		Assertions.assertTrue(tableau2.verificationNumberIdenticalColumn());
		
	}

	/**
	 * Test the Constructor method
	 * Construct the object if the number of columns is valid
	 */
	
	@Test
	public void testConstructorCsv() {
		
		char delimit =';';
		String cheminCsv="";
		String nomCsv=null;
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] arr1 = { "a", "b", "c" };
		String[] arr2 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		
		Tableau tableau = new Tableau(delimit, cheminCsv, nomCsv, list);
		Assertions.assertFalse(tableau.constructorCsv());
		
		
		ArrayList<String[]> list2 = new ArrayList<String[]>();
		String[] arr3 = { "a", "b", "c", "e" };
		String[] arr4 = { "1,0", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		Tableau tableau2 = new Tableau(delimit, cheminCsv, nomCsv, list2);
		Assertions.assertTrue(tableau2.constructorCsv());
	}
}
