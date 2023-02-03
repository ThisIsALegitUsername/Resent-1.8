package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.Mod;

@Module(name = "Tooltips", category = Category.MISC)
public class Tooltips extends Mod {

    @Override
    public void onEnable() {
        if (mc.theWorld != null) mc.gameSettings.advancedItemTooltips = true;
    }

    @Override
    public void onDisable() {
        if (mc.theWorld != null) mc.gameSettings.advancedItemTooltips = false;
    }
}
