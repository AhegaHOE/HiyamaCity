package de.AhegaHOE.listener.PlayerJoinEvent;

import de.AhegaHOE.MySQL.MySQLPointer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinEvent_WelcomePlayer implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        String pName = p.getName();

        if (!MySQLPointer.isUserExists(uuid)) {
            p.sendMessage(ChatColor.GOLD + "Willkommen!");
            MySQLPointer.registerPlayer(uuid, pName);
        } else if (MySQLPointer.isUserExists(uuid)) {
            p.sendMessage(ChatColor.GOLD + "Willkommen zurück!");
        } else {
            MySQLPointer.registerPlayer(uuid, pName);
        }

        if (!MySQLPointer.getUsername(e.getPlayer().getUniqueId()).equals(e.getPlayer().getName())) {
            MySQLPointer.updateUsername(e.getPlayer().getUniqueId(), e.getPlayer().getName());
        }
    }
}
