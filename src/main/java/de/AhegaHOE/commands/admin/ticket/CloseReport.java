package de.AhegaHOE.commands.admin.ticket;

import de.AhegaHOE.commands.user.ticket.Ticket;
import de.AhegaHOE.commands.user.ticket.TicketChat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class CloseReport implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }
        Player p = (Player) sender;

        if (!(p.hasPermission("report"))) {
            return false;
        }

        if (args.length != 0) {
            p.sendMessage("§cFehler: Benutze /cancelreport");
            return false;
        }

        if (!(Ticket.isInReport(p.getUniqueId()))) {
            p.sendMessage("§cFehler: Du bist in keinem Report.");
            return false;
        }

        if (Ticket.UUIDsInReport.containsKey(p.getUniqueId())) {
            p.sendMessage("§cFehler: Du hast den Report nicht gestartet.");
            return false;
        }

        closeReport(p.getUniqueId());


        return false;
    }

    public static void closeReport(UUID reportCloserUUID) {
        Player reportCloser = Bukkit.getPlayer(reportCloserUUID);

        UUID reporterUUID = getKey(Ticket.UUIDsInReport, reportCloser.getUniqueId());


        Player reporter = Bukkit.getPlayer(reporterUUID);
        if (reporter == null) {
            OfflinePlayer offlineReporter = Bukkit.getOfflinePlayer(reporterUUID);
            reportCloser.sendMessage("§cDu hast den Report von §7" + offlineReporter.getName() + " §7(§4OFFLINE§7) §cgeschlossen.");
        } else {
            reporter.sendMessage("§cDein Report wurde von " + reportCloser.getDisplayName() + " §cgeschlossen.");
            reportCloser.sendMessage("§cDu hast den Report von " + reporter.getDisplayName() + " §cgeschlossen.");
        }

        Ticket.UUIDsInReport.remove(reporterUUID, reportCloser.getUniqueId());
        if (reporter != null) {
            TicketChat.setReportChatToggledOff(reporter);
        }
        TicketChat.setReportChatToggledOff(reportCloser);
        Ticket.activeChats.remove(Ticket.chatIDs.get(reporterUUID));
        Ticket.chatIDs.remove(reporterUUID);
        Ticket.chatIDs.remove(reportCloser.getUniqueId());


    }

    private static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
