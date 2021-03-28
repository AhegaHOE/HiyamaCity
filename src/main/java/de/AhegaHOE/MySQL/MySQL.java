package de.AhegaHOE.MySQL;

import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    public static String host;
    public static String port;
    public static String database;
    public static String username;
    public static String password;
    public static Connection con;

    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,
                        password);
                Bukkit.getConsoleSender().sendMessage("§8[§bMySQL§8] §aVerbindung erfolgreich hergestellt!");
            } catch (SQLException e) {
                e.printStackTrace();
                Bukkit.getConsoleSender()
                        .sendMessage("§8[§bMySQL§8] §cKonnte keine Verbindung zur Datenbank herstellen!");
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage("§8[§bMySQL§8] §cVerbindung wurde geschlossen!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return !(con == null);
    }

    public static Connection getConnection() {
        return con;
    }

}