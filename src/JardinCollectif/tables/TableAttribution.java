package JardinCollectif.tables;

import java.util.List;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;

import javax.persistence.*;

public abstract class TableAttribution {
	public static List<Attribution> loadAll() {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Attribution> q = mg.createQuery("SELECT a FROM Attribution a", Attribution.class);
		
		return q.getResultList();
	}

	public static void persist(Attribution a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Attribution a) {
		if (a != null)
        {
            JardinCollectif.cx.getConnection().remove(a);
            return true;
        }
        return false;
	}
	
	public static Attribution load(String pk_lot, Integer pk_membre) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Attribution> q = mg.createQuery("SELECT a FROM Attribution a WHERE m.idMembre = :idMembre AND nomLot = :nomLot", Attribution.class);
		q.setParameter("idMembre", pk_membre);
		q.setParameter("nomLot", pk_lot);
		
		List<Attribution> a = q.getResultList();
		
		if (!a.isEmpty())
			return a.get(0);
		else
			return null;
	}
	
	public static Boolean existsLot(String pk_lot) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Attribution> q = mg.createQuery("SELECT a FROM Attribution a WHERE m.nomLot = :nomLot", Attribution.class);
		q.setParameter("nomLot", pk_lot);
		
		return !q.getResultList().isEmpty();
	}
	
	public static Boolean existsMembre(Integer pk_membre) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Attribution> q = mg.createQuery("SELECT a FROM Attribution a WHERE m.idMembre = :idMembre", Attribution.class);
		q.setParameter("idMembre", pk_membre);
		
		return !q.getResultList().isEmpty();
	}
	
	public static Boolean isNotLast(String pk_lot, Integer pk_membre) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Attribution> q = mg.createQuery("SELECT a FROM Attribution a WHERE m.idMembre != :idMembre AND m.nomLot = :nomLot", Attribution.class);
		q.setParameter("nomLot", pk_lot);
		
		return !q.getResultList().isEmpty();
	}
	
	public static Integer getLotCollaboratorQty(String pk_lot) {
		EntityManager mg = JardinCollectif.cx.getConnection();
		TypedQuery<Attribution> q = mg.createQuery("SELECT a FROM Attribution a WHERE m.nomLot = :nomLot", Attribution.class);
		q.setParameter("nomLot", pk_lot);
		
		return q.getResultList().size();
	}
}
