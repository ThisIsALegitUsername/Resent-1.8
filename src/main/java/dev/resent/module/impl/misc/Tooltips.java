package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;

public class Tooltips extends Mod{
    public Tooltips() {
        super("Tooltips", Category.MISC);
    }

    @Override
    public void onEnable() {
        if(mc.theWorld != null)
        mc.gameSettings.advancedItemTooltips = true;
    }

    @Override
    public void onDisable() {
        if(mc.theWorld != null)
        mc.gameSettings.advancedItemTooltips = false;
    }
}
