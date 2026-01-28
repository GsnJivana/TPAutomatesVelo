package analyseurs;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//import libIO.*;

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
			/* 0 */ { 12,   12,   12,   12,   12,     1,    12,     12,     12,     11,    12 },
			/* 1 */ { 12,    8,   12,    9,   12,     12,    2,     12,     12,     12,    12 },
			/* 2 */ { 12,   12,   12,   12,   12,     12,   12,     12,     12,     12,    12 },
			/* 3 */ { 12,    3,   12,    4,   12,     12,   12,     12,     12,     12,    12 },
			/* 4 */ { 12,   12,   12,   12,   12,     12,    2,     12,     12,     12,    12 },
			/* 5 */ { 12,   12,   12,   12,   12,     12,   12,      7,     10,     12,    12 },
			/* 6 */ {  5,   12,    6,   12,   12,     12,   12,      7,     12,     12,    12 },
			/* 7 */ { 12,   12,   12,   12,   12,     12,    2,     12,     10,     12,    12 },
			/* 8 */ { 12,   12,    6,   12,   12,     12,   12,     12,     12,     12,    12 },
			/* 9 */ { 12,   12,   12,   12,   12,     12,   12,      7,     10,     12,    12 },
			/* 10*/ { 12,   12,   12,   12,   12,      1,   12,     12,     12,     12,   12 },
			/* 12*/ { 12,   12,   12,   12,   12,     12,   12,     12,     12,     12,    12 },
			// Action 12 associée aux transitions vers l’état erreur 
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
	private String nomC;
	private int heureD, heureF, qteA, qteE, tmp, numIdCourant;
	private int nbVelosAdultesLoues = 0;
    private int nbVelosEnfantsLoues = 0;





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

		debutOperation();
		 
	}

	private void debutOperation() {
       heureD=-1;
	   heureF=-1;
	   qteA=0;
	   qteE=0; 
	   tmp=0;
	   numIdCourant = -1;
	   // ou 0 si on veut
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
			case 0: // action réservée à l’initialisation des variables
				debutOperation();
				break;

			case 1:
				numIdCourant= analyseurLexical.getnumIdCourant();
				nomC=analyseurLexical.chaineIdent(numIdCourant);
				break;
			case 2:
				tmp = analyseurLexical.getvalEnt();
				break;
			case 3:
				heureD=tmp;
				break;
			case 4:
				heureF=tmp;
				break;
			case 5:
				qteA=tmp;
				break;
			case 6:
				qteE=tmp;
				break;
			case 7:
				finOperation();
				break;
			case 8:
				heureD=8;
				break;
			case 9:
				heureF=19;
				break;
			case 10:
				finOperation();
				finJournee();
				break;
			case 11:
				afficherBilanFinal();
				break;
			default:
				//Lecture.attenteSurLecture("action" + numAction + " non prévue");
				break;
		}
	}

	private void finOperation(){
		nbOperationTotales++;
		if(heureD!=-1){
			debutLocation();
		}else {
			finLocation();
		}
		debutOperation();
	}

	private void finJournee(){
		afficherBilanJour();
        jourCourant++;
		clientsParJour.add(new HashSet<>());
	}
	
	private void debutLocation() {
        if (maBaseDeLoc.getInfosClient(nomC) != null) {
            System.out.println(nomC + " a déjà une location en cours");
            return;
        }
        
        maBaseDeLoc.enregistrerLoc(nomC, jourCourant, heureD, qteA, qteE);
        clientsParJour.get(jourCourant).add(numIdCourant);
		nbVelosAdultesLoues += qteA;
        nbVelosEnfantsLoues += qteE;
    }


	 private void finLocation() {
        InfosClient infos = maBaseDeLoc.getInfosClient(nomC);
        
        if (infos == null) {
            System.out.println(nomC + " n'a pas de location en cours");
            return;
        }
        
        
        int duree = calculDureeLoc(infos.jourEmprunt, infos.heureDebut, 
                                   jourCourant, heureF);
        int prix = duree * (4 * infos.qteAdulte + 2 * infos.qteEnfant);
        
        System.out.println("Le client:  " + nomC.toUpperCase() + 
                      "  doit payer : " + prix + 
                      " euros pour " + infos.qteAdulte + 
                      " vélo(s) adulte et " + infos.qteEnfant + 
                      " vélo(s) enfant ");
        
        maBaseDeLoc.supprimerClient(nomC);
		nbVelosAdultesLoues -= infos.qteAdulte;
        nbVelosEnfantsLoues -= infos.qteEnfant;
    }
    
	private void afficherBilanJour() {
        System.out.println("****************************");
        System.out.println("BILAN DU JOUR  " + jourCourant);
        System.out.println();
        maBaseDeLoc.afficherLocationsEnCours();
    }

	private void afficherBilanFinal() {
        System.out.println("***** BILAN FINAL *****");
        
        int adultesLoues = compterVelosAdultesLoues();
        int enfantsLoues = compterVelosEnfantsLoues();
        
        System.out.println("Nombre de vélos adulte manquants :  " + (MAX_VELOS_ADULTES - adultesLoues));
        System.out.println("Nombre de vélos enfant manquants :  " + (MAX_VELOS_ENFANT - enfantsLoues));
        System.out.println("Opérations correctes : " + nbOperationCorrectes + 
                          " - Nombre total d'opérations : " + nbOperationTotales);
        System.out.println();
        
        System.out.println("Voici les clients qui doivent encore rendre des vélos");
        System.out.println();
        maBaseDeLoc.afficherLocationsEnCours();
        
        System.out.println("******* BILAN AFFLUENCE ********");
        int maxClients = 0;
        int jourMax = 0;
        for (int i = 1; i < clientsParJour.size(); i++) {
            int nbClients = clientsParJour.get(i).size();
            if (nbClients > maxClients) {
                maxClients = nbClients;
                jourMax = i;
            }
        }
        System.out.println("Le jour de plus grande affluence est : " + jourMax + 
                          "  avec " + maxClients + " clients servis");
    }
	
	private int compterVelosAdultesLoues() {
        return nbVelosAdultesLoues;
      
    }

    private int compterVelosEnfantsLoues() {
        return nbVelosEnfantsLoues;
       
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
