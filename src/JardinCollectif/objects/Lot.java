package JardinCollectif.objects;

import javax.persistence.*;

@Entity
public class Lot {
	@Id
	private String nom;
	private Integer max_collab;

	public Lot(String nomLot) {
		this.nom = nomLot;
	}
	
	public Lot(String nomLot, Integer maxCollabs) {
		this.nom = nomLot;
		this.max_collab = maxCollabs;
	}
	
	public Lot() {
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getMax_collab() {
		return max_collab;
	}

	public void setMax_collab(Integer max_collab) {
		this.max_collab = max_collab;
	}
	
	public String toString() {
		return "Lot " + this.nom + ", Nb max. membres: " + this.max_collab.toString(); 
	}
}
