package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import JardinCollectif.JardinCollectif;

public class TableAttribution extends SQLTable {
	private Integer idMembre;
	private String nomLot;

	public TableAttribution() {
	}

	public TableAttribution(String nomLot) {
		this.nomLot = nomLot;
	}
	
	public TableAttribution(Integer noMembre) {
		this.idMembre = noMembre;
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

	// SQL
	public String toString() {
		return "Membre " + this.idMembre.toString() + " collabore sur le lot " + this.nomLot; 
	}

	public static ArrayList<TableAttribution> fetchAll() {
		ArrayList<TableAttribution> tl = new ArrayList<TableAttribution>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Attributions");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableAttribution o = new TableAttribution();

				o.setIdMembre(rs.getInt(1));
				o.setNomLot(rs.getString(2));

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

			ps = cnn.prepareStatement("INSERT INTO Attributions (nomLot, idMembre) VALUES(?, ?)");
			ps.setInt(1, this.idMembre);
			ps.setString(2, this.nomLot);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Creation failed");

			cnn.commit();
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	@Override
	public Boolean update() {
		// TODO: pas implémenté car pas utilisé dans le cadre du devoir (renommer lot)

		return false;
	}

	@Override
	public Boolean destroy() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Attributions WHERE nomLot=? AND idMembre=?");
			ps.setString(1, this.nomLot);
			ps.setInt(2, this.idMembre);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public Boolean destroyMembre() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Attributions WHERE idMembre=?");
			ps.setInt(1, this.idMembre);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public Boolean destroyLot() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("DELETE FROM Attributions WHERE nomLot=?");
			ps.setString(1, this.nomLot);

			if (ps.executeUpdate() == 0)
				throw new SQLException("Deletion failed");

			cnn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public Boolean fetch() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Attributions WHERE nomLot=? AND idMembre=?");
			ps.setString(1, this.nomLot);
			ps.setInt(2, this.idMembre);

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
	
	public Boolean notLast() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Attributions WHERE idMembre!=?");
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
}
