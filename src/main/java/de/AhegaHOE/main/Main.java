package de.AhegaHOE.main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.HashBiMap;

import de.AhegaHOE.MySQL.MySQL;
import de.AhegaHOE.MySQL.MySQLFile;
import de.AhegaHOE.chat.Chat;
import de.AhegaHOE.commands.admin.AdminDutyCommand;
import de.AhegaHOE.commands.admin.BuildModeCommand;
import de.AhegaHOE.commands.admin.GamemodeCommand;
import de.AhegaHOE.commands.admin.HealCommand;
import de.AhegaHOE.commands.admin.OChat;
import de.AhegaHOE.commands.admin.TPCommand;
import de.AhegaHOE.commands.user.AfkCommand;
import de.AhegaHOE.commands.user.IdCommand;
import de.AhegaHOE.commands.user.KussCommand;
import de.AhegaHOE.commands.user.MECommand;
import de.AhegaHOE.commands.user.OosChat;
import de.AhegaHOE.commands.user.SchreienCommand;
import de.AhegaHOE.commands.user.TimeCommand;
import de.AhegaHOE.commands.user.WhisperCommand;
import de.AhegaHOE.commands.user.languages.setLangCommand;
import de.AhegaHOE.listener.AFKCheck;
import de.AhegaHOE.listener.DamageHandler;
import de.AhegaHOE.listener.PlayerEventHandler;
import de.AhegaHOE.listener.Rank_JoinListener;
import de.AhegaHOE.listener.ServerListPingEvent_MOTD;
import de.AhegaHOE.ranks.RankScoreboard;
import de.AhegaHOE.timecontrol.TimeSync;
import de.AhegaHOE.util.languageHandler;
import de.searlee.commands.SuicideCommand;

public class Main extends JavaPlugin {

	PluginManager pm = Bukkit.getPluginManager();
	public static HashBiMap<Player, Player> playersInCall = HashBiMap.create();
	public static HashMap<Player, Player> callRequest = new HashMap<>();
	private static Main instance;

	public static Main getInstance() {
		return instance;
	}

	public void onEnable() {
		instance = this;
		loadConfig();
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
					"CREATE TABLE IF NOT EXISTS PLAYERDATA (UUID VARCHAR(40),PLAYERNAME VARCHAR(16))");
			PreparedStatement ps1 = MySQL.getConnection()
					.prepareStatement("CREATE TABLE IF NOT EXISTS PHONE (UUID VARCHAR(40), PHONE boolean)");

			ps.executeUpdate();
			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void onDisable() {
		MySQL.disconnect();
	}

	private void loadConfig() {
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
	}

	private void loadListeners() {
		this.pm.registerEvents((Listener) new Rank_JoinListener(), (Plugin) this);
		this.pm.registerEvents((Listener) new Chat(), (Plugin) this);
		this.pm.registerEvents((Listener) new PlayerEventHandler(), (Plugin) this);
		this.pm.registerEvents((Listener) new ServerListPingEvent_MOTD(), (Plugin) this);
		this.pm.registerEvents((Listener) new AFKCheck(), (Plugin) this);
		this.pm.registerEvents((Listener) new DamageHandler(), (Plugin) this);

	}

	private void loadRunnables() {
		RankScoreboard.loadScoreboards();
		AfkCommand.loadAFK();
		TimeSync.setTime();
		AFKCheck.checkAFK();
	}

}
