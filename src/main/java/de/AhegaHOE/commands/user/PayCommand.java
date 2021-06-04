package de.AhegaHOE.commands.user;

import de.AhegaHOE.MySQL.MySQLPointer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }

        Player p = (Player) sender;

        if (args.length != 2) {
            p.sendMessage("§cFehler: Benutze /pay <Spieler> <Anzahl>");
            return false;
        }

        Player t = Bukkit.getPlayer(args[0]);

        if (t == null) {
            p.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
            return false;
        }

        if (t.getName() == p.getName()) {
            p.sendMessage("§cFehler: Du kannst dir selbst kein Geld geben.");
            return false;
        }

        if (!(p.getLocation().distance(t.getLocation()) <= 2.0D)) {
            p.sendMessage("§cFehler: Der angegebene Spieler ist zu weit weg.");
            return false;
        }


        int amount = Integer.parseInt(args[1]);

        if (!(MySQLPointer.hasEnoughMoney(p.getUniqueId(), amount))) {
            p.sendMessage("§cFehler: Du hast nicht genug Geld.");
            return false;
        } else {
            p.sendMessage("§7Du hast §9%target% §9%amount%§7$ zugesteckt.".replace("%target%", t.getDisplayName()).replace("%amount%", "" + amount));
            t.sendMessage("§9%player% §7hat dir §9%amount%§7$ zugesteckt.".replace("%player%", p.getDisplayName()).replace("%amount%", "" + amount));
            MySQLPointer.removeMoney(p.getUniqueId(), amount);
            MySQLPointer.addMoneyBank(t.getUniqueId(), amount);
        }

        return false;
    }
}
