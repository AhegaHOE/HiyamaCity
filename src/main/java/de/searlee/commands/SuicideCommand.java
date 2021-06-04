package de.searlee.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SuicideCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.setHealth(0);
            p.sendMessage(ChatColor.MAGIC + "###" + ChatColor.DARK_PURPLE + " rip §r" + ChatColor.MAGIC + "###");
    
        } else {
        	sender.sendMessage("§cDieser Befehl kann nur als Spieler ausgeführt werden!");
        }
        
        return false;
    }

}