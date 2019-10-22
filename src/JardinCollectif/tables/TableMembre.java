package JardinCollectif.tables;

public class TableMembre extends SQLTable {
	private Integer id;
	private String nom;
	private String prenom;
	private String password;
	private Boolean isAdmin;

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
	
	// management
	public static Boolean promote(TableMembre tm) {
		// TODO: mark tm isAdmin to true, save to DB
		
		return false;
	}
	
	public static Boolean display() {
		// TODO: show all members
		
		return false;
	}

	// SQL
	@Override
	public Boolean create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean destroy() {
		// TODO Auto-generated method stub
		return null;
	}
}
