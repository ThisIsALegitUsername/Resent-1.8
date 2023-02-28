package dev.resent.client;

import dev.resent.module.base.ModManager;
import dev.resent.sound.SoundManager;

public class Resent {
	
    static {
        INSTANCE = new Resent();
    }

    public static final Resent INSTANCE;
    public SoundManager soundManager;
    public ModManager modManager;
    public SaveUtil saveUtil = new SaveUtil();

    public void init() {
        Resent.INSTANCE.modManager = new ModManager();
        Resent.INSTANCE.soundManager = new SoundManager();
    }

}
