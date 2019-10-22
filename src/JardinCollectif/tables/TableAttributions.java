package JardinCollectif.tables;

public class TableAttributions extends SQLTable {
	private Integer idMembre;
	private String nomLot;

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

	public static Boolean addAttribution(TableMembre tm, TableLot tl) {
		// TODO: implémenter l'envoi des données à la BD

		return false;
	}

	public static Boolean delAttribution(TableAttributions ta) {
		// TODO: implement

		return false;
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
