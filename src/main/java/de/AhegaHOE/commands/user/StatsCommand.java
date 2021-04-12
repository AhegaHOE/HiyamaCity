package de.AhegaHOE.commands.user;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.DecimalSeperator;
import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class StatsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player) || args.length == 1) {
            Player t = Bukkit.getPlayer(args[0]);
            // TODO: STATS COMMAND FÜR DIE CONSOLE
        }

        if (args.length == 0) {
            Player p = (Player) sender;

            int money = MySQLPointer.getMoney(p.getUniqueId());
            int bank = MySQLPointer.getBank(p.getUniqueId());

            DecimalFormat decimalFormat = DecimalSeperator.prepareFormat(',', '.', false, (byte) 0);

            p.sendMessage(ChatColor.DARK_GRAY + "==============================");
            p.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "StatsPlayerName").replace("%player%", p.getDisplayName()));
            p.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "StatsPlayTime").replace("%hours%", "" + MySQLPointer.getPlayedHours(p.getUniqueId())).replace("%minutes%", "" + MySQLPointer.getPlayedMinutes(p.getUniqueId())));
            p.sendMessage(ChatColor.GRAY + languageHandler.getMessage(languageHandler.getLocale(p), "StatsMoney").replace("%money%", "" + decimalFormat.format(money)).replace("%bank%", "" + decimalFormat.format(bank)));
            p.sendMessage(ChatColor.DARK_GRAY + "==============================");

        }
        return false;
    }
}
