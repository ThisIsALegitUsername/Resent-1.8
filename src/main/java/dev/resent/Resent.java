package dev.resent;

import dev.resent.module.base.ModManager;

public class Resent {

    static {
        INSTANCE = new Resent();
    }

    public static final String NAME = "Resent", VERSION = "3.5";
    public static final Resent INSTANCE;
    //private ISound uwu;
    public ModManager modManager;

    public void init() {
        Resent.INSTANCE.modManager = new ModManager();
    }

    /*public void playMusic(ResourceLocation loc){
        this.uwu = PositionedSoundRecord.create(loc);
        if(uwu != null){
            Minecraft.getMinecraft().getSoundHandler().stopSounds();
            Minecraft.getMinecraft().getSoundHandler().playSound(uwu);
        }
    }*/
    
}
