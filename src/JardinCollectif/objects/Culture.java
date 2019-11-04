package JardinCollectif.objects;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Culture {
	private String nomLot;
	private String nomPlante;
	private Integer idMembre;
	private Integer nbExemplaires;
	private Date plantee;
	
	public static Integer NOM_LOT = 0;
	public static Integer NOM_PLANTE = 1;
	
	public Culture() {
	}
	
	public Culture(String nom, Integer mode) {
		if (mode == NOM_LOT)
			this.nomLot = nom;
		else if (mode == NOM_PLANTE)
			this.nomPlante = nom;
	}
	
	public Culture(Integer idMembre) {
		this.idMembre = idMembre;
	}
	
	public Culture(String nomLot, String nomPlante, Integer idMembre) {
		this.nomLot = nomLot;
		this.nomPlante = nomPlante;
		this.idMembre = idMembre;
	}
	
	public Culture(String nomLot, String nomPlante, Integer idMembre, Integer nbExemplaires, Date datePlantation) {
		this.nomLot = nomLot;
		this.nomPlante = nomPlante;
		this.idMembre = idMembre;
		this.nbExemplaires = nbExemplaires;
		this.plantee = datePlantation;
	}

	public String getNomLot() {
		return nomLot;
	}

	public void setNomLot(String nomLot) {
		this.nomLot = nomLot;
	}

	public String getNomPlante() {
		return nomPlante;
	}

	public void setNomPlante(String nomPlante) {
		this.nomPlante = nomPlante;
	}

	public Integer getIdMembre() {
		return idMembre;
	}

	public void setIdMembre(Integer idMembre) {
		this.idMembre = idMembre;
	}

	public Integer getNbExemplaires() {
		return nbExemplaires;
	}

	public void setNbExemplaires(Integer nbExemplaires) {
		this.nbExemplaires = nbExemplaires;
	}
	
	public Date getPlantee() {
		return plantee;
	}

	public void setPlantee(Date plantee) {
		this.plantee = plantee;
	}
	
	public String toString() {
		return this.nbExemplaires + " " + this.nomPlante + "(s) en pousse depuis " + this.plantee.toString() + " dans " + this.nomLot + " par membre " + this.idMembre; 
	}
}
