package analyseurs;

import java.io.InputStream;
import java.util.ArrayList;
import libGUI.*;
import libIO.Lecture;

/**
 * La classe Lex modélise un analyseur lexical abstrait
 * 
 * @author Demange, Girard, Hardy, Masson, Perraudeau
 *         janvier 2026
 */
public abstract class Lex {

	/** Flot d'entrée à analyser */
	private InputStream flot;

	/** Dernier caractère lu */
	private char carLu;

	/**
	 * Constructeur
	 * 
	 * @param flot donnée d'entrée à analyser
	 */
	protected Lex(InputStream flot) {
		this.flot = flot;
	}

	/** Accès au dernier caractère lu */
	protected final char getCarLu() {
		return carLu;
	}

	/**
	 * Lecture du nouveau caractère courant dans carLu
	 * à partir du flot d'entrée
	 */
	protected final void lireCarLu() {
		carLu = Lecture.lireChar(this.flot);
		this.notifyObservers(carLu);
		// normalisation des caractères blancs en espaces
		// et des lettres en majuscules
		if (Character.isWhitespace(carLu)) {
			carLu = ' ';
		} else {
			carLu = Character.toUpperCase(carLu);
		}
	}

	/** Accès au prochain item lexical */
	public abstract int lireSymb();

	/**
	 * Gestion de la fenêtre de trace d'exécution.
	 * Cette partie n'est pas directement liée au lexer et ne doit pas être modifiée
	 */
	private final ArrayList<ObserverLexique> lesObserveurs = new ArrayList<>();

	public final void newObserver(ObserverLexique obs) {
		this.lesObserveurs.add(obs);
	}

	public final void notifyObservers(char c) {
		for (ObserverLexique o : this.lesObserveurs) {
			o.nouveauChar(c);
		}
	}

}
