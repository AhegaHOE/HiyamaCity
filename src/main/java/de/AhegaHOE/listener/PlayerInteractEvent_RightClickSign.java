package de.AhegaHOE.listener;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.util.DecimalSeperator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.DecimalFormat;

public class PlayerInteractEvent_RightClickSign implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getClickedBlock().getType() == Material.SIGN ||
                e.getClickedBlock().getType() == Material.SIGN_POST ||
                e.getClickedBlock().getType() == Material.WALL_SIGN) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                String signLine1 = sign.getLine(0);
                String signLine2 = sign.getLine(1);
                String signLine3 = sign.getLine(2);
                String signLine4 = sign.getLine(3);

                if ((signLine1.equalsIgnoreCase("===============") && signLine2.equalsIgnoreCase("Staatsbank") && signLine3.equalsIgnoreCase("Bankmenü")) && signLine4.equalsIgnoreCase("===============")) {
                    DecimalFormat decimalFormat = DecimalSeperator.prepareFormat(',', '.', false, (byte) 0);

                    int money = MySQLPointer.getMoney(p.getUniqueId());
                    int bank = MySQLPointer.getBank(p.getUniqueId());

                    p.sendMessage("");
                    p.sendMessage(ChatColor.DARK_GRAY + "========== §bKontostand§8 ==========");
                    p.sendMessage(ChatColor.GRAY + "§7Geld: §9%money%§7$, Bank: §9%bank%§7$".replace("%money%", "" + decimalFormat.format(money)).replace("%bank%", "" + decimalFormat.format(bank)));
                    p.sendMessage(ChatColor.DARK_GRAY + "===============================");
                    p.sendMessage("");
                } else {
                    return;
                }

                sign.update();
            }
        }
    }
}
