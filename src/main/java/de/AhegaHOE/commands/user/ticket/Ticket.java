package de.AhegaHOE.commands.user.ticket;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import de.AhegaHOE.util.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Ticket implements CommandExecutor, Listener {

    public static Map<UUID, UUID> UUIDsInReport = new HashMap<>();
    public static Map<UUID, String> waitingUUIDs = new HashMap<>();
    public static Map<UUID, String> reportTokens = new HashMap<>();
    public static ArrayList<UUID> UUIDsInWaitingQueue = new ArrayList<>(waitingUUIDs.keySet());
    public static ArrayList<String> reportTokenList = new ArrayList<>(waitingUUIDs.values());
    public static Map<UUID, String> chatIDs = new HashMap<>();
    public static Map<String, UUID[]> activeChats = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return false;
        }


        Player p = (Player) sender;


        if (args.length != 0) {
            p.sendMessage("§cFehler: Benutze /help");
            return false;
        }

        Inventory inv = Bukkit.createInventory(null, 9 * 3, ChatColor.DARK_PURPLE + "Ticket-System");

        ItemStack placeHolder = ItemBuilder.items.get("Placeholder");
        ItemStack allgemeineFrage = ItemBuilder.items.get("Allgemeine Frage");
        ItemStack bugreport = ItemBuilder.items.get("Bugreport");
        ItemStack playerreport = ItemBuilder.items.get("Playerreport");


        inv.setItem(0, placeHolder);
        inv.setItem(1, placeHolder);
        inv.setItem(2, placeHolder);
        inv.setItem(3, placeHolder);
        inv.setItem(4, placeHolder);
        inv.setItem(5, placeHolder);
        inv.setItem(6, placeHolder);
        inv.setItem(7, placeHolder);
        inv.setItem(8, placeHolder);

        inv.setItem(9, placeHolder);
        inv.setItem(10, placeHolder);
        inv.setItem(11, allgemeineFrage);
        inv.setItem(12, placeHolder);
        inv.setItem(13, bugreport);
        inv.setItem(14, placeHolder);
        inv.setItem(15, playerreport);
        inv.setItem(16, placeHolder);
        inv.setItem(17, placeHolder);

        inv.setItem(18, placeHolder);
        inv.setItem(19, placeHolder);
        inv.setItem(20, placeHolder);
        inv.setItem(21, placeHolder);
        inv.setItem(22, placeHolder);
        inv.setItem(23, placeHolder);
        inv.setItem(24, placeHolder);
        inv.setItem(25, placeHolder);
        inv.setItem(26, placeHolder);


        p.openInventory(inv);


        return false;
    }

    @EventHandler
    public void handleGUIClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();

        if (e.getInventory().getTitle().equals(ChatColor.DARK_PURPLE + "Ticket-System")) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()) {

                case PAPER:

                    joinReportQueue(p, "Frage");

                    break;

                case BROWN_MUSHROOM:

                    joinReportQueue(p, "Bug");

                    break;

                case DEAD_BUSH:

                    joinReportQueue(p, "Report");

                    break;

                default:
                    break;
            }
        }


    }

    public static void joinReportQueue(Player p, String reason) {

        if (waitingUUIDs.containsKey(p.getUniqueId())) {
            p.sendMessage("§cFehler: Du bist bereits in einer Warteschlange!\nBenutze /cancelreport um diese zu verlassen.");
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMEN_DEATH, 1, 1);
            p.closeInventory();
            return;
        }

        waitingUUIDs.put(p.getUniqueId(), reason);
        reportTokens.put(p.getUniqueId(), generateKey());
        UUIDsInWaitingQueue.add(p.getUniqueId());
        reportTokenList.add(reportTokens.get(p.getUniqueId()));

        for (Player all : AdminDutyCommand.adminduty) {

            TextComponent textComponent = new TextComponent("§7[§a" + reason + "-Queue§7] " + p.getDisplayName() + " §7hat die Warteschlange §abetreten§7. ");
            TextComponent clickable = new TextComponent("§9KLICKE HIER");
            TextComponent textComponent1 = new TextComponent(" §7um den Report §aanzunehmen§7.");
            clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/acceptreport " + reportTokens.get(p.getUniqueId())));
            clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Führt aus: §9/acceptreport " + reportTokens.get(p.getUniqueId()))));

            all.sendMessage(textComponent, clickable, textComponent1);
        }


        switch (reason) {

            case "Frage":

                p.sendMessage("§aDu hast die Warteschlange für allgemeine Fragen betreten.");


                break;

            case "Bug":

                p.sendMessage("§aDu hast die Warteschlange für Bugs betreten.");


                break;

            case "Report":

                p.sendMessage("§aDu hast die Warteschlange für Clientreports betreten.");


                break;

            default:
                break;
        }


        p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 1);
        p.closeInventory();


    }

    public static void leaveReportQueue(Player p) {

        if (!Ticket.waitingUUIDs.containsKey(p.getUniqueId())) {
            p.sendMessage("§cFehler: Du bist in keiner Warteschlange");
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            return;
        }

        String reason = Ticket.waitingUUIDs.get(p.getUniqueId());
        waitingUUIDs.remove(p.getUniqueId());
        reportTokens.remove(p.getUniqueId());
        UUIDsInWaitingQueue.remove(p.getUniqueId());
        reportTokenList.remove(reportTokens.get(p.getUniqueId()));

        for (Player all : AdminDutyCommand.adminduty) {
            all.sendMessage("§7[§a" + reason + "-Queue§7] " + p.getDisplayName() + " §chat die Warteschlange verlassen.");
        }

        switch (reason) {

            case "Frage":

                p.sendMessage("§cDu hast die Warteschlange für allgemeine Fragen verlassen.");

                break;

            case "Bug":

                p.sendMessage("§cDu hast die Warteschlange für Bugs verlassen.");

                break;

            case "Report":

                p.sendMessage("§cDu hast die Warteschlange für Clientreports verlassen.");

                break;

            default:
                break;

        }


    }

    public static void acceptReport(Player reportAcceptor, Player reporter, String reportToken) {

        if (reporter == null || !(reportToken.equals(reportTokens.get(reporter.getUniqueId())))) {
            reportAcceptor.sendMessage("§cFehler: Ungültiger Token!");
            return;
        }

        if (isInReport(reportAcceptor.getUniqueId())) {
            reportAcceptor.sendMessage("§cFehler: Du bist noch in einem Report!\nBenutze /closereport um den Report zu schließen.");
            reportAcceptor.playSound(reportAcceptor.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            return;
        }

        if (reportAcceptor.equals(reporter)) {
            reportAcceptor.sendMessage("§cFehler: Du kannst deinen eigenen Report nicht annehmen.");
            reportAcceptor.playSound(reportAcceptor.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
            return;
        }

        reportAcceptor.sendMessage("§aDu hast den Report von " + reporter.getDisplayName() + " §aangenommen!");
        reporter.sendMessage("§a" + reportAcceptor.getDisplayName() + " §ahat deinen Report angenommen!");


        // Add in die Chats
        UUIDsInReport.put(reporter.getUniqueId(), reportAcceptor.getUniqueId());
        TicketChat.setReportChatToggledOn(reporter);
        TicketChat.setReportChatToggledOn(reportAcceptor);

        String chatID = generateKey();
        chatIDs.put(reporter.getUniqueId(), chatID);
        chatIDs.put(reportAcceptor.getUniqueId(), chatID);
        activeChats.put(chatIDs.get(reporter.getUniqueId()), new UUID[]{reporter.getUniqueId(), reportAcceptor.getUniqueId()});


        // Remove von der Warteschlange
        reportTokenList.remove(reportTokens.get(reporter.getUniqueId()));
        UUIDsInWaitingQueue.remove(reporter.getUniqueId());
        waitingUUIDs.remove(reporter.getUniqueId());
        reportTokens.remove(reporter.getUniqueId());

    }


    public static List<UUID> getUUIDsInQueue() {
        return UUIDsInWaitingQueue;
    }

    public static List<String> getReportTokens() {
        return reportTokenList;
    }

    public static String getReportToken(UUID p) {
        return reportTokens.get(p);
    }

    public static String generateKey() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static boolean isInReport(UUID p) {
        if (UUIDsInReport.containsKey(p)) {
            return true;
        } else if (UUIDsInReport.containsValue(p)) {
            return true;
        }
        return false;
    }

}
