package de.AhegaHOE.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission("heal")) {
				return false;
			}
			if (args.length == 0) {
				p.setHealth(20);
				p.setFoodLevel(20);
			}
		}
		return false;
	}

}
