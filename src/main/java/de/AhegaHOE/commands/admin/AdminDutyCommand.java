package de.AhegaHOE.commands.admin;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminDutyCommand implements CommandExecutor {

    public static ArrayList<String> adminduty = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("adminduty")) {
                    if (adminduty.contains(p.getName())) {
                        adminduty.remove(p.getName());
                        p.sendMessage("§8[§cAdmindienst§8]§7"
                                + " §7Du hast den §cAdmindienst§7 verlassen.");
                        if (p.getGameMode().equals(GameMode.CREATIVE)) {
                            p.setGameMode(GameMode.SURVIVAL);
                        }
                    } else {
                        adminduty.add(p.getName());
                        p.sendMessage("§8[§cAdmindienst§8]§7"
                                + " §7Du hast den §cAdmindienst§7 betreten.");
                        if (p.getGameMode().equals(GameMode.SURVIVAL)) {
                            p.setGameMode(GameMode.CREATIVE);
                        }
                    }
                }

            }
        }
        return false;
    }

}
