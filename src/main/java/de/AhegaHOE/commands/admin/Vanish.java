package de.AhegaHOE.commands.admin;

import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.Tablist;
import de.AhegaHOE.util.languageHandler;
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

            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "VanishShow"));
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.showPlayer(Main.getInstance(), p);
            }

            Tablist.updateTab();

        } else if (!isVanished(p)) {
            setVanish(p);
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "VanishHide"));
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (!all.hasPermission("vanish.bypass")) {
                    all.hidePlayer(Main.getInstance(), p);
                }
            }

            Tablist.updateTab();

        }

        return false;
    }

    public static void setVanish(Player p) {
        vanishedPlayers.add(p);

    }

    public static void removeVanish(Player p) {
        vanishedPlayers.remove(p);

    }

    public static boolean isVanished(Player p) {
        return vanishedPlayers.contains(p);
    }

}
