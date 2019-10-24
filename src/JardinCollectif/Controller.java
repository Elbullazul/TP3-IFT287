package JardinCollectif;

import java.sql.Connection;

import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableCulture;
import JardinCollectif.tables.TableDemande;
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
		TableDemande tbl_demande = new TableDemande(noMembre);
		TableAttribution tbl_attribution = new TableAttribution(noMembre);

		newLine();
		
		if (tbl_attribution.notLast()) {
			if (tbl_attribution.destroyMembre() && tbl_demande.destroyMembre() && tbl_membre.destroy())
				print("Membre " + noMembre + " détruit");
			else
				showError();
		} else
			print("Le membre est le dernier sur le lot. Supprimer le lot ou ajoutez-y un membre puis réessayez");
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
		TableDemande tbl_demande = new TableDemande(nomLot, noMembre);

		if (tbl_demande.fetch()) {
			TableAttribution tbl_attribution = new TableAttribution(nomLot, noMembre);

			if (tbl_attribution.insert()) {
				tbl_demande.setStatus(TableDemande.STATUS_APPROVED);

				if (tbl_demande.update()) {
					newLine();
					print("Demande acceptée");
				} else
					showError();
			} else
				showError();
		} else
			showError();
	}

	public static void refuserDemande(String nomLot, Integer noMembre) {
		TableDemande tbl_demande = new TableDemande(nomLot, noMembre);

		if (tbl_demande.fetch()) {
			tbl_demande.setStatus(TableDemande.STATUS_DENIED);

			if (tbl_demande.update())
				print("Demande refusée");
			else
				showError();

		} else
			showError();
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
		TableDemande tbl_demande = new TableDemande(nomLot, noMembre);

		newLine();

		if (tbl_lot.fetch()) {
			if (tbl_membre.fetch()) {
				if (tbl_demande.insert()) {
					print("Demande soumise");
				} else
					showError();
			} else
				print("Le membre " + noMembre + " n'existe pas");
		} else
			print("Le lot " + nomLot + " n'existe pas");
	}

	public static void supprimerLot(String nomLot) {
		TableLot tbl_lot = new TableLot(nomLot);
		TableCulture tbl_culture = new TableCulture(nomLot, TableCulture.NOM_LOT);
		TableAttribution tbl_attribution = new TableAttribution(nomLot);

		// TODO: vérifier qu'il n'y a aucune plante sur le lot
		if (!tbl_culture.existsLot()) {
			if (tbl_lot.destroy()) {
				newLine();
				print("Le lot " + nomLot + " a été détruit");
			} else
				showError();
		} else
			print("Des plantes sont encores plantées sur le lot. Transaction annulée");
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

		// TODO: vérifier qu'il n'en a pas de plantée

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
		TableLot tbl_lot = new TableLot(nomLot);
		TableMembre tbl_membre = new TableMembre(noMembre);
		TablePlante tbl_plante = new TablePlante(nomPlante);
		TableAttribution tbl_attribution = new TableAttribution(nomLot, noMembre);
		
		TableCulture tbl_culture = new TableCulture(nomLot, nomPlante, noMembre, nbExemplaires);
		
		newLine();
		
		if (tbl_membre.fetch() && tbl_lot.fetch() && tbl_plante.fetch()) {
			if (tbl_attribution.fetch()) {
				tbl_culture.insert();
				print("Plantes sauvegardées");
			} else
				print("Membre " + noMembre + " pas autorisé dans le lot");
			
		} else
			print("Des données n'existent pas sur la BD. Veuillez les créer avant de poursuivre.\n  Missing: (plante, membre, lot)");
	}

	public static void recolterPlante(String nomPlante, String nomLot, Integer noMembre) {
		// TODO: Remove plant instances in tbl_culture
		// Vérifier que le membre est inscrit au lot
		// Retirer toutes les plantes prêtes
	}

	public static void afficherPlantesLot(String nomLot) {
		TableCulture tbl_culture = new TableCulture(nomLot, TableCulture.NOM_LOT);
		ArrayList<TableCulture> tl = tbl_culture.fetchAllLot();

		newLine();
		for (TableCulture tp : tl) {
			print(tp.toString());
		}
	}
}
