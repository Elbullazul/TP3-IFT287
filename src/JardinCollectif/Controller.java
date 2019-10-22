package JardinCollectif;

public class Controller {
	// Program logic. make new method for each possible command
	// and run the steps and verifications here
	// before calling the corresponding table method

	/*
	 * Membres
	 */
	public void inscrireMembre(String prenom, String nom, String password, Integer noMembre) {
		// TODO: build object and save it
	}
	
	public void supprimerMembre(Integer noMembre) {
		// TODO: fetch object and destroy it
	}
	
	public void afficherMembres() {
		// TODO: run table query (select all)
	}

	// ADMIN members
	public void promouvoirAdministrateur(Integer noMembre) {
		// TODO: build placeholder object and pass to TableMembre.promote(args[])
	}
	
	public void accepterDemande(String nomLot, Integer noMembre) {
		// TODO: update and save fetched object
	}
	
	public void refuserDemande(String nomLot, Integer noMembre) {
		// TODO: update and save fetched object
		// REMIDER: select request with no status
	}
	
	/*
	 * Lots
	 */
	public void ajouterLot(String nomLot, Integer maxCollabs) {
		// TODO: créer l'objet puis le sauvegarder
	}
	
	public void rejoindreLot(String nomLot, Integer noMembre) {
		// TODO: update lot in tbl_attributions
	}
	
	public void supprimerLot(String nomLot) {
		// TODO: sipprimer lot in tbl_attributes 
	}
	
	public void afficherLots() {
		// TODO: select * from tbl_lots
	}
	
	/*
	 * Culture
	 */
	public void ajouterPlante(String nomPlante, Integer nbJours) {
		// TODO: create and save object
	}
	
	public void planterPlante(String nomPlante, String nomLot, Integer noMembre, Integer nbExemplaires) {
		// TODO: Create tuple and sve it to db
	}
	
	public void recolterPlante(String nomPlante, String nomLot, Integer noMembre) {
		// TODO: Remove plant instnces in tbl_culture
	}
	
	public void afficherPlantesLot(String nomLot) {
		// TODO: Select * from tbl_culture where nomlot = nomlot
	}
	
	/*
	 * Plante
	 */
	public void retirerPlante(String nomPlante) {
		// TODO: remove from tbl_plantes if not found in tbl_cultures
	}
	
	public void afficherPlantes() {
		// TODO: select * from tbl_plantes
	}
}
