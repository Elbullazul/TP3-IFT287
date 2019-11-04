package JardinCollectif.objects;

import javax.persistence.*;

@Entity
public class Plante {
	@Id
	private String nom;
	private Integer duree;

	public Plante() {
	}
	
	public Plante(String nom) {
		this.nom = nom;
	}
	
	public Plante(String nom, Integer duree) {
		this.nom = nom;
		this.duree = duree;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nomPlante) {
		this.nom = nomPlante;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}
	
	public String toString() {
		return "Nom: " + this.nom + "     Temps pour mûrir: " + this.duree.toString() + " jours"; 
	}
}
