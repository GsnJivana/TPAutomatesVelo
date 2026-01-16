package main;

import libIO.Ecriture;

/**
 * Classe InfosClient définissant les informations conservées pour une location
 * non terminée
 * 
 * @author Demange, Girard, Grazon, Hardy, Masson
 *         juillet 2019, 2026
 */
public class InfosClient {

    /** Largeur des colonnes pour affichage à l'écran */
    private static final int LARGEUR_COLONNE = 10;

    /** Nom du client ayant loué des vélos */
    public final String nomClient;

    /**
     * Numéro du jour de la location
     * Heure de debut de la location (entre 8 et 19)
     * Nombres de vélos adultes et enfants loués
     */
    public final int jourEmprunt;
    public final int heureDebut;
    public final int qteAdulte;
    public final int qteEnfant;

    /** Constructeur */
    public InfosClient(String nom, int jour, int heurD, int qteA, int qteE) {
        this.nomClient = nom;
        this.jourEmprunt = jour;
        this.heureDebut = heurD;
        this.qteAdulte = qteA;
        this.qteEnfant = qteE;
    }

    /**
     * Affichage à l'écran en colonnes
     * 
     * @param uneChaine chaîne de longueur quelconque
     * @param taille    taille de la colonne d'affichage
     * @return une chaîne cadrée à gauche sur taille caractères
     */
    private String chaineCadrageGauche(String uneChaine, int taille) {
        int longueurChaine = Math.min(LARGEUR_COLONNE, uneChaine.length());
        String chaineCadree = uneChaine.substring(0, longueurChaine);
        for (int k = longueurChaine; k < taille; k++) {
            chaineCadree = chaineCadree + " ";
        }
        return chaineCadree;
    }

    /**
     * Affichage des informations client pour une location en cours
     */
    public void afficheClient() {
        Ecriture.ecrireString(chaineCadrageGauche(this.nomClient, LARGEUR_COLONNE));
        Ecriture.ecrireInt(this.jourEmprunt, LARGEUR_COLONNE);
        Ecriture.ecrireInt(this.heureDebut, LARGEUR_COLONNE);
        Ecriture.ecrireInt(this.qteAdulte, LARGEUR_COLONNE);
        Ecriture.ecrireInt(this.qteEnfant, LARGEUR_COLONNE);
        Ecriture.ecrireStringln();
    }

}