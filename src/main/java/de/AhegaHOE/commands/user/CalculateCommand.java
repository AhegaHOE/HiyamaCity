package de.AhegaHOE.commands.user;

import de.AhegaHOE.util.math.Expression;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Locale;


public class CalculateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(args.length == 0) {
            sender.sendMessage("§cFehler: Benutze /calc <Mathematischer Ausdruck>");
            return true;
        }
        String input = String.join("", args).toLowerCase();
        Expression expr = new Expression(input);
        try {
            expr.evaluate();
        } catch (Expression.ExpressionException e) {
            sender.sendMessage("§cFehler: Deine Rechnung konnte nicht evaluiert werden » " + e.getMessage());
            return true;
        }
        String message = expr.parse();
        sender.sendMessage("§7Rechnung: §9" + input.toUpperCase());
        sender.sendMessage("§7Ergebnis: §9" + message);
        return true;
    }
}