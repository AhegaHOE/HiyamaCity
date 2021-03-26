package de.searlee.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;

public class SuicideCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.setHealth(0);
            p.sendMessage(ChatColor.MAGIC + "rip");
    
        } else {
        	sender.sendMessage(languageHandler.getMessage("en", "PlayerOnly"));
        }
        
        return false;
    }

}