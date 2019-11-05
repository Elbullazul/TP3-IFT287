package JardinCollectif.controllers;

import static JardinCollectif.Util.print;

import JardinCollectif.Connexion;
import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Plante;
import JardinCollectif.tables.TableCulture;
import JardinCollectif.tables.TablePlante;

public abstract class PlanteController {
	public static void creer(String nomPlante, Integer nbJours) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			Plante p = new Plante(nomPlante, nbJours);

			TablePlante.persist(p);
			cnn.commit();

			print("Plante ajout�e");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void supprimer(String nomPlante) {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			Plante p = TablePlante.load(nomPlante);

			if (p == null) {
				print("La plante n'existe pas");
				return;
			} else if (TableCulture.existsPlante(nomPlante)) {
				print("R�coltez tous les exemplaires avant de d�truire la plante");
			}

			TablePlante.remove(p);
			cnn.commit();

			print("La plante a �t� d�truite");
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}

	public static void afficher() {
		Connexion cnn = JardinCollectif.cx;
		cnn.demarreTransaction();

		try {
			for (Plante p : TablePlante.loadAll())
				print(p.toString());
			cnn.commit();
		} catch (Exception e) {
			cnn.rollback();
			e.printStackTrace();
		}
	}
}
