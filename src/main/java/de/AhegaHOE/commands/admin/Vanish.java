package de.AhegaHOE.commands.admin;

import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.Tablist;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Vanish implements CommandExecutor {

    public static List<Player> vanishedPlayers = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;
        if (!p.hasPermission("vanish")) return true;

        if (args.length != 0) {
            p.sendMessage("" + Vanish.vanishedPlayers.size());
            return true;
        }

        if (isVanished(p)) {
            removeVanish(p);
            p.sendMessage("§7Du bist nun nicht mehr im §9vanish§7... - jeder kann §9dich jetzt wieder sehen§7.");


        } else if (!isVanished(p)) {
            setVanish(p);
            p.sendMessage("§7Du bist nun im §9vanish§7... - niemand kann §9dich jetzt sehen§7.");

        }

        return false;
    }

    public static void setVanish(Player p) {
        vanishedPlayers.add(p);
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (!all.hasPermission("vanish.bypass")) {
                all.hidePlayer(Main.getInstance(), p);
            }
        }
        Tablist.updateTab();

    }

    public static void removeVanish(Player p) {
        vanishedPlayers.remove(p);
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.showPlayer(Main.getInstance(), p);
        }
        Tablist.updateTab();
    }

    public static boolean isVanished(Player p) {
        return vanishedPlayers.contains(p);
    }


    public static void removeAllPlayersVanish() {
        for (Player all : Vanish.vanishedPlayers) {
            removeVanish(all);

        }
    }


}
