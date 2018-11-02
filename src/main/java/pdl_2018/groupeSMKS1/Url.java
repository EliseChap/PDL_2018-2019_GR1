package src.main.java.pdl_2018.groupeSMKS1;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import com.google.common.net.InternetDomainName;

import src.main.java.pdl_2018.groupeSMKS1.IUrl;

public class Url implements IUrl{
	
	private String url;
	private char delimit;
	private String nomCsv; 
	private boolean extraWiki;
	private boolean extraHtml;

/**
 * 
 * @param url
 * @return true si l'URL est valide, false sinon
 * 
 */

@Override
public boolean verifURL(String url) {
	URL u = null; 
	try { 
	    u = new URL(url); 
	} catch (MalformedURLException e) { 
	    return false; 
	} 
	try { 
	    URLConnection c = u.openConnection(); 
	    c.connect(); 
	} catch (IOException e) { 
	    return false; 
	} 
	return true; 
}

//TEST
public static boolean isWikiURL(String url) {
	String domain = "wikipedia.org";
	URL u = null; 
	try { 
	    u = new URL(url); 
	} catch (MalformedURLException e) { 
	    return false; 
	}
	String host = u.getHost();
	InternetDomainName name = InternetDomainName.from(host).topPrivateDomain();
    //System.out.println(name); // A supp apres
    //System.out.println(name.toString());// A supp apres
    if(name.toString().equals(domain)){
    	return true;
    }
	return false;
}


@Override
public boolean isWikipediaURL(String url) {
	String domain = "wikipedia.org";
	URL u = null; 
	try { 
	    u = new URL(url); 
	} catch (MalformedURLException e) { 
	    return false; 
	}
	String host = u.getHost();
	InternetDomainName name = InternetDomainName.from(host).topPrivateDomain();
	 if(name.toString().equals(domain)){
	    	return true;
	    }
		return false;
}

@Override
public Extracteur ConstructeurExtracteur(char delimit, String cheminCSV, String nomCSV, boolean extraHTML,
		boolean extraWiki) {
	// TODO Auto-generated method stub
	return null;
} 

public static void main(String[] args){
	String u ="https://fr.wikipedia.org/wiki/France";
	boolean test =isWikiURL(u);
	 System.out.println(test);
}
		
}


