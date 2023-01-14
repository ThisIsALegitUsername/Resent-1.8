package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.setting.ModeSetting;

public class Crosshair extends Mod {

    public Crosshair() {
        super("Crosshair", Category.MISC, true);
        addSetting(color);
    }

    public static ModeSetting color = new ModeSetting("Hovered crosshair color", "", "White", "Red", "Yellow", "Green", "Blue", "Black");
}
