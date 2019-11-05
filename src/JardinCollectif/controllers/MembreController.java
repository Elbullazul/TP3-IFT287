package JardinCollectif.controllers;

import java.util.List;

import JardinCollectif.Connexion;
import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;
import JardinCollectif.objects.Demande;
import JardinCollectif.objects.Membre;
import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableCulture;
import JardinCollectif.tables.TableDemande;
import JardinCollectif.tables.TableMembre;

import static JardinCollectif.Util.*;

public abstract class MembreController {
	public static void creer(String prenom, String nom, String password, Integer noMembre) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			Membre membre = new Membre(prenom, nom, password, noMembre);

			TableMembre.persist(membre);
			cnn.commit();

			print("Le membre a été ajouté");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void supprimer(Integer noMembre) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			Membre m = TableMembre.load(noMembre);
			List<Attribution> listAttributions = TableAttribution.loadAll();

			if (m == null) {
				print("Le membre n'existe pas");
				return;
			}

			if (TableCulture.exists(noMembre)) {
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
			cnn.commit();
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void afficher() {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			for (Membre m : TableMembre.loadAll())
				print(m.toString());
			cnn.commit();
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	// ADMIN members
	public static void promouvoirAdministrateur(Integer noMembre) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			Membre m = TableMembre.load(noMembre);

			if (m == null) {
				print("Le membre n'existe pas");
				return;
			}

			m.setIsAdmin(true);
			
			TableMembre.persist(m);
			cnn.commit();
			
			print("Le membre " + noMembre.toString() + " est maintenant un administrateur");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}
}
