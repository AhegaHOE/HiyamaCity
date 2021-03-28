package de.AhegaHOE.main;

import de.AhegaHOE.MySQL.MySQL;
import de.AhegaHOE.MySQL.MySQLFile;
import de.AhegaHOE.chat.Chat;
import de.AhegaHOE.commands.admin.*;
import de.AhegaHOE.commands.user.*;
import de.AhegaHOE.commands.user.languages.setLangCommand;
import de.AhegaHOE.listener.*;
import de.AhegaHOE.ranks.RankScoreboard;
import de.AhegaHOE.util.PlaytimeTracker;
import de.AhegaHOE.util.TimeSync;
import de.AhegaHOE.util.languageHandler;
import de.searlee.commands.SuicideCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends JavaPlugin {

    PluginManager pm = Bukkit.getPluginManager();
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        loadConfigs();
        languageHandler.loadMessages();
        loadCommands();
        loadListeners();
        loadRunnables();
        MySQLFile file = new MySQLFile();
        file.setStandard();
        file.readData();
        MySQL.connect();

        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS PLAYERDATA (UUID VARCHAR(40),PLAYERNAME VARCHAR(16),PLAYEDHOURS INT(255), PLAYEDMINUTES INT(255))");


            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void onDisable() {
        MySQL.disconnect();
    }

    private void loadConfigs() {
        getConfig().options().copyDefaults(false);
        saveConfig();
    }

    private void loadCommands() {
        getCommand("o").setExecutor(new OChat());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("me").setExecutor(new MECommand());
        getCommand("w").setExecutor(new WhisperCommand());
        getCommand("buildmode").setExecutor(new BuildModeCommand());
        getCommand("s").setExecutor(new SchreienCommand());
        getCommand("time").setExecutor(new TimeCommand());
        getCommand("adminduty").setExecutor(new AdminDutyCommand());
        getCommand("afk").setExecutor(new AfkCommand());
        getCommand("tp").setExecutor(new TPCommand());
        getCommand("id").setExecutor(new IdCommand());
        getCommand("kuss").setExecutor(new KussCommand());
        getCommand("oos").setExecutor(new OosChat());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("setlang").setExecutor(new setLangCommand());
        getCommand("stats").setExecutor(new StatsCommand());
    }

    private void loadListeners() {
        this.pm.registerEvents(new Rank_JoinListener(), this);
        this.pm.registerEvents(new Chat(), this);
        this.pm.registerEvents(new PlayerEventHandler(), this);
        this.pm.registerEvents(new ServerListPingEvent_MOTD(), this);
        this.pm.registerEvents(new AFKCheck(), this);
        this.pm.registerEvents(new DamageHandler(), this);
        this.pm.registerEvents(new PlaytimeTracker(), this);

    }

    private void loadRunnables() {
        RankScoreboard.loadScoreboards();
        AfkCommand.loadAFK();
        TimeSync.setTime();
        AFKCheck.checkAFK();
        PlaytimeTracker.startPlaytimeTracking();
    }


}