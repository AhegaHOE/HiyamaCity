package de.AhegaHOE.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;

public class OosChat implements CommandExecutor {
    String error = "Du kannst den /oos Command nur als Spieler benutzen!";

    String msg;

    public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length >= 1) {
                String msg = String.join(" ", args);

                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().distance(t.getLocation()) <= 20.0D) {
                        t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "OutOfSituationAdds").replace("%player%", p.getDisplayName()).replace("%message%", msg));
                    }
                }

            } else
                sender.sendMessage(
                        languageHandler.getMessage(languageHandler.getLocale(p), "OosChatCommandErrorFalseArgs"));


        }
        return false;
    }
}