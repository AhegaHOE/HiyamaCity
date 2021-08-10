package de.AhegaHOE.commands.admin.banmanaging;

import de.AhegaHOE.util.Util;
import de.AhegaHOE.util.banmanagement.Banning;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class TempBan implements CommandExecutor {
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.forLanguageTag("de"));


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("system.tempban")) {
            sender.sendMessage("§cFehler: Du hast dazu keine Rechte!");
            return true;
        }

        if (args.length <= 2) {
            sender.sendMessage("§cFehler: Benutze /tempban <Spieler> <Zeit> <Grund>");
            return true;
        }

        UUID uuid = Bukkit.getPlayerUniqueId(args[0]);

        long time = 0;
        int duration;

        String format = ((args[1].endsWith("min") ? args[1].substring(args[1].length() - 3, args[1].length()).toLowerCase() : args[1].substring(args[1].length() - 1, args[1].length()).toLowerCase()));

        try {
            duration = ((args[1].endsWith("min") ? Integer.parseInt(args[1].substring(0, args[1].length() - 3)) : Integer.parseInt(args[1].substring(0, args[1].length() - 1))));
        } catch (NumberFormatException e) {
            sender.sendMessage("§cFehler: Deine Zeitangabe ist nicht gültig.\nBitte überprüfe deine Eingabe.");
            return true;
        }

        switch (format) {

            case "y":
                time = duration * 1000 * 60 * 60 * 24 * 365;
                break;

            case "m":
                time = duration * 1000 * 60 * 60 * 24 * 30;
                break;

            case "w":
                time = duration * 1000 * 60 * 60 * 24 * 7;
                break;

            case "d":
                time = duration * 1000 * 60 * 60 * 24;
                break;

            case "h":
                time = duration * 1000 * 60 * 60;
                break;

            case "min":
                time = duration * 1000 * 60;
                break;

            case "s":
                time = duration * 1000;
                break;

            default:
                sender.sendMessage("§cFehler: Benutze Y (Jahre), M (Monate), W (Wochen), D (Tage), H (Stunden), MIN (Sekunden) oder S (Sekunden)");
                return true;

        }


        if (Banning.hasActiveBans(uuid)) {
            sender.sendMessage("§cFehler: Dieser Spieler ist bereits gebannt.");
            return true;
        }

        String message = "";

        for (int i = 2; i < args.length; i++) {
            message += args[i] + " ";
        }

        message = message.trim();

        Banning.createBan(uuid, ((sender instanceof Player) ? ((Player) sender).getUniqueId() : null), message, System.currentTimeMillis() + time);
        sender.sendMessage("§7Du hast §9" + args[0] + " §aerfolgreich §7für §9" + Util.getRemainingTime(System.currentTimeMillis() + time) + " §7gebannt.\nGrund: " + message);
        if (Bukkit.getPlayer(uuid) != null)
            Bukkit.getPlayer(uuid).kickPlayer("§8Dein Account wurde von HiyamaCity gesperrt.§5\n" +
                    " \n" +
                    ((message == "") ? "" : "Grund: " + message + "\n") +
                    "Ban-ID: " + Banning.getBanId(uuid) + "\n" +
                    "Tag des Bannes: " + formatter.format(System.currentTimeMillis()) + "\n" +
                    ((Banning.getBanEnd(uuid) == 0) ? "" : "Tag der Entbannung: " + formatter.format(Banning.getBanEnd(uuid)) + "\n") +
                    ((Banning.getBanEnd(uuid) == 0) ? "" : "Verbleibende Zeit: " + Util.getRemainingTime(System.currentTimeMillis() + time) + "\n") +
                    " \n" +
                    "§8Wir geben dir die Möglichkeit einen Entbannungsantrag in unserem Forum zu stellen.\n" +
                    "https://hiyamacity.de/forum/index.php?board/21-entbannungsanträge/");


        return false;
    }

}
