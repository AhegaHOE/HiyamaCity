package de.AhegaHOE.ranks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;

public class RankScoreboard {

    static Scoreboard sm = Bukkit.getScoreboardManager().getNewScoreboard();

    public static void loadScoreboards() {
        sm.registerNewTeam("00000Admin");
        sm.registerNewTeam("00001Moderator");
        sm.registerNewTeam("00002Volunteer");
        sm.registerNewTeam("00003Builder");
        sm.registerNewTeam("00004Premium");
        sm.registerNewTeam("00005Spieler");

        // sm.getTeam("00000Admin").setPrefix("");

        for (Player t : Bukkit.getOnlinePlayers()) {
            setPrefix(t);
        }
    }

    public static void setPrefix(Player p) {
        String team;
        if (p.hasPermission("rank.admin")) {
            team = "00000Admin";
        } else if (p.hasPermission("rank.moderator")) {
            team = "00001Moderator";
        } else if (p.hasPermission("rank.volunteer")) {
            team = "00002Volunteer";
        } else if (p.hasPermission("rank.builder")) {
            team = "00003Builder";
        } else if (p.hasPermission("rank.premium")) {
            team = "00004Premium";
        } else {
            team = "00005Spieler";
        }

        sm.getTeam(team).addPlayer(p);
        p.setDisplayName(sm.getTeam(team).getPrefix() + p.getName());

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(sm);
        }
    }

}
