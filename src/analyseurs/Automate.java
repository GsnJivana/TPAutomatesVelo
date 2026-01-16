package analyseurs;

import java.util.ArrayList;
import libIO.*;
import libGUI.*;

/**
 * La classe Automate modélise un automate fini abstrait d'analyse syntaxique
 * 
 * @author Demange, Girard, Hardy, Masson, Perraudeau
 *         janvier 2026
 */
public abstract class Automate {

	/** L'analyseur lexical utilisé **/
	private final Lex analyseurLexical;

	/** Etats spécifiques de l'automate syntaxique */
	private final int etatInitial;
	private final int etatFinal;
	private final int etatErreur;

	/** Etat courant de l'automate syntaxique */
	private int etatCourant;

	/** Types d'erreurs détectées */
	protected static final int FATALE = 0;
	protected static final int NONFATALE = 1;

	/**
	 * Drapeau pour arrêter l'interpreteur sur une erreur fatale avant la fin de la
	 * donnée
	 * - géré par getTransition si passage dans etatErreur provoque l'arrêt
	 * - geré par faireAction si certaines actions provoquent l'arrêt
	 */
	protected boolean errFatale;

	/**
	 * Constructeur
	 */
	protected Automate(Lex lex, int etatI, int etatF, int etatE) {
		this.analyseurLexical = lex;
		this.errFatale = false;
		// définition des états particuliers de l'analyse syntaxique
		this.etatInitial = etatI;
		this.etatFinal = etatF;
		this.etatErreur = etatE;
	}

	/** Accès à l'analyseur lexical sous-jacent */
	protected final Lex getAnalyseurLexical() {
		return this.analyseurLexical;
	}

	/**
	 * Initialisations nécessaires aux actions, parfois nécessaires avant d'exécuter
	 * la première action
	 */
	abstract void initAction();

	/** Calcul du prochain état */
	abstract int getTransition(int etat, int unite);

	/** Calcul de la prochaine action à exécuter */
	abstract int getAction(int etat, int unite);

	/** Exécution de la prochaine action */
	abstract void faireAction(int etat, int unite);

	/**
	 * Gestion des erreurs sémantiques
	 * 
	 * @param tErr    type de l'erreur (FATALE ou NONFATALE)
	 * @param messErr message associé à l'erreur
	 */
	protected final void erreur(int tErr, String messErr) {
		Lecture.attenteSurLecture(messErr);
		switch (tErr) {
			case FATALE:
				errFatale = true;
				break;
			case NONFATALE:
				etatCourant = etatErreur;
				break;
			default:
				Lecture.attenteSurLecture("paramètre incorrect pour Automate.erreur");
		}
	}

	/** Interpréteur mettant en oeuvre l'analyse syntaxique par automate fini */
	public final void interpreteur() {
		// initialisation état courant
		etatCourant = etatInitial;

		// initialisations nécessaires aux actions
		initAction();

		while (etatCourant != etatFinal && !errFatale) {
			int token = analyseurLexical.lireSymb();
			int etatDepart = etatCourant;
			int action = getAction(etatCourant, token);
			faireAction(etatCourant, token);
			etatCourant = getTransition(etatCourant, token);
			this.notifyObservers(etatDepart, token, etatCourant, action);
		}
	}

	/** Affichage sur la fenêtre graphique de la trace d'exécution */
	private final ArrayList<ObserverAutomate> lesObserveurs = new ArrayList<>();

	public final void newObserver(ObserverAutomate obs) {
		this.lesObserveurs.add(obs);
	}

	public final void notifyObservers(int etatDepart, int unite, int etatArrive, int action) {
		for (ObserverAutomate o : this.lesObserveurs) {
			o.notification(etatDepart, unite, etatArrive, action);
		}
	}

	public final void newObserver(ObserverAutomate oAuto, ObserverLexique oLex) {
		this.newObserver(oAuto);
		this.analyseurLexical.newObserver(oLex);
		analyseurLexical.notifyObservers(analyseurLexical.getCarLu());
	}
}