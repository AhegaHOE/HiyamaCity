package de.AhegaHOE.listener.AFK;

import de.AhegaHOE.commands.user.AfkCommand;
import de.AhegaHOE.listener.AFKCheck;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveEvent_AFK implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getYaw() != e.getTo().getYaw() || e.getFrom().getPitch() != e.getTo().getPitch()) {
            AFKCheck.playerLastMoveTime.put(e.getPlayer(), System.currentTimeMillis());
            if (AfkCommand.Afk.contains(e.getPlayer())) {
                AFKCheck.removeAFK(e.getPlayer());
                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (e.getPlayer().getLocation().distance(t.getLocation()) <= 8.0D) {
                        t.sendMessage(ChatColor.GOLD + e.getPlayer().getDisplayName()
                                + " §6hat den AFK-Modus verlassen.");
                    }
                }
            }
        }
        if (AfkCommand.Afk.contains(e.getPlayer())) {
            AFKCheck.removeAFK(e.getPlayer());
            for (Player t : Bukkit.getOnlinePlayers()) {
                if (e.getPlayer().getLocation().distance(t.getLocation()) <= 8.0D) {
                    t.sendMessage(ChatColor.GOLD + e.getPlayer().getDisplayName()
                            + " §6hat den AFK-Modus verlassen.");
                }
            }
        }
    }
}
