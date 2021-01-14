package projetjava;

//LIBRAIRIE JAVA
import java.io.File;
import java.io.IOException;

public class PlayList {
	
	private File dossier;
	
	public static String getPlaylistMp3(File dossier) throws IOException {
		
		String lignes="";
		File[] f = dossier.listFiles();	// declaration d une file qui va contenir l ensemble des dossier et sous dossier (et fichier) a partir d un chemin
		
		for (int i = 0; i < f.length; i++) {
			
			if(f[i].isDirectory()) {	// si f est un repertoire
				String fichierDossier = getPlaylistMp3(f[i]);	// alors appel recursive 
				lignes = lignes + fichierDossier ;
			}
			
			else {
				String texte = f[i].getName();	// recuperer le chemin
				if(texte.endsWith(".mp3")) {
					String file = f[i].toString();
					lignes = lignes + "<track><location>" + file + "</location></track>\n";	// pour ajouter les chemins des mp3 dans l ecriture du format xspf
				}
			}
			
		}
		
		return lignes;
	}

	public File getDossier() {
		return dossier;
	}

	public void setDossier(File dossier) {
		this.dossier = dossier;
	}
	
}