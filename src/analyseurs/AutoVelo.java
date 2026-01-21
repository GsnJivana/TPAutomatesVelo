package analyseurs;

import java.io.*;

/**
 * La classe AutoVelo met en oeuvre l'automate d'analyse syntaxique des
 * locations de vélos, par interpreteur de tables
 * 
 * @author
 *         janvier 2026
 *         NGO Hoang Duc Huy
 *         GNANSOUNOU Jivana
 *         PECCI Noé
 */

public abstract class AutoVelo extends Automate {
	// TODO à compléter
	// RAPPEL: reprise après erreur demandée sur les items VIRG, PTVIRG et BARRE

	/** Table des transitions */
	// TODO compléter la table TRANSIT, avec la numérotation standard des états
	private static final int[][] TRANSIT = {
			// Etat ADULTE DEBUT ENFANT FIN HEURES IDENT NBENTIER VIRG PTVIRG BARRE AUTRES
			/* 0 */ { 11, 11, 11, 11, 11, 1, 11, 11, 11, 12, 11 },
			/* 1 */ { 11, 4, 11, 5, 11, 11, 2, 11, 11, 11, 11 },
			/* 2 */ { 11, 11, 11, 11, 3, 11, 11, 11, 11, 11, 11 },
			/* 3 */ { 11, 4, 11, 5, 11, 11, 11, 11, 11, 11, 11 },
			/* 4 */ { 11, 11, 11, 11, 11, 11, 6, 11, 11, 11, 11 },
			/* 5 */ { 11, 11, 11, 11, 11, 11, 11, 10, 0, 11, 11 },
			/* 6 */ { 7, 11, 9, 11, 11, 11, 11, 11, 11, 11, 11 },
			/* 7 */ { 11, 11, 11, 11, 11, 11, 8, 10, 0, 11, 11 },
			/* 8 */ { 11, 11, 9, 11, 11, 11, 11, 11, 11, 11, 11 },
			/* 9 */ { 11, 11, 11, 11, 11, 11, 11, 10, 0, 11, 11 },
			/* 10 */ { 11, 11, 11, 11, 11, 1, 11, 11, 11, 11, 11 },
			/* 11 */ { 11, 11, 11, 11, 11, 11, 11, 10, 0, 12, 11 },// ETAT D'ERREUR
	};

	/**
	 * Constructeur classe AutoVelo
	 * 
	 * @param flot donnée à analyser
	 */
	protected AutoVelo(InputStream flot) {
		// on utilise ici un analyseur lexical de type LexVelo
		// on numérote les états initial, final, et d'erreur selon la convention
		super(new LexVelo(flot), 0, TRANSIT.length, TRANSIT.length - 1);
	}

	/**
	 * Définition de la méthode abstraite getTransition de Automate
	 * 
	 * @param etat  code de l'état courant
	 * @param unite code de l'unité lexicale courante
	 * @return code de l'état suivant
	 **/
	@Override
	public final int getTransition(int etat, int unite) {
		return TRANSIT[etat][unite];
	}
}