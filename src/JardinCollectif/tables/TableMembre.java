package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import JardinCollectif.JardinCollectif;

public class TableMembre extends SQLTable {
	private Integer id;
	private String nom;
	private String prenom;
	private String password;
	private Boolean isAdmin;

	public TableMembre() {
	}

	public TableMembre(Integer noMembre) {
		this.id = noMembre;
	}

	public TableMembre(String prenom, String nom, String password, Integer noMembre) {
		this.prenom = prenom;
		this.nom = nom;
		this.password = password;
		this.id = noMembre;
		this.isAdmin = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	// management
	public String toString() {
		String s = "";
		if (this.isAdmin)
			s += "[ADMIN] ";
		else
			s += "        ";

		s += "(" + this.id.toString() + ") " + this.nom + ", " + this.prenom + " pw:" + this.password;

		return s;
	}

	public static ArrayList<TableMembre> fetchAll() {
		ArrayList<TableMembre> tl = new ArrayList<TableMembre>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Membres ORDER BY id");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TableMembre o = new TableMembre();

				o.setId(rs.getInt(1));
				o.setPrenom(rs.getString(2));
				o.setNom(rs.getString(3));
				o.setPassword(rs.getString(4));
				o.setIsAdmin(rs.getBoolean(5));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			return null;
		}

		return tl;
	}

	// SQL
	@Override
	public Boolean insert() {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("INSERT INTO Membres (id, prenom, nom, password, isAdmin) VALUES(?, ?, ?, ?, ?)");
			ps.setInt(1, this.id);
			ps.setString(2, this.prenom);
			ps.setString(3, this.nom);
			ps.setString(4, this.password);
			ps.setBoolean(5, this.isAdmin);

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

			ps = cnn.prepareStatement("UPDATE Membres SET prenom=?, nom=?, password=?, isAdmin=? WHERE id=?");
			ps.setString(1, this.prenom);
			ps.setString(2, this.nom);
			ps.setString(3, this.password);
			ps.setBoolean(4, this.isAdmin);
			
			ps.setInt(5, this.id);

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

			ps = cnn.prepareStatement("DELETE FROM Membres WHERE id=?");
			ps.setInt(1, this.id);
			
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

			ps = cnn.prepareStatement("SELECT * FROM Membres WHERE id = ?");
			ps.setInt(1, this.id);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				prenom = rs.getString(2);
				nom = rs.getString(3);
				password = rs.getString(4);
				isAdmin = rs.getBoolean(5);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
}
