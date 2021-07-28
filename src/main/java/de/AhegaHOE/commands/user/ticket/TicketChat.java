package de.AhegaHOE.commands.user.ticket;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TicketChat implements CommandExecutor {

    public static List<Player> reportChatToggledOn = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }

        Player p = (Player) sender;

        if (args.length != 0) {
            p.sendMessage("§cFehler: Benutze /togglerc");
            return false;
        }

        if (!(Ticket.isInReport(p.getUniqueId()))) {
            p.sendMessage("§cFehler: Du bist in keinem Report.");
            return false;
        }

        if (!(hasReportChatToggled(p))) {
            setReportChatToggledOn(p);
        } else {
            setReportChatToggledOff(p);
        }


        return false;
    }

    public static boolean hasReportChatToggled(Player p) {
        return reportChatToggledOn.contains(p);
    }

    public static void setReportChatToggledOn(Player p) {
        reportChatToggledOn.add(p);
        p.sendMessage("§aDu schreibst nun im Report-Chat.");
    }

    public static void setReportChatToggledOff(Player p) {
        reportChatToggledOn.remove(p);
        p.sendMessage("§cDu schreibst nun nicht mehr im Report-Chat.");
    }
}
