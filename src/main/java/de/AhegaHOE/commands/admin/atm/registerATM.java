package de.AhegaHOE.commands.admin.atm;

import de.AhegaHOE.util.ATMConfigHandler;
import de.AhegaHOE.util.languageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class registerATM implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(languageHandler.getMessage("en", "PlayerOnly"));
            return false;
        }

        Player p = (Player) sender;

        if (args.length != 0) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "RegisterATMFalseArgs"));
            return false;
        }

        if(!(p.hasPermission("registeratm"))) {
            return false;
        }

        ATMConfigHandler.addLocation(p.getEyeLocation());

        return false;
    }
}
