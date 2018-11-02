package src.main.java.pdl_2018.groupeSMKS1;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

import src.main.java.pdl_2018.groupeSMKS1.IUrl;

public class Url implements IUrl{ 

/**
 * 
 * @param url
 * @return true si l'URL est valide, false sinon
 * 
 */
public boolean isValidURL(String url) {  
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
public boolean verifURL(String URL) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean isWikipediaURL(String URL) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Extracteur ConstructeurExtracteur(char delimit, String cheminCSV, String NomCSV, boolean extraHTML,
		boolean extraWiki) {
	// TODO Auto-generated method stub
	return null;
} 



}
