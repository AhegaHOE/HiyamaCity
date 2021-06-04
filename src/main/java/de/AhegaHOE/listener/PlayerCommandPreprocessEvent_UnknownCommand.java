package de.AhegaHOE.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessEvent_UnknownCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();
        String[] args = msg.split(" ");
        Player p = e.getPlayer();

        if (Bukkit.getServer().getHelpMap().getHelpTopic(args[0]) == null) {
            e.setCancelled(true);
            p.sendMessage("§cFehler: Befehl nicht gefunden.");
        }
    }
}
