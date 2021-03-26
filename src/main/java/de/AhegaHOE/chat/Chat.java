package de.AhegaHOE.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;

public class Chat implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p1 = e.getPlayer();

		String message = e.getMessage();
		message = message.replace("%", "%");

		e.setCancelled(true);

		for (Player p2 : Bukkit.getOnlinePlayers()) {

			if (p1.getLocation().distance(p2.getLocation()) <= 8.0D) {
				if (message.contains("?")) {
					p2.sendMessage("§f " + p1.getDisplayName() + languageHandler.getMessage(languageHandler.getLocale(p2), "Ask") + "§f" + message);
					continue;
				}
				p2.sendMessage("§f " + p1.getDisplayName() + languageHandler.getMessage(languageHandler.getLocale(p2), "Say") + "§f" + message);
				continue;
			}
			if (p1.getLocation().distance(p2.getLocation()) <= 16.0D) {
				if (message.contains("?")) {
					p2.sendMessage("§7 " + p1.getDisplayName() + languageHandler.getMessage(languageHandler.getLocale(p2), "Ask") + "§7" + message);
					continue;
				}
				p2.sendMessage("§7 " + p1.getDisplayName() + languageHandler.getMessage(languageHandler.getLocale(p2), "Say") +"§7" + message);
				continue;
			}
			if (p1.getLocation().distance(p2.getLocation()) <= 24.0D) {
				if (message.contains("?")) {
					p2.sendMessage("§8 " + p1.getDisplayName() + languageHandler.getMessage(languageHandler.getLocale(p2), "Ask") +"§8" + message);
					continue;
				}
				p2.sendMessage("§8 " + p1.getDisplayName() + languageHandler.getMessage(languageHandler.getLocale(p2), "Say") +"§8" + message);
			}
		}
	}
}
