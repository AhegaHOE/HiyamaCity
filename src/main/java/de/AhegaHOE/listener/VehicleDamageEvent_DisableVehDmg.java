package de.AhegaHOE.listener;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class VehicleDamageEvent_DisableVehDmg implements Listener {

    @EventHandler
    public void onBoat(VehicleDamageEvent e) {
        Player p = (Player) e.getAttacker();
        if (!(AdminDutyCommand.adminduty.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }
}
