package de.AhegaHOE.listener.AFK;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import de.AhegaHOE.commands.user.AfkCommand;
import de.AhegaHOE.listener.AFKCheck;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityEvent_AFK implements Listener {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getDamager();

        if (AFKCheck.isAFK(p)) {
            if (!AdminDutyCommand.isInAdminduty(p)) {
                e.setCancelled(true);
            }
        }

    }


}
