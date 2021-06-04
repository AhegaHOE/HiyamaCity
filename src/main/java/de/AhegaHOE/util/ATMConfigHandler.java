package de.AhegaHOE.util;

import de.AhegaHOE.main.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ATMConfigHandler {


    public static List<Location> ATMLocations = new ArrayList<>();
    public static int atmcount = new FileBuilder(Main.getInstance().getDataFolder() + "/atm_locations", "atmlocs.yml").getInt("ATMCount");

    public static void createATMConfig() {

    }

    public static void addLocation(Location eyeLocation) {
        FileBuilder fb = new FileBuilder(Main.getInstance().getDataFolder() + "/atm_locations", "atmlocs.yml");
        if (fb.getString("ATMCount") == null) {
            fb.setValue("ATMCount", "0");
            fb.save();
        }

        fb.setValue(atmcount + ".world", eyeLocation.getWorld().getName());
        fb.setValue(atmcount + ".x", eyeLocation.getX());
        fb.setValue(atmcount + ".y", eyeLocation.getY());
        fb.setValue(atmcount + ".z", eyeLocation.getZ());
        atmcount++;
        fb.setValue("ATMCount", atmcount);
        fb.save();
    }
}
