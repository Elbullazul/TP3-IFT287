package JardinCollectif.tables;

import java.util.List;

import javax.persistence.*;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Plante;

public abstract class TablePlante {
	public static List<Plante> loadAll() {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Plante> q = mg.createQuery("SELECT p FROM Plante p", Plante.class);

		return q.getResultList();
	}
	
	public static void persist(Plante a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Plante a) {
		if (a != null)
        {
            JardinCollectif.cx.getConnection().remove(a);
            return true;
        }
        return false;
	}

	public static Plante load(String pk_plante) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Plante> q = mg.createQuery("SELECT p FROM Plante p WHERE p.nomPlante = :nomPlante", Plante.class);
		q.setParameter("nomPlante", pk_plante);

		List<Plante> a = q.getResultList();

		if (!a.isEmpty())
			return a.get(0);
		else
			return null;
	}
}
