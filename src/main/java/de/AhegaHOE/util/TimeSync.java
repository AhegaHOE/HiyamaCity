package de.AhegaHOE.util;

import de.AhegaHOE.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeSync {

	public static void setTime() {
		int secpp = 60;
		new BukkitRunnable() {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

			@Override
			public void run() {
				int h = Integer.parseInt(format.format(Calendar.getInstance().getTime()).substring(0, 2));
				int min = Integer.parseInt(format.format(Calendar.getInstance().getTime()).substring(3, 5));
				int sec = Integer.parseInt(format.format(Calendar.getInstance().getTime()).substring(6, 8));
				int time = h * 60 + min + (sec / 60);
				int multi = 0;
				if (h >= 6 && h <= 24) {
					multi = -6000;
				} else if (h <= 6 && h >= 0) {
					multi = 18000;
				}
				int ticks = time * (16 + 2 / 3) + multi;
				for (World w : Bukkit.getWorlds()) {
					w.setTime(ticks);
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 20 * secpp);
	}

}
