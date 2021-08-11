package de.AhegaHOE.commands.admin;

import java.util.ArrayList;
import java.util.UUID;

import de.AhegaHOE.commands.user.ticket.Ticket;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminDutyCommand implements CommandExecutor {

    public static ArrayList<Player> adminduty = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }

        Player p = (Player) sender;
        if (args.length != 0) {
            p.sendMessage("§cFehler: Benutze /aduty");
            return false;
        }

        if (!p.hasPermission("adminduty")) {
            return false;
        }

        if (!isInAdminduty(p)) {
            setAdminduty(p);
        } else {
            removeAdminduty(p);
        }

        return false;
    }

    public static void setAdminduty(Player p) {
        adminduty.add(p);
        p.sendMessage("§8[§cAdmindienst§8]§7" + " §7Du hast den §cAdmindienst§7 betreten.");

        if (Ticket.UUIDsInWaitingQueue.iterator().hasNext()) {
            p.sendMessage("§7Es sind noch §9Reports §7offen.");
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
            for (UUID all : Ticket.UUIDsInWaitingQueue) {
                Player allWaiting = Bukkit.getPlayer(all);
                TextComponent waitingPlayer = new TextComponent(allWaiting.getDisplayName() + " §7-> " + Ticket.waitingUUIDs.get(allWaiting.getUniqueId()) + " | ");
                TextComponent clickable = new TextComponent("§9KLICKE HIER");
                clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptreport " + Ticket.reportTokens.get(allWaiting.getUniqueId())));
                clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Führt aus: §9/acceptreport " + Ticket.reportTokens.get(allWaiting.getUniqueId()))));

                p.sendMessage(waitingPlayer, clickable);
            }
        }

    }

    public static void removeAdminduty(Player p) {
        adminduty.remove(p);
        p.sendMessage("§8[§cAdmindienst§8]§7" + " §7Du hast den §cAdmindienst§7 verlassen.");
    }

    public static boolean isInAdminduty(Player p) {
        return adminduty.contains(p);
    }

}

