package de.AhegaHOE.listener.PlayerQuitEvent;

import de.AhegaHOE.util.Tablist;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvent_QuitMessage implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        e.setQuitMessage("");
        Tablist.updateTab();
    }
}
