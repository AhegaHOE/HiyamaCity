package de.AhegaHOE.commands.admin.moneymanaging;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.DecimalSeperator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class CheckFinancesCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if (!p.hasPermission("checkfinances")) return false;
        if (args.length != 1) return false;
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) return false;

        int money = MySQLPointer.getMoney(t.getUniqueId());
        int bank = MySQLPointer.getBank(t.getUniqueId());

        DecimalFormat decimalFormat = DecimalSeperator.prepareFormat(',', '.', false, (byte) 0);

        p.sendMessage("§7Der Finanzstatus von §9%target% §7ist:".replace("%target%", t.getDisplayName()));
        p.sendMessage("§7Geld: §9%money%§7$, Bank: §9%bank%§7$".replace("%money%", "" + decimalFormat.format(money)).replace("%bank%", "" + decimalFormat.format(bank)));
        return false;
    }
}
