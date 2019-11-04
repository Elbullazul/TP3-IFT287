package JardinCollectif.tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.*;

import JardinCollectif.Controller;
import JardinCollectif.JardinCollectif;
import JardinCollectif.objects.Attribution;
import JardinCollectif.objects.Culture;

public abstract class TableCulture {
	public static ArrayList<Culture> loadAll() {
		ArrayList<Culture> tl = new ArrayList<Culture>();
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures ORDER BY nomLot, nomPlante");

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Culture o = new Culture();
				
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

	public static void persist(Culture a) {
		JardinCollectif.cx.getConnection().persist(a);
	}

	public static Boolean remove(Culture a) {
		if (a != null)
        {
            JardinCollectif.cx.getConnection().remove(a);
            return true;
        }
        return false;
	}
	
	public static Culture load(String pk_lot, String pk_plante, Integer pk_membre) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomLot=? AND nomPlante=? AND idMembre=?");
			ps.setString(1, pk_lot);
			ps.setString(2, pk_plante);
			ps.setInt(3, pk_membre);

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
	
	public static Boolean existsLot(String pk_lot) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomLot=?");
			ps.setString(1, pk_lot);
			
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
	
	public static Boolean existsPlante(String pk_plante) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE nomPlante=?");
			ps.setString(1, pk_plante);
			
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
	
	public static Boolean existsMembre(Integer pk_membre) {
		PreparedStatement ps;

		try {
			Connection cnn = JardinCollectif.cx.getConnection();

			ps = cnn.prepareStatement("SELECT * FROM Cultures WHERE idMembre=?");
			ps.setInt(1, pk_membre);
			
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
