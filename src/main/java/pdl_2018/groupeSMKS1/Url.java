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

@Override
public boolean isWikipediaURL(String url) {
	URL u = null; 
	try { 
	    u = new URL(url); 
	} catch (MalformedURLException e) { 
	    return false; 
	}
	String host = u.getHost();
	InternetDomainName name = InternetDomainName.from(host).topPrivateDomain();
	System.out.println(host);
    System.out.println(name);
	return false;
}

@Override
public Extracteur ConstructeurExtracteur(char delimit, String cheminCSV, String nomCSV, boolean extraHTML,
		boolean extraWiki) {
	// TODO Auto-generated method stub
	return null;
} 



}
