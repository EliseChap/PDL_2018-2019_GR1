package src.main.java.pdl_2018.groupeSMKS1;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.collections4.ListUtils;

public class Comparateur implements IComparateur{
	private ArrayList<String[]> wiki;
	private ArrayList<String[]> html ;
	
	public Comparateur(ArrayList<String[]> wiki,ArrayList<String[]> html ){
		this.wiki=wiki;
		this.html=html;
	}
	
	public boolean  comparaison() {
		int nbListWiki = wiki.size();
		int nbListHtml = html.size();
		boolean identique = true;
		String[] tabWiki;
		String[] tabHtml;
		
		int i = 0;
		while(i<nbListWiki || i<nbListHtml) {
			if(i>=nbListWiki) {
				tabHtml = html.get(i);
				identique=false;
				System.out.println("ligne "+i+" differente html"+" : "+Arrays.asList(tabHtml).toString());
				System.out.println("ligne "+i+" differente wiki"+" : ");
			}
			if(i>=nbListHtml) {
				tabWiki = wiki.get(i);
				identique=false;
				System.out.println("ligne "+i+" differente wiki"+" : "+Arrays.asList(tabWiki).toString());
				System.out.println("ligne "+i+" differente html"+" : ");
			}
			
			if(i<nbListWiki && i<nbListHtml) {
				tabHtml = html.get(i);
				tabWiki = wiki.get(i);
			if(!Arrays.equals(tabWiki, tabHtml)) {
				identique=false;
				System.out.println("diffrérence entre les deux fichiers :");
				System.out.println("ligne "+i+" differente wiki"+" : "+Arrays.asList(tabWiki).toString());
				System.out.println("ligne "+i+" differente html"+" : "+Arrays.asList(tabHtml).toString());
			}}
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
		String[] arr4 = { "a2;", "b", "c", "e" };
		String[] arr5 = { "a2;" };
		String[] arr3 = { "1,0:-", "2", "3", "4" };
		list3.add(arr4);
		list3.add(arr5);
		list3.add(arr3);

		
		Comparateur comparateur = new Comparateur(list, list3);
		comparateur.comparaison();
	    //System.out.println(list);
	   // System.out.println(list.equals(list3));
	
 
	
	}
}
