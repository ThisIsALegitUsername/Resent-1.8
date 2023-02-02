package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.module.setting.ModeSetting;

@Module(name = "Hotbar", category = Category.MISC, hasSetting = true)
public class HotbarAnimation extends Mod{
    
    public HotbarAnimation(){  addSetting(speed); }

    public static ModeSetting speed = new ModeSetting("Speed", "", "Slow", "Fast", "Normal");
    
    public int getSpeed(){
        if(speed.getValue() == "Fast")
            return 20;
        if(speed.getValue() == "Slow")
            return 5;
        return 12;
    }
}
