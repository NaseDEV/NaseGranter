package ru.nase.nasegranter.utils;

import java.util.*;

public class CooldownUtil
{
    private static HashMap<String, Long> tags;
    
    static {
        CooldownUtil.tags = new HashMap<String, Long>();
    }
    
    public static void set(final String player, final long time) {
        CooldownUtil.tags.put(player, System.currentTimeMillis() + time);
    }
    
    public static boolean has(final String player) {
        return CooldownUtil.tags.get(player) != null && CooldownUtil.tags.get(player) > System.currentTimeMillis();
    }
    
    public static int left(final String player) {
        return (int)(CooldownUtil.tags.get(player) - System.currentTimeMillis()) / 1000;
    }
    
    public static void delete(final String player) {
        CooldownUtil.tags.remove(player);
    }
}
