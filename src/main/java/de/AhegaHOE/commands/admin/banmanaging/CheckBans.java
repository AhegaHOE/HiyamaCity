package de.AhegaHOE.commands.admin.banmanaging;

import de.AhegaHOE.util.UUIDFetcher;
import de.AhegaHOE.util.banmanagement.Banning;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.UUID;

import static javafx.beans.binding.Bindings.concat;

public class CheckBans implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("system.checkbans")) {
            sender.sendMessage("§cFehler: Du hast dafür keine Rechte!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cFehler: Benutze /checkbans <Spieler>");
            return true;
        }

        UUID uuid = Bukkit.getPlayerUniqueId(args[0]);

        if(!Banning.hasBans(uuid)) {
            sender.sendMessage("§cFehler: Der Spieler " + args[0] + " wurde nicht gefunden.");
            return true;
        }

        ArrayList<String> info = Banning.getBanIDs(uuid);
        ArrayList<Object> banInfo;
        int bans = info.size();

        sender.sendMessage(" \n");
        sender.sendMessage(ChatColor.DARK_GRAY + "=============== §7Ban-Info §8===============\n");
        sender.sendMessage("§7Spieler: §9" + UUIDFetcher.getName(uuid) + "\n");

        for(int i = 0; i < bans; i++) {
            banInfo = Banning.getBanIDInfo(info.get(i));
            String active = (((boolean) banInfo.get(3)) ? "§aJa" : "§cNein");
            TextComponent middleComponent = new TextComponent();
            TextComponent middleFirst = new TextComponent("§7Ban-ID: §9");
            TextComponent middleSecound = new TextComponent(" §7| Aktiv?: " + active);

            middleComponent.setText("§9" + info.get(i));
            middleComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Führt §9/checkbanid " + info.get(i) + " §7aus.")));
            middleComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/checkbanid " + info.get(i)));
            sender.sendMessage(middleFirst, middleComponent, middleSecound);
        }
        TextComponent footerComponent = new TextComponent(ChatColor.DARK_GRAY + "========================================\n ");
        sender.sendMessage(footerComponent);



        return false;
    }
}
