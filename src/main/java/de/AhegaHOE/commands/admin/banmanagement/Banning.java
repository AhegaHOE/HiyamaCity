package de.AhegaHOE.commands.admin.banmanagement;

import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Banning implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {


        /* TODO: Ban-System

        DATABASE STRUCTURE:

        » banned uuid (varchar)
        » ban-id (varchar)
        » ban created by uuid (varchar)
        » ban start (long)
        » ban end (long)
        » vorherige bans (varchar)
        » grund (varchar)



         /ban <Spieler> [Grund]
         » jeder ban bekommt eine ban-id
         » vorgegebene gründe mit tabcompleter
            » <gründe>
         » eigene gründe

         /checkban <ban-id>
         » gibt infos über den ban
            » spieler + uuid + hover-command (/checkplayer <spieler>)
            » start
            » ende
            » grund
            »
        */
        return false;
    }



}
