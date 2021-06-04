package de.AhegaHOE.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KussCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length == 1) {

                Player t = Bukkit.getPlayer(args[0]);
                if (t == null) {
                    p.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
                    return true;
                }
                if (p.getLocation().distance(t.getLocation()) >= 2.0D) {
                    p.sendMessage("§cFehler: Der angegebene Spieler ist zu weit weg.");
                    return true;
                }
                if (args[0].equalsIgnoreCase(p.getName())) {
                    p.sendMessage("§cFehler: Du kannst dich nicht selber küssen.");
                    return true;
                }

                for (Player o : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().distance(o.getLocation()) <= 8.0) {
                        o.sendMessage("§d* %player%§d hat %target%§d einen Kuss gegeben.".replace("%player%", p.getDisplayName()).replace("%target%", t.getDisplayName()));
                    }
                }

            } else {
                p.sendMessage("§cFehler: Benutze /kuss <Spieler>");
            }
        }
        return false;
    }

}
