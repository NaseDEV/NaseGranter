package ru.nase.nasegranter.utils;

import org.bukkit.entity.*;
import com.comphenix.protocol.wrappers.*;

public class TitleUtil
{
    public static void sendTitle(final Player player, final String title, final String subtitle, final int fadeIn, final int stay, final int fadeOut) {
        titlePacket(EnumWrappers.TitleAction.TITLE, title, fadeIn, stay, fadeOut).sendPacket(player);
        titlePacket(EnumWrappers.TitleAction.SUBTITLE, subtitle, fadeIn, stay, fadeOut).sendPacket(player);
    }
    
    public static void clearTitle(final Player player) {
        titlePacket(EnumWrappers.TitleAction.CLEAR).sendPacket(player);
    }
    
    public static void resetTitle(final Player player) {
        titlePacket(EnumWrappers.TitleAction.RESET).sendPacket(player);
    }
    
    public static void sendTimes(final Player player, final int fadeIn, final int stay, final int fadeOut) {
        titlePacket(EnumWrappers.TitleAction.TIMES, fadeIn, stay, fadeOut).sendPacket(player);
    }
    
    private static WrapperPlayServerTitle titlePacket(final EnumWrappers.TitleAction action, final String text, final int fadeIn, final int stay, final int fadeOut) {
        final WrapperPlayServerTitle packet = new WrapperPlayServerTitle();
        packet.setAction(action);
        packet.setTitle(WrappedChatComponent.fromText(text));
        packet.setFadeIn(fadeIn);
        packet.setFadeOut(fadeOut);
        packet.setStay(stay);
        return packet;
    }
    
    private static WrapperPlayServerTitle titlePacket(final EnumWrappers.TitleAction action, final int fadeIn, final int stay, final int fadeOut) {
        final WrapperPlayServerTitle packet = new WrapperPlayServerTitle();
        packet.setAction(action);
        packet.setFadeIn(fadeIn);
        packet.setFadeOut(fadeOut);
        packet.setStay(stay);
        return packet;
    }
    
    private static WrapperPlayServerTitle titlePacket(final EnumWrappers.TitleAction action) {
        final WrapperPlayServerTitle packet = new WrapperPlayServerTitle();
        packet.setAction(action);
        return packet;
    }
}
