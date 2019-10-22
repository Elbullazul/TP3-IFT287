package JardinCollectif.tables;

public class TablePlante extends SQLTable {
	private String nomPlante;
	private Integer duree;

	public String getNomPlante() {
		return nomPlante;
	}

	public void setNomPlante(String nomPlante) {
		this.nomPlante = nomPlante;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	@Override
	public Boolean insert() {
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

	@Override
	public Boolean fetch() {
		// TODO Auto-generated method stub
		return null;
	}
}
