package de.AhegaHOE.MySQL;

import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                ps.close();
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
                ps.close();

                PreparedStatement ps1 = MySQL.getConnection().prepareStatement("INSERT INTO MONEY (UUID, MONEY, BANK) VALUES (?,?,?)");
                ps1.setString(1, uuid.toString());
                ps1.setInt(2, 500);
                ps1.setInt(3, 2200);
                ps1.executeUpdate();
                ps1.close();

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
            ps.close();
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
            while (rs.next()) {
                return rs.getInt("PLAYEDHOURS");
            }
            ps.close();
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
            while (rs.next()) {
                return rs.getInt("PLAYEDMINUTES");
            }
            ps.close();
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
            ps.close();
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
            ps.close();
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
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getMoney(UUID uuid) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("SELECT MONEY FROM MONEY WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("MONEY");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public static boolean hasEnoughMoney(UUID uuid, int needed) {
        int currentMoney = MySQLPointer.getMoney(uuid);
        if (currentMoney == -1) {
            return false;
        } else if (currentMoney >= needed) {
            return true;
        }
        return false;
    }

    public static void removeMoney(UUID uuid, int amount) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE MONEY SET MONEY = ? WHERE UUID = ?");
            int currentMoney = MySQLPointer.getMoney(uuid);
            int remainingMoney = currentMoney - amount;
            ps.setInt(1, remainingMoney);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMoney(UUID uuid, int amount) {

        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE MONEY SET MONEY = ? WHERE UUID = ?");
            int currentMoney = MySQLPointer.getMoney(uuid);
            int newMoney = currentMoney + amount;
            ps.setInt(1, newMoney);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int getBank(UUID uuid) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("SELECT BANK FROM MONEY WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("BANK");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public static boolean hasEnoughMoneyBank(UUID uuid, int needed) {
        int currentMoney = MySQLPointer.getBank(uuid);
        if (currentMoney == -1) {
            return false;
        } else if (currentMoney >= needed) {
            return true;
        }
        return false;
    }

    public static void removeMoneyBank(UUID uuid, int amount) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE MONEY SET BANK = ? WHERE UUID = ?");
            int currentMoney = MySQLPointer.getBank(uuid);
            int remainingMoney = currentMoney - amount;
            ps.setInt(1, remainingMoney);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addMoneyBank(UUID uuid, int amount) {

        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE MONEY SET BANK = ? WHERE UUID = ?");
            int currentMoney = MySQLPointer.getBank(uuid);
            int newMoney = currentMoney + amount;
            ps.setInt(1, newMoney);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void setMoney(UUID uuid, int amount) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE MONEY SET MONEY = ? WHERE UUID = ?");
            ps.setInt(1, amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setBank(UUID uuid, int amount) {
        try {
            PreparedStatement ps;
            ps = MySQL.getConnection().prepareStatement("UPDATE MONEY SET BANK = ? WHERE UUID = ?");
            ps.setInt(1, amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getRemainingBanTime(UUID uuid) {
        PreparedStatement ps;
        long current = System.currentTimeMillis();
        String end = "";
        if (!isLong(end)) {
            return "PERMANENT";
        }
        long longEnd = Long.parseLong(end);
        long diff = current - longEnd;

        long seconds = (diff / 1000) % 60;
        long minutes = ((diff / (1000 * 60)) % 60);
        long hours = ((diff / (1000 * 60 * 60)) % 24);
        long days = ((diff / (1000 * 60 * 60 * 24)) % 7);
        long weeks = (diff / (1000 * 60 * 60 * 24 * 7));

        if (weeks >= 1) {
            return weeks + "w " + days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        } else if (weeks == 0) {
            return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        } else if (days == 0) {
            if (hours == 1 && minutes == 1 && seconds == 1) {
                return hours + " Stunde " + minutes + " Minute " + seconds + " Sekunde";
            } else if (minutes == 1 && seconds == 1) {
                return hours + " Stunden " + minutes + " Minute " + seconds + " Sekunde";
            } else if (seconds == 1) {
                return hours + " Stunden " + minutes + " Minuten " + seconds + " Sekunde";
            } else {
                return hours + " Stunden " + minutes + " Minuten " + seconds + " Sekunden";
            }
        } else if (hours == 0) {
            if (minutes == 1 && seconds == 1) {
                return minutes + " Minute " + seconds + " Sekunde";
            } else if (seconds == 1) {
                return minutes + " Minuten " + seconds + " Sekunde";
            } else {
                return minutes + " Minuten " + seconds + " Sekunden";
            }
        } else if (minutes == 0) {

            if (seconds == 1) {
                return seconds + "Sekunde";
            } else {
                return seconds + "Sekunden";
            }
        }
        return null;
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