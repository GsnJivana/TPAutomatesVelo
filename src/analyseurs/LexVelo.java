package analyseurs;

import libIO.*;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * La classe LexVelo implémente un analyseur lexical pour une application de
 * location de velos
 * 
 * @author // TODO compléter les noms du trinôme
 *         janvier 2026
 */

public class LexVelo extends Lex {
	// TODO à compléter

	/** Nombre de mots réservés dans l'application Velo */
	public static final int NBRES = 5;

	/** Code des items lexicaux */
	protected static final int ADULTE = 0, DEBUT = 1, ENFANT = 2, FIN = 3, HEURES = 4;
	protected static final int IDENT = 5, NBENTIER = 6;
	protected static final int VIRG = 7, PTVIRG = 8, BARRE = 9, AUTRES = 10;

	/** Affichage souhaité des items lexicaux dans une fenêtre graphique */
	public static final String[] images = {
			"ADULTE", "DEBUT", "ENFANT", "FIN",
			"HEURES", "IDENT", "NBENT",
			"VIRG", "PTVIRG", "BARRE", "AUTRE" };

	/** Attributs des items lexicaux */
	private int valEnt;
	private int numIdCourant;

	/** Table des mots réservés et identifiants */
	private final ArrayList<String> tabIdent;

	/**
	 * Constructeur classe LexVelo
	 * 
	 * @param flot donnée d'entrée à analyser
	 */
	public LexVelo(InputStream flot) {
		super(flot);

		// Initialisation de tabIdent avec les mots réservés
		this.tabIdent = new ArrayList<>();
		for (int i = 0; i < NBRES; i++) {
			this.tabIdent.add(i, images[i]);
		}

		// Prélecture du premier caractère de la donnée
		lireCarLu();
	}

	/**
	 * @return la valeur de l'attribut de l'item NBENTIER
	 */
	public final int getvalEnt() {
		return valEnt;
	}

	/**
	 * @return la valeur de l'attribut de l'item IDENT
	 */
	public final int getnumIdCourant() {
		return numIdCourant;
	}

	/**
	 * Lecture du prochain item lexical, et mise à jour des attributs lexicaux
	 * correspondants.
	 * 
	 * @return code de l'item lexical reconnu
	 */
	@Override
	public final int lireSymb() {
		// TODO
		throw new UnsupportedOperationException("méthode lireSymb à implémenter");
	}

	/**
	 * Accès à la chaîne d'un identificateur non réservé
	 * 
	 * @param numIdent numéro d'un ident dans la table des idents
	 * @return chaîne correspondant à numIdent
	 */
	public final String chaineIdent(int numIdent) {
		// TODO
		throw new UnsupportedOperationException("méthode chaineIdent à imlémenter");
	}

	/**
	 * Main pour tester l'analyseur lexical seul, sans analyse syntaxique
	 */
	public static void main(String[] args) {

		InputStream flot;
		if (args.length == 1) {
			flot = Lecture.ouvrir(args[0]);
		} else {
			String nomfich = Lecture.lireString("nom du fichier d'entrée : ");
			flot = Lecture.ouvrir(nomfich);
		}
		if (flot == null) {
			Lecture.attenteSurLecture("Erreur avec le flux du fichier d'entrée");
			System.exit(0);
		}

		LexVelo testVelo = new LexVelo(flot);

		int token; // unité lexicale courante
		do {
			token = testVelo.lireSymb();
			switch (token) {
				case NBENTIER:
					Lecture.attenteSurLecture("token : " + images[token] +
							" attribut valEnt = " + testVelo.valEnt);
					break;
				case IDENT:
					Lecture.attenteSurLecture("token : " + images[token]
							+ " attribut numIdCourant = " + testVelo.numIdCourant
							+ " chaine associee = " + testVelo.chaineIdent(testVelo.numIdCourant));
					break;
				default:
					Lecture.attenteSurLecture("token : " + images[token]);
			}
		} while (token != BARRE);

		Lecture.fermer(flot);
		Lecture.attenteSurLecture("fin d'analyse");
	}
}
