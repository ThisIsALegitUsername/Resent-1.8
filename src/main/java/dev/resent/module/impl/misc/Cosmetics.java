package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.setting.BooleanSetting;
import dev.resent.setting.ModeSetting;

public class Cosmetics extends Mod{
    public Cosmetics(){
       super("Cosmetics", Category.MISC, true);
        addSetting(who, show, dragonwings, crystalwings, glasses, hat);
    }

    public static BooleanSetting show = new BooleanSetting("Show cosmetics", "", true);
    public static BooleanSetting crystalwings = new BooleanSetting("Crystal wings", "", false);
    public static BooleanSetting dragonwings = new BooleanSetting("Dragon wings", "", true);
    public static BooleanSetting hat = new BooleanSetting("Top hat", "", true);
    public static BooleanSetting glasses = new BooleanSetting("Glasses", "", true);
    public static ModeSetting who = new ModeSetting("Who to render on", "", "Only you", "Everyone", "Everyone else");
    
}
