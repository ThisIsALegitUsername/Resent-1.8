package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.setting.BooleanSetting;
import dev.resent.setting.ModeSetting;

public class HUD extends Mod{
    public HUD(){
        super("Hud", Category.MISC, true);
        addSetting(theme, animated);
    }

    public static final ModeSetting theme = new ModeSetting("Theme", "", "Classic", "Light", "Dark", "Rainbow");
    public static final BooleanSetting animated = new BooleanSetting("Animated", "", true);

}
