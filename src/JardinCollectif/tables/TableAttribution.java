package JardinCollectif.tables;

public class TableAttribution extends SQLTable {
	private Integer idMembre;
	private String nomLot;

	public TableAttribution() {
	}
	
	public TableAttribution(String nomLot, Integer noMembre) {
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
