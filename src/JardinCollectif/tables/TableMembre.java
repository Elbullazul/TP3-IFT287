package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.*;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;
import JardinCollectif.objects.Membre;

public abstract class TableMembre {
	public static ArrayList<Membre> loadAll() {
		ArrayList<Membre> tl = new ArrayList<Membre>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Membres ORDER BY id");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Membre o = new Membre();

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

	public static void persist(Membre a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Membre a) {
		if (a != null)
        {
            JardinCollectif.cx.getConnection().remove(a);
            return true;
        }
        return false;
	}

	public static Membre load(Integer pk_membre) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Membres WHERE id = ?");
			ps.setInt(1, pk_membre);

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
