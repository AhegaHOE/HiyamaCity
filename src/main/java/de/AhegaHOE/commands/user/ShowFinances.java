package de.AhegaHOE.commands.user;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.languageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowFinances implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if (args.length != 1) return false;
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) return false;
        if (!(p.getLocation().distance(t.getLocation()) <= 8.0D)) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerTooFarAway"));
            return false;
        }
        p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "ShowFinancesMessageSelf").replace("%target%", t.getDisplayName()));
        t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "ShowFinancesMessageOther").replace("%player%", p.getDisplayName()));
        t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "ShowFinancesMoney").replace("%bank%", "" + MySQLPointer.getBank(p.getUniqueId())).replace("%money%", "" + MySQLPointer.getMoney(p.getUniqueId())));


        return false;
    }
}
