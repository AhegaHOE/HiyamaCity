package de.AhegaHOE.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.AhegaHOE.ranks.RankScoreboard;

public class Rank_JoinListener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		RankScoreboard.setPrefix(e.getPlayer());
	}

}
