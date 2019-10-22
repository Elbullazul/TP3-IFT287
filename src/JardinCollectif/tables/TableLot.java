package JardinCollectif.tables;

public class TableLot extends SQLTable {
	private String nom;
	private Integer max_collab;

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
