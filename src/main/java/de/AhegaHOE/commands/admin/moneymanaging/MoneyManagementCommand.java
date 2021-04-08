package de.AhegaHOE.commands.admin.moneymanaging;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.languageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MoneyManagementCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        // /moneymanagement <name> "bank/money" "set/remove/add" <amount>
        // TODO: Zahlanzeige aufhübschen sprich alle 3 Digits ein Trennpunkt einfügen.

        if (!(sender instanceof Player)) {
            sender.sendMessage(languageHandler.getMessage("en", "PlayerOnly"));
            return false;
        }
        Player p = (Player) sender;

        if (args.length != 4) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "MoneyManagementCommandFalseArgs"));
            return false;
        }

        Player t = Bukkit.getPlayer(args[0]);

        if (t == null) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "PlayerNotFound"));
            return false;
        }

        if (!(args[1].equalsIgnoreCase("bank") || args[1].equalsIgnoreCase("money"))) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "MoneyManagementCommandFalseArgs"));
            return false;
        }
        if (!(args[2].equalsIgnoreCase("set") || args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("add"))) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "MoneyManagementCommandFalseArgs"));
            return false;
        }

        if (!(isInt(args[3]))) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "NoNumber"));
            return false;
        }
        if (args[3].startsWith("-")) {
            p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "NumberNegative"));
            return false;
        }

        UUID uuid = t.getUniqueId();
        int amount = Integer.parseInt(args[3]);
        int currentMoney = MySQLPointer.getMoney(uuid);
        if (args[1].equalsIgnoreCase("bank")) {
            if (args[2].equalsIgnoreCase("set")) {
                // Set Bank Money
                MySQLPointer.setBank(t.getUniqueId(), amount);
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "SetBankSelf").replace("%target%", t.getDisplayName()).replace("%amount%", "" + amount));
                t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "SetBankOther").replace("%player%", p.getDisplayName()).replace("%amount%", "" + amount));

            }
            if (args[2].equalsIgnoreCase("remove")) {
                // Removes Bank Money
                if (amount > currentMoney) {
                    // Remove currentMoney
                    MySQLPointer.removeMoneyBank(uuid, currentMoney);
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "RemoveBankSelf").replace("%target%", t.getDisplayName()).replace("%amount%", "" + currentMoney));
                    t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "RemoveBankOther").replace("%player%", p.getDisplayName()).replace("%amount%", "" + currentMoney));

                } else {
                    // Remove Amount
                    MySQLPointer.removeMoneyBank(uuid, amount);
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "RemoveBankSelf").replace("%target%", t.getDisplayName()).replace("%amount%", "" + amount));
                    t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "RemoveBankOther").replace("%player%", p.getDisplayName()).replace("%amount%", "" + amount));

                }
            }
            if (args[2].equalsIgnoreCase("add")) {
                // Adds Bank Money
                MySQLPointer.addMoneyBank(uuid, amount);
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "AddBankSelf").replace("%target%", t.getDisplayName()).replace("%amount%", "" + amount));
                t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "AddBankOther").replace("%player%", p.getDisplayName()).replace("%amount%", "" + amount));

            }
        }
        if (args[1].equalsIgnoreCase("money")) {
            if (args[2].equalsIgnoreCase("set")) {
                // Set Bank Money
                MySQLPointer.setMoney(t.getUniqueId(), amount);
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "SetMoneySelf").replace("%target%", t.getDisplayName()).replace("%amount%", "" + amount));
                t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "SetMoneyOther").replace("%player%", p.getDisplayName()).replace("%amount%", "" + amount));

            }
            if (args[2].equalsIgnoreCase("remove")) {
                // Removes Bank Money
                if (amount > currentMoney) {
                    // Remove currentMoney
                    MySQLPointer.removeMoney(uuid, currentMoney);
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "RemoveMoneySelf").replace("%target%", t.getDisplayName()).replace("%amount%", "" + currentMoney));
                    t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "RemoveMoneyOther").replace("%player%", p.getDisplayName()).replace("%amount%", "" + currentMoney));

                } else {
                    // Remove Amount
                    MySQLPointer.removeMoney(uuid, amount);
                    p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "RemoveMoneySelf").replace("%target%", t.getDisplayName()).replace("%amount%", "" + amount));
                    t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "RemoveMoneyOther").replace("%player%", p.getDisplayName()).replace("%amount%", "" + amount));

                }
            }
            if (args[2].equalsIgnoreCase("add")) {
                // Adds Bank Money
                MySQLPointer.addMoney(uuid, amount);
                p.sendMessage(languageHandler.getMessage(languageHandler.getLocale(p), "AddMoneySelf").replace("%target%", t.getDisplayName()).replace("%amount%", "" + amount));
                t.sendMessage(languageHandler.getMessage(languageHandler.getLocale(t), "AddMoneyOther").replace("%player%", p.getDisplayName()).replace("%amount%", "" + amount));

            }
        }

        return false;
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
