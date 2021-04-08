package de.AhegaHOE.commands.user;

import de.AhegaHOE.MySQL.MySQL;
import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.languageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(languageHandler.getMessage("en", "PlayerOnly"));
            return false;
        }

        Player p = (Player) sender;

        if (args.length != 2) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PayCommandFalseArgs"));
            return false;
        }

        Player t = Bukkit.getPlayer(args[0]);

        if (t == null) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerNotFound"));
            return false;
        }

        if(t.getName() == p.getName()) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "CantPaySelf"));
            return false;
        }

        if (!(p.getLocation().distance(t.getLocation()) <= 2.0D)) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerTooFarAway"));
            return false;
        }


        int amount = Integer.parseInt(args[1]);

        if (!(MySQLPointer.hasEnoughMoney(p.getUniqueId(), amount))) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "NotEnoughMoney"));
            return false;
        } else {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PayMessageSelf").replace("%target%", t.getDisplayName()).replace("%amount%", "" + amount));
            t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "PayMessageOther").replace("%player%", p.getDisplayName()).replace("%amount%", "" + amount));
            MySQLPointer.removeMoney(p.getUniqueId(), amount);
            MySQLPointer.addMoneyBank(t.getUniqueId(), amount);
        }

        return false;
    }
}
