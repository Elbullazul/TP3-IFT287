package JardinCollectif.controllers;

import static JardinCollectif.Util.print;

import java.util.Date;

import JardinCollectif.Connexion;
import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;
import JardinCollectif.objects.Culture;
import JardinCollectif.objects.Lot;
import JardinCollectif.objects.Membre;
import JardinCollectif.objects.Plante;
import JardinCollectif.tables.TableAttribution;
import JardinCollectif.tables.TableCulture;
import JardinCollectif.tables.TableLot;
import JardinCollectif.tables.TableMembre;
import JardinCollectif.tables.TablePlante;

public abstract class CultureController {
	public static void creer(String nomPlante, String nomLot, Integer noMembre, Integer nbExemplaires,
			Date datePlantation) {

		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
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
			cnn.commit();

			print("Les exemplaires ont été plantés");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void supprimer(String nomPlante, String nomLot, Integer noMembre) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
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
				if (cl.getIdMembre() == noMembre && cl.getNomLot() == nomLot && cl.getNomPlante() == nomPlante) {
					TableCulture.remove(cl);
				}
			}

			cnn.commit();

			print("Plantes récoltées");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void afficher(String nomLot) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			for (Culture c : TableCulture.loadAll()) {
				if (c.getNomLot() == nomLot) {
					Plante p = TablePlante.load(c.getNomPlante());
					print(p.toString());
				}
			}
			
			cnn.commit();
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}
}
