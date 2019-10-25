package JardinCollectif;

import java.sql.Connection;
import java.sql.Date;

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

		TableCulture tbl_culture = new TableCulture(noMembre);

		ArrayList<TableLot> lots_membre = tbl_attribution.getLotsMembre();

		newLine();

		if (!tbl_membre.fetch()) {
			print("Le membre n'existe pas");
			return;
		}

		for (TableLot tl : lots_membre) {
			tbl_attribution.setNomLot(tl.getNom());

			if (!tbl_attribution.notLast()) {
				print("Le membre est le dernier membre sur le lot " + tl.getNom() + ". Transaction Annulée");
				return;
			}
		}

		if (tbl_culture.existsMembre()) {
			print("Le membre a encore des plantes en culture. Récoltez-les avant de poursuivre");
			return;
		}

		// supprimer si trouvé dans ces tables
		if (tbl_attribution.fetch())
			tbl_attribution.destroyMembre();

		if (tbl_demande.existsMembre()) {
			tbl_demande.destroyMembre();
		}

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
		TableLot tbl_lot = new TableLot(nomLot);
		TableMembre tbl_membre = new TableMembre(noMembre);
		TableDemande tbl_demande = new TableDemande(nomLot, noMembre);
		TableAttribution tbl_attribution = new TableAttribution(nomLot, noMembre);

		newLine();

		if (!tbl_lot.fetch()) {
			print("Le lot n'existe pas");
			return;
		}

		if (!tbl_membre.fetch()) {
			print("Le membre n'existe pas");
			return;
		}

		if (!(tbl_attribution.getMembresLot().size() < tbl_lot.getMax_collab())) {
			print("Le lot est plein");
			return;
		}

		if (!tbl_demande.fetch()) {
			print("Aucune demande ouverte pour ce membre et ce lot");
			return;
		}
		
		if (tbl_attribution.insert()) {
			tbl_demande.setStatus(TableDemande.STATUS_APPROVED);

			if (tbl_demande.update()) {
				print("Demande acceptée");
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
		
		TableAttribution tbl_attribution = new TableAttribution(nomLot, noMembre);

		newLine();
		
		// vérifier que pas déjà membre
		if (tbl_attribution.fetch()) {
			print("Déjà membre");
			return;
		}
		
		// vérifier que la demande n'existe pas déja
		if (tbl_demande.fetch()) {
			print("Demande déjà soumise");
			return;
		}

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

		TableDemande tbl_demande = new TableDemande(nomLot);

		newLine();

		if (!tbl_lot.fetch()) {
			print("Le lot n'existe pas");
			return;
		}

		if (tbl_culture.existsLot()) {
			print("Des plantes sont encores plantées sur le lot. Transaction annulée");
			return;
		}

		if (tbl_attribution.existsLot())
			tbl_attribution.destroyLot();

		if (tbl_demande.existsLot())
			tbl_demande.destroyLot();

		if (tbl_lot.destroy()) {
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
		TableCulture tbl_culture = new TableCulture(nomPlante, TableCulture.NOM_PLANTE);

		newLine();
		
		if (!tbl_plante.fetch()) {
			print("La plante n'existe pas");
			return;
		}
		
		if (!tbl_culture.existsPlante()) {
			if (tbl_plante.destroy()) {
				print("Plante " + nomPlante + " supprimée");
			} else
				showError();
		} else
			print("Des exemplaires sont encore en culture. Récoltez-les d'abord");
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
	public static void planterPlante(String nomPlante, String nomLot, Integer noMembre, Integer nbExemplaires,
			Date datePlantation) {
		TableLot tbl_lot = new TableLot(nomLot);
		TableMembre tbl_membre = new TableMembre(noMembre);
		TablePlante tbl_plante = new TablePlante(nomPlante);
		TableAttribution tbl_attribution = new TableAttribution(nomLot, noMembre);

		TableCulture tbl_culture = new TableCulture(nomLot, nomPlante, noMembre, nbExemplaires, datePlantation);

		newLine();

		if (tbl_membre.fetch() && tbl_lot.fetch() && tbl_plante.fetch()) {
			if (tbl_attribution.fetch()) {
				if (tbl_culture.insert())
					print("Plantes sauvegardées");
				else
					showError();
			} else
				print("Membre " + noMembre + " pas autorisé dans le lot");

		} else {
			print("Informations incorrectes: ");

			if (!tbl_membre.fetch())
				print("Membre inexistant");
			else if (!tbl_lot.fetch())
				print("Lot inexistant");
			else if (!tbl_plante.fetch())
				print("Plante inexistante");
			else if (!tbl_attribution.fetch())
				print("Le membre n'est pas inscrit sur ce lot");
		}
	}

	public static void recolterPlante(String nomPlante, String nomLot, Integer noMembre) {
		TableLot tbl_lot = new TableLot(nomLot);
		TableMembre tbl_membre = new TableMembre(noMembre);
		TablePlante tbl_plante = new TablePlante(nomPlante);
		TableAttribution tbl_attribution = new TableAttribution(nomLot, noMembre);

		TableCulture tbl_culture = new TableCulture(nomLot, nomPlante, noMembre);

		newLine();

		if (!tbl_membre.fetch())
			print("Membre inexistant");
		else if (!tbl_lot.fetch())
			print("Lot inexistant");
		else if (!tbl_plante.fetch())
			print("Plante inexistante");
		else if (!tbl_attribution.fetch())
			print("Le membre n'est pas inscrit sur ce lot");
		else if (!tbl_culture.fetch())
			print("La plante n'existe pas pour ce lot ou membre");
		else if (tbl_membre.getId() != tbl_culture.getIdMembre())
			print("Seul le membre ayant planté les plantes peut les récolter");
		else {
			if (tbl_culture.recolterPlante())
				print("Plantes récoltées");
		}
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
