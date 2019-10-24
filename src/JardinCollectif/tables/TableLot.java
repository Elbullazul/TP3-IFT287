package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import JardinCollectif.JardinCollectif;

public class TableLot extends SQLTable {
	private String nom;
	private Integer max_collab;

	public TableLot(String nomLot) {
		this.nom = nomLot;
	}
	
	public TableLot(String nomLot, Integer maxCollabs) {
		this.nom = nomLot;
		this.max_collab = maxCollabs;
	}
	
	public TableLot() {
	}

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
	public String toString() {
		return "Lot " + this.nom + ": max_collab: " + this.max_collab.toString(); 
	}
	
	public static ArrayList<TableLot> fetchAll() {
		ArrayList<TableLot> tl = new ArrayList<TableLot>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Lots ORDER BY nom");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableLot o = new TableLot();

				o.setNom(rs.getString(1));
				o.setMax_collab(rs.getInt(2));

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

			ps = cnn.prepareStatement("INSERT INTO Lots (nom, max_collab) VALUES(?, ?)");
			ps.setString(1, this.nom);
			ps.setInt(2, this.max_collab);

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
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("UPDATE Lots SET max_collab=?) WHERE nom=?");
			ps.setInt(1, this.max_collab);
			ps.setString(2, this.nom);

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

			ps = cnn.prepareStatement("DELETE FROM Lots WHERE nom=?");
			ps.setString(1, this.nom);
			
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

			ps = cnn.prepareStatement("SELECT * FROM Lots WHERE nom=?");
			ps.setString(1, this.nom);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				max_collab = rs.getInt(2);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
}
