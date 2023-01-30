package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;

public class HUD extends Mod{
    public HUD(){
        super("Theme", Category.MISC, true);
        addSetting(fontTheme, animationTheme, round);
    }

    public static final ModeSetting fontTheme = new ModeSetting("Font", "", "Classic", "Rainbow");
    public static final BooleanSetting round = new BooleanSetting("Rounded", "", true);
    public static final ModeSetting animationTheme = new ModeSetting("Animation", "", "Ease back in", "Ease in out quad", "Elastic", "Smooth step", "Decelerate");
    //public static final BooleanSetting animated = new BooleanSetting("Animated", "", true);

}
