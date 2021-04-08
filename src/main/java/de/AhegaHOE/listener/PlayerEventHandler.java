package de.AhegaHOE.listener;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
import org.bukkit.block.ShulkerBox;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.BeaconInventory;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;

import de.AhegaHOE.commands.admin.AdminDutyCommand;
import de.AhegaHOE.commands.admin.BuildModeCommand;
import de.AhegaHOE.commands.user.AfkCommand;
import de.AhegaHOE.util.languageHandler;
import net.md_5.bungee.api.ChatColor;

public class PlayerEventHandler implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!(AdminDutyCommand.adminduty.contains(p.getName()) || BuildModeCommand.buildmode.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!(AdminDutyCommand.adminduty.contains(p.getName()) || BuildModeCommand.buildmode.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (!(AdminDutyCommand.adminduty.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBoat(VehicleDamageEvent e) {
        Player p = (Player) e.getAttacker();
        if (!(AdminDutyCommand.adminduty.contains(p.getName()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInv(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof Chest || e.getInventory().getHolder() instanceof DoubleChest
                || e.getInventory().getHolder() instanceof ShulkerBox
                || e.getInventory().equals(e.getPlayer().getEnderChest())
                || e.getInventory().getHolder() instanceof Hopper || e.getInventory().getHolder() instanceof Dispenser
                || e.getInventory().getHolder() instanceof Dropper || e.getInventory().getHolder() instanceof Minecart
                || e.getInventory() instanceof HorseInventory || e.getInventory().getHolder() instanceof Donkey
                || e.getInventory() instanceof LlamaInventory || e.getInventory() instanceof CraftingInventory
                || e.getInventory() instanceof FurnaceInventory || e.getInventory() instanceof EnchantingInventory
                || e.getInventory() instanceof BeaconInventory || e.getInventory().getHolder() instanceof Villager
                || e.getInventory() instanceof BrewerInventory || e.getInventory() instanceof AnvilInventory) {
            Player p = (Player) e.getPlayer();
            if (!(AdminDutyCommand.adminduty.contains(p.getName()))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getYaw() != e.getTo().getYaw() || e.getFrom().getPitch() != e.getTo().getPitch()) {
            AFKCheck.playerLastMoveTime.put(e.getPlayer(), System.currentTimeMillis());
            if (AfkCommand.Afk.contains(e.getPlayer())) {
                AFKCheck.removeAFK(e.getPlayer());
                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (e.getPlayer().getLocation().distance(t.getLocation()) <= 8.0D) {
                        t.sendMessage(ChatColor.GOLD + e.getPlayer().getDisplayName()
                                + languageHandler.getMessage(languageHandler.getLocale(t), "Leave"));
                    }
                }
            }
        }
        if (AfkCommand.Afk.contains(e.getPlayer())) {
            AFKCheck.removeAFK(e.getPlayer());
            for (Player t : Bukkit.getOnlinePlayers()) {
                if (e.getPlayer().getLocation().distance(t.getLocation()) <= 8.0D) {
                    t.sendMessage(ChatColor.GOLD + e.getPlayer().getDisplayName()
                            + languageHandler.getMessage(languageHandler.getLocale(t), "Leave"));
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        AFKCheck.playerLastMoveTime.put(e.getPlayer(), System.currentTimeMillis());
        if (AfkCommand.Afk.contains(e.getPlayer())) {
            AFKCheck.removeAFK(e.getPlayer());
            for (Player t : Bukkit.getOnlinePlayers()) {
                if (e.getPlayer().getLocation().distance(t.getLocation()) <= 8.0D) {
                    t.sendMessage(ChatColor.GOLD + e.getPlayer().getDisplayName()
                            + languageHandler.getMessage(languageHandler.getLocale(t), "Leave"));
                }
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        AFKCheck.playerLastMoveTime.put(e.getPlayer(), System.currentTimeMillis());
        if (AfkCommand.Afk.contains(e.getPlayer())) {
            AFKCheck.removeAFK(e.getPlayer());

            for (Player t : Bukkit.getOnlinePlayers()) {
                if (e.getPlayer().getLocation().distance(t.getLocation()) <= 8.0D) {
                    t.sendMessage(ChatColor.GOLD + e.getPlayer().getDisplayName()
                            + languageHandler.getMessage(languageHandler.getLocale(t), "Leave"));
                }
            }
        }

        Player p = e.getPlayer();
        List<String> cmds = Arrays.asList("minecraft:me", "minecraft:msg", "minecraft:tell", "minecraft:trigger",
                "minecraft:w", "?", "about", "bukkit:?", "bukkit:about", "bukkit:help", "bukkit:pl", "bukkit:plugins",
                "bukkit:ver", "bukkit:version", "msg", "tell", "trigger", "help", "?", "about", "pl", "plugins", "ver",
                "version");
        if (!p.hasPermission("cmds")) {
            cmds.forEach(all -> {

                if (e.getMessage().toLowerCase().contains("/" + all.toLowerCase())) {
                    e.setCancelled(true);
                }

            });
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player || e.getDamager() instanceof Arrow) {
            Player p = (Player) e.getEntity();

            if (AfkCommand.Afk.contains(p)) {
                if (!AdminDutyCommand.adminduty.contains(e.getDamager().getName())) {
                    e.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                || e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            if (AfkCommand.Afk.contains(e.getPlayer())) {
                AFKCheck.removeAFK(e.getPlayer());
                for (Player t : Bukkit.getOnlinePlayers()) {
                    if (e.getPlayer().getLocation().distance(t.getLocation()) <= 8.0D) {
                        t.sendMessage(ChatColor.GOLD + p.getDisplayName()
                                + languageHandler.getMessage(languageHandler.getLocale(t), "Leave"));
                    }
                }
            }
        }

    }

    @EventHandler
    public void disableMobSpawns(EntitySpawnEvent e) {
        if (!e.getEntityType().equals(e.getEntityType().DROPPED_ITEM)) {
            e.setCancelled(true);
        } else {
            Item item = (Item) e.getEntity();
            ItemStack itemStack = item.getItemStack();
            if (itemStack.getType().equals(Material.BLACK_SHULKER_BOX)
                    || itemStack.getType().equals(Material.BLUE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.BROWN_SHULKER_BOX)
                    || itemStack.getType().equals(Material.CYAN_SHULKER_BOX)
                    || itemStack.getType().equals(Material.GRAY_SHULKER_BOX)
                    || itemStack.getType().equals(Material.GREEN_SHULKER_BOX)
                    || itemStack.getType().equals(Material.LIGHT_BLUE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.LIME_SHULKER_BOX)
                    || itemStack.getType().equals(Material.MAGENTA_SHULKER_BOX)
                    || itemStack.getType().equals(Material.ORANGE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.PINK_SHULKER_BOX)
                    || itemStack.getType().equals(Material.PURPLE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.RED_SHULKER_BOX)
                    || itemStack.getType().equals(Material.SILVER_SHULKER_BOX)
                    || itemStack.getType().equals(Material.WHITE_SHULKER_BOX)
                    || itemStack.getType().equals(Material.YELLOW_SHULKER_BOX)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage("");
    }

    @EventHandler
    public void onEnterDimension(PlayerPortalEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        AFKCheck.playerLastMoveTime.put(e.getPlayer(), System.currentTimeMillis());
        Player p = e.getPlayer();
        e.setJoinMessage("");

        if (!MySQLPointer.isUserExists(p.getUniqueId())) {
            p.sendMessage(ChatColor.GOLD + languageHandler.getMessage(languageHandler.getLocale(p), "WelcomeFirst"));
            String Playername = p.getName();
            UUID uuid = p.getUniqueId();
            MySQLPointer.registerPlayer(uuid, Playername);
        } else if (MySQLPointer.isUserExists(p.getUniqueId())) {
            p.sendMessage(ChatColor.GOLD + languageHandler.getMessage(languageHandler.getLocale(p), "WelcomeBack"));
        } else {
            UUID uuid = p.getUniqueId();
            String Playername = p.getName();
            MySQLPointer.registerPlayer(uuid, Playername);
        }

        if (!MySQLPointer.getUsername(e.getPlayer().getUniqueId()).equals(e.getPlayer().getName())) {
            MySQLPointer.updateUsername(e.getPlayer().getUniqueId(), e.getPlayer().getName());
        }

        FileConfiguration config = Main.getInstance().getConfig();
        UUID uuid = p.getUniqueId();
        if (config.get(uuid.toString()) == null) {
            languageHandler.setLocale(p, "de");
            config.set(uuid.toString(), "de");
            Main.getInstance().saveConfig();
            return;
        }
        String localeFileName = config.getString(uuid.toString());
        languageHandler.setLocale(p, localeFileName.toLowerCase());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        languageHandler.removePlayer(e.getPlayer());
        AFKCheck.removeAFK(e.getPlayer());
        if (AFKCheck.playerLastMoveTime.containsKey(e.getPlayer())) {
            AFKCheck.playerLastMoveTime.remove(e.getPlayer());
        }
        e.setQuitMessage("");
    }

}
