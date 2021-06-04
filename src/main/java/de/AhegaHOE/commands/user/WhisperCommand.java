package de.AhegaHOE.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class WhisperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length >= 1) {
                String message = String.join(" ", (CharSequence[]) args);
                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().distance(t.getLocation()) <= 2.0D) {
                        t.sendMessage(ChatColor.GRAY + p.getDisplayName() + "§7"
                                + " flüstert: " + ChatColor.GRAY
                                + message);
                        continue;

                    }
                    if (p.getLocation().distance(t.getLocation()) <= 4.0D) {
                        t.sendMessage(ChatColor.DARK_GRAY + p.getDisplayName() + "§8"
                                + " flüstert: "
                                + ChatColor.DARK_GRAY + message);
                    }
                }
            } else {
                p.sendMessage("§cFehler: Benutze /whisper <Nachricht>");
            }
        }
        return false;
    }

}
