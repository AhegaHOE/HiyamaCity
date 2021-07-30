package de.AhegaHOE.commands.admin;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.DecimalSeperator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Locale;

public class CheckPlayer implements CommandExecutor {

    DecimalFormat decimalFormat = DecimalSeperator.prepareFormat(',', '.', false, (byte) 0);


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {


        if (args.length != 1) {
            sender.sendMessage("§cFehler: Benutze /checkplayer <Spieler>");
            return false;
        } else {

            OfflinePlayer t = Bukkit.getOfflinePlayer(args[0]);
            if (t == null || !MySQLPointer.isUserExists(t.getUniqueId())) {
                sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
                return false;
            }

            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.forLanguageTag("de"));


            int money = MySQLPointer.getMoney(t.getUniqueId());
            int bank = MySQLPointer.getBank(t.getUniqueId());
            long timeLastPlayed = t.getLastPlayed();
            long timeFirstPlayed = t.getFirstPlayed();

            sender.sendMessage(ChatColor.DARK_GRAY + "========================================");
            sender.sendMessage(ChatColor.GRAY + "§7Spielername: §9%player%".replace("%player%", t.getName()));
            if (!t.isOnline()) {
                sender.sendMessage(ChatColor.GRAY + "Status: " + ChatColor.RED + "Offline");
            } else {
                sender.sendMessage(ChatColor.GRAY + "Status: " + ChatColor.GREEN + "Online");
            }
            sender.sendMessage(ChatColor.GRAY + "§7Zuerst gespielt: §9%time%".replace("%time%", formatter.format(timeFirstPlayed)));
            sender.sendMessage(ChatColor.GRAY + "§7Zuletzt gespielt: §9%time%".replace("%time%", formatter.format(timeLastPlayed)));
            if (t.isOnline()) {
                Player t1 = (Player) t;
                sender.sendMessage(ChatColor.GRAY + "§7Gesundheit: §9%amount%".replace("%amount%", "" + t1.getHealth()));
                sender.sendMessage(ChatColor.GRAY + "§7Nahrung: §9%amount%".replace("%amount%", "" + t1.getFoodLevel()));
            }
            sender.sendMessage(ChatColor.GRAY + "§7Spielzeit: §9%hours%§7 std. §9%minutes%§7 min.".replace("%hours%", "" + MySQLPointer.getPlayedHours(t.getUniqueId())).replace("%minutes%", "" + MySQLPointer.getPlayedMinutes(t.getUniqueId())));
            sender.sendMessage(ChatColor.GRAY + "§7Geld: §9%money%§7$, Bank: §9%bank%§7$".replace("%money%", "" + decimalFormat.format(money)).replace("%bank%", "" + decimalFormat.format(bank)));
            sender.sendMessage(ChatColor.DARK_GRAY + "========================================");

        }


        return false;
    }
}
