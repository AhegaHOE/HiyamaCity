package de.AhegaHOE.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class PlayerCommandPreprocessEvent_DisableCommands implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        List<String> cmds = Arrays.asList("minecraft:me", "minecraft:msg", "minecraft:tell", "minecraft:trigger",
                "minecraft:w", "?", "about", "bukkit:?", "bukkit:about", "bukkit:help", "bukkit:pl", "bukkit:plugins",
                "bukkit:ver", "bukkit:version", "msg", "tell", "trigger", "help", "?", "about", "pl", "plugins", "ver",
                "version");
        if (!p.hasPermission("cmds")) {
            cmds.forEach(all -> {

                if (e.getMessage().toLowerCase().contains("/" + all.toLowerCase())) {
                    e.setCancelled(true);
                }

            });
        }
    }
}
