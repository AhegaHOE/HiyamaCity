package de.AhegaHOE.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.AhegaHOE.main.Main;
import de.AhegaHOE.ranks.RankScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.AhegaHOE.commands.user.AfkCommand;
import net.md_5.bungee.api.ChatColor;

public final class AFKCheck implements Listener {

    public static HashMap<Player, Long> playerLastMoveTime = new HashMap<>();
    public static HashMap<String, Long> afkModeTimeStamp = new HashMap<>();

    private static int afktime = 5 * 60;

    public static void checkAFK() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            ArrayList<Player> playersToRemove = new ArrayList<>();
            for (Map.Entry<Player, Long> entry : playerLastMoveTime.entrySet()) {
                if (!AfkCommand.Afk.contains(entry.getKey())) {
                    if ((System.currentTimeMillis() - entry.getValue()) > afktime * 1000) {
                        Player p = entry.getKey();
                        playersToRemove.add(p);
                        setAFK(p);
                        afkModeTimeStamp.put(p.getName(), System.currentTimeMillis());
                        for (Player t : Bukkit.getOnlinePlayers()) {
                            if (p.getLocation().distance(t.getLocation()) <= 8.0D) {
                                t.sendMessage(ChatColor.GOLD + p.getDisplayName()
                                        + " §6hat den AFK-Modus betreten.");
                            }
                        }
                    }

                }
            }
            for (Player playerToRemove : playersToRemove) {
                playerLastMoveTime.remove(playerToRemove);
            }

        }, 0, 20);

    }

    public static void setAFK(Player p) {
        if (!AfkCommand.Afk.contains(p)) {
            AfkCommand.Afk.add(p);
            RankScoreboard.sm.getTeam("99999AFK").addEntry(p.getName());
            afkModeTimeStamp.put(p.getName(), System.currentTimeMillis());
            for (Player t : Bukkit.getOnlinePlayers()) {
                t.setScoreboard(RankScoreboard.sm);
            }
        }
    }

    public static void removeAFK(Player p) {
        if (AfkCommand.Afk.contains(p)) {
            AfkCommand.Afk.remove(p);
            RankScoreboard.sm.getTeam("99999AFK").removeEntry(p.getName());
            afkModeTimeStamp.remove(p.getName(), System.currentTimeMillis());
            for (Player t : Bukkit.getOnlinePlayers()) {
                t.setScoreboard(RankScoreboard.sm);
                RankScoreboard.setPrefix(t);
            }
        }
    }

    public static boolean isAFK(Player p) {
        return AfkCommand.Afk.contains(p);
    }

}
