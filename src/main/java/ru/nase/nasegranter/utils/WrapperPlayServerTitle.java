package ru.nase.nasegranter.utils;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.*;

public class WrapperPlayServerTitle extends AbstractPacket
{
    public static final PacketType TYPE;
    
    static {
        TYPE = PacketType.Play.Server.TITLE;
    }
    
    public WrapperPlayServerTitle() {
        super(new PacketContainer(WrapperPlayServerTitle.TYPE), WrapperPlayServerTitle.TYPE);
        this.handle.getModifier().writeDefaults();
    }
    
    public WrapperPlayServerTitle(final PacketContainer packet) {
        super(packet, WrapperPlayServerTitle.TYPE);
    }
    
    public EnumWrappers.TitleAction getAction() {
        return (EnumWrappers.TitleAction)this.handle.getTitleActions().read(0);
    }
    
    public void setAction(final EnumWrappers.TitleAction value) {
        this.handle.getTitleActions().write(0, (EnumWrappers.TitleAction) value);
    }
    
    public WrappedChatComponent getTitle() {
        return (WrappedChatComponent)this.handle.getChatComponents().read(0);
    }
    
    public void setTitle(final WrappedChatComponent value) {
        this.handle.getChatComponents().write(0, (WrappedChatComponent) value);
    }
    
    public int getFadeIn() {
        return (int)this.handle.getIntegers().read(0);
    }
    
    public void setFadeIn(final int value) {
        this.handle.getIntegers().write(0, (Integer) value);
    }
    
    public int getStay() {
        return (int)this.handle.getIntegers().read(1);
    }
    
    public void setStay(final int value) {
        this.handle.getIntegers().write(1, (Integer) value);
    }
    
    public int getFadeOut() {
        return (int)this.handle.getIntegers().read(2);
    }
    
    public void setFadeOut(final int value) {
        this.handle.getIntegers().write(2, (Integer) value);
    }
}
