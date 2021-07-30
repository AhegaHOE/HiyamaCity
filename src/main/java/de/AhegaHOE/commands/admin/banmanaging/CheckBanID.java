package de.AhegaHOE.commands.admin.banmanaging;

import de.AhegaHOE.util.UUIDFetcher;
import de.AhegaHOE.util.banmanagement.Banning;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class CheckBanID implements CommandExecutor {
    DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.forLanguageTag("de"));


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("system.checkbanid")) {
            sender.sendMessage("§cFehler: Du hast dafür keine Rechte!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cFehler: Benutze /checkbanid <Ban-ID>");
            return true;
        }

        String banId = args[0];
        if (!Banning.isBanIdAlreadyUsed(banId)) {
            sender.sendMessage("§cFehler: Diese Ban-ID existiert nicht.");
            return true;
        }
        ArrayList<Object> infos = Banning.getBanIDInfo(banId);


        String active = (((boolean) infos.get(3)) ? "§aJa" : "§cNein");
        String reasonString = ((infos.get(4) == null) ? "§cN/A" : "§9" + infos.get(4));

        TextComponent banid = new TextComponent(" \n" + ChatColor.DARK_GRAY + "========================================\n" + "§7Ban-ID: §9" + infos.get(0) + "\n");

        TextComponent uuid = new TextComponent("§7Spieler: §9" + UUIDFetcher.getName(UUID.fromString((String) infos.get(1))) + "\n");
        uuid.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Bringt dich auf: §9https://de.namemc.com/profile/" + infos.get(1))));
        uuid.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://de.namemc.com/profile/" + infos.get(1)));

        TextComponent buuid = ((infos.get(2) == null) ? new TextComponent("§7Züchtiger: §cN/A \n") : new TextComponent("§7Züchtiger: §9" + UUIDFetcher.getName(UUID.fromString((String) infos.get(2))) + "\n"));


        TextComponent isactive = new TextComponent("§7Aktiver Ban?: " + active + "\n");
        TextComponent reason = new TextComponent("§7Grund: §9" + reasonString + "\n");
        TextComponent start = new TextComponent("§7Tag des Bannes: §9" + formatter.format(infos.get(5)) + "\n");
        TextComponent end = (((Long) infos.get(6) == 0) ? new TextComponent("§7Tag der Entbannung: §cN/A" + "\n" + ChatColor.DARK_GRAY + "========================================" + " \n") : new TextComponent("§7Tag der Entbannung: §9" + formatter.format(infos.get(6)) + "\n" + ChatColor.DARK_GRAY + "========================================" + " \n"));

        sender.sendMessage(banid, uuid, buuid, isactive, reason, start, end);

        return false;
    }
}
