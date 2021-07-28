package de.AhegaHOE.commands.user.ticket;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CancelTicket implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player p = (Player) sender;

        Ticket.leaveReportQueue(p);

        return false;
    }
}
