package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.*;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;
import JardinCollectif.objects.Lot;

public abstract class TableLot {
	public static ArrayList<Lot> loadAll() {
		ArrayList<Lot> tl = new ArrayList<Lot>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Lots ORDER BY nom");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Lot o = new Lot();

				o.setNom(rs.getString(1));
				o.setMax_collab(rs.getInt(2));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			return null;
		}

		return tl;
	}
	
	public static Void persist(Lot a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Lot a) {
		if (a != null)
        {
            JardinCollectif.cx.getConnection().remove(a);
            return true;
        }
        return false;
	}

	public static Lot load(String pk_lot) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Lots WHERE nom=?");
			ps.setString(1, pk_lot);

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
