YESIL Husamettin

Language de Programmation : JAVA

Logiciel de programmation : 		https://www.eclipse.org/downloads/packages/release/oxygen/3a/eclipse-ide-java-developers
Logiciel de creation de diagramme :	http://dia-installer.de/index.html.fr


Librairies externes : 	https://github.com/mpatric/mp3agic
			http://jacomp3player.sourceforge.net


Java SE Development Kit 8 : 	https://www.oracle.com/fr/java/technologies/javase/javase-jdk8-downloads.html


Pour lancer le programme :
	- Copier l'ensemble des fichiers .jar (cli.jar et gui.jar) dans votre Bureau (C:\Users\username\Desktop\).
	- Creer un repertoire (avec sous repertoire si vous le voulez) contenant des fichiers .mp3 dans votre Bureau.
	- Ouvrir l'invite de commande et ce placer dans le Bureau (C:\Users\username\Desktop>).
	- Entrer : 
		java -jar cli.jar				// Pour executer le mode console du programme.
		java -jar cli.jar -h				// Pour avoir de l'aide.
		java -jar cli.jar -d .				// Pour extraire les metadata des fichiers .mp3 contenue dans un repertoire.
		java -jar cli.jar -f music.mp3			// Pour extraire les metadata d'un fichiers .mp3 dans le repertoire courant.
		java -jar cli.jar -d ./music/ -o playlist.xspf	// Pour creer une playlist au format xspf avec des fichiers .mp3 contenue dans un repertoire (ici music).
		java -jar gui.jar				// Pour executer l'interface graphique.
	
Information :
	/metadata/ :
		doc/ : La javadoc, ouvrire le fichier index.html
		library/ : Les librairies utiliser pour implementer les class
		src/projetjava/ : Les class implementer pour le fonctionnement du programme.
		cli.jar : Le mode console
		gui.jar : L'interface graphique
