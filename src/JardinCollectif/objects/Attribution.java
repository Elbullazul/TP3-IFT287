package JardinCollectif.objects;

import javax.persistence.*;

@Entity
public class Attribution {
	@Id
	private Integer idMembre;
	@Id
	private String nomLot;

	public Attribution() {
	}

	public Attribution(String nomLot) {
		this.nomLot = nomLot;
	}
	
	public Attribution(Integer noMembre) {
		this.idMembre = noMembre;
	}
	
	public Attribution(String nomLot, Integer noMembre) {
		this.nomLot = nomLot;
		this.idMembre = noMembre;
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
	
	public String toString() {
		return "Membre " + this.idMembre.toString() + " collabore sur le lot " + this.nomLot; 
	}
}
