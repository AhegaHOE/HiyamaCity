package de.AhegaHOE.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p1 = e.getPlayer();

        String message = e.getMessage();


        e.setCancelled(true);

        for (Player p2 : Bukkit.getOnlinePlayers()) {

            if (p1.getLocation().distance(p2.getLocation()) <= 8.0D) {
                if (message.contains("?")) {
                    p2.sendMessage("§f" + p1.getDisplayName() + "§f" + " fragt: " + "§f" + message);
                    continue;
                }
                p2.sendMessage("§f" + p1.getDisplayName() + "§f" + " sagt: " + "§f" + message);
                continue;
            }
            if (p1.getLocation().distance(p2.getLocation()) <= 16.0D) {
                if (message.contains("?")) {
                    p2.sendMessage("§7" + p1.getDisplayName() + "§7" + " fragt: " + "§7" + message);
                    continue;
                }
                p2.sendMessage("§7" + p1.getDisplayName() + "§7" + " sagt: " + "§7" + message);
                continue;
            }
            if (p1.getLocation().distance(p2.getLocation()) <= 24.0D) {
                if (message.contains("?")) {
                    p2.sendMessage("§8" + p1.getDisplayName() + "§8" + " fragt: " + "§8" + message);
                    continue;
                }
                p2.sendMessage("§8" + p1.getDisplayName() + "§8" + " sagt: " + "§8" + message);
            }
        }
    }
}
