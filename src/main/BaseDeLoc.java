package main;

import java.util.ArrayList;

import libIO.*;

/**
 * Classe BaseDeLoc représentant les locations non terminées
 * 
 * @author Demange, Girard, Grazon, Hardy, Masson
 *         juillet 2019, janvier 2026
 */

// Exemple de Base de Loc en fin de traitement (18 vélos non rendus)
// CLIENT JOUR HEURE_DEBUT ADULTE ENFANT
// --------- -- -- ---
// GIRARD 1 8 3 1
// GRAZON 3 15 8 0
// MASSON 3 15 6 0

public class BaseDeLoc {

	/** Tableau de toutes les locations en cours */
	private final ArrayList<InfosClient> tableFiches;

	/** Constructeur classe BasedeLoc */
	public BaseDeLoc() {
		this.tableFiches = new ArrayList<>();
	}

	/**
	 * Enregistre une nouvelle location en cours
	 * 
	 * @precondition le client n'est pas déjà présent dans la base de location
	 * 
	 * @param nomC  nom du client commençant une location
	 * @param jour  jour de début de la nouvelle location
	 * @param heurD heure de début de la nouvelle location
	 * @param qteA  quantité de vélos adulte loués pour cette nouvelle location
	 * @param qteE  quantité de vélos enfant loués pour cette nouvelle location
	 * 
	 */
	public void enregistrerLoc(String nomC, int jour, int heurD, int qteA, int qteE) {
		InfosClient nouveauClient = new InfosClient(nomC, jour, heurD, qteA, qteE);
		tableFiches.add(nouveauClient);
	}

	/**
	 * Accès aux informations de la location en cours pour un client
	 * 
	 * @param nomClient nom du client dont on cherche les informations
	 * @return les informations de la location en cours pour le client nomClient
	 *         ou null si client inexistant
	 */
	public InfosClient getInfosClient(String nomClient) {
		for (InfosClient client : tableFiches) {
			if (client.nomClient.equals(nomClient)) {
				return client;
			}
		}
		return null;
	}

	/**
	 * Supprime une location en cours
	 * 
	 * @precondition le client est présent dans BaseDeLoc
	 *
	 * @param nomClient nom du client dont on veut supprimer la location
	 */
	public void supprimerClient(String nomClient) {
		InfosClient clientRecherche = null;
		int i = 0;
		while (i < tableFiches.size() && clientRecherche == null) {
			if (tableFiches.get(i).nomClient.equals(nomClient)) {
				clientRecherche = tableFiches.get(i);
			} else {
				i++;
			}
		}
		if (clientRecherche != null) {
			tableFiches.remove(clientRecherche);
		}
	}

	/** Affichage de toutes les locations en cours */
	public void afficherLocationsEnCours() {
		Ecriture.ecrireStringln();
		Ecriture.ecrireStringln("      CLIENT      JOUR    HEURE_DEBUT  ADULTE    ENFANT\n"
				+ "---------------    ----      ---       ---       ---     ");
		for (InfosClient client : tableFiches) {
			client.afficheClient();
		}
	}
}