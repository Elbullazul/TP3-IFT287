package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import JardinCollectif.JardinCollectif;

public class TableDemande extends SQLTable {
	private Date id;
	private Integer idMembre;
	private String nomLot;
	private Integer status;
	
	public static Integer STATUS_PENDING = -1;
	public static Integer STATUS_DENIED = 0;
	public static Integer STATUS_APPROVED = 1;
	
	public TableDemande() {
	}
	
	public TableDemande(Integer idMembre) {
		this.idMembre = idMembre;
	}
	
	public TableDemande(String nomLot) {
		this.nomLot = nomLot;
	}
	
	public TableDemande(String nomLot, Integer idMembre) {
		this.nomLot = nomLot;
		this.idMembre = idMembre;
		this.status = STATUS_PENDING;
	}
	
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String toString() {
		String s = "Demande soumise par: " + this.idMembre.toString() + " pour le lot " + this.nomLot + " Status: ";
		
		switch (this.status) {
		case -1:
			s += "Pending";
			break;
		case 0:
			s += "denied";
			break;
		case 1:
			s += "approved";
			break;
		}
		
		return s;
	}

	// SQL
	public static ArrayList<TableDemande> fetchAll() {
		ArrayList<TableDemande> tl = new ArrayList<TableDemande>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT idMembre, nomLot, status FROM Demandes WHERE status=?");
			ps.setInt(1, STATUS_PENDING);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableDemande o = new TableDemande();

				o.setIdMembre(rs.getInt(1));
				o.setNomLot(rs.getString(2));
				o.setStatus(rs.getInt(3));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return tl;
	}
	
	@Override
	public Boolean insert() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("INSERT INTO Demandes (idMembre, nomLot, status) VALUES(?, ?, ?)");
			ps.setInt(1, this.idMembre);
			ps.setString(2, this.nomLot);
			ps.setInt(3,  this.status);

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
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("UPDATE Demandes SET status=? WHERE idMembre=? AND nomLot=? AND status=?");
			ps.setInt(1, this.status);
			ps.setInt(2, this.idMembre);
			ps.setString(3, this.nomLot);
			ps.setInt(4,  STATUS_PENDING);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Update failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean destroy() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Demandes WHERE idMembre=? AND nomLot=? AND status=?");
			ps.setInt(1, this.idMembre);
			ps.setString(2, this.nomLot);
			ps.setInt(3, this.status);
			
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

			ps = cnn.prepareStatement("DELETE FROM Demandes WHERE nomLot=?");
			ps.setString(1, this.nomLot);
			
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

			ps = cnn.prepareStatement("DELETE FROM Demandes WHERE idMembre=?");
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

			ps = cnn.prepareStatement("SELECT idMembre, nomLot, status FROM Demandes WHERE idMembre=? AND nomLot=? AND status=?");
			ps.setInt(1, this.idMembre);
			ps.setString(2, this.nomLot);
			ps.setInt(3, STATUS_PENDING);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				status= rs.getInt(3);

				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
