package de.AhegaHOE.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventorySee implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("invsee")) return true;
        if (args.length != 1) {
            p.sendMessage("§cFehler: Benutze /invsee <Spieler>");
            return true;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            p.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
            return true;
        }

        p.openInventory(t.getInventory());

        return false;
    }
}
