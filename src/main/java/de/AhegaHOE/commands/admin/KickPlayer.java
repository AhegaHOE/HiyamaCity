package de.AhegaHOE.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickPlayer implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length < 1) {
                sender.sendMessage("§cFehler: Benutze /kick <Spieler> [Grund]");
                return true;
            }
            Player t = Bukkit.getPlayer(args[0]);
            if (t == null) {
                sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
                return true;
            }
            if (args.length != 1) {
                String message = "";
                for (int i = 1; i < args.length; i++) {
                    message += message + args[i] + " ";
                }
                t.kickPlayer("§cDu wurdest von %player%§c gekickt. \n Grund: %reason%".replace("%player%", "CONSOLE").replace("%reason%", message));
            } else {
                t.kickPlayer("§cDu wurdest gekickt.");
            }
        } else if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("kick")) {
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage("§cFehler: Benutze /kick <Spieler> [Grund]");
                return true;
            }
            Player t = Bukkit.getPlayer(args[0]);
            if (t == null) {
                sender.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
                return true;
            }
            if (args.length != 1) {
                String message = "";
                for (int i = 1; i < args.length; i++) {
                    message += message + args[i] + " ";
                }
                t.kickPlayer("§cDu wurdest von %player%§c gekickt. \n Grund: %reason%".replace("%player%", p.getDisplayName()).replace("%reason%", message));
            } else {
                t.kickPlayer("§cDu wurdest gekickt.");
            }
        }
        return false;
    }
}
