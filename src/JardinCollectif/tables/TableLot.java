package JardinCollectif.tables;

import java.util.List;

import javax.persistence.*;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Lot;

public abstract class TableLot {
	public static List<Lot> loadAll() {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Lot> q = mg.createQuery("SELECT l FROM Lot l", Lot.class);

		return q.getResultList();
	}

	public static void persist(Lot a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Lot a) {
		if (a != null) {
			JardinCollectif.cx.getConnection().remove(a);
			return true;
		}
		return false;
	}

	public static Lot load(String pk_lot) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Lot> q = mg.createQuery("SELECT l FROM Lot l WHERE l.nomLot = :nomLot", Lot.class);
		q.setParameter("nomLot", pk_lot);

		List<Lot> a = q.getResultList();

		if (!a.isEmpty())
			return a.get(0);
		else
			return null;
	}
}
