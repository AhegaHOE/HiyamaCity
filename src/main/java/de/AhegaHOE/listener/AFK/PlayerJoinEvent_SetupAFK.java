package de.AhegaHOE.listener.AFK;

import de.AhegaHOE.listener.AFKCheck;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEvent_SetupAFK implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        AFKCheck.playerLastMoveTime.put(e.getPlayer(), System.currentTimeMillis());
    }
}
