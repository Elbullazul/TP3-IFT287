package JardinCollectif.tables;

import java.util.List;

import javax.persistence.*;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Membre;

public abstract class TableMembre {
	public static List<Membre> loadAll() {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Membre> q = mg.createQuery("SELECT m FROM Membre m", Membre.class);

		return q.getResultList();
	}

	public static void persist(Membre a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Membre a) {
		if (a != null)
        {
            JardinCollectif.cx.getConnection().remove(a);
            return true;
        }
        return false;
	}

	public static Membre load(Integer pk_membre) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Membre> q = mg.createQuery("SELECT m FROM Membre m WHERE m.idMembre = :idMembre", Membre.class);
		q.setParameter("idMembre", pk_membre);

		List<Membre> a = q.getResultList();

		if (!a.isEmpty())
			return a.get(0);
		else
			return null;
	}
}
