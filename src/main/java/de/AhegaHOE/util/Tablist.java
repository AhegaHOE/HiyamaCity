package de.AhegaHOE.util;

import de.AhegaHOE.commands.admin.Vanish;
import de.AhegaHOE.main.Main;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class Tablist {


    public static void setTab(Player p) {

        int playerCount = Bukkit.getOnlinePlayers().size() - Vanish.vanishedPlayers.size();

        String header = languageHandler.getMessage(languageHandler.getLocale(p), "TablistHeader");
        String footer = languageHandler.getMessage(languageHandler.getLocale(p), "TablistPlayersOnline").replace("%current%", "" + playerCount).replace("%max%", "" + Bukkit.getMaxPlayers());

        IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + header + "\"}");
        IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + footer + "\"}");

        PacketPlayOutPlayerListHeaderFooter tabPacket = new PacketPlayOutPlayerListHeaderFooter();

        try {

            Field headerField = tabPacket.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(tabPacket, tabHeader);

            Field footerField = tabPacket.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(tabPacket, tabFooter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(tabPacket);
        }

    }

    public static void updateTab() {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            for (Player all : Bukkit.getOnlinePlayers()) {
                Tablist.setTab(all);
            }
        }, 1);
    }

}
