package de.AhegaHOE.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;

public class SchreienCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length >= 1) {

				String message = String.join(" ", (CharSequence[]) args);
				for (Player t : Bukkit.getOnlinePlayers()) {
					if (p.getLocation().distance(t.getLocation()) <= 32.0D) {
						t.sendMessage(ChatColor.WHITE + p.getDisplayName() + "§f"
								+ languageHandler.getMessage(languageHandler.getLocale(t), "Shout") + message);
						continue;
					}

					if (p.getLocation().distance(t.getLocation()) <= 48.0D) {
						t.sendMessage(ChatColor.GRAY + p.getDisplayName() + "§7"
								+ languageHandler.getMessage(languageHandler.getLocale(t), "Shout") + message);
						continue;
					}

					if (p.getLocation().distance(t.getLocation()) <= 64.0D) {
						t.sendMessage(ChatColor.DARK_GRAY + p.getDisplayName() + "§8"
								+ languageHandler.getMessage(languageHandler.getLocale(t), "Shout") + message);
					}
				}
			} else {
				p.sendMessage(
						languageHandler.getMessage(languageHandler.getLocale(p), "SchreienCommandErrorFalseArgs"));
			}
		}

		return false;

	}
}
