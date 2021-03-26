package de.AhegaHOE.commands.user.languages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.languageHandler;

public class setLangCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(languageHandler.getMessage("en", "PlayerOnly"));
			return false;
		}
		Player p = (Player) sender;
		if (args.length < 1) {
			p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "setLangCommandNEArgs"));
			return false;
		}

		String fileName = args[0];
		languageHandler.setLocale(p, fileName);
		if (languageHandler.files.contains(args[0])) {
			p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "LocaleSet"));
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> langFiles = new ArrayList<>();
		File folder = new File(Main.getInstance().getDataFolder() + "/locales");
		for (File file : folder.listFiles()) {
			langFiles.add(file.getName().split(".yml")[0]);
		}

		return null;
	}

}
