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
	
	public boolean  differentNombreLigneWikiHtml() {
		
		int nbreLigneWiki = wiki.size();
		int nbreLigneHtml = html.size();
		System.out.println("nombre de ligne Wiki : "+nbreLigneWiki);
		System.out.println("nombre de ligne Html : "+nbreLigneHtml);
		return nbreLigneWiki==nbreLigneHtml;
	}
	
	public int nombreLigneWiki() {
		return wiki.size();
	}
	public int nombreLigneHtml() {
		return html.size();
	}
	
	public int numberIdenticalColumnWiki() {
		int memoire = 0;

		for (String[] strArr : wiki) {
			if(memoire==0) {
				memoire=strArr.length;
			}
			else {
				if(strArr.length!=memoire) return -1;
			}
		}
		return memoire;
	}
	
	public int numberIdenticalColumnHtml() {
		int memoire = 0;

		for (String[] strArr : html) {
			if(memoire==0) {
				memoire=strArr.length;
			}
			else {
				if(strArr.length!=memoire) return -1;
			}
		}
		return memoire;
	}
	
	public boolean differenceNumberlColumn() {
		int nbreWiki = numberIdenticalColumnWiki();
		int nbreHtml = numberIdenticalColumnHtml();
		if(nbreWiki==-1 || nbreHtml==-1 || nbreWiki!=nbreHtml) {
			return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		
		
		
		ArrayList<String[]> list = new ArrayList<String[]>();
		String[] arr1 = { "a2;", "b", "c", "e" };
		String[] arr2 = { "1,0:-", "2", "3" };
		list.add(arr1);
		list.add(arr2);
		
		
		ArrayList<String[]> list3 = new ArrayList<String[]>();
		String[] arr4 = { "a2;", "b", "c", "e" };
		String[] arr5 = { "1,0:-", "2", "3" };

		list3.add(arr4);
		list3.add(arr5);


		
		Comparateur comparateur = new Comparateur(list, list3);
		System.out.println("compare ligne " + comparateur.differentNombreLigneWikiHtml());
		System.out.println("compare colonne " + comparateur.differenceNumberlColumn());
		//comparateur.comparaison();
	    //System.out.println(list);
	   // System.out.println(list.equals(list3));
	
 
	
	}
}
