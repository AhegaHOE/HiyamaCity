package de.AhegaHOE.commands.user;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.xml.soap.Text;
import java.util.UUID;

public class UUIDCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("§cFehler: Das kannst du nur als Spieler");
            return true;
        }

        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();
        TextComponent clickable = new TextComponent(ChatColor.BLUE + uuid.toString());
        clickable.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid.toString()));
        clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Klicke hier um deine UUID im Eingabefenster zu kopieren.").color(ChatColor.GRAY).create()));
        p.sendMessage(new TextComponent("§7Deine §9UUID §7lautet:"), clickable);

        return false;
    }
}
