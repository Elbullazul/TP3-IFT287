package JardinCollectif.controllers;

import static JardinCollectif.Util.print;

import JardinCollectif.Connexion;
import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;
import JardinCollectif.objects.Demande;
import JardinCollectif.objects.Lot;
import JardinCollectif.objects.Membre;
import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableDemande;
import JardinCollectif.tables.TableLot;
import JardinCollectif.tables.TableMembre;

public abstract class DemandeController {
	public static void creer(String nomLot, Integer noMembre) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			Lot l = TableLot.load(nomLot);
			Membre m = TableMembre.load(noMembre);
			Demande d = TableDemande.load(noMembre, nomLot, Demande.STATUS_PENDING);
			Attribution a = TableAttribution.load(nomLot, noMembre);

			if (a != null) {
				print("Le membre est déja membre du lot");
				return;
			} else if (l == null) {
				print("Le lot n'existe pas");
				return;
			} else if (m == null) {
				print("Le membre n'existe pas");
				return;
			} else if (d != null) {
				print("Une demande est déja ouverte");
				return;
			}

			TableDemande.persist(d);
			cnn.commit();

			print("Demande soumise pour évaluation");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void accepter(String nomLot, Integer noMembre) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			Lot l = TableLot.load(nomLot);
			Membre m = TableMembre.load(noMembre);
			Attribution a = new Attribution(nomLot, noMembre);
			Demande d = TableDemande.load(noMembre, nomLot, Demande.STATUS_PENDING);

			if (l == null) {
				print("Le lot n'existe pas");
				return;
			} else if (m == null) {
				print("Le membre n'existe pas");
				return;
			} else if (d == null) {
				print("Aucune demande ouverte pour ce membre et ce lot");
				return;
			}

			if (TableAttribution.getLotCollaboratorQty(nomLot) < l.getMax_collab()) {
				TableAttribution.persist(a);
			} else {
				print("Le lot est plein");
				return;
			}

			d.setStatus(Demande.STATUS_APPROVED);

			TableDemande.persist(d);
			cnn.commit();

			print("Demande acceptée.");

		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void refuser(String nomLot, Integer noMembre) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			Demande d = TableDemande.load(noMembre, nomLot, Demande.STATUS_PENDING);

			if (d == null) {
				print("Aucune demande ouverte pour ce membre et ce lot");
				return;
			}

			d.setStatus(Demande.STATUS_DENIED);

			TableDemande.persist(d);
			cnn.commit();

			print("Demande refusée");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}
}
