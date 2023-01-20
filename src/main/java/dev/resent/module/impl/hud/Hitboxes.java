package dev.resent.module.impl.hud;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;

public class Hitboxes extends Mod {

    public Hitboxes() {
        super("Hitboxes", Category.HUD, true);
        addSetting(color, old);
    }

    public static ModeSetting color = new ModeSetting("Color", "", "White", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "Black");
    public static BooleanSetting old = new BooleanSetting("1.7 Hitboxes", "", true);
}
