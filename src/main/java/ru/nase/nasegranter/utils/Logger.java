package ru.nase.nasegranter.utils;

import org.bukkit.*;
import ru.nase.nasegranter.*;
import ru.nase.nasegranter.Main;

public class Logger
{
    public static void info(final Object text) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[" + Main.getInstance().getName() + "] " + text);
    }
    
    public static void warning(final Object text) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[" + Main.getInstance().getName() + "] " + text);
    }
    
    public static void error(final Object text) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[" + Main.getInstance().getName() + "] " + text);
    }
}
