package de.AhegaHOE.commands.admin.moneymanaging;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.languageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckFinancesCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if (!p.hasPermission("checkfinances")) return false;
        if (args.length != 1) return false;
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) return false;

        p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "CheckFinancesCommand1").replace("%target%", t.getDisplayName()));
        p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "CheckFinancesCommand2").replace("%money%", "" + MySQLPointer.getMoney(t.getUniqueId())).replace("%bank%", "" + MySQLPointer.getBank(t.getUniqueId())));
        return false;
    }
}
