package JardinCollectif.objects;

import javax.persistence.*;

@Entity
public class Membre {
	@Id
	private Integer id;
	private String nom;
	private String prenom;
	private String password;
	private Boolean isAdmin;

	public Membre() {
	}

	public Membre(Integer noMembre) {
		this.id = noMembre;
	}

	public Membre(String prenom, String nom, String password, Integer noMembre) {
		this.prenom = prenom;
		this.nom = nom;
		this.password = password;
		this.id = noMembre;
		this.isAdmin = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String toString() {
		String s = "";
		if (this.isAdmin)
			s += "[ADMIN] ";
		else
			s += "        ";

		s += "(" + this.id.toString() + ") " + this.nom + ", " + this.prenom + " pw:" + this.password;

		return s;
	}
}
