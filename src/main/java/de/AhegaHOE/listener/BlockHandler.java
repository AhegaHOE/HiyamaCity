package de.AhegaHOE.listener;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import de.AhegaHOE.commands.admin.BuildModeCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockHandler implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block block = e.getBlock();
        if (!(AdminDutyCommand.adminduty.contains(p.getName()) || BuildModeCommand.buildmode.contains(p.getName()))) {
            e.setCancelled(true);
        } else if (block.getType() == Material.ITEM_FRAME || block.getType() == Material.PAINTING) {
            if (!(AdminDutyCommand.adminduty.contains(p.getName()))) {
                e.setCancelled(true);
            }
        } else {
            Location loc = block.getLocation();
            Block two = loc.getChunk().getWorld().getBlockAt(block.getX(), block.getZ(), block.getY());
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!(AdminDutyCommand.adminduty.contains(p.getName()) || BuildModeCommand.buildmode.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }
}
