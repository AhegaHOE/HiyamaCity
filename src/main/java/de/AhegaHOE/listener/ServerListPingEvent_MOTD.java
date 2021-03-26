package de.AhegaHOE.listener;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import net.md_5.bungee.api.ChatColor;

public class ServerListPingEvent_MOTD implements Listener {
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		String top = "                  " + "§7§k!!§r" + ChatColor.GOLD + " Reallife-Roleplay " + "§7§k!!§r";
		String[] bottom = { "                " + "§7§k!!§r §c§lWartungsarbeiten §7§k!!§r" };
		e.setMotd(String.valueOf(top) + "\n" + bottom[(new Random()).nextInt(bottom.length)]);
	}
}