package de.AhegaHOE.util;

import de.AhegaHOE.main.Main;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Broadcaster {

    private final long MESSAGE_DELAY = 5 * 20 * 60;
    private final List<Object> MESSAGES = Arrays.asList("\n §7[§e§l!§r§7] §cInformation: §7Schau doch auf unserem §9Forum §7vorbei\n§7» §9https://hiyamacity.de/forum/\n ",
            "\n §7[§e§l!§r§7] §cInformation: §7HiyamaCity auf §cYouTube!\n§7» §9https://hiyamacity.de/YT/\n ",
            "\n §7[§e§l!§r§7] §cInformation: §7Unser §9Teamspeak3-Server\n§7» §9IP: HiyamaCity\n ");

    public void startBroadcast() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> Bukkit.broadcastMessage(pickMessage()), 0, MESSAGE_DELAY);
    }

    public Broadcaster() {

    }

    private String pickMessage() {

        int random = new Random().nextInt(MESSAGES.size());
        return MESSAGES.get(random).toString();
    }

}
