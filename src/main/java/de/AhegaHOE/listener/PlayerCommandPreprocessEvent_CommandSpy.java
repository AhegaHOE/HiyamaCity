package de.AhegaHOE.listener;

import de.AhegaHOE.commands.admin.CommandSpy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessEvent_CommandSpy implements Listener {
    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (!all.hasPermission("commandspy.bypass")) {
                for (Player spying : CommandSpy.getSpyingPlayers())
                    spying.sendMessage("§9%player% §7hat den Befehl: §8%message% §7ausgeführt."
                            .replace("%player%", p.getDisplayName()).replace("%message%", ChatColor.GRAY + e.getMessage()));
            }
        }
    }
}
