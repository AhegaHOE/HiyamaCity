package de.AhegaHOE.commands.admin;

import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        // TODO: NACHRICHT FÜR DIE ANDEREN SPIELER AUßER SENDER
        if (!(sender instanceof Player)) {

            if (args.length == 0) {

                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!all.hasPermission("cc.bypass")) {
                        clearChat(all);
                        sender.sendMessage(ChatColor.GREEN + "Chat cleared!");
                    }
                }
            } else if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    sender.sendMessage(languageHandler.getMessage("de", "PlayerNotFound"));
                    return true;
                }
                sender.sendMessage("§aChat of " + t.getDisplayName() + "§a cleared!");
                clearChat(t);

            } else {
                sender.sendMessage(languageHandler.getMessage("de", "ClearChatFalseArgs"));
                return true;
            }

        } else {

            Player p = (Player) sender;

            if (!p.hasPermission("cc")) return true;

            if (args.length == 0) {

                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!all.hasPermission("cc.bypass")) {
                        clearChat(all);
                    }
                }

                Bukkit.broadcastMessage(languageHandler.getMessage(languageHandler.getLocale(p), "ChatClear").replace("%player%", p.getDisplayName()));

            } else if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerNotFound"));
                    return true;
                }

                clearChat(t);
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "ChatClearOther").replace("%player%", t.getDisplayName()));

            } else {
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "ClearChatFalseArgs"));
                return true;
            }
        }
        return false;
    }


    private void clearChat(Player p) {
        for (int i = 0; i < 1000; i++) {
            p.sendMessage("");
        }
    }
}
