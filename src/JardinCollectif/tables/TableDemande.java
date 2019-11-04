package JardinCollectif.tables;

import java.util.List;

import javax.persistence.*;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Demande;

public abstract class TableDemande {
	public static List<Demande> loadAll() {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Demande> q = mg.createQuery("SELECT d FROM Demande d", Demande.class);

		return q.getResultList();
	}

	public static void persist(Demande a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Demande a) {
		if (a != null) {
			JardinCollectif.cx.getConnection().remove(a);
			return true;
		}
		return false;
	}

	public static Demande load(Integer pk_membre, String pk_lot, Integer status) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Demande> q = mg.createQuery(
				"SELECT d FROM Demande d WHERE d.idMembre = :idMembre AND d.nomLot = :nomLot AND d.status = :status",
				Demande.class);
		q.setParameter("idMembre", pk_membre);
		q.setParameter("nomLot", pk_lot);
		q.setParameter("nomPlante", status);

		List<Demande> a = q.getResultList();

		if (!a.isEmpty())
			return a.get(0);
		else
			return null;
	}

	public static Boolean existsMembre(Integer pk_membre) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Demande> q = mg.createQuery("SELECT d FROM Demande d WHERE d.idMembre = :idMembre", Demande.class);
		q.setParameter("idMembre", pk_membre);
		
		return !q.getResultList().isEmpty();
	}

	public static Boolean existsLot(String pk_lot) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Demande> q = mg.createQuery("SELECT d FROM Demande d WHERE d.nomLot = :nomLot", Demande.class);
		q.setParameter("nomLot", pk_lot);
		
		return !q.getResultList().isEmpty();
	}
}
