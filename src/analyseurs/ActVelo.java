package analyseurs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import libIO.*;

import main.InfosClient;
import main.BaseDeLoc;

/**
 * La classe ActVelo met en oeuvre les actions de l'automate d'analyse
 * syntaxique des locations de vélos
 * 
 * @author
 *         janvier 2026
 *         NGO Hoang Duc Huy
 *         GNANSOUNOU Jivana
 *         PECCI Noé
 */

public class ActVelo extends AutoVelo {
	// TODO à compléter

	// Raccourci d'accès à l'analyseur lexical
	private final LexVelo analyseurLexical = (LexVelo) this.getAnalyseurLexical();

	/** Table des actions */
	// TODO compléter la table ACTION
	private final int[][] ACTION = {
			// Etat ADULTE DEBUT ENFANT FIN HEURES IDENT NBENTIER VIRG PTVIRG BARRE AUTRES
			/* 0 */ { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			/* 1 */ { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			/* 2 */ { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			/* ... {...} */
	};

	/** Nombre de vélos initialement disponibles */
	private static final int MAX_VELOS_ADULTES = 50;
	private static final int MAX_VELOS_ENFANT = 20;

	/** Ensemble des locations en cours (non terminées) */
	private final BaseDeLoc maBaseDeLoc;

	/** Ensemble des clients différents vus, pour chaque jour */
	private final ArrayList<Set<Integer>> clientsParJour;

	// Rappel: chaque validation correspond à un jour différent
	// jourCourant correspond à la validation en cours d'analyse
	private int jourCourant;

	// Rappel: chaque validation est composée de plusieurs opérations
	// nbOperationTotales comptabilise toutes les opérations contenues dans la
	// donnée à analyser, erronées ou non
	private int nbOperationTotales;

	// nbOperationCorrectes comptabilise toutes les opérations sans erreur
	// contenues dans la donnée à analyser
	private int nbOperationCorrectes;

	// TODO compléter la déclaration des variables nécessaires aux actions

	/**
	 * Constructeur classe ActVelo
	 * 
	 * @param flot donnée à analyser
	 */
	public ActVelo(InputStream flot) {
		super(flot);
		clientsParJour = new ArrayList<>();
		maBaseDeLoc = new BaseDeLoc();
	}

	/**
	 * Définition de la méthode abstraite getAction de Automate
	 * 
	 * @param etat  code de l'état courant
	 * @param unite code de l'unité lexicale courante
	 * @return code de l'action à exécuter
	 **/
	@Override
	public final int getAction(int etat, int unite) {
		return ACTION[etat][unite];
	}

	/**
	 * Définition de la méthode abstraite initAction de Automate
	 */
	@Override
	public final void initAction() {

		// initialisations à effectuer avant les actions
		nbOperationCorrectes = 0;
		nbOperationTotales = 0;
		jourCourant = 1;

		// initialisation des clients du premier jour
		// NB: le jour 0 n'est pas utilisé
		clientsParJour.add(0, new HashSet<>());
		clientsParJour.add(1, new HashSet<>());

		// TODO compléter l'initialisation des variables nécessaires aux actions

	}

	/**
	 * Définition de la méthode abstraite faireAction de Automate
	 * 
	 * @param etat  code de l'état courant
	 * @param unite code de l'unité lexicale courante
	 **/
	@Override
	public final void faireAction(int etat, int unite) {
		executer(ACTION[etat][unite]);
	}

	/**
	 * Exécution d'une action
	 * 
	 * @param numAction numéro de l'action à exécuter
	 */
	private final void executer(int numAction) {

		switch (numAction) {
			case -1: // action vide
				break;

			// TODO compléter les actions

			default:
				Lecture.attenteSurLecture("action " + numAction + " non prévue");
		}
	}

	/**
	 * Calcul de la durée d'une location
	 *
	 * @param jourDebutLoc  numéro du jour de début de la location à partir de 1
	 * @param heureDebutLoc heure du début de la location, entre 8 et 19
	 * @param jourFinLoc    numéro du jour de la fin de la location à partir de 1
	 * @param heureFinLoc   heure de fin de la location, entre 8 et 19
	 * @return nombre d'heures comptabilisées pour la location
	 *         (les heures de nuit entre 19h et 8h ne sont pas comptabilisées)
	 */
	private final int calculDureeLoc(int jourDebutLoc, int heureDebutLoc, int jourFinLoc, int heureFinLoc) {
		int duree = 0;
		if (jourDebutLoc == jourFinLoc) { // vélos rendus le jour de l'emprunt
			if (heureFinLoc != heureDebutLoc) {
				duree = heureFinLoc - heureDebutLoc;
			} else {
				duree = 1;
			}
		} else { // vélos rendus après le jour d'emprunt (duree négative interdite)
			duree = 19 - heureDebutLoc; // duree du premier jour
			duree = duree + (heureFinLoc - 8); // ajout de la duree du dernier jour
			if (jourFinLoc > jourDebutLoc + 1) { // plus 11h ouvrées par jour intermediaire
				duree = duree + 11 * (jourFinLoc - jourDebutLoc - 1);
			}
		}
		return duree;
	}
}
