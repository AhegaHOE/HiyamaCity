package de.AhegaHOE.commands.user;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.languageHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player) || args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            sender.sendMessage(ChatColor.GRAY + "Der Spieler " + ChatColor.AQUA + t.getDisplayName() + ChatColor.GRAY + " hat eine Spielzeit von: " + ChatColor.AQUA + MySQLPointer.getPlayedHours(t.getUniqueId()) + "h " + MySQLPointer.getPlayedMinutes(t.getUniqueId()) + "m" + ChatColor.GRAY + ".");
        }
        if (args.length == 0) {
            Player p = (Player) sender;
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "StatsCommand").replace("%hours%", "" + MySQLPointer.getPlayedHours(p.getUniqueId())).replace("%minutes%", "" + MySQLPointer.getPlayedMinutes(p.getUniqueId())));

        }
        return false;
    }
}
