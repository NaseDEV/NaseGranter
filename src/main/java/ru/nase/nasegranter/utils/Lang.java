package ru.nase.nasegranter.utils;

import java.util.*;
import ru.nase.nasegranter.*;
import ru.nase.nasegranter.Main;

import java.io.*;

public class Lang
{
    private static PropertyResourceBundle messages;
    
    public static void init() {
        try {
            Main.getInstance().saveResource("messages.properties", false);
            final File file = new File(Main.getInstance().getDataFolder(), "messages.properties");
            final Reader reader = new InputStreamReader(new FileInputStream(file));
            Lang.messages = new PropertyResourceBundle(reader);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getString(final String key) {
        if (!Lang.messages.containsKey(key)) {
            return key;
        }
        return Lang.messages.getString(key);
    }
}
