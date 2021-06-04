package de.AhegaHOE.commands.user;

import java.text.DateFormat;
import java.util.Locale;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TimeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Locale.forLanguageTag("de"));
                long time = System.currentTimeMillis();

                p.sendMessage(ChatColor.BLUE + formatter.format(time));
            }
            return false;
        }
        return false;

    }


}
