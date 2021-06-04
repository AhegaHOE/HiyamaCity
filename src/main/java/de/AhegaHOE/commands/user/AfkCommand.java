package de.AhegaHOE.commands.user;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.listener.AFKCheck;
import net.md_5.bungee.api.ChatColor;

public class AfkCommand implements CommandExecutor {

    public static ArrayList<Player> Afk = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (Afk.contains(p)) {
                    AFKCheck.removeAFK(p);
                    for (Player t : Bukkit.getOnlinePlayers()) {
                        if (p.getLocation().distance(t.getLocation()) <= 8.0D) {
                            t.sendMessage(ChatColor.GOLD + p.getDisplayName()
                                    + " §6hat den AFK-Modus verlassen.");
                            AFKCheck.afkModeTimeStamp.remove(p.getName(), System.currentTimeMillis());
                        }
                    }
                } else {
                    AFKCheck.setAFK(p);
                    for (Player t : Bukkit.getOnlinePlayers()) {
                        if (p.getLocation().distance(t.getLocation()) <= 8.0D) {
                            t.sendMessage(ChatColor.GOLD + p.getDisplayName()
                                    + " §6hat den AFK-Modus betreten.");
                            AFKCheck.afkModeTimeStamp.put(p.getName(), System.currentTimeMillis());
                        }
                    }
                }
            }
        }
        return false;
    }

}
