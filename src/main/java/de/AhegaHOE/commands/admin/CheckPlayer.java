package de.AhegaHOE.commands.admin;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.DecimalSeperator;
import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

public class CheckPlayer implements CommandExecutor {

    DecimalFormat decimalFormat = DecimalSeperator.prepareFormat(',', '.', false, (byte) 0);


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {

            if (args.length != 1) {
                sender.sendMessage(languageHandler.getMessage("de", "CheckPlayerFalseArgs"));
                return false;
            } else {

                OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                if (t == null || !MySQLPointer.isUserExists(t.getUniqueId())) {
                    sender.sendMessage(languageHandler.getMessage("de", "PlayerNotFound"));
                    return false;
                }

                DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.forLanguageTag("de"));


                int money = MySQLPointer.getMoney(t.getUniqueId());
                int bank = MySQLPointer.getBank(t.getUniqueId());
                long timeLastPlayed = t.getLastPlayed();
                long timeFirstPlayed = t.getFirstPlayed();

                sender.sendMessage(ChatColor.DARK_GRAY + "========================================");
                sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage("de", "StatsPlayerName").replace("%player%", t.getName()));
                sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage("de", "FirstPlayed").replace("%time%", formatter.format(timeFirstPlayed)));
                sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage("de", "LastPlayed").replace("%time%", formatter.format(timeLastPlayed)));
                if (t.isOnline()) {
                    Player t1 = (Player) t;
                    sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage("de", "Healthlevel").replace("%amount%", "" + t1.getHealth()));
                    sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage("de", "Foodlevel").replace("%amount%", "" + t1.getFoodLevel()));
                }
                sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage("de", "StatsPlayTime").replace("%hours%", "" + MySQLPointer.getPlayedHours(t.getUniqueId())).replace("%minutes%", "" + MySQLPointer.getPlayedMinutes(t.getUniqueId())));
                sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage("de", "StatsMoney").replace("%money%", "" + decimalFormat.format(money)).replace("%bank%", "" + decimalFormat.format(bank)));
                sender.sendMessage(ChatColor.DARK_GRAY + "========================================");

            }

        } else if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("checkplayer")) {

                if (args.length != 1) {
                    sender.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "CheckPlayerFalseArgs"));
                    return false;
                } else {

                    OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
                    if (t == null || !MySQLPointer.isUserExists(t.getUniqueId())) {
                        p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerNotFound"));
                        return false;
                    }

                    DateFormat formatterDate = DateFormat.getDateInstance(DateFormat.FULL, Locale.forLanguageTag(languageHandler.getLocale(p)));
                    DateFormat formatterTime = DateFormat.getTimeInstance(DateFormat.FULL, Locale.forLanguageTag(languageHandler.getLocale(p)));

                    int money = MySQLPointer.getMoney(t.getUniqueId());
                    int bank = MySQLPointer.getBank(t.getUniqueId());
                    long timeLastPlayed = t.getLastPlayed();
                    long timeFirstPlayed = t.getFirstPlayed();


                    sender.sendMessage(ChatColor.DARK_GRAY + "========================================");
                    sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "StatsPlayerName").replace("%player%", t.getName()));
                    sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "FirstPlayed").replace("%time%", formatterDate.format(timeFirstPlayed) + "\n" + formatterTime.format(timeFirstPlayed)));
                    sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "LastPlayed").replace("%time%", formatterDate.format(timeLastPlayed) + "\n" + formatterTime.format(timeLastPlayed)));
                    if (t.isOnline()) {
                        Player t1 = (Player) t;
                        sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "Healthlevel").replace("%amount%", "" + t1.getHealth()));
                        sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "Foodlevel").replace("%amount%", "" + t1.getFoodLevel()));
                    }
                    sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "StatsPlayTime").replace("%hours%", "" + MySQLPointer.getPlayedHours(t.getUniqueId())).replace("%minutes%", "" + MySQLPointer.getPlayedMinutes(t.getUniqueId())));
                    sender.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "StatsMoney").replace("%money%", "" + decimalFormat.format(money)).replace("%bank%", "" + decimalFormat.format(bank)));
                    sender.sendMessage(ChatColor.DARK_GRAY + "========================================");

                }

            }


        } else {
            return false;
        }


        return false;
    }
}
