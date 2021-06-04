package de.AhegaHOE.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSpy implements CommandExecutor {

    public static List<Player> playersSpying = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;
        if (!p.hasPermission("commandspy")) {
            return true;
        }
        if (args.length != 0) {
            return true;
        }
        if (isCommandSpying(p)) {
            p.sendMessage("§cCommandSpy deaktiviert!");
            removeCommandSpying(p);
        } else {
            p.sendMessage("§aCommandSpy aktiviert!");
            setCommandSpying(p);
        }

        return false;
    }

    public static void setCommandSpying(Player p) {
        if (!isCommandSpying(p)) {
            playersSpying.add(p);
        }
    }

    public static void removeCommandSpying(Player p) {
        if (isCommandSpying(p)) {
            playersSpying.remove(p);
        }
    }

    public static boolean isCommandSpying(Player p) {
        return playersSpying.contains(p);
    }

    public static List<Player> getSpyingPlayers() {
        return playersSpying;
    }
}