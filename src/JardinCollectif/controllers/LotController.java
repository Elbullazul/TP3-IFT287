package JardinCollectif.controllers;

import static JardinCollectif.Util.print;

import JardinCollectif.Connexion;
import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;
import JardinCollectif.objects.Demande;
import JardinCollectif.objects.Lot;
import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableCulture;
import JardinCollectif.tables.TableDemande;
import JardinCollectif.tables.TableLot;

public abstract class LotController {
	public static void creer(String nomLot, Integer maxCollabs) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			Lot l = new Lot(nomLot, maxCollabs);

			TableLot.persist(l);
			cnn.commit();
			
			print("Le lot a été ajouté");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void supprimer(String nomLot) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
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
			cnn.commit();

			print("Le lot a été détruit");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void afficher() {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			for (Lot l : TableLot.loadAll())
				print(l.toString());
			cnn.commit();
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}
}
