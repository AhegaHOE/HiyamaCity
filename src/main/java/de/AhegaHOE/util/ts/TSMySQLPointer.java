package de.AhegaHOE.util.ts;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.AhegaHOE.MySQL.MySQL;
import de.AhegaHOE.main.Main;
import de.AhegaHOE.ranks.RankScoreboard;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class TSMySQLPointer {


    public static void linkAccount(Client c, String uuid) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE TS_DATA SET UUID = ? WHERE UID = ?");
            ps.setString(1, uuid);
            ps.setString(2, c.getUniqueIdentifier());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Client getClientByUID(String uid) {
        return Main.ts3Api.getClientByUId(uid);
    }

    public static Client getClientByUUID(UUID uuid) {
        return Main.ts3Api.getClientByUId(getUIDbyUUID(uuid));
    }

    public static boolean isUserExist(String uid) {
        return !getTSInfo(uid).isEmpty();
    }

    public static boolean isUserExist(UUID uuid) {
        return !getTSInfo(uuid).isEmpty();
    }

    public static boolean isConfirmed(String uid) {
        if (getTSInfo(uid).isEmpty()) {

            return false;
        }
        return Boolean.parseBoolean(getTSInfo(uid).get(3));
    }

    public static boolean isConfirmed(UUID uuid) {
        if (getTSInfo(uuid).isEmpty()) {
            return false;
        }
        return Boolean.parseBoolean(getTSInfo(uuid).get(3));
    }

    public static String getRankByUID(String uid) {
        if (!isUserExist(uid)) {
            return null;
        }
        return getTSInfo(uid).get(2);
    }

    public static String getRankByUUID(UUID uuid) {
        if (!isUserExist(uuid)) {
            return null;
        }
        return getTSInfo(uuid).get(2);
    }

    public static String getUUIDbyUID(String uid) {
        if (!isUserExist(uid)) {
            return null;
        }
        return getTSInfo(uid).get(0);
    }

    public static String getUIDbyUUID(UUID uuid) {
        if (!isUserExist(uuid)) {
            return null;
        }
        return getTSInfo(uuid).get(1);
    }

    public static ArrayList<String> getTSInfo(UUID uuid) {
        PreparedStatement ps;
        ArrayList<String> info = new ArrayList<>();
        try {
            ps = MySQL.getConnection().prepareStatement("SELECT * FROM TS_DATA WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                info.add(rs.getString("UUID"));
                info.add(rs.getString("UID"));
                info.add(rs.getString("RANK"));
                info.add(String.valueOf(rs.getBoolean("CONFIRMED")));
            }
            ps.close();
            return info;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return info;

    }

    public static ArrayList<String> getTSInfo(String uid) {
        PreparedStatement ps;
        ArrayList<String> info = new ArrayList<>();
        try {
            ps = MySQL.getConnection().prepareStatement("SELECT * FROM TS_DATA WHERE UID = ?");
            ps.setString(1, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                info.add(rs.getString("UUID"));
                info.add(rs.getString("UID"));
                info.add(rs.getString("RANK"));
                info.add(String.valueOf(rs.getBoolean("CONFIRMED")));
            }
            ps.close();
            return info;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return info;

    }

    public static void setConfirmed(String uid, boolean bool) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE TS_DATA SET CONFIRMED = ? WHERE UID = ?");
            ps.setBoolean(1, bool);
            ps.setString(2, uid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void setConfirmed(UUID uuid, boolean bool) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE TS_DATA SET CONFIRMED = ? WHERE UUID = ?");
            ps.setBoolean(1, bool);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void setRank(Client c, String rank) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE TS_DATA SET RANK = ? WHERE UID = ?");
            ps.setString(1, rank);
            ps.setString(2, c.getUniqueIdentifier());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTeamspeakSync(UUID uuid) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("DELETE FROM TS_DATA WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registerTeamspeakSynchronization(UUID uuid, String uid) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("INSERT INTO TS_DATA (UUID, UID, RANK, CONFIRMED) VALUES (?,?,?,?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, uid);
            ps.setString(3, RankScoreboard.getRankName(Bukkit.getPlayer(uuid)));
            ps.setBoolean(4, false);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sendVerificationMessageTeamspeak(String uuid, String uid) {

        String uuidString = ((uuid == null)? "<UUID>" : uuid);

        Client c = Main.ts3Api.getClientByUId(uid);
        System.out.println("is user exist: " + isUserExist(uid));
        System.out.println("is confirmed: " + isConfirmed(uid));

        if(isUserExist(uid) && isConfirmed(uid)) {
            return;
        }

        if (!isUserExist(uid) || isConfirmed(uid)) {
            Main.ts3Api.sendPrivateMessage(c.getId(), "[COLOR=#9a9a9a][[/COLOR][B][COLOR=#ffff55]![/COLOR][/B][COLOR=#9a9a9a]][/COLOR] Willkommen auf dem Community-Teamspeak von [COLOR=#55ffff]HiyamaCity![/COLOR][COLOR=#9a9a9a][[/COLOR][B][COLOR=#ffff55]![/COLOR][/B][COLOR=#9a9a9a]][/COLOR]\n" +
                    "Du hast deinen Minecraft-Account noch nicht verifiziert. Schreibe dazu: \"/ts verify " + c.getUniqueIdentifier() + "\" auf unserem Minecraft-Server (HiyamaCity.de) um dich zu verifizieren.");
            return;
        } else if (isUserExist(uid) && !isConfirmed(uid)) {
            Main.ts3Api.sendPrivateMessage(c.getId(), "[COLOR=#55FF55]Du hast deinen Minecraft-Account verbunden. Antworte nurnoch mit[/COLOR] [COLOR=#55FFFF]\"!verify " + uuidString + "\"[/COLOR] [COLOR=#55FF55]um deine Identität zu bestätigen.[/COLOR]");
            return;
        }
    }



}


