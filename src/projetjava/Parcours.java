package projetjava;

//LIBRAIRIE JAVA
import java.io.*;
import java.util.*;

public class Parcours {
	
	private File dossier;

	public static List<File> getFiles(File dossier){
		
		List<File> fichier = new ArrayList<File>();
		
	// parcours par arboressence
		for(File f : dossier.listFiles()) {
			
			if(f.isDirectory()) {	// si f est un repertoire
				List<File> fichierDossier = getFiles(f);	// alors appel recursive
				fichier.addAll(fichierDossier);
			}
			else {
				String texte = f.getName();	// recuperer le chemin du file
				if(texte.endsWith(".mp3")) {
					fichier.add(f);	// ajouter dans la list
				}
			}
			
		}
		return fichier;
	}

	public File getDossier() {
		return dossier;
	}

	public void setDossier(File dossier) {
		this.dossier = dossier;
	}
	
}