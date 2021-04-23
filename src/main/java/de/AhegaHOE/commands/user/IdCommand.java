package de.AhegaHOE.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.AhegaHOE.listener.AFKCheck;
import de.AhegaHOE.util.languageHandler;

import java.text.DateFormat;
import java.util.Locale;

public class IdCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (AfkCommand.Afk.contains(t)) {
                        DateFormat formatterTime = DateFormat.getTimeInstance(DateFormat.FULL, Locale.forLanguageTag("de"));
                        long time = Long.parseLong(String.valueOf(AFKCheck.afkModeTimeStamp));
                        sender.sendMessage(languageHandler.getMessage("de", "AFKMessage").replace("%target%", t.getDisplayName()).replace("%time%", formatterTime.format(time)));
                    } else {
                        sender.sendMessage(languageHandler.getMessage("de", "Ping").replace("%target%", t.getDisplayName()).replace("%ping%", "" + ((CraftPlayer) t).getHandle().ping));
                    }
                }
            }
        } else {
            Player p = (Player) sender;
            if (args.length == 1) {
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player t = Bukkit.getPlayer(args[0]);
                    if (AfkCommand.Afk.contains(t)) {
                        DateFormat formatterTime = DateFormat.getTimeInstance(DateFormat.FULL, Locale.forLanguageTag(languageHandler.getLocale(p)));
                        long time = AFKCheck.afkModeTimeStamp.get(t.getName());
                        p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "AFKMessage").replace("%target%", t.getDisplayName()).replace("%time%", formatterTime.format(time)));
                    } else {
                        p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "Ping").replace("%target%", t.getDisplayName()).replace("%ping%", "" + ((CraftPlayer) t).getHandle().ping));
                    }
                }
            }
        }
        return false;
    }

}

