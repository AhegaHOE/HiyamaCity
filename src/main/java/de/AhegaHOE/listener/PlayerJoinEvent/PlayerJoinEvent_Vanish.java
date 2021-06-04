package de.AhegaHOE.listener.PlayerJoinEvent;

import de.AhegaHOE.commands.admin.Vanish;
import de.AhegaHOE.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEvent_Vanish implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (Player vanish : Vanish.vanishedPlayers) {
            if (!p.hasPermission("vanish.bypass")) {
                p.hidePlayer(Main.getInstance(), vanish);
            }
        }
    }
}
