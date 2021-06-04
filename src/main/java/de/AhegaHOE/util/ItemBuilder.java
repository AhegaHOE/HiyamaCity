package de.AhegaHOE.util;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Sign;

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

        Bukkit.getConsoleSender().sendMessage("§8[§bItemBuilder§8] " + ChatColor.GREEN + "Items erfolgreich generiert!");
    }
}
