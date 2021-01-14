package projetjava;

//LIBRAIRIE JAVA
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.io.*;
import java.util.*;

//LIBRAIRIE EXTERNE
import com.mpatric.mp3agic.ID3v1;
import jaco.mp3.player.MP3Player;

public class Gui {
	
	public static void main(String[] args) throws IOException {
		
		
	//CREATION DU CADRE ET PANNEAU
		
		JFrame cadre = new JFrame("PlayList Maker");
		JPanel panneau = new JPanel();
		panneau.setBackground(Color.LIGHT_GRAY);
		Dimension dim = new Dimension(1500, 1000);
		panneau.setPreferredSize(dim);
		cadre.setContentPane(panneau);
		
		
	//CREATION DE LA JLISTE DE BASE QUI VA PARCOURIR ET EXTRAIRE LES FICHIERS MP3 ET DU PLAYER AVEC LA LIBRAIRIE JACO
		
		MP3Player player = new MP3Player();	// creation du MP3Player avec librairie Jaco
		List<File> files = new ArrayList<File>();
		
		if(args.length==0) {	// si dans l invite de commande rien est saisie apres java -jar gui.jar
			String txt1 = "C:\\Users\\huery\\Desktop";
			File folder = new File(txt1);
			files = Parcours.getFiles(folder);	// alors parcours par arboressence avec la methode de la class Parcours a partir du dossier ou le jar est executer
		}
		else {
			String txt2 = args[0];
			File folder = new File(txt2);
			files = Parcours.getFiles(folder);	// sinon a partir du chemin entrez dans l invite de commande
		}
		
		Vector<String> listestr = new Vector<String>();
		for(int i = 0; i<files.size(); i++) {
			String tmp = String.valueOf(files.get(i));
			listestr.addElement(tmp);
		}
		JList liste = new JList(listestr); // ajout des chemin des fichiers mp3 dans une JList avec l intermediaire d un Vector 
			
			
		//CREATION DU BOUTTON PARCOURIR
			
			JButton parcourir = new JButton("Parcourir");
			panneau.add(parcourir);
			
			parcourir.addActionListener(e->{
				panneau.add(liste, BorderLayout.WEST);
				panneau.validate();
				});
			
			
		//CREATION DU BOUTTON METADATA
			
			JButton metadata = new JButton("Metadata");
			panneau.add(metadata);
			
			metadata.addActionListener(e->{
				
				// declaration des variables pour utiliser les methode de la librarie mpatric.mp3agic
				String chem = String.valueOf(listestr.get(liste.getSelectedIndex()));
				Metadata tagmp3 = new Metadata();
				ID3v1 montag = null;
				Vector<String> metadatalist = new Vector<String>();
				
				// extraction des metadata dans un Vector<String>
				metadatalist.addElement(tagmp3.getTrackMp3(montag, chem));
				metadatalist.addElement(tagmp3.getArtistMp3(montag, chem));
				metadatalist.addElement(tagmp3.getTitleMp3(montag, chem));
				metadatalist.addElement(tagmp3.getAlbumMp3(montag, chem));
				metadatalist.addElement(tagmp3.getYearMp3(montag, chem));
				
				// affichage du Vector par une JList qui contient les element du Vector
				JList show = new JList(metadatalist);
				panneau.add(show, BorderLayout.EAST);
				panneau.validate();
				
				metadata.addActionListener(e2->{
					panneau.repaint();	// sinon bug affichage
					panneau.validate();
				});
				
			});
			
			
		//CREATION DES BOUTTONS POUR CREER PLAYLIST
			
			JButton addto = new JButton("Ajouter a la PlayList");
			panneau.add(addto);
			
			JButton mp3playlist = new JButton("Creer la PlayList");
			panneau.add(mp3playlist);
			
			JButton showpl = new JButton("Afficher la PlayList");
			panneau.add(showpl);
			
			JButton plplayer = new JButton("Jouer la PlayList");
			panneau.add(plplayer);
			
			JButton reinitialiser = new JButton("Reinatiliser la Selection");
			panneau.add(reinitialiser);
			
			Vector<String> pl = new Vector<String>();
			
			// ajout des chemin selectionner dans la playlist (librairie JACO) et dans le Vector<String> pl qui va contenir les chemin des fichier mp3 pour la playlist
			addto.addActionListener(e->{
				String chem = String.valueOf(listestr.get(liste.getSelectedIndex()));
				player.addToPlayList(new File(listestr.get(liste.getSelectedIndex())));
				pl.addElement(chem);
				JOptionPane.showMessageDialog(null, "Vous avez bien ajouter cette musique a votre PlayList", "Ajouter",JOptionPane.WARNING_MESSAGE); // message de confirmation pour l'ajout dans la playlist
			});
			
			mp3playlist.addActionListener(e->{
				
				// parcour du Vector qui contient les chemin des fichier mp3 selectionner avec Iterator
				String lignes = "" ;
				Iterator<String> itr = pl.iterator();
		        while(itr.hasNext()){
		        	lignes = lignes  + "<track><location>" + itr.next() + "</location></track>\n";
		        }
		        
		        String reponse = JOptionPane.showInputDialog(null, "Entrez le nom de la PlayList : ");	// fenetre de dialogue pour entrer le nom de la playlist et generer le file
		        JLabel label = new JLabel();
		        label.setText(reponse);
		        File fichier = new File(label.getText());
		        
		        // ecrire dans un fichier a l aide d un PrintWriter
				PrintWriter writer = null;	
				try {
					writer = new PrintWriter(fichier);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				        + "<playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\">\r\n"
				        + "    <trackList>\r\n"
				        + 		String.valueOf(lignes)
				        + "    </trackList>\r\n"
				        + "</playlist>");
				writer.close();
				
			});
			
			showpl.addActionListener(e->{
				JList plliste = new JList(pl);	// nouvelle JList pour afficher le contenue de la playlist en cours de creation
				panneau.add(plliste, BorderLayout.SOUTH);
				panneau.validate();
			});
			
			plplayer.addActionListener(e->{
				panneau.add(player);	// MP3Player de la librairie JACO
				panneau.validate();
			});
			
			reinitialiser.addActionListener(e->{
				pl.removeAllElements();	// pour refaire une liste pour la playlist
			});
			
			
		//CREATION DES BOUTTONS CHARGEMENT ET AFFICHAGE POUR PLAYLIST
			
			JButton charger = new JButton("Charger une PlayList"); 
			panneau.add(charger);
			
			JButton chemrpl = new JButton("Afficher La PlayList");
			panneau.add(chemrpl);
			
			charger.addActionListener(e->{
				
				// parcours par arborescence pour extraire les fichier xspf et les inserer dans une List<File>
				File folderpl = new File("C:\\Users\\huery\\Desktop");
				List<File> chargpl = new ArrayList<File>();
				for(File fpl : folderpl.listFiles()) { 		// pour tout les dossier ou fichier de folderpl on affecte cela a fpl pour chaque incrementation
					String texte = fpl.getName();
					if(texte.endsWith(".xspf")) {
						chargpl.add(fpl);
					}
				}
				
				JList lespl = new JList();
				Vector<String> lesplvec = new Vector<String>(); // creation d un Vector<String> similaire a une arraylist avec indice
				for(int j = 0; j<chargpl.size(); j++) {
					String tmplespl = String.valueOf(chargpl.get(j)); // affecte la chaine de caractere du File de la List<File> ce trouvant a l indice j et ajoute dans la nouvelle Vector<String>
					lesplvec.addElement(tmplespl);
				}
				
				JList listedespl = new JList(lesplvec);		// ajout du Vector dans une JList pour ajouter dans le JPanel
				panneau.add(listedespl, BorderLayout.SOUTH);
				panneau.validate();
				
				
				chemrpl.addActionListener(e2->{
					
					BufferedReader lecteurAvecBuffer = null;	// creation d un BufferedReader pour creer un lecteur de texte
					
					try {
						lecteurAvecBuffer = new BufferedReader(new FileReader(lesplvec.get(listedespl.getSelectedIndex()))); // affecte a ce lecteur de texte un fichier (ici le fichier playlist selectionner dans l interface)
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					
					String voirpl;
					Vector<String> listpl = new Vector<String>();
					try {
						while ((voirpl = lecteurAvecBuffer.readLine()) != null) {
						    listpl.addElement(voirpl);	// tant que la ligne contient de l ecriture alors l ajouter dans un Vector
						  }
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					try {
						lecteurAvecBuffer.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					JList listplvec = new JList(listpl); // pour afficher le lecteur de texte dans le panneau par une JList
					panneau.add(listplvec);
					panneau.validate();
					
				});
				
			});			
			
			
		//CREATION DU BOUTTON QUITTER
			JButton quitter = new JButton("Quitter");
			panneau.add(quitter);
			quitter.addActionListener(e->{
				cadre.dispose(); // pour quitter l'interface par le boutton quitter
			});
			
			
		//EXECUTION DU CADRE
			cadre.pack();
			panneau.validate();
			cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			cadre.setVisible(true);
	}

}
