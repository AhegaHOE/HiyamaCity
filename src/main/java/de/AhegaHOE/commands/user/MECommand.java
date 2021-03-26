package de.AhegaHOE.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;

public class MECommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length >= 1) {

				String message = String.join(" ", (CharSequence[]) args);
				String punkt = "";
				if (!(message.endsWith("."))) {
					punkt = ".";
				}

				for (Player t : Bukkit.getOnlinePlayers()) {
					if (p.getLocation().distance(t.getLocation()) <= 8.0D) {
						t.sendMessage(ChatColor.DARK_AQUA + "* " + p.getDisplayName() + " " + message + punkt);
					}
				}
			} else if (args.length == 0) {
				sender.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "MeCommandErrorFalseArgs"));
			}
		}

		return false;
	}

}
