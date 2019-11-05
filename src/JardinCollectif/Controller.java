package JardinCollectif;

import java.util.Date;
import JardinCollectif.controllers.*;

public class Controller {
	// Program logic. make new method for each possible command
	// and run the steps and verifications here
	// before calling the corresponding table method

	/*
	 * Membres
	 */
	public static void inscrireMembre(String prenom, String nom, String password, Integer noMembre) {
		MembreController.creer(prenom, nom, password, noMembre);
	}

	public static void supprimerMembre(Integer noMembre) {
		MembreController.supprimer(noMembre);
	}

	public static void afficherMembres() {
		MembreController.afficher();
	}

	// ADMIN members
	public static void promouvoirAdministrateur(Integer noMembre) {
		MembreController.promouvoirAdministrateur(noMembre);
	}

	/*
	 * Demandes
	 */
	public static void rejoindreLot(String nomLot, Integer noMembre) {
		DemandeController.creer(nomLot, noMembre);
	}
	
	public static void accepterDemande(String nomLot, Integer noMembre) {
		DemandeController.accepter(nomLot, noMembre);
	}

	public static void refuserDemande(String nomLot, Integer noMembre) {
		DemandeController.refuser(nomLot, noMembre);
	}

	/*
	 * Lots
	 */
	public static void ajouterLot(String nomLot, Integer maxCollabs) {
		LotController.creer(nomLot, maxCollabs);
	}

	public static void supprimerLot(String nomLot) {
		LotController.supprimer(nomLot);
	}

	public static void afficherLots() {
		LotController.afficher();
	}

	/*
	 * Plante
	 */
	public static void ajouterPlante(String nomPlante, Integer nbJours) {
		PlanteController.creer(nomPlante, nbJours);
	}

	public static void retirerPlante(String nomPlante) {
		PlanteController.supprimer(nomPlante);
	}

	public static void afficherPlantes() {
		PlanteController.afficher();
	}

	/*
	 * Culture
	 */
	public static void planterPlante(String nomPlante, String nomLot, Integer noMembre, Integer nbExemplaires,
			Date datePlantation) {
		
		CultureController.creer(nomPlante, nomLot, noMembre, nbExemplaires, datePlantation);
	}

	public static void recolterPlante(String nomPlante, String nomLot, Integer noMembre) {
		CultureController.supprimer(nomPlante, nomLot, noMembre);
	}

	public static void afficherPlantesLot(String nomLot) {
		CultureController.afficher(nomLot);
	}
}
