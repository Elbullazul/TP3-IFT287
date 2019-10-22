package JardinCollectif.tables;

public class TableCulture extends SQLTable {
	private String nomLot;
	private String nomPlante;
	private Integer noMembre;
	private Integer nbExemplaires;

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

	public Integer getNoMembre() {
		return noMembre;
	}

	public void setNoMembre(Integer noMembre) {
		this.noMembre = noMembre;
	}

	public Integer getNbExemplaires() {
		return nbExemplaires;
	}

	public void setNbExemplaires(Integer nbExemplaires) {
		this.nbExemplaires = nbExemplaires;
	}

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
