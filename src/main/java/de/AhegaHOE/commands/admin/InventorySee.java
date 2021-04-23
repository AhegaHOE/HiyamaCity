package de.AhegaHOE.commands.admin;

import de.AhegaHOE.util.languageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventorySee implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("invsee")) return true;
        if (args.length != 1) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "InvSeeFalseArgs"));
            return true;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerNotFound"));
            return true;
        }

        p.openInventory(t.getInventory());

        return false;
    }
}
