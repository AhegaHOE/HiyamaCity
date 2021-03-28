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
                        .prepareStatement("INSERT INTO playerdata (UUID, PLAYERNAME, PLAYEDHOURS, PLAYEDMINUTES) VALUES (?,?,?,?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, Playername);
                ps.setInt(3, 0);
                ps.setInt(4, 0);
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

    public static int getPlayedHours(UUID uuid) {
        PreparedStatement ps;
        try {
            ps = MySQL.getConnection().prepareStatement("SELECT PLAYEDHOURS FROM playerdata WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return rs.getInt("PLAYEDHOURS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public static int getPlayedMinutes(UUID uuid) {
        PreparedStatement ps;
        try {
            ps = MySQL.getConnection().prepareStatement("SELECT PLAYEDMINUTES FROM playerdata WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return rs.getInt("PLAYEDMINUTES");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public static void updatePlayedHours(UUID uuid, int hours) {
        PreparedStatement ps;
        try {
            ps = MySQL.getConnection().prepareStatement("UPDATE PLAYERDATA SET PLAYEDHOURS = ? WHERE UUID = ?");
            ps.setInt(1, hours);
            ps.setString(2, uuid.toString());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayedMinutes(UUID uuid, int minutes) {
        PreparedStatement ps;
        try {
            ps = MySQL.getConnection().prepareStatement("UPDATE PLAYERDATA SET PLAYEDMINUTES = ? WHERE UUID = ?");
            ps.setInt(1, minutes);
            ps.setString(2, uuid.toString());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
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


}