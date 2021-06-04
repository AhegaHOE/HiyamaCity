package de.AhegaHOE.util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class FileBuilder {

    private File f;
    private YamlConfiguration c;

    public FileBuilder(String filePath, String fileName) {
        this.f = new File(filePath, fileName);
        this.c = YamlConfiguration.loadConfiguration(this.f);
    }

    public FileBuilder setValue(String valuePath, Object obj) {
        c.set(valuePath, obj);
        return this;
    }

    public int getInt(String valuePath) {
        return c.getInt(valuePath);
    }

    public String getString(String valuePath) {
        return c.getString(valuePath);
    }

    public boolean getBoolean(String valuePath) {
        return c.getBoolean(valuePath);
    }

    public List<String> getStringList(String valuePath) {
        return c.getStringList(valuePath);
    }

    public Set<String> getKeys(boolean deep) {
        return c.getKeys(deep);
    }

    public ConfigurationSection getConfiguartionSection(String section) {
        return c.getConfigurationSection(section);
    }

    public long getLong(String valuePath) {
        return c.getLong(valuePath);
    }

    public double getDouble(String valuePath) {
        return c.getDouble(valuePath);
    }

    public FileBuilder save() {
        try {
            this.c.save(this.f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

}
