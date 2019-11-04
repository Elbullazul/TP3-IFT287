package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.*;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;
import JardinCollectif.objects.Plante;

public abstract class TablePlante {
	public static ArrayList<Plante> loadAll() {
		ArrayList<Plante> tl = new ArrayList<Plante>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Plantes ORDER BY nom");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Plante o = new Plante();

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
	
	public static void persist(Plante a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Plante a) {
		if (a != null)
        {
            JardinCollectif.cx.getConnection().remove(a);
            return true;
        }
        return false;
	}

	public static Plante load(String pk_plante) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Plantes WHERE nom=?");
			ps.setString(1, pk_plante);

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
