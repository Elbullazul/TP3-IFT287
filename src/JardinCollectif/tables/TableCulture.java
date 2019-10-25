package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import JardinCollectif.Controller;
import JardinCollectif.JardinCollectif;

public class TableCulture extends SQLTable {
	private String nomLot;
	private String nomPlante;
	private Integer idMembre;
	private Integer nbExemplaires;
	private Date plantee;
	
	public static Integer NOM_LOT = 0;
	public static Integer NOM_PLANTE = 1;
	
	public TableCulture() {
	}
	
	public TableCulture(String nom, Integer mode) {
		if (mode == NOM_LOT)
			this.nomLot = nom;
		else if (mode == NOM_PLANTE)
			this.nomPlante = nom;
	}
	
	public TableCulture(Integer idMembre) {
		this.idMembre = idMembre;
	}
	
	public TableCulture(String nomLot, String nomPlante, Integer idMembre) {
		this.nomLot = nomLot;
		this.nomPlante = nomPlante;
		this.idMembre = idMembre;
	}
	
	public TableCulture(String nomLot, String nomPlante, Integer idMembre, Integer nbExemplaires, Date datePlantation) {
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

	@Override
	public String toString() {
		return this.nbExemplaires + " " + this.nomPlante + "(s) en pousse depuis " + this.plantee.toString() + " dans " + this.nomLot + " par membre " + this.idMembre; 
	}

	// SQL
	public static ArrayList<TableCulture> fetchAll() {
		ArrayList<TableCulture> tl = new ArrayList<TableCulture>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures ORDER BY nomLot, nomPlante");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableCulture o = new TableCulture();
				
				o.setNomPlante(rs.getString(1));
				o.setNomLot(rs.getString(2));
				o.setIdMembre(rs.getInt(3));
				o.setNbExemplaires(rs.getInt(4));
				o.setPlantee(rs.getDate(5));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			return null;
		}

		return tl;
	}
	
	public ArrayList<TableCulture> fetchAllLot() {
		ArrayList<TableCulture> tl = new ArrayList<TableCulture>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomLot=? ORDER BY nomLot, nomPlante");
			ps.setString(1, this.nomLot);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableCulture o = new TableCulture();
				
				o.setNomPlante(rs.getString(1));
				o.setNomLot(rs.getString(2));
				o.setIdMembre(rs.getInt(3));
				o.setNbExemplaires(rs.getInt(4));
				o.setPlantee(rs.getDate(5));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			return null;
		}

		return tl;
	}

	@Override
	public Boolean insert() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("INSERT INTO Cultures (nomPlante, nomLot, idMembre, quantitee, plantee) VALUES(?, ?, ?, ?, ?)");
			ps.setString(1, this.nomPlante);
			ps.setString(2, this.nomLot);
			ps.setInt(3, this.idMembre);
			ps.setInt(4,  this.nbExemplaires);
			ps.setDate(5, this.plantee);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Creation failed");

			cnn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public Boolean update() {
		// TODO: pas utilisé dans le devoir

		return false;
	}

	@Override
	public Boolean destroy() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE nomLot=? AND nomPlante=? AND idMembre=? AND plantee=?");
			ps.setString(1, this.nomLot);
			ps.setString(2, this.nomPlante);
			ps.setInt(3, this.idMembre);
			ps.setDate(4, this.plantee);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean destroyLot() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE nomLot=?");
			ps.setString(1, this.nomLot);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean destroyPlante() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE nomPlante=?");
			ps.setString(1, this.nomPlante);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean destroyMembre() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE idMembre=?");
			ps.setInt(1, this.idMembre);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean fetch() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomLot=? AND nomPlante=? AND idMembre=?");
			ps.setString(1, this.nomLot);
			ps.setString(2, this.nomPlante);
			ps.setInt(3, this.idMembre);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				nbExemplaires = rs.getInt(4);
				plantee = rs.getDate(5);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean existsLot() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomLot=?");
			ps.setString(1, this.nomLot);
			
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			}

			rs.close();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean existsPlante() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomPlante=?");
			ps.setString(1, this.nomPlante);
			
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			}

			rs.close();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean existsMembre() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE idMembre=?");
			ps.setInt(1, this.idMembre);
			
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			}

			rs.close();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public Boolean recolterPlante() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();
			
			// charger la durée
			TablePlante tp = new TablePlante(this.nomPlante);
			tp.fetch();
			
			// Aditionner les jours de maturation
			Calendar c = Calendar.getInstance();
	        c.setTime(this.plantee);
	        c.add(Calendar.DATE, tp.getDuree());

			ps = cnn.prepareStatement("DELETE FROM Cultures WHERE nomLot=? AND nomPlante=? AND idMembre=? AND plantee <= ?");
			
			ps.setString(1, this.nomLot);
			ps.setString(2, this.nomPlante);
			ps.setInt(3, this.idMembre);
			ps.setDate(4, new Date(c.getTimeInMillis()));

			if (ps.executeUpdate() == 0)
				throw new SQLException("Reaping failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
}
