package de.AhegaHOE.commands.user;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.DecimalSeperator;
import de.AhegaHOE.util.languageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.*;

public class BankCommand implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        // /bank einzahlen/abbuchen <amount>
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageHandler.getMessage("en", "PlayerOnly"));
            return false;
        }
        Player p = (Player) sender;

        if (args.length != 2) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "BankCommandFalseArgs"));
            return false;
        }

        List<String> possibilities = new ArrayList<>(Arrays.asList("withdraw", "deposit", "einzahlen", "abbuchen"));

        if (!(possibilities.contains(args[0].toLowerCase()))) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "BankCommandFalseArgs"));
            return false;
        }
        if (!(isInt(args[1]))) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "NoNumber"));
            return false;
        }

        if (args[1].startsWith("-")) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "NumberNegative"));
            return false;
        }

        UUID uuid = p.getUniqueId();
        int currentMoney = MySQLPointer.getMoney(uuid);
        int currentBank = MySQLPointer.getBank(uuid);
        int amount = Integer.parseInt(args[1]);
        DecimalFormat decimalFormat = DecimalSeperator.prepareFormat(',', '.', false, (byte) 0);

        if (args[0].equalsIgnoreCase("einzahlen") || args[0].equalsIgnoreCase("deposit")) {
            if (amount > currentMoney) {
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "NotEnoughMoney"));
                return false;
            } else if (amount <= currentMoney) {
                MySQLPointer.removeMoney(uuid, amount);
                MySQLPointer.addMoneyBank(uuid, amount);
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "BankDeposit").replace("%amount%", decimalFormat.format(amount)));
            }
        } else if (args[0].equalsIgnoreCase("withdraw") || args[0].equalsIgnoreCase("abbuchen")) {
            if (amount > currentBank) {
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "NotEnoughMoney"));
                return false;
            } else if (amount <= currentBank) {
                MySQLPointer.removeMoneyBank(uuid, amount);
                MySQLPointer.addMoney(uuid, amount);
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "BankWithdraw").replace("%amount%", decimalFormat.format(amount)));
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> possibilities = new ArrayList<>(Arrays.asList("withdraw", "deposit", "einzahlen", "abbuchen"));
            return possibilities;
        }
        return null;
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException numberFormatException) {

            return false;
        }
    }
}
