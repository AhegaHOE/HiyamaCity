package de.AhegaHOE.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;

public class KussCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 1) {

				Player t = Bukkit.getPlayer(args[0]);
				if (t == null) {
					p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerNotFound"));
					return true;
				}
				if (p.getLocation().distance(t.getLocation()) >= 2.0D) {
					p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerTooFarAway"));
					return true;
				}
				if (args[0].equals(p.getName())) {
					p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "CantKissSelf"));
					return true;
				}

				for (Player o : Bukkit.getOnlinePlayers()) {
					if (p.getLocation().distance(o.getLocation()) <= 8.0) {
						o.sendMessage(languageHandler.getMessage(languageHandler.getLocale(o), "Kiss").replace("%player%", p.getDisplayName()).replace("%target%", t.getDisplayName()));
					}
				}

			} else {
				p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "KussCommandErrorFalseArgs"));
			}
		}
		return false;
	}

}
