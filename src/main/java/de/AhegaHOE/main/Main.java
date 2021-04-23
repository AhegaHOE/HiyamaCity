package de.AhegaHOE.main;

import de.AhegaHOE.MySQL.MySQL;
import de.AhegaHOE.MySQL.MySQLFile;
import de.AhegaHOE.chat.Chat;
import de.AhegaHOE.commands.admin.*;
import de.AhegaHOE.commands.admin.moneymanaging.CheckFinancesCommand;
import de.AhegaHOE.commands.admin.moneymanaging.MoneyManagementCommand;
import de.AhegaHOE.commands.user.*;
import de.AhegaHOE.commands.user.languages.setLangCommand;
import de.AhegaHOE.listener.*;
import de.AhegaHOE.ranks.RankScoreboard;
import de.AhegaHOE.util.PlaytimeTracker;
import de.AhegaHOE.util.Tablist;
import de.AhegaHOE.util.TimeSync;
import de.AhegaHOE.util.languageHandler;
import de.searlee.commands.SuicideCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

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
            PreparedStatement ps1 = MySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS MONEY (UUID VARCHAR(40), MONEY INT(255), BANK INT(255))");


            ps.executeUpdate();
            ps1.executeUpdate();
            ps.close();
            ps1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        FileConfiguration config = Main.getInstance().getConfig();


        for (Player all : Bukkit.getOnlinePlayers()) {
            UUID uuid = all.getUniqueId();


            if (config.get(uuid.toString()) == null) {
                languageHandler.setLocale(all, "de");
                config.set(uuid.toString(), "de");
                Main.getInstance().saveConfig();
                return;
            }
            String localeFileName = config.getString(uuid.toString());
            languageHandler.setLocale(all, localeFileName.toLowerCase());


        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            Tablist.setTab(all);
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
        getCommand("setlang").setTabCompleter(new setLangCommand());
        getCommand("stats").setExecutor(new StatsCommand());
        getCommand("checkfinances").setExecutor(new CheckFinancesCommand());
        getCommand("showfinances").setExecutor(new ShowFinances());
        getCommand("moneymanagement").setExecutor(new MoneyManagementCommand());
        getCommand("moneymanagement").setTabCompleter(new MoneyManagementCommand());
        getCommand("pay").setExecutor(new PayCommand());
        getCommand("bank").setExecutor(new BankCommand());
        getCommand("bank").setTabCompleter(new BankCommand());
        getCommand("checkplayer").setExecutor(new CheckPlayer());
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("commandspy").setExecutor(new CommandSpy());
        getCommand("inventorysee").setExecutor(new InventorySee());
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
        TimeSync.setTime();
        AFKCheck.checkAFK();
        PlaytimeTracker.startPlaytimeTracking();

    }


}