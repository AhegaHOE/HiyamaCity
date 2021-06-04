package de.AhegaHOE.commands.user;

import de.AhegaHOE.util.commandCooldownHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class DiceCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return true;
        }
        Player p = (Player) sender;
        if (args.length != 0) {
            p.sendMessage("§cFehler: Benutze /dice");
            return true;
        }
        if (commandCooldownHandler.isOnCooldown(p)) {
            p.sendMessage("§cFehler: Dieser Command ist auf Cooldown! \nVersuche es in %remaining%s erneut.".replace("%remaining%", "" + commandCooldownHandler.getTimeLeft(p)));
            return true;
        }

        commandCooldownHandler.setCooldown(p);

        Random r = new Random();
        int result = r.nextInt(6);
        result++;
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.getLocation().distance(p.getLocation()) <= 8.0D) {
                all.sendMessage("§9%player% §7hat eine §9%result% §7gewürfelt.".replace("%player%", p.getDisplayName()).replace("%result%", "" + result));
            }
        }
        return false;
    }
}
