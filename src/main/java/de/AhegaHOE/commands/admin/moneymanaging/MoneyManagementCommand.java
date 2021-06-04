package de.AhegaHOE.commands.admin.moneymanaging;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.DecimalSeperator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MoneyManagementCommand implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }
        Player p = (Player) sender;

        if (args.length != 4) {
            p.sendMessage("§cFehler: Benutze /moneymanagement <Spieler> bank/purse set/remove/add <Anzahl>");
            return false;
        }

        Player t = Bukkit.getPlayer(args[0]);

        if (t == null) {
            p.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
            return false;
        }

        if (!(args[1].equalsIgnoreCase("bank") || args[1].equalsIgnoreCase("purse"))) {
            p.sendMessage("§cFehler: Benutze /moneymanagement <Spieler> bank/purse set/remove/add <Anzahl>");
            return false;
        }
        if (!(args[2].equalsIgnoreCase("set") || args[2].equalsIgnoreCase("remove") || args[2].equalsIgnoreCase("add"))) {
            p.sendMessage("§cFehler: Benutze /moneymanagement <Spieler> bank/purse set/remove/add <Anzahl>");
            return false;
        }

        if (!(isInt(args[3]))) {
            p.sendMessage("§cFehler: Du hast keine gültige Zahl angegeben.");
            return false;
        }
        if (args[3].startsWith("-")) {
            p.sendMessage("§cFehler: Deine angegebene Zahl darf nicht negativ sein.");
            return false;
        }

        UUID uuid = t.getUniqueId();

        DecimalFormat decimalFormat = DecimalSeperator.prepareFormat(',', '.', false, (byte) 0);

        // TODO:

        int amount = Integer.parseInt(args[3]);
        int currentMoney = MySQLPointer.getMoney(uuid);
        if (args[1].equalsIgnoreCase("bank")) {
            if (args[2].equalsIgnoreCase("set")) {
                // Set Bank Money
                MySQLPointer.setBank(t.getUniqueId(), amount);
                p.sendMessage("§7Du hast das Bankkonto von §9%target% §7auf §9%amount%§7$ gesetzt.".replace("%target%", t.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));
                t.sendMessage("§9%player% §7hat dein Bankkonto auf §9%amount%§7$ gesetzt.".replace("%player%", p.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));

            } else if (args[2].equalsIgnoreCase("remove")) {
                // Removes Bank Money
                if (amount > currentMoney) {
                    // Remove currentMoney
                    MySQLPointer.removeMoneyBank(uuid, currentMoney);
                    p.sendMessage("§7Du hast §9%amount%§7$ des Bankkonto von §9%target%§7 entfernt.".replace("%target%", t.getDisplayName()).replace("%amount%", decimalFormat.format(currentMoney)));
                    t.sendMessage("§9%player% §7hat §9%amount%§7$ von deinem Bankkonto entfernt.".replace("%player%", p.getDisplayName()).replace("%amount%", decimalFormat.format(currentMoney)));

                } else {
                    // Remove Amount
                    MySQLPointer.removeMoneyBank(uuid, amount);
                    p.sendMessage("§7Du hast §9%amount%§7$ des Bankkonto von §9%target%§7 entfernt.".replace("%target%", t.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));
                    t.sendMessage("§9%player% §7hat §9%amount%§7$ von deinem Bankkonto entfernt.".replace("%player%", p.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));

                }
            } else if (args[2].equalsIgnoreCase("add")) {
                // Adds Bank Money
                MySQLPointer.addMoneyBank(uuid, amount);
                p.sendMessage("§7Du hast §9%amount%§7$ dem Bankkonto von §9%target%§7 hinzugefügt.".replace("%target%", t.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));
                t.sendMessage("§9%player% §7hat §9%amount%§7$ deinem Bankkonto hinzugefügt.".replace("%player%", p.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));

            }
        } else if (args[1].equalsIgnoreCase("purse")) {
            if (args[2].equalsIgnoreCase("set")) {
                // Set Bank Money
                MySQLPointer.setMoney(t.getUniqueId(), amount);
                p.sendMessage("§7Du hast das Portemonnaie von §9%target% §7auf §9%amount%§7$ gesetzt.".replace("%target%", t.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));
                t.sendMessage("§9%player% §7hat dein Portemonnaie auf §9%amount%§7$ gesetzt.".replace("%player%", p.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));

            } else if (args[2].equalsIgnoreCase("remove")) {
                // Removes Bank Money
                if (amount > currentMoney) {
                    // Remove currentMoney
                    MySQLPointer.removeMoney(uuid, currentMoney);
                    p.sendMessage("§7Du hast §9%amount%§7$ aus dem Portemonnaie von §9%target%§7 entfernt.".replace("%target%", t.getDisplayName()).replace("%amount%", decimalFormat.format(currentMoney)));
                    t.sendMessage("§9%player% §7hat §9%amount%§7$ aus deinem Portemonnaie entfernt.".replace("%player%", p.getDisplayName()).replace("%amount%", decimalFormat.format(currentMoney)));

                } else {
                    // Remove Amount
                    MySQLPointer.removeMoney(uuid, amount);
                    p.sendMessage("§7Du hast §9%amount%§7$ aus dem Portemonnaie von §9%target%§7 entfernt.".replace("%target%", t.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));
                    t.sendMessage("§9%player% §7hat §9%amount%§7$ aus deinem Portemonnaie entfernt.".replace("%player%", p.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));

                }
            } else if (args[2].equalsIgnoreCase("add")) {
                // Adds Bank Money
                MySQLPointer.addMoney(uuid, amount);
                p.sendMessage("§7Du hast §9%amount%§7$ dem Portemonnaie von §9%target%§7 hinzugefügt.".replace("%target%", t.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));
                t.sendMessage("§9%player% §7hat §9%amount%§7$ zu deinem Portemonnaie hinzugefügt.".replace("%player%", p.getDisplayName()).replace("%amount%", decimalFormat.format(amount)));

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

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 2) {
            List<String> possibilities = new ArrayList<>(Arrays.asList("bank", "purse"));
            return possibilities;
        }
        if (args.length == 3) {
            List<String> possibilities = new ArrayList<>(Arrays.asList("set", "remove", "add"));
            return possibilities;
        }
        return null;
    }
}
