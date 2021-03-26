package de.AhegaHOE.commands.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import de.AhegaHOE.listener.AFKCheck;
import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;

public class AfkCommand implements CommandExecutor {

	public static ArrayList<Player> Afk = new ArrayList<>();

	public static Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();

	public static void loadAFK() {
		sb.registerNewTeam("99999AFK");
		sb.getTeam("99999AFK").setOption(Option.COLLISION_RULE, OptionStatus.NEVER);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (Afk.contains(p)) {
					AFKCheck.removeAFK(p);
					for (Player t : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().distance(t.getLocation()) <= 8.0D) {
							t.sendMessage(ChatColor.GOLD + p.getDisplayName()
									+ languageHandler.getMessage(languageHandler.getLocale(t), "Leave"));
							AFKCheck.afkModeTimeStamp.remove(p.getName(),
									new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));
						}
					}
				} else {
					AFKCheck.setAFK(p);
					for (Player t : Bukkit.getOnlinePlayers()) {
						if (p.getLocation().distance(t.getLocation()) <= 8.0D) {
							t.sendMessage(ChatColor.GOLD + p.getDisplayName()
									+ languageHandler.getMessage(languageHandler.getLocale(t), "Enter"));
							AFKCheck.afkModeTimeStamp.put(p.getName(),
									new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));
						}
					}
				}
			}
		}
		return false;
	}

}
