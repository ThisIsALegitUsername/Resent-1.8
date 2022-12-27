package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;

public class Fullbright extends Mod {
    
    public static Fullbright INSTANCE = new Fullbright();

    public Fullbright(){
        super("FullBright", Category.MISC);
    }

    @Override
    public void onEnable() {
        if(mc.thePlayer != null && mc.theWorld != null && mc.gameSettings != null){
            mc.gameSettings.gammaSetting = 100;
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer != null && mc.theWorld != null && mc.gameSettings != null) {
            mc.gameSettings.gammaSetting = 1;
        }
    }
}
