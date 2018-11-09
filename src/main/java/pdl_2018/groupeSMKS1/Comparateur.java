package src.main.java.pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.collections4.ListUtils;

public class Comparateur implements IComparateur{
	ArrayList<String[]> wiki;
	ArrayList<String[]> html ;
	
	public Comparateur(ArrayList<String[]> wiki,ArrayList<String[]> html ){
		this.wiki=wiki;
		this.html=html;
	}
	
	public boolean  comparaison() {
		int nbListWiki = wiki.size();
		int nbListHtml = html.size();
		boolean identique = true;
		int i = 0;
		while(i<nbListWiki && i<nbListHtml) {
			String[] tabWiki = wiki.get(i);
			String[] tabHtml = html.get(i);
			if(!Arrays.equals(tabWiki, tabHtml)) {
				identique=false;
				System.out.println("diffrérence entre les deux fichiers :");
				System.out.println("wiki"+" : "+Arrays.asList(tabWiki).toString());
				System.out.println("html"+" : "+Arrays.asList(tabHtml).toString());
			}
			i++;
		}
		return identique;
	}

	public static void main(String[] args) {
		
		
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] arr1 = { "a2;", "b", "c", "e" };
		String[] arr2 = { "1,0:-", "2", "3", "4" };
		list.add(arr1);
		list.add(arr2);
		
		
		ArrayList<String[]> list3 = new ArrayList<String[]>();
		String[] arr4 = { "a;", "b", "c" };
		String[] arr3 = { "1,0:-", "2", "3", "4" };
		list3.add(arr4);
		list3.add(arr3);
		
		Comparateur comparateur = new Comparateur(list, list3);
		comparateur.comparaison();
	    //System.out.println(list);
	   // System.out.println(list.equals(list3));
	
 
	
	}
}
