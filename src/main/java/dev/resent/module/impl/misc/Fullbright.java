package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;

@Module(name = "FullBright", category = Category.MISC)
public class Fullbright extends Mod {

    public float previousGamma;

    @Override
    public void onEnable() {
        if (mc.thePlayer != null && mc.theWorld != null && mc.gameSettings != null) {
            //Resent.INSTANCE.soundManager.playAva();
            previousGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = 100;
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer != null && mc.theWorld != null && mc.gameSettings != null) {
            //Resent.INSTANCE.soundManager.stopMusic();
            mc.gameSettings.gammaSetting = previousGamma;
        }
    }
}
