package JardinCollectif.tables;

import java.util.List;

import javax.persistence.*;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Culture;

public abstract class TableCulture {
	public static List<Culture> loadAll() {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Culture> q = mg.createQuery("SELECT c FROM Culture c", Culture.class);

		return q.getResultList();
	}

	public static void persist(Culture a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Culture a) {
		if (a != null) {
			JardinCollectif.cx.getConnection().remove(a);
			return true;
		}
		return false;
	}

	public static Culture load(String pk_lot, String pk_plante, Integer pk_membre) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Culture> q = mg.createQuery(
				"SELECT c FROM Culture c WHERE c.idMembre = :idMembre AND c.nomLot = :nomLot AND c.nomPlante = :nomPlante",
				Culture.class);
		q.setParameter("idMembre", pk_membre);
		q.setParameter("nomLot", pk_lot);
		q.setParameter("nomPlante", pk_plante);

		List<Culture> a = q.getResultList();

		if (!a.isEmpty())
			return a.get(0);
		else
			return null;
	}

	public static Boolean existsLot(String pk_lot) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Culture> q = mg.createQuery("SELECT c FROM Culture c WHERE c.nomLot = :nomLot", Culture.class);
		q.setParameter("nomLot", pk_lot);
		
		return !q.getResultList().isEmpty();
	}

	public static Boolean existsPlante(String pk_plante) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Culture> q = mg.createQuery("SELECT c FROM Culture c WHERE c.nomPlante = :nomPlante", Culture.class);
		q.setParameter("nomPlante", pk_plante);
		
		return !q.getResultList().isEmpty();
	}

	public static Boolean existsMembre(Integer pk_membre) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Culture> q = mg.createQuery("SELECT c FROM Culture c WHERE c.idMembre = :idMembre", Culture.class);
		q.setParameter("idMembre", pk_membre);
		
		return !q.getResultList().isEmpty();
	}
}
