package de.AhegaHOE.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DamageHandler implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (p.getItemInHand().isSimilar(getItem(Material.BLAZE_ROD, "Brechstange"))) {
				e.setDamage(20.0);
			} else if(p.getItemInHand().isSimilar(getItem(Material.FEATHER, "Messer"))) {
				e.setDamage(20.0);
			}

		}
	}
	
	private ItemStack getItem(Material material, String displayName) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		item.setItemMeta(meta);
		return item;
	}

}
