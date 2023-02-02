package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;

@Module(name = "Cosmetics", category = Category.MISC, hasSetting = true)
public class Cosmetics extends Mod{
    public Cosmetics(){ addSetting(who, show, halo, crystalwings, glasses, hat); }

    public static BooleanSetting show = new BooleanSetting("Show cosmetics", "", true);
    public static BooleanSetting crystalwings = new BooleanSetting("Crystal wings", "", false);
    public static BooleanSetting halo = new BooleanSetting("Halo", "", false);
    //public static BooleanSetting dragonwings = new BooleanSetting("Dragon wings", "", true);
    public static BooleanSetting hat = new BooleanSetting("Top hat", "", true);
    public static BooleanSetting glasses = new BooleanSetting("Glasses", "", true);
    public static ModeSetting who = new ModeSetting("Who to render on", "", "Only you", "Everyone", "Everyone else");
    
}
