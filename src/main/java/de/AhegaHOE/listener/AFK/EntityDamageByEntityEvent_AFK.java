package de.AhegaHOE.listener.AFK;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import de.AhegaHOE.commands.user.AfkCommand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityEvent_AFK implements Listener {

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player || e.getDamager() instanceof Arrow) {
            Player p = (Player) e.getEntity();

            if (AfkCommand.Afk.contains(p)) {
                if (!AdminDutyCommand.adminduty.contains(e.getDamager().getName())) {
                    e.setCancelled(true);
                }
            }

        }

    }
}
