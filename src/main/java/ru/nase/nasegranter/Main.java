package ru.nase.nasegranter;

import org.bukkit.plugin.java.*;
import net.milkbowl.vault.*;
import net.milkbowl.vault.permission.*;
import org.bukkit.plugin.*;
import ru.nase.nasegranter.commands.*;
import org.bukkit.command.*;
import org.bukkit.configuration.file.*;
import java.io.*;
import org.bukkit.*;
import ru.nase.nasegranter.utils.*;
import ru.nase.nasegranter.commands.GrantCommand;
import ru.nase.nasegranter.utils.Config;
import ru.nase.nasegranter.utils.Lang;
import ru.nase.nasegranter.utils.Logger;

public class Main extends JavaPlugin
{
    public static Main plugin;
    private static Vault vault;
    public static FileConfiguration config;
    public static FileConfiguration dataConfig;
    private static File dataFile;
    private static Permission permission;
    
    public static Main getInstance() {
        return Main.plugin;
    }
    
    public static Vault getVault() {
        return Main.vault;
    }
    
    public static Permission getPermission() {
        return Main.permission;
    }
    
    public void saveData() {
        try {
            Main.dataConfig.save(Main.dataFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean setupPermissions() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        final RegisteredServiceProvider<Permission> rsp = (RegisteredServiceProvider<Permission>)this.getServer().getServicesManager().getRegistration((Class)Permission.class);
        if (rsp == null) {
            return false;
        }
        Main.permission = (Permission)rsp.getProvider();
        return Main.permission != null;
    }
    
    private void registerCommands() {
        this.getCommand("grant").setExecutor((CommandExecutor)new GrantCommand());
    }
    
    public void onEnable() {
        final long time = System.currentTimeMillis();
        (Main.plugin = this).setupPermissions();
        Main.config = Config.get("config.yml");
        Lang.init();
        try {
            Main.dataFile = new File(String.valueOf(this.getDataFolder().getAbsolutePath()) + "/storage.yml");
            if (!Main.dataFile.exists()) {
                Main.dataFile.createNewFile();
            }
            Main.dataConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(Main.dataFile);
            if (Main.dataConfig.getConfigurationSection("storage") == null) {
                Main.dataConfig.set("storage.enot228.Vip", (Object)3);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.registerCommands();
        Logger.info(ChatColor.WHITE + "\u041f\u043b\u0430\u0433\u0438\u043d \u0432\u043a\u043b\u044e\u0447\u0435\u043d. (" + ChatColor.GREEN + (System.currentTimeMillis() - time) + " ms" + ChatColor.WHITE + ")");
    }
    
    public void onDisable() {
        this.saveData();
        Logger.info("\u041f\u043b\u0430\u0433\u0438\u043d \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d.");
    }
}
