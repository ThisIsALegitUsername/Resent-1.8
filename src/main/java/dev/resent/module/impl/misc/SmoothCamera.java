package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;

public class SmoothCamera extends Mod {
    public SmoothCamera() {
        super("SmoothCamera", Category.MISC);
    }

    @Override
    public void onEnable() {
        if (mc.theWorld != null)
            mc.gameSettings.smoothCamera = true;
    }

    @Override
    public void onDisable() {
        if (mc.theWorld != null)
            mc.gameSettings.smoothCamera = false;
    }
}
