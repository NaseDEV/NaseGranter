package ru.nase.nasegranter.grant;

import org.bukkit.entity.*;
import ru.nase.nasegranter.*;
import ru.nase.nasegranter.Main;

public class Storage
{
    public static int getAvailableGroupGrants(final Player p, final String group) {
        return Main.dataConfig.getInt("storage." + p.getName() + "." + group);
    }
    
    public static boolean containsStorage(final Player p, final String group) {
        return Main.dataConfig.getString("storage." + p.getName() + "." + group) != null;
    }
    
    public static void setAvailableGroupGrants(final Player p, final String group, final int amount) {
        Main.dataConfig.set("storage." + p.getName() + "." + group, (Object)amount);
        Main.getInstance().saveData();
    }
}
