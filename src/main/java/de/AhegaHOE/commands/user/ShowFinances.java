package de.AhegaHOE.commands.user;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.DecimalSeperator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class ShowFinances implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if (args.length != 1) {
            p.sendMessage("§cFehler: Benutze /showfinances <Spieler>");
            return false;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            p.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
            return false;
        }
        if (!(p.getLocation().distance(t.getLocation()) <= 8.0D)) {
            p.sendMessage("§cFehler: Der angegebene Spieler ist zu weit weg.");
            return false;
        }

        int money = MySQLPointer.getMoney(p.getUniqueId());
        int bank = MySQLPointer.getBank(p.getUniqueId());

        DecimalFormat decimalFormat = DecimalSeperator.prepareFormat(',', '.', false, (byte) 0);

        p.sendMessage("§7Du hast §9%target% §7deine Finanzen gezeigt.".replace("%target%", t.getDisplayName()));
        t.sendMessage("§9%player% §7hat dir deren Finanzen gezeigt.".replace("%player%", p.getDisplayName()));
        t.sendMessage("§7Geld: §9%money%§7$, Bank: §9%bank%§7$".replace("%money%", "" + decimalFormat.format(money)).replace("%bank%", "" + decimalFormat.format(bank)));

        return false;
    }
}
