// NGO Hoang Duc Huy 
// GNANSOUNOU Jivana
// PECCI Noé

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

		// On ignore les espaces et assimilés.
		while (getCarLu() == ' ') {
			lireCarLu();
		}

		// On détecte le début de l'item lexical IDENT
		if ((getCarLu() >= '0') && (getCarLu() <= '9')) {
			return lireEnt();
		}

		// On détecte le début de l'item lexical IDENT
		if ((getCarLu() >= 'a') && (getCarLu() <= 'z') ||
				(getCarLu() >= 'A') && (getCarLu() <= 'Z')) {
			return lireIdent();
		}

		// On détecte un autre item lexical
		switch (getCarLu()) {
			case ',':
				lireCarLu();
				return VIRG;
			case ';':
				lireCarLu();
				return PTVIRG;
			case '/':
				return BARRE;
			default:
				System.out.println("Caractère incorrect");
				lireCarLu();
				return AUTRES;
		}
	}

	/**
	 * Accès à la chaîne d'un identificateur non réservé
	 * 
	 * @param numIdent numéro d'un ident dans la table des idents
	 * @return chaîne correspondant à numIdent
	 */
	public final String chaineIdent(int numIdent) {
		// On vérifie que numIdent est bien un identificateur
		if (numIdent >= NBRES && numIdent < tabIdent.size()) {
			return tabIdent.get(numIdent);
		}
		return "";
	}

	/**
	 * Lecture d'un item NBENTIER
	 * et mise à jour de l'attribut lexical valEnt.
	 * 
	 * @return code NBENTIER
	 */
	private int lireEnt() {
		String s = "";
		do {
			s = s + getCarLu();
			lireCarLu();
		} while ((getCarLu() >= '0') && (getCarLu() <= '9'));
		valEnt = Integer.parseInt(s);
		return NBENTIER;
	}

	private int lireIdent() {
		// Lecture du mot (suite de lettres)
		String s = "";
		do {
			s = s + getCarLu();
			lireCarLu();
		} while ((getCarLu() >= 'a') && (getCarLu() <= 'z') ||
				(getCarLu() >= 'A') && (getCarLu() <= 'Z'));

		// Vérification si le mot est un mot réservé
		for (int i = 0; i < NBRES; i++) {
			if (s.equals(images[i])) {
				return i; // Retourne le code du mot réservé
			}
		}

		// Si ce n'est pas un mot réservé, c'est un identificateur
		// On vérifie si l'identificateur existe déjà dans la table
		for (int i = NBRES; i < tabIdent.size(); i++) {
			if (s.equals(tabIdent.get(i))) {
				numIdCourant = i;
				return IDENT;
			}
		}

		// C'est un nouvel identificateur donc on l'ajoute à la table
		tabIdent.add(s);
		numIdCourant = tabIdent.size() - 1;
		return IDENT;
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
