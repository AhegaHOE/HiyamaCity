package de.AhegaHOE.util.banmanagement;

import de.AhegaHOE.MySQL.MySQL;
import de.AhegaHOE.util.Util;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.*;

public class Banning implements CommandExecutor, Listener {
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.forLanguageTag("de"));


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {


        // /ban <name> [grund]
        if (!(sender.hasPermission("system.ban"))) {
            sender.sendMessage("§cFehler: Du hast dazu keine Rechte!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cFehler: Benutze /ban <Spieler> [Grund]");
            return true;
        }

        UUID uuid = Bukkit.getPlayerUniqueId(args[0]);

        if (Banning.hasActiveBans(uuid)) {
            sender.sendMessage("§cFehler: Der Spieler ist bereits gebannt.");
            return true;
        }

        String message = "";

        for (int i = 1; i < args.length; i++) {
            message += args[i] + " ";
        }

        message = message.trim();

        sender.sendMessage("§7Du hast §9" + args[0] + " §aerfolgreich §7gebannt.\nGrund: " + message);

        Banning.createBan(uuid, ((sender instanceof Player) ? ((Player) sender).getUniqueId() : null), message);


        if (Bukkit.getPlayer(uuid) == null) {

            return true;
        }

        Player t = Bukkit.getPlayer(uuid);

        if (Bukkit.getPlayer(uuid) != null)
            Bukkit.getPlayer(uuid).kickPlayer("§cDein Account wurde von HiyamaCity gesperrt.\n" +
                    " \n" +
                    ((message == "") ? "" : "Grund: " + message + "\n") +
                    "Ban-ID: " + Banning.getBanId(uuid) + "\n" +
                    "Tag des Bannes: §4" + formatter.format(System.currentTimeMillis()) + "§c\n" +
                    ((Banning.getBanEnd(uuid) == 0) ? "" : "Tag der Entbannung: §4" + formatter.format(Banning.getBanEnd(uuid)) + "§c\n") +
                    ((Banning.getBanEnd(uuid) == 0) ? "" : "Verbleibende Zeit: " + Util.getRemainingTime(getBanEnd(uuid)) + "\n") +
                    " \n" +
                    "Wir geben dir die Möglichkeit einen Entbannungsantrag in unserem Forum zu stellen.\n" +
                    "https://hiyamacity.de/forum/index.php?board/21-entbannungsanträge/");



        /* TODO: Ban-System
        TODO: TIMEBAN
        TODO: CheckBans (spieler) gibt einem alle Bans von einem Spieler aus + ob aktiv oder nicht.
        TODO: es ist möglich auf die Ban-IDs zu klicken/hovern um direkte infos zu dem ban zu bekommen.

        DATABASE STRUCTURE:

        » banned uuid (varchar)
        » ban-id (varchar)
        » ban created by uuid (varchar)
        » ban start (long)
        » ban end (long)
        » vorherige bans (varchar)
        » grund (varchar)
        »



         /ban <Spieler> [Grund]
         » jeder ban bekommt eine ban-id
         » vorgegebene gründe mit tabcompleter
            » <gründe>
         » eigene gründe

         /checkban <ban-id>
         » gibt infos über den ban
            » spieler + uuid + hover-command (/checkplayer <spieler>)
            » start
            » ende
            » grund
            »


            BANID, UUID, BUUID, ISACTIVE, REASON, START, END


        */


        return false;
    }

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent e) {

        UUID uuid = e.getPlayer().getUniqueId();
        long current = System.currentTimeMillis();
        long end = getBanEnd(uuid);
        long start = getBanStart(uuid);


        if (end != 0 && end < current) {
            deactivateBan(uuid);
        }

        String message = getBanReason(uuid);


        if (!hasActiveBans(uuid)) {
            return;
        }
        System.out.println(end);
        e.disallow(PlayerLoginEvent.Result.KICK_BANNED, "§cDein Account wurde von HiyamaCity gesperrt.\n" +
                " \n" +
                ((message == "") ? "" : "Grund: " + message + "\n") +
                "Ban-ID: " + Banning.getBanId(uuid) + "\n" +
                "Tag des Bannes: §4" + formatter.format(start) + "§c\n" +
                ((Banning.getBanEnd(uuid) == 0) ? "" : "Tag der Entbannung: §4" + formatter.format(Banning.getBanEnd(uuid)) + "§c\n") +
                ((Banning.getBanEnd(uuid) == 0) ? "" : "Verbleibende Zeit: " + Util.getRemainingTime(end) + "\n") +
                " \n" +
                "Wir geben dir die Möglichkeit einen Entbannungsantrag in unserem Forum zu stellen.\n" +
                "https://hiyamacity.de/forum/index.php?board/21-entbannungsanträge/");


    }

    public static void deactivateBan(UUID uuid) {
        PreparedStatement ps;
        try {
            ps = MySQL.getConnection().prepareStatement("UPDATE BANS SET ISACTIVE = ? WHERE UUID = ?");
            ps.setBoolean(1, false);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {

        }

    }

    public static boolean isBanned(UUID uuid) {

        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("SELECT ISACTIVE FROM BANS WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getBoolean("ISACTIVE"));
                return rs.getBoolean("ISACTIVE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static String getBanId(UUID uniqueId) {
        PreparedStatement ps;
        try {
            ps = MySQL.getConnection().prepareStatement("SELECT BANID FROM BANS WHERE ISACTIVE LIKE ? AND UUID LIKE ?");
            ps.setBoolean(1, true);
            ps.setString(2, uniqueId.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("BANID");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String getBanReason(UUID uuid) {
        PreparedStatement ps;

        try {
            ps = MySQL.getConnection().prepareStatement("SELECT REASON FROM BANS WHERE ISACTIVE LIKE ? AND UUID LIKE ?");
            ps.setBoolean(1, true);
            ps.setString(2, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("REASON");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Long getBanEnd(UUID uuid) {
        PreparedStatement ps;

        try {

            ps = MySQL.getConnection().prepareStatement("SELECT END FROM BANS WHERE ISACTIVE LIKE ? AND UUID LIKE ?");
            ps.setBoolean(1, true);
            ps.setString(2, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getLong("END");
            }
            ps.close();
            return (long) 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static Long getBanStart(UUID uuid) {
        PreparedStatement ps;

        try {

            ps = MySQL.getConnection().prepareStatement("SELECT START FROM BANS WHERE ISACTIVE LIKE ? AND UUID LIKE ?");
            ps.setBoolean(1, true);
            ps.setString(2, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getLong("START");
            }
            ps.close();
            return (long) 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void createBan(UUID victim, UUID punisher, String reason, Long end) {
        long start = System.currentTimeMillis();
        String banId = generateBanId();
        PreparedStatement ps;


        try {
            ps = MySQL.getConnection().prepareStatement("INSERT INTO BANS (BANID, UUID, BUUID, ISACTIVE, REASON, START, END) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, banId);
            ps.setString(2, victim.toString());
            if ((punisher == null)) {
                ps.setString(3, null);
            } else {
                ps.setString(3, punisher.toString());
            }
            ps.setBoolean(4, true);
            ps.setString(5, reason);
            ps.setLong(6, start);
            ps.setLong(7, end);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createBan(UUID victim, UUID punisher, String reason) {
        long start = System.currentTimeMillis();
        String banId = generateBanId();
        PreparedStatement ps;

        try {
            ps = MySQL.getConnection().prepareStatement("INSERT INTO BANS (BANID, UUID, BUUID, ISACTIVE, REASON, START) VALUES (?,?,?,?,?,?)");
            ps.setString(1, banId);
            ps.setString(2, victim.toString());
            if ((punisher == null)) {
                ps.setString(3, null);
            } else {
                ps.setString(3, punisher.toString());
            }
            ps.setBoolean(4, true);
            ps.setString(5, reason);
            ps.setLong(6, start);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createBan(UUID victim, UUID punisher) {
        long start = System.currentTimeMillis();
        String banId = generateBanId();
        PreparedStatement ps;

        try {
            ps = MySQL.getConnection().prepareStatement("INSERT INTO BANS (BANID, UUID, BUUID, ISACTIVE, START) VALUES (?,?,?,?,?)");
            ps.setString(1, banId);
            ps.setString(2, victim.toString());
            ps.setString(3, punisher.toString());
            ps.setBoolean(4, true);
            ps.setLong(5, start);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createBan(UUID victim, String reason, Long end) {
        long start = System.currentTimeMillis();
        String banId = generateBanId();
        PreparedStatement ps;

        try {
            ps = MySQL.getConnection().prepareStatement("INSERT INTO BANS (BANID, UUID, ISACTIVE, REASON, START, END) VALUES (?,?,?,?,?,?)");
            ps.setString(1, banId);
            ps.setString(2, victim.toString());
            ps.setBoolean(3, true);
            ps.setString(4, reason);
            ps.setLong(5, start);
            ps.setLong(6, end);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean hasBans(UUID uuid) {
        PreparedStatement ps;
        try {
            ps = MySQL.getConnection().prepareStatement("SELECT BANID FROM BANS WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ArrayList<String> getBanIDs(UUID uuid) {
        PreparedStatement ps;
        ArrayList<String> banIDs = new ArrayList<>();
        try {
            ps = MySQL.getConnection().prepareStatement("SELECT BANID FROM BANS WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                banIDs.add(rs.getString("BANID"));
            }
            ps.close();
            return banIDs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return banIDs;
    }


    public static ArrayList<Object> getBanIDInfo(String banID) {
        PreparedStatement ps;
        ArrayList<Object> infos = new ArrayList<>();

        try {
            ps = MySQL.getConnection().prepareStatement("SELECT * FROM BANS WHERE BANID = ?");
            ps.setString(1, banID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                infos.add(rs.getString("BANID"));
                infos.add(rs.getString("UUID"));
                infos.add(rs.getString("BUUID"));
                infos.add(rs.getBoolean("ISACTIVE"));
                infos.add(rs.getString("REASON"));
                infos.add(rs.getLong("START"));
                infos.add(rs.getLong("END"));
            }
            ps.close();
            return infos;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return infos;
    }

    public static void createBan(UUID victim, String reason) {
        long start = System.currentTimeMillis();
        String banId = generateBanId();
        PreparedStatement ps;

        try {
            ps = MySQL.getConnection().prepareStatement("INSERT INTO BANS (BANID, UUID, ISACTIVE, REASON, START) VALUES (?,?,?,?,?)");
            ps.setString(1, banId);
            ps.setString(2, victim.toString());
            ps.setBoolean(3, true);
            ps.setString(4, reason);
            ps.setLong(5, start);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createBan(UUID victim, Long end) {
        long start = System.currentTimeMillis();
        String banId = generateBanId();
        PreparedStatement ps;

        try {
            ps = MySQL.getConnection().prepareStatement("INSERT INTO BANS (BANID, UUID, ISACTIVE, START, END) VALUES (?,?,?,?,?)");
            ps.setString(1, banId);
            ps.setString(2, victim.toString());
            ps.setBoolean(3, true);
            ps.setLong(4, start);
            ps.setLong(5, end);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createBan(UUID victim) {
        long start = System.currentTimeMillis();
        String banId = generateBanId();
        PreparedStatement ps;

        try {
            ps = MySQL.getConnection().prepareStatement("INSERT INTO BANS (BANID, UUID, ISACTIVE, START) VALUES (?,?,?,?)");
            ps.setString(1, banId);
            ps.setString(2, victim.toString());
            ps.setBoolean(3, true);
            ps.setLong(4, start);
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static String generateBanId() {
        String banId = RandomStringUtils.randomAlphanumeric(12);
        if (isBanIdAlreadyUsed(banId)) {
            generateBanId();
        }
        return banId;
    }

    public static boolean isBanIdAlreadyUsed(String banId) {

        PreparedStatement ps;

        try {
            ps = MySQL.getConnection().prepareStatement("SELECT BANID FROM BANS WHERE BANID = ?");
            ps.setString(1, banId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    public static boolean hasActiveBans(UUID uuid) {
        PreparedStatement ps;
        try {
            ps = MySQL.getConnection().prepareStatement("SELECT ISACTIVE, UUID FROM BANS WHERE ISACTIVE LIKE ? AND UUID LIKE ?");
            ps.setBoolean(1, true);
            ps.setString(2, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("ISACTIVE")) {
                    System.out.println(rs.getBoolean("ISACTIVE"));
                    return true;
                }
            }
            ps.close();
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean isLong(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
