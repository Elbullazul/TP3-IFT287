package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import JardinCollectif.JardinCollectif;

public class TablePlante extends SQLTable {
	private String nom;
	private Integer duree;

	public TablePlante() {
	}
	
	public TablePlante(String nom) {
		this.nom = nom;
	}
	
	public TablePlante(String nom, Integer duree) {
		this.nom = nom;
		this.duree = duree;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nomPlante) {
		this.nom = nomPlante;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	public String toString() {
		return "Nom: " + this.nom + "     Temps pour mûrir: " + this.duree.toString() + " jours"; 
	}

	// SQL
	public static ArrayList<TablePlante> fetchAll() {
		ArrayList<TablePlante> tl = new ArrayList<TablePlante>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Plantes ORDER BY nom");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TablePlante o = new TablePlante();

				o.setNom(rs.getString(1));
				o.setDuree(rs.getInt(2));

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

			ps = cnn.prepareStatement("INSERT INTO Plantes (nom, duree) VALUES(?, ?)");
			ps.setString(1, this.nom);
			ps.setInt(2, this.duree);

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

			ps = cnn.prepareStatement("UPDATE Plantes SET duree=?) WHERE nom=?");
			ps.setInt(1, this.duree);
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

			ps = cnn.prepareStatement("DELETE FROM Plantes WHERE nom=?");
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

			ps = cnn.prepareStatement("SELECT * FROM Plantes WHERE name=?");
			ps.setString(1, this.nom);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				duree = rs.getInt(2);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
}
