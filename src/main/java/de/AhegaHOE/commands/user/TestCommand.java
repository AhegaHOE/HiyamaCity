package de.AhegaHOE.commands.user;

import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.ts.TeamspeakEventHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.util.Locale;

public class TestCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.forLanguageTag("de"));
        Player p = (Player) sender;
        return false;
    }

}
