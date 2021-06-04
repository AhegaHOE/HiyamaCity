package de.AhegaHOE.commands.user;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ForumCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
            return true;
        }

        Player p = (Player) sender;

        if (args.length != 0) {
            p.sendMessage("§cFehler: Benutze /forum");
            return true;
        }

        TextComponent textComponent = new TextComponent("§7Um ins Forum zu gelangen: ");
        TextComponent clickable = new TextComponent("§9KLICKE HIER");
        clickable.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://hiyamacity.de/forum/"));
        clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Bringt dich auf: §9https://hiyamacity.de/forum/")));

        p.sendMessage(textComponent, clickable);

        return false;
    }
}
