package de.AhegaHOE.commands.admin.banmanaging;

import de.AhegaHOE.util.banmanagement.Banning;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Unban implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender.hasPermission("system.unban"))) {
            sender.sendMessage("§cFehler: Du hast dafür keine Rechte!");
            return true;
        }

        if(args.length != 1) {
            sender.sendMessage("§cFehler: Benutze /unban <Spieler>");
            return true;
        }

        UUID uuid = Bukkit.getPlayerUniqueId(args[0]);

        if(uuid == null) {
            sender.sendMessage("§cFehler: Dieser Spieler existiert nicht.");
            return true;
        }

        if(!Banning.hasActiveBans(uuid)) {
            sender.sendMessage("§cFehler: Dieser Spieler ist nicht gebannt!");
            return true;
        }

        Banning.deactivateBan(uuid);
        sender.sendMessage("§7Du hast den Spieler §9" + Bukkit.getOfflinePlayer(uuid).getName() + " §aerfolgreich §7entbannt.");


        return false;
    }
}
