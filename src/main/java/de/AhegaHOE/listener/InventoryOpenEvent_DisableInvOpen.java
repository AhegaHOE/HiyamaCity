package de.AhegaHOE.listener;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import org.bukkit.block.*;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.*;

public class InventoryOpenEvent_DisableInvOpen implements Listener {

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest
                || e.getInventory().getHolder() instanceof ShulkerBox
                || e.getInventory().equals(e.getPlayer().getEnderChest())
                || e.getInventory().getHolder() instanceof Hopper || e.getInventory().getHolder() instanceof Dispenser
                || e.getInventory().getHolder() instanceof Dropper || e.getInventory().getHolder() instanceof Minecart
                || e.getInventory() instanceof HorseInventory || e.getInventory().getHolder() instanceof Donkey
                || e.getInventory() instanceof LlamaInventory || e.getInventory() instanceof CraftingInventory
                || e.getInventory() instanceof FurnaceInventory || e.getInventory() instanceof EnchantingInventory
                || e.getInventory() instanceof BeaconInventory || e.getInventory().getHolder() instanceof Villager
                || e.getInventory() instanceof BrewerInventory || e.getInventory() instanceof AnvilInventory) {
            Player p = (Player) e.getPlayer();
            if (!(AdminDutyCommand.adminduty.contains(p.getName()))) {
                e.setCancelled(true);
            }
        }
    }
}
