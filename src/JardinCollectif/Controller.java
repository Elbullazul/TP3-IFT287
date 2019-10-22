package JardinCollectif;

import java.sql.Connection;

import JardinCollectif.tables.TableMembre;
import java.util.ArrayList;

public class Controller {
	private static final String ArrayList = null;

	// Program logic. make new method for each possible command
	// and run the steps and verifications here
	// before calling the corresponding table method

	public static void print(String chain) {
		System.out.println(chain);
	}
	
	public static void print(Integer num) {
		System.out.println(num.toString());
	}
	
	public static void print(Boolean val) {
		System.out.println(val.toString());
	}

	private static void showError() {
		newLine();
		print("Database error. Try again");
	}
	
	public static void newLine() {
		print("");
	}
	
	/*
	 * Membres
	 */
	public static void inscrireMembre(String prenom, String nom, String password, Integer noMembre) {
		TableMembre tbl_membre = new TableMembre(prenom, nom, password, noMembre);

		if (tbl_membre.insert())
			print("\nMembre " + noMembre + " ajouté");
		else
			showError();
	}
	
	public static void supprimerMembre(Integer noMembre) {
		TableMembre tbl_membre = new TableMembre(noMembre);
		
		if (tbl_membre.destroy())
			print("Membre " + noMembre + " détruit");
		else
			showError();
	}

	public static void afficherMembres() {
		ArrayList<TableMembre> tl = TableMembre.fetchAll();
		
		newLine();
		for (TableMembre tp : tl) {
			print(tp.toString());
		}
	}

	// ADMIN members
	public static void promouvoirAdministrateur(Integer noMembre) {
		// TODO: build placeholder object and pass to TableMembre.promote(args[])
	}

	public static void accepterDemande(String nomLot, Integer noMembre) {
		// TODO: update and save fetched object
	}

	public static void refuserDemande(String nomLot, Integer noMembre) {
		// TODO: update and save fetched object
		// REMIDER: select request with no status
	}

	/*
	 * Lots
	 */
	public static void ajouterLot(String nomLot, Integer maxCollabs) {
		// TODO: créer l'objet puis le sauvegarder
	}

	public static void rejoindreLot(String nomLot, Integer noMembre) {
		// TODO: update lot in tbl_attributions
	}

	public static void supprimerLot(String nomLot) {
		// TODO: sipprimer lot in tbl_attributes
	}

	public static void afficherLots() {
		// TODO: select * from tbl_lots
	}

	/*
	 * Culture
	 */
	public static void ajouterPlante(String nomPlante, Integer nbJours) {
		// TODO: create and save object
	}

	public static void planterPlante(String nomPlante, String nomLot, Integer noMembre, Integer nbExemplaires) {
		// TODO: Create tuple and sve it to db
	}

	public static void recolterPlante(String nomPlante, String nomLot, Integer noMembre) {
		// TODO: Remove plant instnces in tbl_culture
	}

	public static void afficherPlantesLot(String nomLot) {
		// TODO: Select * from tbl_culture where nomlot = nomlot
	}

	/*
	 * Plante
	 */
	public static void retirerPlante(String nomPlante) {
		// TODO: remove from tbl_plantes if not found in tbl_cultures
	}

	public static void afficherPlantes() {
		// TODO: select * from tbl_plantes
	}
}
