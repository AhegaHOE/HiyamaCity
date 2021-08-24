package de.AhegaHOE.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemBuilder {


    public static Map<String, ItemStack> items = new HashMap<String, ItemStack>();

    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material material, short subID) {
        item = new ItemStack(material, 1, subID);
        meta = item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, (short) 0);
    }

    public ItemBuilder setName(String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemStack buildItem() {
        item.setItemMeta(meta);
        return item;
    }


    public static void generateItems() {
        Bukkit.getConsoleSender().sendMessage("§8[§bItemBuilder§8] " + ChatColor.GOLD + "Items werden generiert...");

        ItemStack Ausweis = new ItemBuilder(Material.PAPER).setName(ChatColor.RED + "Erste-Hilfe-Nachweis").setLore(
                ChatColor.GRAY + "hallo :)")
                .buildItem();
        items.put("Ausweis", Ausweis);

        ItemStack ATM_Sign = new ItemStack(Material.SIGN);
        items.put("ATM-Sign", ATM_Sign);

        // Allgemeiner GUI Placeholder
        ItemStack placeHolder = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
        ItemMeta placeHolderMeta = placeHolder.getItemMeta();
        placeHolderMeta.setDisplayName(" ");
        placeHolder.setItemMeta(placeHolderMeta);
        items.put("Placeholder", placeHolder);

        // Item für Report -> Allgemeine Frage
        ItemStack allgemeineFrage = new ItemStack(Material.PAPER);
        ItemMeta allgemeineFrageMeta = allgemeineFrage.getItemMeta();
        allgemeineFrageMeta.setDisplayName(ChatColor.GREEN + "Allgemeine Frage");
        allgemeineFrageMeta.setLore(Arrays.asList(ChatColor.GRAY + "Stelle eine allgemeine Frage."));
        allgemeineFrage.setItemMeta(allgemeineFrageMeta);
        items.put("Allgemeine Frage", allgemeineFrage);

        // Item für Report -> Bugreport
        ItemStack bugReport = new ItemStack(Material.BROWN_MUSHROOM);
        ItemMeta bugReportMeta = bugReport.getItemMeta();
        bugReportMeta.setDisplayName(ChatColor.RED + "Bugreport");
        bugReportMeta.setLore(Arrays.asList(ChatColor.GRAY + "Melde einen Fehler."));
        bugReport.setItemMeta(bugReportMeta);
        items.put("Bugreport", bugReport);

        // Item für Report -> Playerreport
        ItemStack playerReport = new ItemStack(Material.DEAD_BUSH);
        ItemMeta playerReportMeta = playerReport.getItemMeta();
        playerReportMeta.setDisplayName(ChatColor.DARK_RED + "Clientreport");
        playerReportMeta.setLore(Arrays.asList(ChatColor.GRAY + "Melde einen Spieler."));
        playerReport.setItemMeta(playerReportMeta);
        items.put("Playerreport", playerReport);


        Bukkit.getConsoleSender().sendMessage("§8[§bItemBuilder§8] " + ChatColor.GREEN + "Items erfolgreich generiert!");
    }
}
