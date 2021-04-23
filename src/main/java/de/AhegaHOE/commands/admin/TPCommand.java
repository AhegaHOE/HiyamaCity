package de.AhegaHOE.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.util.languageHandler;

public class TPCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((args.length == 0 || args.length > 4) && sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("tp")) {
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "TPCommandErrorFalseArgs"));
            }
        }
        if (args.length == 1 && sender instanceof Player) {

            Player p = (Player) sender;
            if (p.hasPermission("tp")) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    p.teleport(t.getLocation());
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "TeleportSelfToPlayer").replace("%target%", t.getDisplayName()));
                    t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "TeleportOtherToSelf").replace("%player%", p.getDisplayName()));
                } else {
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "TPCommandErrorFalseArgsTeleportPlayerToPlayer1"));
                }
            }

        } else if (args.length == 2) {
            Player p = (Player) sender;
            if (p.hasPermission("tp")) {
                Player t = Bukkit.getPlayer(args[0]);
                Player t1 = Bukkit.getPlayer(args[1]);
                if (t != null && t1 != null) {
                    t.teleport(t1.getLocation());
                    if (t1.getName().equals(p.getName())) {
                        p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "TeleportOtherToSelfMessageSelf").replace("%target%", t.getDisplayName()));
                        t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "TeleportOtherToSelfMessageOther").replace("%player%", p.getDisplayName()));
                    } else {
                        p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "TeleportOtherToOtherSelf").replace("%target%", t.getDisplayName()).replace("%target1%", t1.getDisplayName()));

                        t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "TeleportOtherToOtherOther").replace("%player%", p.getDisplayName()).replace("%target1%", t1.getDisplayName()));

                        t1.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t1), "TeleportOtherToOtherTarget1").replace("%player%", p.getDisplayName()).replace("%target%", t.getDisplayName()));
                    }

                } else {
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "TPCommandErrorFalseArgsTeleportPlayerToPlayer"));
                }
            }
        } else if (args.length == 3 && sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("tp")) {
                if (isInt(args[0]) && isInt(args[1]) && isInt(args[2])) {
                    float x = Integer.parseInt(args[0]);
                    float y = Integer.parseInt(args[1]);
                    float z = Integer.parseInt(args[2]);
                    Location loc = new Location(p.getWorld(), x, y, z, p.getLocation().getYaw(),
                            p.getLocation().getPitch());
                    p.teleport(loc);
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "TeleportSelfToCoordinate").replace("%x%", args[0]).replace("%y%", args[1]).replace("%z%", args[2]));
                } else {
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "TPCommandErrorFalseArgsTeleportToCoordinates"));
                }
            }
        } else if (args.length == 4 && sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("tp")) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    if (isInt(args[1]) && isInt(args[2]) && isInt(args[3])) {
                        float x = Integer.parseInt(args[1]);
                        float y = Integer.parseInt(args[2]);
                        float z = Integer.parseInt(args[3]);
                        Location loc = new Location(p.getWorld(), x, y, z, p.getLocation().getYaw(),
                                p.getLocation().getPitch());
                        t.teleport(loc);
                        p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "TeleportOtherToCoordinateSelf").replace("%target%", t.getDisplayName()).replace("%x%", "" + x).replace("%y%", "" + y).replace("%z%", "" + z));
                        t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "TeleportOtherToCoordinateOther").replace("%player%", p.getDisplayName()).replace("%x%", "" + x).replace("%y%", "" + y).replace("%z%", "" + z));
                    }
                }
            }
        }
        return false;
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {

            return false;
        }
    }
}