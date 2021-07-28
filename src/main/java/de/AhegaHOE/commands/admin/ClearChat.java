package de.AhegaHOE.commands.admin;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)) {

            if (args.length == 0) {

                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!all.hasPermission("cc.bypass")) {
                        clearChat(all);
                        sender.sendMessage(ChatColor.GREEN + "Chat gecleart!");
                    }
                }
            } else if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    sender.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
                    return true;
                }
                sender.sendMessage("§aChat von " + t.getDisplayName() + "§a wurde gecleart!");
                clearChat(t);

            } else {
                sender.sendMessage("§cFehler: Benutze /clearchat [Spieler]");
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

                Bukkit.broadcastMessage("§4§l%player% §4§lhat den Chat gecleart!".replace("%player%", p.getDisplayName()));

            } else if (args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    p.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
                    return true;
                }

                clearChat(t);
                p.sendMessage("§7Du hast den §aChat§7 von §9%player%§7 gecleart.".replace("%player%", t.getDisplayName()));

            } else {
                p.sendMessage("§cFehler: Benutze /clearchat [Spieler]");
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
