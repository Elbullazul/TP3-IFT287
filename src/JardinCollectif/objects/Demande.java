package JardinCollectif.objects;

import javax.persistence.*;

@Entity
public class Demande {
	private Integer idMembre;
	private String nomLot;
	private Integer status;
	
	public static Integer STATUS_PENDING = -1;
	public static Integer STATUS_DENIED = 0;
	public static Integer STATUS_APPROVED = 1;
	
	public Demande() {
	}
	
	public Demande(Integer idMembre) {
		this.idMembre = idMembre;
	}
	
	public Demande(String nomLot) {
		this.nomLot = nomLot;
	}
	
	public Demande(String nomLot, Integer idMembre) {
		this.nomLot = nomLot;
		this.idMembre = idMembre;
		this.status = STATUS_PENDING;
	}

	public Integer getIdMembre() {
		return idMembre;
	}

	public void setIdMembre(Integer idMembre) {
		this.idMembre = idMembre;
	}

	public String getNomLot() {
		return nomLot;
	}

	public void setNomLot(String nomLot) {
		this.nomLot = nomLot;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String toString() {
		String s = "Demande soumise par: " + this.idMembre.toString() + " pour le lot " + this.nomLot + " Status: ";
		
		switch (this.status) {
		case -1:
			s += "Pending";
			break;
		case 0:
			s += "denied";
			break;
		case 1:
			s += "approved";
			break;
		}
		
		return s;
	}
}
