package de.AhegaHOE.commands.admin.atm;

import de.AhegaHOE.util.ATMConfigHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class registerATM implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }

        Player p = (Player) sender;

        if (args.length != 0) {
            p.sendMessage("§cFehler: Benutze /registeratm");
            return false;
        }

        if (!(p.hasPermission("registeratm"))) {
            return false;
        }

        Location loc = p.getTargetBlock(null, 5).getLocation();
        ATMConfigHandler.addLocation(loc);
        p.sendMessage(ChatColor.GRAY + "ATM-" + ATMConfigHandler.atmcount + " erfolgreich erstellt" +
                "\n" + "X: " + loc.getBlockX() +
                "\n" + "Y: " + loc.getBlockY() +
                "\n" + "Z: " + loc.getBlockZ());


        return false;
    }
}
