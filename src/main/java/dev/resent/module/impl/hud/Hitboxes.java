package dev.resent.module.impl.hud;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.setting.BooleanSetting;
import dev.resent.setting.ModeSetting;

public class Hitboxes extends Mod{
    public Hitboxes() {
        super("Hitboxes", Category.HUD, true);
        addSetting(color, old);
    }

    public static ModeSetting color = new ModeSetting("Color", "", "White", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "Black");
    public BooleanSetting old = new BooleanSetting("1.7 Hitboxes", "", true);

    public void onEnable(){
        mc.getRenderManager().setDebugBoundingBox(true); 
    }

    public void onDisable(){
        mc.getRenderManager().setDebugBoundingBox(false);
    }
}
