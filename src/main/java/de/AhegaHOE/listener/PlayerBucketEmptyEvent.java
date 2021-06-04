package de.AhegaHOE.listener;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerBucketEmptyEvent implements Listener {
    @EventHandler
    public void onBucketEmpty(org.bukkit.event.player.PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        Material mat = e.getBucket();
        if (!(AdminDutyCommand.adminduty.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }
}
