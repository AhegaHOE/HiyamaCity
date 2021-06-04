package de.AhegaHOE.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String cmdlabel, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("system.gamemode")) {
            return true;
        }
        if (args.length == 0) {

            p.sendMessage("§cFehler: Benutze /gamemode <Zahl> [Spieler]");
            return true;
        }

        if (!isInt(args[0])) {

            p.sendMessage("§cFehler: Der angegebene Spielmodus ist keine Zahl");
            return true;
        }

        GameMode gm = null;

        switch (Integer.valueOf(args[0]).intValue()) {

            case 0:
                gm = GameMode.SURVIVAL;
                break;

            case 1:
                gm = GameMode.CREATIVE;
                break;

            case 2:
                gm = GameMode.ADVENTURE;
                break;

            case 3:
                gm = GameMode.SPECTATOR;
                break;
        }

        if (Integer.valueOf(args[0]).intValue() > 3 || args[0].startsWith("-")) {
            p.sendMessage("§cFehler: Der angegebene Spielmodus ist größer als 3 oder negativ.");
            return true;
        }

        if (args.length == 1) {
            String str = gm.name().toLowerCase();
            String cap = String.valueOf(str.substring(0, 1).toUpperCase()) + str.substring(1);
            p.sendMessage("§8[§aSpielmodus§8]§7" + " §7Dein §aSpielmodus §7wurde zu §a%gamemode% §7geändert.".replace("%gamemode%", cap));
            p.setGameMode(gm);
            return true;
        }

        if (args.length == 2) {
            Player t = Bukkit.getPlayer(args[1]);

            if (t == null) {
                p.sendMessage("§cFehler: Der angegebene Spieler wurde nicht gefunden.");
                return true;
            }

            t.setGameMode(gm);
            String str = gm.name().toLowerCase();
            String cap = String.valueOf(str.substring(0, 1).toUpperCase()) + str.substring(1);

            t.sendMessage("§8[§aSpielmodus§8]§7"
                    + " §a%player% §7hat deinen §aSpielmodus §7zu §a%gamemode% §7geändert."
                    .replace("%player%", p.getDisplayName()).replace("%gamemode%", cap));

            p.sendMessage("§8[§aSpielmodus§8]§7"
                    + " §7Du hast den §aSpielmodus §7von §a%target% §7zu §a%gamemode% §7geändert."
                    .replace("%target%", t.getDisplayName()).replace("%gamemode%", cap));

            return true;
        }
        return false;
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException numberFormatException) {

            return false;
        }
    }
}
