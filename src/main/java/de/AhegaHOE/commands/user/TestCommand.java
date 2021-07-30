package de.AhegaHOE.commands.user;

import de.AhegaHOE.util.banmanagement.Banning;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.DateFormat;
import java.util.Locale;

public class TestCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.forLanguageTag("de"));
        long currentTimeMillis = System.currentTimeMillis();
        //commandSender.sendMessage(currentTimeMillis + "\n" + formatter.format(currentTimeMillis));
        //commandSender.sendMessage(Banning.generateBanId());
        commandSender.sendMessage( "" + Banning.hasActiveBans(Bukkit.getPlayerUniqueId("Azusagawachan")));
        commandSender.sendMessage("" + Banning.getBanIDs(Bukkit.getPlayerUniqueId("Azusagawachan")));
        return false;
    }

}
