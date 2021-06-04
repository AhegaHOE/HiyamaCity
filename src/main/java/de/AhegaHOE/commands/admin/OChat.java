package de.AhegaHOE.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OChat implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length >= 1) {
                String message = String.join(" ", args);

                if (p.isOp()) {
                    for (Player t : Bukkit.getOnlinePlayers()) {
                        t.sendMessage("(( " + p.getDisplayName() + ": " + message + " ))");
                    }
                }

            } else if (args.length == 0) {
                sender.sendMessage("§cFehler: Benutze /o <Nachricht>");
            }
        }
        return false;
    }

}