package de.AhegaHOE.ranks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.scoreboard.Team;

public class RankScoreboard {

    public static Scoreboard sm = Bukkit.getScoreboardManager().getNewScoreboard();

    public static void loadScoreboards() {
        sm.registerNewTeam("00000Mentor");
        sm.registerNewTeam("00001Moderator");
        sm.registerNewTeam("00002Helfer");
        sm.registerNewTeam("00003Spieler");
        sm.registerNewTeam("99999AFK");
        sm.getTeam("99999AFK").setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);


        sm.getTeam("00000Mentor").setPrefix("§4Mentor §7• §4");
        sm.getTeam("00000Mentor").setSuffix("§r");

        sm.getTeam("00001Moderator").setPrefix("§2Mod §7• §2");
        sm.getTeam("00001Moderator").setSuffix("§r");

        sm.getTeam("00002Helfer").setPrefix("§6Helfer §7• §6");
        sm.getTeam("00002Helfer").setSuffix("§r");

        sm.getTeam("00003Spieler").setPrefix("§7");
        sm.getTeam("00003Spieler").setSuffix("§r");

        sm.getTeam("99999AFK").setPrefix("§6AFK §7• §6");
        sm.getTeam("99999AFK").setSuffix("§r");


        for (Player t : Bukkit.getOnlinePlayers()) {
            setPrefix(t);

        }
    }

    public static void setPrefix(Player p) {
        String team;
        if (p.hasPermission("rank.Mentor")) {
            team = "00000Mentor";
        } else if (p.hasPermission("rank.moderator")) {
            team = "00001Moderator";
        } else if (p.hasPermission("rank.supporter")) {
            team = "00002Helfer";
        } else {
            team = "00003Spieler";
        }

        sm.getTeam(team).addPlayer(p);
        p.setDisplayName(sm.getTeam(team).getPrefix() + p.getName() + sm.getTeam(team).getSuffix());

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(sm);
        }
    }


}
