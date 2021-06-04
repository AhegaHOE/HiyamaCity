package de.AhegaHOE.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((args.length == 0 || args.length > 4) && sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("tp")) {
                p.sendMessage("§cFehler: Benutze /tp [Spieler] <Spieler> oder /tp [Spieler] <x> <y> <z>");
            }
        }
        if (args.length == 1 && sender instanceof Player) {

            Player p = (Player) sender;
            if (p.hasPermission("tp")) {
                Player t = Bukkit.getPlayer(args[0]);
                if (t != null) {
                    p.teleport(t.getLocation());
                    p.sendMessage("§7Du hast dich zu §9%target% §7teleportiert.".replace("%target%", t.getDisplayName()));
                    if (!Vanish.isVanished(p)) {
                        t.sendMessage("§9%player% §7hat sich zu dir teleportiert.".replace("%player%", p.getDisplayName()));
                    }
                } else {
                    p.sendMessage("§cFehler: Benutze /tp [Spieler] <Spieler>");
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
                        p.sendMessage("§7Du hast §9%target% §7zu dir teleportiert.".replace("%target%", t.getDisplayName()));
                        t.sendMessage("§9%player% §7hat dich zu sich teleportiert.".replace("%player%", p.getDisplayName()));
                    } else {
                        p.sendMessage("§7Du hast §9%target% §7zu §9%target1% §7teleportiert.".replace("%target%", t.getDisplayName()).replace("%target1%", t1.getDisplayName()));

                        t.sendMessage("§9%player% §7hat dich zu §9%target1% §7teleportiert.".replace("%player%", p.getDisplayName()).replace("%target1%", t1.getDisplayName()));

                        t1.sendMessage("§9%player% §7hat §9%target% §7zu dir teleportiert.".replace("%player%", p.getDisplayName()).replace("%target%", t.getDisplayName()));
                    }

                } else {
                    p.sendMessage("§cFehler: Benutze /tp <Spieler> [Spieler]");
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
                    p.sendMessage("§7Du hast dich zu den Koordinaten: §9%x%§7, §9%y%§7, §9%z% §7teleportiert.".replace("%x%", args[0]).replace("%y%", args[1]).replace("%z%", args[2]));
                } else {
                    p.sendMessage("§cFehler: Benutze /tp <x> <y> <z>");
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
                        p.sendMessage("§7Du hast §9%target% §7zu den Koordinaten: §9%x%§7, §9%y%§7, §9%z% §7teleportiert.".replace("%target%", t.getDisplayName()).replace("%x%", "" + x).replace("%y%", "" + y).replace("%z%", "" + z));
                        t.sendMessage("§7Du wurdest von §9%player% §7zu den Koordinaten: §9%x%§7, §9%y%§7, §9%z% §7teleportiert.".replace("%player%", p.getDisplayName()).replace("%x%", "" + x).replace("%y%", "" + y).replace("%z%", "" + z));
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