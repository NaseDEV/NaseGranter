package ru.nase.nasegranter.grant;

import ru.nase.nasegranter.*;
import org.bukkit.entity.*;
import ru.nase.nasegranter.Main;

public class Group
{
    public static int getGroupPriority(final String group) {
        return Main.config.getInt("priorities." + group);
    }
    
    public static boolean hasGroup(final String group) {
        return Main.config.getConfigurationSection("priorities").contains(group);
    }
    
    public static String getGroupName(final String group) {
        return Main.config.getString("groups_names." + group);
    }
    
    public static boolean canGiveGroup(final Player p, final String group) {
        final String playerGroup = Main.getPermission().getPlayerGroups(p)[0];
        return Main.config.getString("limits." + playerGroup + "." + group) != null;
    }
    
    public static int getGiveGroups(final Player p, final String group) {
        final String playerGroup = Main.getPermission().getPlayerGroups(p)[0];
        return Main.config.getInt("limits." + playerGroup + "." + group);
    }
}
