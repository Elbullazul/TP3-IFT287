package JardinCollectif.tables;

import java.sql.Date;

public class TableDemande extends SQLTable {
	private Date id;
	private Integer idMembre;
	private String nomLot;
	private Boolean status;

	public Date getId() {
		return id;
	}

	public void setId(Date id) {
		this.id = id;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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
