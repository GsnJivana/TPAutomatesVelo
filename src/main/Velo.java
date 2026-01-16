package main;

import java.io.InputStream;

import analyseurs.ActVelo;
import libIO.*;
import libGUI.*;

/**
 * La classe Velo correspond au programme principal d'un
 * analyseur syntaxique par automate d'états fini avec actions
 * mis en oeuvre par interpréteur de tables
 * 
 * @author Demange, Girard, Hardy, Masson, Perraudeau
 *         janvier 2022, 2026
 *
 */

public class Velo {

	/**
	 * Méthode main de l'application
	 * 
	 * @param args soit vide, soit contenant le nom de fichier à analyser
	 */
	public static void main(String[] args) {

		FenAffichage fenetre = new FenAffichage();

		// La donnée à analyser
		InputStream flot;
		if (args.length == 1) {
			flot = Lecture.ouvrir(args[0]);
		} else {
			String nomfich = Lecture.lireString("nom du fichier d'entrée : ");
			flot = Lecture.ouvrir(nomfich);
		}
		if (flot == null) {
			System.out.println("Erreur avec le flux du fichier d'entrée");
			System.exit(1);
		}

		// L'analyseur syntaxique
		ActVelo analyseur = new ActVelo(flot);
		// Fenêtre graphique
		analyseur.newObserver(fenetre, fenetre);
		// Lancement de l'analyse syntaxique
		analyseur.interpreteur();

		// Fermeture fichier d'entrée et fenêtre de trace d'exécution
		Lecture.fermer(flot);
		Lecture.attenteSurLecture("\nFin d'analyse. La fenêtre graphique est encore active");
		fenetre.fermer();

	}
}