package de.AhegaHOE.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PlayerPortalEvent_DisablePortals implements Listener {

    @EventHandler
    public void onPlayerPortalEvent(PlayerPortalEvent e) {
        e.setCancelled(true);
    }
}
