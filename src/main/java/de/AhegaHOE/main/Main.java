package de.AhegaHOE.main;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.reconnect.ConnectionHandler;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.AhegaHOE.MySQL.MySQL;
import de.AhegaHOE.MySQL.MySQLFile;
import de.AhegaHOE.chat.Chat;
import de.AhegaHOE.commands.admin.*;
import de.AhegaHOE.commands.admin.atm.registerATM;
import de.AhegaHOE.commands.admin.banmanaging.CheckBanID;
import de.AhegaHOE.commands.admin.banmanaging.CheckBans;
import de.AhegaHOE.commands.admin.banmanaging.TempBan;
import de.AhegaHOE.commands.admin.banmanaging.Unban;
import de.AhegaHOE.commands.admin.moneymanaging.CheckFinancesCommand;
import de.AhegaHOE.commands.admin.moneymanaging.MoneyManagementCommand;
import de.AhegaHOE.commands.admin.ticket.AcceptReport;
import de.AhegaHOE.commands.admin.ticket.CloseReport;
import de.AhegaHOE.commands.user.*;
import de.AhegaHOE.commands.user.ticket.CancelTicket;
import de.AhegaHOE.commands.user.ticket.Ticket;
import de.AhegaHOE.commands.user.ticket.TicketChat;
import de.AhegaHOE.listener.AFK.*;
import de.AhegaHOE.listener.*;
import de.AhegaHOE.listener.PlayerJoinEvent.PlayerJoinEvent_JoinMessage;
import de.AhegaHOE.listener.PlayerJoinEvent.PlayerJoinEvent_NPC;
import de.AhegaHOE.listener.PlayerJoinEvent.PlayerJoinEvent_Vanish;
import de.AhegaHOE.listener.PlayerJoinEvent.PlayerJoinEvent_WelcomePlayer;
import de.AhegaHOE.listener.PlayerQuitEvent.PlayerQuitEvent_QuitMessage;
import de.AhegaHOE.listener.PlayerQuitEvent.PlayerQuitEvent_Vanish;
import de.AhegaHOE.ranks.RankScoreboard;
import de.AhegaHOE.util.*;
import de.AhegaHOE.util.banmanagement.Banning;
import de.AhegaHOE.util.ts.TSMySQLPointer;
import de.AhegaHOE.util.ts.TeamspeakEventHandler;
import de.searlee.commands.SuicideCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Main extends JavaPlugin {

    public static final TS3Config ts3Config = new TS3Config().setHost("localhost").setFloodRate(TS3Query.FloodRate.UNLIMITED).setEnableCommunicationsLogging(true).setReconnectStrategy(ReconnectStrategy.exponentialBackoff()).setConnectionHandler(new ConnectionHandler() {

        @Override
        public void onConnect(TS3Api ts3Api) {
            Bukkit.getConsoleSender().sendMessage("§8[§bTeamspeak-Bot§8] §aTeamspeak-Bot connected!");
        }

        @Override
        public void onDisconnect(TS3Query ts3Query) {
            ts3Query.exit();
            Bukkit.getConsoleSender().sendMessage("§8[§bTeamspeak-Bot§8] §cTeamspeak-Bot disconnected!");
        }
    });

    public static final TS3Query ts3Query = new TS3Query(ts3Config);
    public static final TS3Api ts3Api = ts3Query.getApi();

    PluginManager pm = Bukkit.getPluginManager();
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }


    public void onEnable() {
        instance = this;
        loadConfigs();
        loadCommands();
        loadListeners();
        loadRunnables();
        MySQLFile file = new MySQLFile();
        file.setStandard();
        file.readData();
        MySQL.connect();
        loadTeamspeakBot();
        ItemBuilder.generateItems();


        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS PLAYERDATA (UUID VARCHAR(40),PLAYERNAME VARCHAR(16), " +
                            "PLAYEDHOURS INT(255), PLAYEDMINUTES INT(255))");
            PreparedStatement ps1 = MySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS MONEY (UUID VARCHAR(40), MONEY INT(255), BANK INT(255))");
            PreparedStatement ps2 = MySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS BANS (BANID VARCHAR(255), UUID VARCHAR(40), BUUID VARCHAR(40), " +
                            "ISACTIVE TINYINT(1), REASON VARCHAR(255), START BIGINT(100), END BIGINT(255))");
            PreparedStatement ps3 = MySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS TS_DATA (UUID VARCHAR(40), UID VARCHAR(255), RANK VARCHAR(255), CONFIRMED TINYINT(1))");


            ps.executeUpdate();
            ps1.executeUpdate();
            ps2.executeUpdate();
            ps3.executeUpdate();
            ps.close();
            ps1.close();
            ps2.close();
            ps3.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        FileConfiguration config = Main.getInstance().getConfig();

        for (Player all : Bukkit.getOnlinePlayers()) {
            Tablist.setTab(all);
        }
    }

    private void loadTeamspeakBot() {
        Bukkit.getConsoleSender().sendMessage("§8[§bTeamspeak-Bot§8] §6Teamspeak-Bot wird gestartet...");
        ts3Query.connect();
        ts3Api.login("serveradmin", "3EL8-TaKX_CVFBg$eeMA");
        ts3Api.selectVirtualServerByPort(9987);
        ts3Api.setNickname("HiyamaCity-Bot");
        TeamspeakEventHandler.loadEvents();
        Bukkit.getConsoleSender().sendMessage("§8[§bTeamspeak-Bot§8] §aTeamspeak-Bot wurde gestartet!");
        for (Client c : Main.ts3Api.getClients()) {
            if (c.isServerQueryClient()) {
                return;
            }
            TeamspeakEventHandler.updateRank(c);
            TSMySQLPointer.sendVerificationMessageTeamspeak(TSMySQLPointer.getUUIDbyUID(c.getUniqueIdentifier()), c.getUniqueIdentifier());
        }
    }

    public void onDisable() {
        ts3Query.exit();
        Vanish.removeAllPlayersVanish();
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
        getCommand("kick").setExecutor(new KickPlayer());
        getCommand("dice").setExecutor(new DiceCommand());
        getCommand("coinflip").setExecutor(new HeadsOrTailsCommand());
        getCommand("registeratm").setExecutor(new registerATM());
        getCommand("forum").setExecutor(new ForumCommand());
        getCommand("help").setExecutor(new Ticket());
        getCommand("cancelreport").setExecutor(new CancelTicket());
        getCommand("acceptreport").setExecutor(new AcceptReport());
        getCommand("closereport").setExecutor(new CloseReport());
        getCommand("togglereportchat").setExecutor(new TicketChat());
        getCommand("calculate").setExecutor(new CalculateCommand());
        getCommand("testcommand").setExecutor(new TestCommand());
        getCommand("ban").setExecutor(new Banning());
        getCommand("unban").setExecutor(new Unban());
        getCommand("checkbanid").setExecutor(new CheckBanID());
        getCommand("checkbans").setExecutor(new CheckBans());
        getCommand("tempban").setExecutor(new TempBan());
        getCommand("ts").setExecutor(new TeamspeakCommand());
        getCommand("uuid").setExecutor(new UUIDCommand());


    }

    private void loadListeners() {
        this.pm.registerEvents(new PlayerJoinEvent_WelcomePlayer(), this);
        this.pm.registerEvents(new Rank_JoinListener(), this);
        this.pm.registerEvents(new Ticket(), this);
        this.pm.registerEvents(new Chat(), this);
        this.pm.registerEvents(new BlockHandler(), this);
        this.pm.registerEvents(new ServerListPingEvent_MOTD(), this);
        this.pm.registerEvents(new AFKCheck(), this);
        this.pm.registerEvents(new DamageHandler(), this);
        this.pm.registerEvents(new PlaytimeTracker(), this);
        this.pm.registerEvents(new DropHandler(), this);
        this.pm.registerEvents(new DoorHandler(), this);
        this.pm.registerEvents(new EntitySpawnEvent_DisableMobSpawns(), this);
        this.pm.registerEvents(new FoodLevelChangeEvent_FoodLevelHandler(), this);
        this.pm.registerEvents(new InventoryOpenEvent_DisableInvOpen(), this);
        this.pm.registerEvents(new PlayerBucketEmptyEvent(), this);
        this.pm.registerEvents(new PlayerCommandPreprocessEvent_DisableCommandContainingVanishPlayers(), this);
        this.pm.registerEvents(new PlayerCommandPreprocessEvent_UnknownCommand(), this);
        this.pm.registerEvents(new PlayerCommandPreprocessEvent_CommandSpy(), this);
        this.pm.registerEvents(new PlayerCommandPreprocessEvent_DisableCommands(), this);
        this.pm.registerEvents(new PlayerCommandPreprocessEvent_AFK(), this);
        this.pm.registerEvents(new PlayerDeathEvent_DeathHandler(), this);
        this.pm.registerEvents(new PlayerInteractEvent_ItemsFrames(), this);
        this.pm.registerEvents(new PlayerInteractEvent_AFK(), this);
        this.pm.registerEvents(new PlayerPortalEvent_DisablePortals(), this);
        this.pm.registerEvents(new VehicleDamageEvent_DisableVehDmg(), this);
        this.pm.registerEvents(new AsyncPlayerChatEvent_AFK(), this);
        this.pm.registerEvents(new EntityDamageByEntityEvent_AFK(), this);
        this.pm.registerEvents(new PlayerMoveEvent_AFK(), this);
        this.pm.registerEvents(new PlayerQuitEvent_AFK(), this);
        this.pm.registerEvents(new PlayerJoinEvent_SetupAFK(), this);
        this.pm.registerEvents(new PlayerJoinEvent_JoinMessage(), this);
        this.pm.registerEvents(new PlayerJoinEvent_Vanish(), this);
        this.pm.registerEvents(new PlayerQuitEvent_QuitMessage(), this);
        this.pm.registerEvents(new PlayerQuitEvent_Vanish(), this);
        this.pm.registerEvents(new PlayerJoinEvent_NPC(), this);
        this.pm.registerEvents(new Banning(), this);


    }

    private void loadRunnables() {
        RankScoreboard.loadScoreboards();
        TimeSync.setTime();
        AFKCheck.checkAFK();
        PlaytimeTracker.startPlaytimeTracking();
        //new Broadcaster().startBroadcast();

    }

}