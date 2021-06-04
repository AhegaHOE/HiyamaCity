package de.AhegaHOE.listener.PlayerJoinEvent;

import de.AhegaHOE.util.Tablist;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinEvent_JoinMessage implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        e.setJoinMessage("");
        Tablist.updateTab();
    }
}
