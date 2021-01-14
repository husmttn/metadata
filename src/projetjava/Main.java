package projetjava;

//LIBRAIRIE JAVA
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//LIBRAIRIE EXTERNES
import com.mpatric.mp3agic.ID3v1;


public class Main {

	public static void main(String[] args) throws IOException {
		
		if(args.length==0) {
			System.out.println("Il manque des parametres, pour obtenir de l'aide taper -h ou --help");
		}
		
		else {
			
			if(args.length==1) {
				if(args[0].equals("-h") || args[0].equals("--help")){
					System.out.println("Si vous voulez extraire les metadata des mp3 present dans un dossier : java -jar projet.jar -d .");
					System.out.println("Si vous voulez extraire les metadata du mp3 : java -jar projet.jar -f music.mp3");
					System.out.println("Si vous voulez creer une playlist a partir d un repertoire : java -jar projet.jar -d ./music/ -o playlist.xspf");
					System.out.println("Si vous voulez executer l interface graphique : java -jar gui.jar");
				}
			}
			
			if(args.length==2) {
				String txtargs = args[1];
				if (args[0].equals("-d") && args[1].equals(txtargs)) {
					List<File> files = new ArrayList<File>();
					File folder = new File(args[1]);
					files = Parcours.getFiles(folder);
					for(int i = 0; i<files.size(); i++) {
						System.out.println(files.get(i));
						String tmp = String.valueOf(files.get(i));
						Path source = Paths.get(tmp);
						String mimetmp = Files.probeContentType(source);
						if(mimetmp.equals("audio/mpeg")) {
							System.out.println(Files.probeContentType(source));
							Metadata tagmp3 = new Metadata();
							ID3v1 montag = null;
							tagmp3.extractTag(montag, tmp);
						}
						System.out.println("\n");
					}

				}
				String txt = args[1];
				if(args[0].equals("-f") && args[1].equals(txt)) {
					Metadata tagmp3 = new Metadata();
					ID3v1 montag = null;
					String tmp = (args[1]);
					tagmp3.extractTag(montag, tmp);
				}
			}
			
			if(args.length==4) {
				String dossierplaylist=args[1];
				String fichierxspf=args[3];
				if(args[0].equals("-d") && args[1].equals(dossierplaylist) && args[1].length()>1 && args[2].equals("-o") && args[3].equals(fichierxspf)) {
					File fichier = new File(fichierxspf);
					File dossiertransfere = new File(dossierplaylist);
					PrintWriter writer = new PrintWriter(fichier);
					writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
					        + "<playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\">\r\n"
					        + "    <trackList>\r\n"
					        + 		PlayList.getPlaylistMp3(dossiertransfere)
					        + "    </trackList>\r\n"
					        + "</playlist>");
					System.out.println("Le fichier "+args[3]+" a bien été crée");
					writer.close();
				}
			}
			
			
		}
	}
}
