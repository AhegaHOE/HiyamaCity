package de.AhegaHOE.util;

import de.AhegaHOE.main.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ATMConfigHandler {

    public static List<Location> ATMLocations = new ArrayList<>();

    private static File atmLocs = new File(Main.getInstance().getDataFolder(), "ATMLocations.yml");

    public static void createATMConfig() {

    }

    public static void addLocation(Location eyeLocation) {

    }
}
