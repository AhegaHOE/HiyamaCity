package de.AhegaHOE.commands.admin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.util.languageHandler;

public class BuildModeCommand implements CommandExecutor {

	public static ArrayList<String> buildmode = new ArrayList<>();

	public boolean onCommand(CommandSender sender, Command cmd, String stringlabel, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				Player p = (Player) sender;
				if (p.hasPermission("system.buildmode")) {
					if (buildmode.contains(p.getName())) {
						buildmode.remove(p.getName());
						p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "BuildmodePrefix")
								+ languageHandler.getMessage(languageHandler.getLocale(p), "BuildmodeMessageLeave"));
						if (p.getGameMode().equals(GameMode.CREATIVE)) {
							p.setGameMode(GameMode.SURVIVAL);
						}
					} else {

						buildmode.add(p.getName());
						p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "BuildmodePrefix")
								+ languageHandler.getMessage(languageHandler.getLocale(p), "BuildmodeMessageEnter"));
						if (p.getGameMode().equals(GameMode.SURVIVAL)) {
							p.setGameMode(GameMode.CREATIVE);
						}
					}
				}
			} else if (args.length == 1) {
				if (Bukkit.getPlayer(args[0]) != null) {
					Player t = Bukkit.getPlayer(args[0]);
					Player p = (Player) sender;

					if (buildmode.contains(t.getName())) {
						buildmode.remove(t.getName());
						p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "BuildmodePrefix")
								+ languageHandler.getMessage(
										languageHandler.getLocale(p), "BuildmodeRemoveOtherPlayerMessage").replace("%target%", t.getDisplayName()));

						t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "BuildmodePrefix")
								+ languageHandler
										.getMessage(languageHandler.getLocale(t), "BuildmodeRemoveOtherTargetMessage")
										.replace("%player%", p.getDisplayName()));

						if (t.getGameMode().equals(GameMode.CREATIVE)) {
							t.setGameMode(GameMode.SURVIVAL);
						}
					} else {

						buildmode.add(t.getName());
						p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "BuildmodePrefix")
								+ languageHandler.getMessage(
										languageHandler.getLocale(p), "BuildmodeSetOtherPlayerMessage").replace("%target%", t.getDisplayName()));

						t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "BuildmodePrefix")
								+ languageHandler
										.getMessage(languageHandler.getLocale(t), "BuildmodeSetOtherTargetMessage")
										.replace("%player%", p.getDisplayName()));
						if (t.getGameMode().equals(GameMode.SURVIVAL)) {
							t.setGameMode(GameMode.CREATIVE);
						}
					}
				}

			}
		}

		return false;
	}
}