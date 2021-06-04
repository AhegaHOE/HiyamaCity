package de.AhegaHOE.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OosChat implements CommandExecutor {


    String msg;

    public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length >= 1) {
                String msg = String.join(" ", args);

                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().distance(t.getLocation()) <= 20.0D) {
                        t.sendMessage("§c( %player% §cfügt hinzu: %message% )".replace("%player%", p.getDisplayName()).replace("%message%", msg));
                    }
                }

            } else
                sender.sendMessage("§cFehler: Benutze /oos <Nachricht>");


        }
        return false;
    }
}