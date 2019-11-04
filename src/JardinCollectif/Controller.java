package JardinCollectif;

import JardinCollectif.tables.*;
import JardinCollectif.objects.*;

import java.util.Date;
import java.util.List;

public class Controller {
	public static Boolean newCycle = false;
	private static final String ArrayList = null;

	// Program logic. make new method for each possible command
	// and run the steps and verifications here
	// before calling the corresponding table method

	public static void print(Object o) {
		if (newCycle) {
			System.out.println();
			newCycle = false;
		}
		System.out.println(o.toString());
	}

	/*
	 * Membres
	 */
	public static void inscrireMembre(String prenom, String nom, String password, Integer noMembre) {
		Membre membre = new Membre(prenom, nom, password, noMembre);
		TableMembre.persist(membre);

		print("Le membre a été ajouté");
	}

	public static void supprimerMembre(Integer noMembre) {
		Membre m = TableMembre.load(noMembre);
		List<Attribution> listAttributions = TableAttribution.loadAll();

		if (m == null) {
			print("Le membre n'existe pas");
			return;
		}

		if (TableCulture.existsMembre(noMembre)) {
			print("Le membre a encore des plantes en culture. Récoltez-les avant de poursuivre");
			return;
		}

		if (TableDemande.existsMembre(noMembre)) {
			for (Demande d : TableDemande.loadAll()) {
				if (d.getIdMembre() == noMembre) {
					TableDemande.remove(d);
				}
			}
		}

		if (TableAttribution.existsMembre(noMembre)) {
			for (Attribution a : listAttributions) {
				if (!TableAttribution.isNotLast(a.getNomLot(), a.getIdMembre())) {
					print("Le membre est le dernier sur le lot " + a.getNomLot()
							+ ". Détruire le lot avant de continuer");
					return;
				}
			}

			for (Attribution a : listAttributions) {
				if (a.getIdMembre() == noMembre) {
					TableAttribution.remove(a);
				}
			}
		}
	}

	public static void afficherMembres() {
		for (Membre m : TableMembre.loadAll())
			print(m.toString());
	}

	// ADMIN members
	public static void promouvoirAdministrateur(Integer noMembre) {
		Membre m = TableMembre.load(noMembre);

		if (m == null) {
			print("Le membre n'existe pas");
		}

		m.setIsAdmin(true);
		TableMembre.persist(m);
		print("Le membre " + noMembre.toString() + " est maintenant un administrateur");
	}

	public static void accepterDemande(String nomLot, Integer noMembre) {
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

		print("Demande acceptée.");
	}

	public static void refuserDemande(String nomLot, Integer noMembre) {
		Demande d = TableDemande.load(noMembre, nomLot, Demande.STATUS_PENDING);

		if (d == null) {
			print("Aucune demande ouverte pour ce membre et ce lot");
			return;
		}

		d.setStatus(Demande.STATUS_DENIED);
		TableDemande.persist(d);

		print("Demande refusée");
	}

	/*
	 * Lots
	 */
	public static void ajouterLot(String nomLot, Integer maxCollabs) {
		Lot l = new Lot(nomLot, maxCollabs);
		TableLot.persist(l);

		print("Le lot a été ajouté");
	}

	public static void rejoindreLot(String nomLot, Integer noMembre) {
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
		print("Demande soumise pour évaluation");
	}

	public static void supprimerLot(String nomLot) {
		Lot l = TableLot.load(nomLot);

		if (l == null) {
			print("Le lot n'existe pas");
			return;
		} else if (TableCulture.existsLot(nomLot)) {
			print("Des plantes sont encores plantées sur le lot. Récoltez-les avant de détruire le lot");
			return;
		}

		for (Attribution a : TableAttribution.loadAll()) {
			if (a.getNomLot() == nomLot) {
				TableAttribution.remove(a);
			}
		}

		for (Demande d : TableDemande.loadAll()) {
			if (d.getNomLot() == nomLot) {
				TableDemande.remove(d);
			}
		}

		TableLot.remove(l);
		print("Le lot a été détruit");
	}

	public static void afficherLots() {
		for (Lot l : TableLot.loadAll())
			print(l.toString());
	}

	/*
	 * Plante
	 */
	public static void ajouterPlante(String nomPlante, Integer nbJours) {
		Plante p = new Plante(nomPlante, nbJours);
		TablePlante.persist(p);

		print("Plante ajoutée");
	}

	public static void retirerPlante(String nomPlante) {
		Plante p = TablePlante.load(nomPlante);

		if (p == null) {
			print("La plante n'existe pas");
			return;
		} else if (TableCulture.existsPlante(nomPlante)) {
			print("Récoltez tous les exemplaires avant de détruire la plante");
		}

		TablePlante.remove(p);
		print("La plante a été détruite");
	}

	public static void afficherPlantes() {
		for (Plante p : TablePlante.loadAll())
			print(p.toString());
	}

	/*
	 * Culture
	 */
	public static void planterPlante(String nomPlante, String nomLot, Integer noMembre, Integer nbExemplaires,
			Date datePlantation) {
		
		Lot l = TableLot.load(nomLot);
		Membre m = TableMembre.load(noMembre);
		Plante p = TablePlante.load(nomPlante);
		Attribution a = TableAttribution.load(nomLot, noMembre);
		Culture c = new Culture(nomLot, nomPlante, noMembre, nbExemplaires, datePlantation);
		
		if (m == null) {
			print("Le membre n'existe pas");
			return;
		} else if (l == null) {
			print("Le lot n'existe pas");
			return;
		} else if (p == null) {
			print("La plante n'existe pas");
			return;
		} else if (a == null) {
			print("Le membre n'est pas membre de ce lot");
			return;
		}
		
		TableCulture.persist(c);
		print("Les exemplaires ont été plantés");
	}

	public static void recolterPlante(String nomPlante, String nomLot, Integer noMembre) {
		Lot l = TableLot.load(nomLot);
		Membre m = TableMembre.load(noMembre);
		Plante p = TablePlante.load(nomPlante);
		Attribution a = TableAttribution.load(nomLot, noMembre);
		Culture c = TableCulture.load(nomLot, nomPlante, noMembre);
		
		if (m == null) {
			print("Le membre n'existe pas");
			return;
		} else if (l == null) {
			print("Le lot n'existe pas");
			return;
		} else if (p == null) {
			print("La plante n'existe pas");
			return;
		} else if (a == null) {
			print("Le membre n'est pas inscrit sur ce lot");
			return;
		} else if (c == null) {
			print("Aucun exemplaire planté pour ce membre, lot et plante");
			return;
		}
		
		for (Culture cl : TableCulture.loadAll()) {
			if (cl.getIdMembre() == noMembre &&
					cl.getNomLot() == nomLot &&
					cl.getNomPlante() == nomPlante) {
				TableCulture.remove(cl);
			}
		}
		
		print("Plantes récoltées");
	}

	public static void afficherPlantesLot(String nomLot) {
		for (Culture c : TableCulture.loadAll()) {
			if (c.getNomLot() == nomLot) {
				Plante p = TablePlante.load(c.getNomPlante());
				print(p.toString());
			}
		}
	}
}
