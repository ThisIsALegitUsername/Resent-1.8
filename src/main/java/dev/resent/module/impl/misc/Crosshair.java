package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.module.setting.ModeSetting;

@Module(name = "Crosshair", category = Category.MISC, hasSetting = true)
public class Crosshair extends Mod {

    public Crosshair() { addSetting(color); }

    public static ModeSetting color = new ModeSetting("Hovered crosshair color", "", "White", "Red", "Yellow", "Green", "Blue", "Black");
}
