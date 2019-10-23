package JardinCollectif;

import java.sql.Connection;

import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableLot;
import JardinCollectif.tables.TableMembre;
import JardinCollectif.tables.TablePlante;

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
		TableMembre tbl_membre = new TableMembre(noMembre);

		// vérifier si l'utilisateur existe
		if (tbl_membre.fetch()) {
			tbl_membre.setIsAdmin(true);

			if (tbl_membre.update())
				print("Membre " + noMembre.toString() + " promu au rôle d'administrateur");
			else
				showError();

		} else {
			print("Le membre " + noMembre.toString() + " n'existe pas");
		}
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
		TableLot tbl_lot = new TableLot(nomLot, maxCollabs);

		if (tbl_lot.insert()) {
			newLine();
			print("Le lot " + nomLot + " ajouté");
		} else
			showError();
	}

	public static void rejoindreLot(String nomLot, Integer noMembre) {
		TableLot tbl_lot = new TableLot(nomLot);
		TableMembre tbl_membre = new TableMembre(noMembre);
		TableAttribution tbl_attribution = new TableAttribution(nomLot, noMembre);

		newLine();
		
		if (tbl_lot.fetch()) {
			if (tbl_membre.fetch()) {
				tbl_attribution.insert();
			} else
				print("Le membre " + noMembre + " n'existe pas");
		} else
			print("Le lot " + nomLot + " n'existe pas");
	}

	public static void supprimerLot(String nomLot) {
		TableLot tbl_lot = new TableLot(nomLot);

		// TODO: vérifier qu'il n'y a personne ni aucune plante sur le lot
		if (tbl_lot.destroy()) {
			newLine();
			print("Le lot " + nomLot + " a été détruit");
		} else
			showError();
	}

	public static void afficherLots() {
		ArrayList<TableLot> tl = TableLot.fetchAll();

		newLine();
		for (TableLot tp : tl) {
			print(tp.toString());
		}
	}

	/*
	 * Plante
	 */
	public static void ajouterPlante(String nomPlante, Integer nbJours) {
		TablePlante tbl_plante = new TablePlante(nomPlante, nbJours);

		if (tbl_plante.insert()) {
			newLine();
			print("Plante " + nomPlante + " ajoutée");
		} else
			showError();
	}

	public static void retirerPlante(String nomPlante) {
		TablePlante tbl_plante = new TablePlante(nomPlante);

		// vérifier qu'il n'en a pas de plantée
		
		if (tbl_plante.destroy()) {
			newLine();
			print("Plante " + nomPlante + " supprimée");
		} else
			showError();
	}

	public static void afficherPlantes() {
		ArrayList<TablePlante> tl = TablePlante.fetchAll();

		newLine();
		for (TablePlante tp : tl) {
			print(tp.toString());
		}
	}

	/*
	 * Culture
	 */
	public static void planterPlante(String nomPlante, String nomLot, Integer noMembre, Integer nbExemplaires) {
		// TODO: Create tuple and save it to db
	}

	public static void recolterPlante(String nomPlante, String nomLot, Integer noMembre) {
		// TODO: Remove plant instances in tbl_culture
	}

	public static void afficherPlantesLot(String nomLot) {
		// TODO: Select * from tbl_culture where nomlot = nomlot
	}
}
