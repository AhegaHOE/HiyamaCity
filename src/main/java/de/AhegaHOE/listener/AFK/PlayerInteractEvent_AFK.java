package de.AhegaHOE.listener.AFK;

import de.AhegaHOE.commands.user.AfkCommand;
import de.AhegaHOE.listener.AFKCheck;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractEvent_AFK implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            if (AfkCommand.Afk.contains(e.getPlayer())) {
                AFKCheck.removeAFK(e.getPlayer());
                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (e.getPlayer().getLocation().distance(t.getLocation()) <= 8.0D) {
                        t.sendMessage(ChatColor.GOLD + p.getDisplayName()
                                + " §6hat den AFK-Modus verlassen.");
                    }
                }
            }
        }
    }
}
