package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.*;

import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;
import JardinCollectif.objects.Demande;

public abstract class TableDemande {
	public static ArrayList<Demande> loadAll() {
		ArrayList<Demande> tl = new ArrayList<Demande>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT idMembre, nomLot, status FROM Demandes WHERE status=?");
			ps.setInt(1, STATUS_PENDING);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Demande o = new Demande();

				o.setIdMembre(rs.getInt(1));
				o.setNomLot(rs.getString(2));
				o.setStatus(rs.getInt(3));

				tl.add(o);
			}

			rs.close();
		} catch (SQLException e) {
			return null;
		}

		return tl;
	}
	
	public static void persist(Demande a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Demande a) {
		if (a != null)
        {
            JardinCollectif.cx.getConnection().remove(a);
            return true;
        }
        return false;
	}
	
	public static Demande load(Integer pk_membre, String pk_lot, Integer status) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT idMembre, nomLot, status FROM Demandes WHERE idMembre=? AND nomLot=? AND status=?");
			ps.setInt(1, pk_membre);
			ps.setString(2, pk_lot);
			ps.setInt(3, status);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				status= rs.getInt(3);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public static Boolean existsMembre(Integer pk_membre) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Demandes WHERE idMembre=?");
			ps.setInt(1, pk_membre);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				status = rs.getInt(3);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
	
	public static Boolean existsLot(String pk_lot) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Demandes WHERE nomLot=?");
			ps.setString(1, pk_lot);

			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				rs.close();

				throw new SQLException("Not found");
			} else {
				status = rs.getInt(3);

				rs.close();
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}
}
