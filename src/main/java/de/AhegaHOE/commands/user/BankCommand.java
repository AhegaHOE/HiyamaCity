package de.AhegaHOE.commands.user;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.ATMConfigHandler;
import de.AhegaHOE.util.DecimalSeperator;
import de.AhegaHOE.util.FileBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

public class BankCommand implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        // /bank einzahlen/abbuchen <amount>
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }
        Player p = (Player) sender;

        if (!isNearATM(p)) {
            p.sendMessage("§7Du bist nicht an einem §9Bankautomat§7.");
            return true;
        }

        if (args.length > 3 || args.length < 2) {
            p.sendMessage("§cFehler: Benutze /bank abbuchen/einzahlen/überweisen [Spieler] <Anzahl>");
            return false;
        }

        List<String> possibilities = new ArrayList<>(Arrays.asList("einzahlen", "abbuchen", "überweisen"));

        if (!(possibilities.contains(args[0].toLowerCase()))) {
            p.sendMessage("§cFehler: Benutze /bank abbuchen/einzahlen/überweisen [Spieler] <Anzahl>");
            return false;
        }

        if (args[1].startsWith("-")) {
            p.sendMessage("§cFehler: Deine angegebene Zahl darf nicht negativ sein.");
            return false;
        }

        UUID uuid = p.getUniqueId();
        int currentMoney = MySQLPointer.getMoney(uuid);
        int currentBank = MySQLPointer.getBank(uuid);

        DecimalFormat decimalFormat = DecimalSeperator.prepareFormat(',', '.', false, (byte) 0);


        String methodeCase = args[0];

        switch (methodeCase) {
            case "einzahlen":
            case "deposit":

                if (args.length != 2) {
                    p.sendMessage("§cFehler: Benutze /bank abbuchen/einzahlen/überweisen [Spieler] <Anzahl>");
                    return false;
                }

                if (!(isInt(args[1]))) {
                    p.sendMessage("§cFehler: Du hast keine gültige Zahl angegeben.");
                    return false;
                }
                int amount = Integer.parseInt(args[1]);

                if (amount == 0) {
                    p.sendMessage("§cFehler: Du hast keine gültige Zahl angegeben.");
                    return true;
                }

                if (amount > currentMoney) {
                    p.sendMessage("§cFehler: Du hast nicht genug Geld.");
                    return false;
                } else {
                    MySQLPointer.removeMoney(uuid, amount);
                    MySQLPointer.addMoneyBank(uuid, amount);
                    p.sendMessage("§7Du hast §9%amount%§7$ auf dein Bankkonto eingezahlt.".replace("%amount%", decimalFormat.format(amount)));
                }
                break;

            case "abbuchen":
            case "withdraw":

                if (args.length != 2) {
                    p.sendMessage("§cFehler: Benutze /bank abbuchen/einzahlen/überweisen [Spieler] <Anzahl>");
                    return false;
                }

                if (!(isInt(args[1]))) {
                    p.sendMessage("§cFehler: Du hast keine gültige Zahl angegeben.");
                    return false;
                }

                amount = Integer.parseInt(args[1]);

                if (amount == 0) {
                    p.sendMessage("§cFehler: Du hast keine gültige Zahl angegeben.");
                    return true;
                }

                if (amount > currentBank) {
                    p.sendMessage("§cFehler: Du hast nicht genug Geld.");
                    return false;
                } else {
                    MySQLPointer.removeMoneyBank(uuid, amount);
                    MySQLPointer.addMoney(uuid, amount);
                    p.sendMessage("§7Du hast §9%amount%§7$ von deinem Bankkonto abgebucht.".replace("%amount%", decimalFormat.format(amount)));
                }
                break;

            case "überweisen":
            case "transfer":

                if (args.length != 3) {
                    p.sendMessage("§cFehler: Benutze /bank abbuchen/einzahlen/überweisen [Spieler] <Anzahl>");
                    return false;
                }

                if (!(isInt(args[2]))) {
                    p.sendMessage("§cFehler: Du hast keine gültige Zahl angegeben.");
                    return false;
                }

                Player t = Bukkit.getPlayer(args[1]);
                int amount1 = Integer.parseInt(args[2]);
                if (t == null) {
                    p.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
                    return false;
                }
                if (t.getDisplayName().equalsIgnoreCase(p.getDisplayName())) {
                    p.sendMessage("§7Du kannst dir nicht selbst Geld überweisen.");
                    return false;
                }
                if (amount1 > currentBank) {
                    p.sendMessage("§cFehler: Du hast nicht genug Geld.");
                    return false;
                } else {
                    MySQLPointer.removeMoneyBank(uuid, amount1);
                    MySQLPointer.addMoneyBank(t.getUniqueId(), amount1);
                    p.sendMessage("§7Du hast §9%amount%§7$ an §9%target%§7 überwiesen.".replace("%amount%", decimalFormat.format(amount1)).replace("%target%", t.getDisplayName()));
                    t.sendMessage("§9%target%§7 hat dir §9%amount%§7$ überwiesen.".replace("%amount%", decimalFormat.format(amount1)).replace("%target%", p.getDisplayName()));

                }
                break;
        }


        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {

            List<String> de = new ArrayList<>(Arrays.asList("einzahlen", "abbuchen", "überweisen"));
            if (commandSender instanceof Player) {

                return de;

            }

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

    private boolean isNearATM(Player p) {
        FileBuilder fb = new FileBuilder(Main.getInstance().getDataFolder() + "/atm_locations", "atmlocs.yml");
        for (int i = 0; i < ATMConfigHandler.atmcount; i++) {
            if (p.getLocation().distance(new Location(
                    Bukkit.getWorld(fb.getString(i + ".world")),
                    fb.getDouble(i + ".x"),
                    fb.getDouble(i + ".y"),
                    fb.getDouble(i + ".z"))) <= 1.5D) {
                return true;
            }
        }
        return false;
    }
}
