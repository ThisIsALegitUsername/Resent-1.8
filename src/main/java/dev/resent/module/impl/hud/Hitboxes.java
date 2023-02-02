package dev.resent.module.impl.hud;

import dev.resent.annotation.Module;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;

@Module(name = "Hitboxes", category = Category.HUD, hasSetting = true)
public class Hitboxes extends Mod {

    public Hitboxes() { addSetting(color, old); }

    public static ModeSetting color = new ModeSetting("Color", "", "White", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "Black");
    public static BooleanSetting old = new BooleanSetting("1.7 Hitboxes", "", true);
}
