package de.AhegaHOE.util;

import de.AhegaHOE.MySQL.MySQL;
import de.AhegaHOE.MySQL.MySQLPointer;
import de.AhegaHOE.listener.AFKCheck;
import de.AhegaHOE.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlaytimeTracker implements Listener {

    public static void startPlaytimeTracking() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!AFKCheck.isAFK(p.getPlayer())) {

                    int hours = MySQLPointer.getPlayedHours(p.getUniqueId());
                    int minutes = MySQLPointer.getPlayedMinutes(p.getUniqueId());


                    minutes++;
                    MySQLPointer.updatePlayedMinutes(p.getUniqueId(), minutes);

                    if (minutes >= 60) {
                        minutes = 0;
                        MySQLPointer.updatePlayedMinutes(p.getUniqueId(), 0);
                        hours++;
                        MySQLPointer.updatePlayedHours(p.getUniqueId(), hours);
                    }
                }
            }

        }, 20 * 60, 20 * 60);
    }
}
