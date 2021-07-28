package de.AhegaHOE.commands.admin.ticket;

import de.AhegaHOE.commands.user.ticket.Ticket;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class AcceptReport implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("report")) {
            return false;
        }

        if (args.length != 1) {
            p.sendMessage("§cFehler: Benutze /acceptreport <Token>");
            return false;
        }

        String token = args[0];
        Ticket.acceptReport(p, Bukkit.getPlayer(getKey(Ticket.reportTokens, token)), token);


        return false;
    }

    private <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
