package de.AhegaHOE.util;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class commandCooldownHandler {

    public static Map<Player, Long> cooldowns = new HashMap<>();
    private static final long cooldown = (5 * 1000);

    public static boolean isOnCooldown(Player p) {
        if (cooldowns.containsKey(p)) {
            if (cooldowns.get(p) > System.currentTimeMillis()) {
                return true;
            }
        }
        return false;
    }

    public static void setCooldown(Player p) {
        cooldowns.put(p, System.currentTimeMillis() + cooldown);
    }

    public static long getTimeLeft(Player p) {
        return (cooldowns.get(p) - System.currentTimeMillis()) / 1000;
    }
}
