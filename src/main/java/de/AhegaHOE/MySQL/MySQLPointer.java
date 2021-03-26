package de.AhegaHOE.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySQLPointer {

	public static boolean isUserExists(UUID uuid) {
		try {
			PreparedStatement ps;
			ps = MySQL.getConnection().prepareStatement("SELECT PLAYERNAME FROM playerdata WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void delete(UUID uuid) {
		if (isUserExists(uuid)) {
			try {
				PreparedStatement ps = MySQL.getConnection().prepareStatement("DELETE FROM playerdata WHERE UUID = ?");
				ps.setString(1, uuid.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void registerPlayer(UUID uuid, String Playername) {

		if (!isUserExists(uuid)) {
			try {
				PreparedStatement ps = MySQL.getConnection()
						.prepareStatement("INSERT INTO playerdata (UUID, PLAYERNAME) VALUES (?,?)");
				ps.setString(1, uuid.toString());
				ps.setString(2, Playername.toString());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static String getUsername(UUID uuid) {
		try {
			PreparedStatement ps;
			ps = MySQL.getConnection().prepareStatement("SELECT PLAYERNAME FROM playerdata WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			return rs.toString();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}
	}

	public static void updateUsername(UUID uuid, String playername) {
		try {
			PreparedStatement ps;
			ps = MySQL.getConnection().prepareStatement("UPDATE PLAYERDATA SET PLAYERNAME = ? WHERE UUID = ?");
			ps.setString(1, playername);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean hasPhone(UUID uuid) {
		try {
			PreparedStatement ps;
			ps = MySQL.getConnection().prepareStatement("SELECT PHONE FROM PHONE WHERE UUID = ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
