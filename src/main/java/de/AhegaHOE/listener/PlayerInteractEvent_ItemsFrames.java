package de.AhegaHOE.listener;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEvent_ItemsFrames implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if (AdminDutyCommand.adminduty.contains(p.getName())) {
            return;
        }
        if (e.getRightClicked() != null) {
            if (e.getRightClicked().getType() == EntityType.ITEM_FRAME)
                e.setCancelled(true);
        }
    }


    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (AdminDutyCommand.adminduty.contains(p.getName())) {
            return;
        }
        if (e.getRightClicked() != null) {
            if (e.getRightClicked().getType() == EntityType.ITEM_FRAME)
                e.setCancelled(true);
        }
    }
}
