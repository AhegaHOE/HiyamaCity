package de.AhegaHOE.commands.user;

import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.commandCooldownHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HeadsOrTailsCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return true;
        }
        Player p = (Player) sender;
        if (args.length != 0) {
            p.sendMessage("§cFehler: Benutze /coinflip");
            return true;
        }
        if (commandCooldownHandler.isOnCooldown(p)) {
            p.sendMessage("§cFehler: Dieser Command ist auf Cooldown! \nVersuche es in %remaining%s erneut.".replace("%remaining%", "" + commandCooldownHandler.getTimeLeft(p)));
            return true;
        }

        commandCooldownHandler.setCooldown(p);

        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.getLocation().distance(p.getLocation()) <= 8.0D) {
                all.sendMessage("§9%player% §7wirft eine §9Münze§7...".replace("%player%", p.getDisplayName()));
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    if (Math.random() < 0.5) {
                        all.sendMessage("§7Es ist Kopf...");
                    } else {
                        all.sendMessage("§7Es ist Zahl...");
                    }
                }, 2 * 20);
            }
        }

        return false;
    }
}
