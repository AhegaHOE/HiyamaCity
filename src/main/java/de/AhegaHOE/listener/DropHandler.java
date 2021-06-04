package de.AhegaHOE.listener;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropHandler implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (!(AdminDutyCommand.adminduty.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }
}
