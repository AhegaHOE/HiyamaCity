package de.AhegaHOE.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.util.languageHandler;

public class OChat implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length >= 1) {
				String message = String.join(" ", (CharSequence[]) args);

				if (p.isOp()) {
					for (Player t : Bukkit.getOnlinePlayers()) {
						t.sendMessage("(( " + p.getDisplayName() + ": " + message + " ))");
					}
				}

			} else if (args.length == 0) {
				sender.sendMessage(
						languageHandler.getMessage(languageHandler.getLocale(p), "OChatCommandErrorFalseArgs"));
			}
		}
		return false;
	}

	public static String convertArrayToStringMethod(String[] strArray) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < strArray.length; i++) {
			stringBuilder.append(strArray[i]);
		}
		return stringBuilder.toString();
	}
}