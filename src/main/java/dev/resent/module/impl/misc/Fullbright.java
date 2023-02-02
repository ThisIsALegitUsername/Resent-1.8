package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;

@Module(name = "FullBright", category = Category.MISC)
public class Fullbright extends Mod {

    @Override
    public void onEnable() {
        if (mc.thePlayer != null && mc.theWorld != null && mc.gameSettings != null) {
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
