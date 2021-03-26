package de.AhegaHOE.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.util.languageHandler;

public class GamemodeCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;

		if (!p.hasPermission("system.gamemode")) {
			return true;
		}
		if (args.length == 0) {

			p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "GamemodeCommandErrorFalseArgs"));
			return true;
		}

		if (!isInt(args[0])) {

			p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "GamemodCommandErrorNoNumber"));
			return true;
		}

		GameMode gm = null;

		switch (Integer.valueOf(args[0]).intValue()) {

		case 0:
			gm = GameMode.SURVIVAL;
			break;

		case 1:
			gm = GameMode.CREATIVE;
			break;

		case 2:
			gm = GameMode.ADVENTURE;
			break;

		case 3:
			gm = GameMode.SPECTATOR;
			break;
		}

		if (Integer.valueOf(args[0]).intValue() > 3 || args[0].startsWith("-")) {
			p.sendMessage(
					languageHandler.getMessage(languageHandler.getLocale(p), "GamemodeCommandErrorCouldntParseNumber"));
			return true;
		}

		if (args.length == 1) {
			String str = gm.name().toLowerCase();
			String cap = String.valueOf(str.substring(0, 1).toUpperCase()) + str.substring(1);
			p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "GamemodePrefix") + languageHandler
					.getMessage(languageHandler.getLocale(p), "GamemodeSelfMessage").replace("%gamemode%", cap));
			p.setGameMode(gm);
			return true;
		}

		if (args.length == 2) {
			Player t = Bukkit.getPlayer(args[1]);

			if (t == null) {
				p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerNotFound"));
				return true;
			}

			t.setGameMode(gm);
			String str = gm.name().toLowerCase();
			String cap = String.valueOf(str.substring(0, 1).toUpperCase()) + str.substring(1);

			t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "GamemodePrefix")
					+ languageHandler.getMessage(languageHandler.getLocale(t), "GamemodeChangedByOther")
							.replace("%player%", p.getDisplayName()).replace("%gamemode%", cap));

			p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "GamemodePrefix")
					+ languageHandler.getMessage(languageHandler.getLocale(p), "GamemodeOtherChange")
							.replace("%target%", t.getDisplayName()).replace("%gamemode%", cap));

			return true;
		}
		return false;
	}

	private boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException numberFormatException) {

			return false;
		}
	}
}
