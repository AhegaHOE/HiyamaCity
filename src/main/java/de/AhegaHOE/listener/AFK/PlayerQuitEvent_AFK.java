package de.AhegaHOE.listener.AFK;

import de.AhegaHOE.listener.AFKCheck;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvent_AFK implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        AFKCheck.removeAFK(e.getPlayer());
    }
}
