package ru.nase.nasegranter.utils;

import ru.nase.nasegranter.*;
import org.bukkit.configuration.file.*;
import ru.nase.nasegranter.Main;

import java.io.*;

public class Config
{
    public static FileConfiguration get(final String name) {
        final File f = new File(Main.getInstance().getDataFolder(), name);
        if (Main.getInstance().getResource(name) == null) {
            return save((FileConfiguration)YamlConfiguration.loadConfiguration(f), name);
        }
        if (!f.exists()) {
            Main.getInstance().saveResource(name, false);
        }
        return (FileConfiguration)YamlConfiguration.loadConfiguration(f);
    }
    
    public static FileConfiguration save(final FileConfiguration config, final String name) {
        try {
            config.save(new File(Main.getInstance().getDataFolder(), name));
        }
        catch (IOException e) {
            Logger.error(e.getMessage());
        }
        return config;
    }
}
