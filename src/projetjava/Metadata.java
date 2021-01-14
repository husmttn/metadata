package projetjava;

//LIBRAIRIE JAVA
import java.io.*;

//LIBRAIRIE EXTERNES
import com.mpatric.mp3agic.*;

public class Metadata {
	
	private String tmp;
	private ID3v1 montag;
	private String saisie;
	
	// VERIFICATION DU MP3 SI ID3 TAG
	public static ID3v1 getTag(String saisie) {
		try {
			Mp3File file = new Mp3File(saisie);
			if(file.hasId3v1Tag()) {
				return file.getId3v1Tag();
			}
			else if(file.hasId3v2Tag()) {
				return file.getId3v2Tag();
			}
		}
		catch(Exception excep) {
			System.out.println("ERREUR DE FORMAT");
		}
		return null;	// si aucune boucle verifier
	}
	
	
	public void extractTag(ID3v1 montag, String tmp) throws IOException {
		
		String saisie ;
		saisie=tmp;	// recupere le chemin du fichier mp3
		montag = getTag(saisie);	// verifie si ID3 tag
		
		//EXTRACTION DES METADATA
		if(montag!=null) {
			System.out.println("Track: " + montag.getTrack());
        	System.out.println("Artist: " + montag.getArtist());
        	System.out.println("Title: " + montag.getTitle());
        	System.out.println("Album: " + montag.getAlbum());
        	System.out.println("Year: " + montag.getYear());
		}
		else {
			System.out.println("Pas le bon format"); 
		}
	}
	
	public String getTrackMp3(ID3v1 montag, String saisie) {
		montag = getTag(saisie);
		return "Track : " + montag.getTrack();
	}
	
	public String getArtistMp3(ID3v1 montag, String saisie) {
		montag = getTag(saisie);
		return "Artist : " + montag.getArtist();
	}
	
	public String getTitleMp3(ID3v1 montag, String saisie) {
		montag = getTag(saisie);
		return "Title : " + montag.getTitle();
	}
	
	public String getAlbumMp3(ID3v1 montag, String saisie) {
		montag = getTag(saisie);
		return "Album : " + montag.getAlbum();
	}
	
	public String getYearMp3(ID3v1 montag, String saisie) {
		montag = getTag(saisie);
		return "Year : " + montag.getYear();
	}

	public String getTmp() {
		return tmp;
	}

	public void setTmp(String tmp) {
		this.tmp = tmp;
	}

	public ID3v1 getMontag() {
		return montag;
	}

	public void setMontag(ID3v1 montag) {
		this.montag = montag;
	}

	public String getSaisie() {
		return saisie;
	}

	public void setSaisie(String saisie) {
		this.saisie = saisie;
	}
	
}
