package de.AhegaHOE.listener;

import de.AhegaHOE.commands.admin.Vanish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessEvent_DisableCommandContainingVanishPlayers implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        if (!p.hasPermission("vanish.bypass.command")) {
            Vanish.vanishedPlayers.forEach(all -> {
                if (message.toLowerCase().contains(all.getPlayer().getName().toLowerCase())) {
                    p.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
                    e.setCancelled(true);
                }
            });
        }
    }
}
