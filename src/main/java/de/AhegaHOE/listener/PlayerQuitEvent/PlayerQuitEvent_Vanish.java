package de.AhegaHOE.listener.PlayerQuitEvent;

import de.AhegaHOE.commands.admin.Vanish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvent_Vanish implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        Vanish.removeVanish(e.getPlayer());
    }
}
