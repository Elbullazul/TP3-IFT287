package JardinCollectif;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.<br><br>
 * 
 * Cette classe ouvre une connexion avec une BD via JDBC.<br>
 * La m�thode serveursSupportes() indique les serveurs support�s.<br>
 * <pre>
 * Pr�-condition
 *   Le driver JDBC appropri� doit �tre accessible.
 * 
 * Post-condition
 *   La connexion est ouverte en mode autocommit false et s�rialisable, 
 *   (s'il est support� par le serveur).
 * </pre>
 * <br>
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * @author Marc Frappier - Universit� de Sherbrooke
 * @version Version 2.0 - 13 novembre 2004
 * 
 * 
 * @author Vincent Ducharme - Universit� de Sherbrooke
 * @version Version 3.0 - 21 mai 2016
 */
public class Connexion
{
	 private EntityManager em;
	 private EntityManagerFactory emf;

    /**
     * Ouverture d'une connexion en mode autocommit false et s�rialisable (si
     * support�)
     * 
     * @param serveur Le type de serveur SQL � utiliser (Valeur : local, dinf).
     * @param bd      Le nom de la base de donn�es sur le serveur.
     * @param user    Le nom d'utilisateur � utiliser pour se connecter � la base de donn�es.
     * @param pass    Le mot de passe associ� � l'utilisateur.
     */
	 public Connexion(String serveur, String bd, String user, String pass) throws Exception
	    {
	        if (serveur.equals("local"))
	        {
	            emf = Persistence.createEntityManagerFactory(bd);
	        }
	        else if (serveur.equals("dinf"))
	        {
	        	Map<String, String> properties = new HashMap<String, String>();
	        	  properties.put("javax.persistence.jdbc.user", user);
	        	  properties.put("javax.persistence.jdbc.password", pass);
	        	emf = Persistence.createEntityManagerFactory("objectdb://bd-info2.dinf.usherbrooke.ca:6136/"+user+"/" + bd, properties);
	        }
	        else
	        {
	            throw new Exception("Serveur inconnu");
	        }
	    	
	    	em = emf.createEntityManager();
	        
	        System.out.println("Ouverture de la connexion :\n"
	                + "Connect� sur la BD ObjectDB "
	                + bd + " avec l'utilisateur " + user);
	    }

    /**
     * Fermeture d'une connexion
     */
    public void fermer()
    {
        em.close();
        emf.close();
        System.out.println("Connexion ferm�e ");
    }

    /**
     * Commit
     */
    public void commit()
    {
        em.getTransaction().commit();
    }
    
    public void demarreTransaction()
    {
    	em.getTransaction().begin();
    }
    
    /**
     * Rollback
     */
    public void rollback()
    {
       em.getTransaction().rollback();
    }

    /**
     * retourne la Connection ObjectDB
     */
    public EntityManager getConnection()
    {
        return em;
    }
}