package de.AhegaHOE.listener;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class EntitySpawnEvent_DisableMobSpawns implements Listener {

    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent e) {
        if (!e.getEntityType().equals(e.getEntityType().DROPPED_ITEM)) {
            e.setCancelled(true);
        } else {
            Item item = (Item) e.getEntity();
            ItemStack itemStack = item.getItemStack();
            if (itemStack.getType().equals(Material.BLACK_SHULKER_BOX)
                    || itemStack.getType().equals(Material.BLUE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.BROWN_SHULKER_BOX)
                    || itemStack.getType().equals(Material.CYAN_SHULKER_BOX)
                    || itemStack.getType().equals(Material.GRAY_SHULKER_BOX)
                    || itemStack.getType().equals(Material.GREEN_SHULKER_BOX)
                    || itemStack.getType().equals(Material.LIGHT_BLUE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.LIME_SHULKER_BOX)
                    || itemStack.getType().equals(Material.MAGENTA_SHULKER_BOX)
                    || itemStack.getType().equals(Material.ORANGE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.PINK_SHULKER_BOX)
                    || itemStack.getType().equals(Material.PURPLE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.RED_SHULKER_BOX)
                    || itemStack.getType().equals(Material.SILVER_SHULKER_BOX)
                    || itemStack.getType().equals(Material.WHITE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.YELLOW_SHULKER_BOX)) {
                e.setCancelled(true);
            }
        }
    }
}
