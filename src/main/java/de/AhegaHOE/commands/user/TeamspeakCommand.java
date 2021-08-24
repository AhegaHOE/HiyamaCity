package de.AhegaHOE.commands.user;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.main.Main;
import de.AhegaHOE.util.ts.TSMySQLPointer;
import de.AhegaHOE.util.ts.TeamspeakEventHandler;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamspeakCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cFehler: Du kannst das nur als Spieler!");
            return true;
        }

        Player p = (Player) sender;
        UUID uuid = p.getUniqueId();

        if (args.length < 1 || args.length > 2) {
            p.sendMessage("§cFehler: Benutze /ts reset/info/verify [Eindeutige-ID]");
            return true;
        }

        if (!(args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("verify"))) {
            p.sendMessage("§cFehler: Benutze /ts reset/info/verify [Eindeutige-ID]");
            return true;
        }

        switch (args[0]) {

            case "reset": {
                if (!TSMySQLPointer.isUserExist(uuid)) {
                    p.sendMessage("§cFehler: Dein Minecraft-Account ist nicht sychronisiert.");
                    return true;
                }
                Client c = TSMySQLPointer.getClientByUUID(uuid);
                TSMySQLPointer.deleteTeamspeakSync(uuid);
                TeamspeakEventHandler.updateRank(c);
                Main.ts3Api.sendPrivateMessage(c.getId(), "[COLOR=#55ff55]Deine Teamspeak-Sychronisation wurde erfolgreich aufgehoben![/COLOR]");
                p.sendMessage("§aDeine Teamspeak-Sychronisation wurde erfolgreich aufgehoben!");

            }
            break;

            case "info": {

            }
            break;

            case "verify": {
                if (args.length != 2) {
                    p.sendMessage("§cFehler: Benutze /ts verify <Eindeutige-ID>");
                    return true;
                }

                if (TSMySQLPointer.isUserExist(uuid)) {
                    if (TSMySQLPointer.isConfirmed(uuid)) {
                        p.sendMessage("§cFehler: Du hast bereits einen sychronisierten Teamspeak-Account.");
                        return true;
                    } else if (!TSMySQLPointer.isConfirmed(uuid)) {
                        p.sendMessage("§cFehler: Du musst deinen Account im Teamspeak bestätigen.");
                        return true;
                    }

                }

                if (Main.ts3Api.getDatabaseClientByUId(args[1]) == null) {
                    p.sendMessage("§cFehler: Die angegebene Eindeutige-ID war bis jetzt nicht auf dem Server, wir bitten dich vorher, mit der Identität von der deine Eindeutige-ID stammt, den Teamspeak-Server zu betreten.");
                    return true;
                }

                if (!Main.ts3Api.isClientOnline(args[1])) {
                    p.sendMessage("§cFehler: Die angegebene Eindeutige-ID ist nicht online.");
                    return true;
                }
                p.sendMessage("§aDein Teamspeak-Account wurde erfolgreich verbunden. Du hast einen Nachricht von unserem Bot bekommen.\nEr gibt dir weitere Infos wie du fortfahren musst.");
                TSMySQLPointer.registerTeamspeakSynchronization(uuid, args[1]);
                TSMySQLPointer.sendVerificationMessageTeamspeak(uuid, args[1]);
            }
            break;

            default:
                break;
        }

        return false;
    }
}
