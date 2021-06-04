package de.AhegaHOE.listener.PlayerJoinEvent;

import de.AhegaHOE.util.NPCHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEvent_NPC implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(NPCHandler.getNPCs() == null) {
            return;
        }
        if(NPCHandler.getNPCs().isEmpty()) {
            return;
        }
        NPCHandler.sendNPCJoinPacket(p);
    }

}
